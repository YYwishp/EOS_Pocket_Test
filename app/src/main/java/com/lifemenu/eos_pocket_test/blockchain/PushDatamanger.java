package com.lifemenu.eos_pocket_test.blockchain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lifemenu.eos_pocket_test.blockchain.api.EosChainInfo;
import com.lifemenu.eos_pocket_test.blockchain.bean.JsonToBeanResultBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.JsonToBinRequest;
import com.lifemenu.eos_pocket_test.blockchain.bean.PushTxSuccessBean;
import com.lifemenu.eos_pocket_test.blockchain.chain.Action;
import com.lifemenu.eos_pocket_test.blockchain.chain.PackedTransaction;
import com.lifemenu.eos_pocket_test.blockchain.chain.SignedTransaction;
import com.lifemenu.eos_pocket_test.blockchain.cypto.ec.EosPrivateKey;
import com.lifemenu.eos_pocket_test.blockchain.types.TypeChainId;
import com.lifemenu.eos_pocket_test.blockchain.util.GsonEosTypeAdapterFactory;
import com.lifemenu.eos_pocket_test.callback.OnResponseListener;
import com.lifemenu.eos_pocket_test.utils.RequestUtils;
import com.lifemenu.eos_pocket_test.utils.SPUtils;

import java.util.ArrayList;

public class PushDatamanger {
	public final static String PERMISSONION = Constants.PERMISSONION;
	String contract, action, message, userpassword;
	String[] permissions;
	Gson mGson = new GsonBuilder()
			.registerTypeAdapterFactory(new GsonEosTypeAdapterFactory())
			.excludeFieldsWithoutExposeAnnotation().create();
	private Callback mCallback;
	private EosChainInfo mChainInfo;
	private SignedTransaction txnBeforeSign;

	public PushDatamanger(Callback callback) {
		this.mCallback = callback;
	}

	/**
	 * --------1
	 *
	 * @param contract
	 * @param action
	 * @param message
	 * @param permissionAccount
	 */
	public void pushAction(String contract, String action, String message, String permissionAccount) {
		this.message = message;
		this.contract = contract;
		this.action = action;
		permissions = new String[]{permissionAccount + "@" + PERMISSONION};
		//---------2
		getChainInfo();
	}

	/**
	 * --------2
	 */
	public void getChainInfo() {
		RequestUtils.getInstance().rpcGetInfo(new OnResponseListener<EosChainInfo>() {
			@Override
			public void onSuccess(EosChainInfo eosChainInfo) {
				if (eosChainInfo != null) {
					mChainInfo = eosChainInfo;
					//----------3
					getabi_json_to_bin();
				}
			}

			@Override
			public void onFailed(Exception e) {
			}
		});
	}

	/**
	 * -------3
	 */
	private void getabi_json_to_bin() {
		JsonToBinRequest jsonToBinRequest = new JsonToBinRequest(contract, action, message.replaceAll("\\r|\\n", ""));
		RequestUtils.getInstance().rpcAbiJsonToBin(mGson.toJson(jsonToBinRequest), new OnResponseListener<JsonToBeanResultBean>() {
			@Override
			public void onSuccess(JsonToBeanResultBean bean) {
				if (bean != null) {
					//创建交易-----4
					txnBeforeSign = createTransaction(contract, action, bean.getBinargs(), permissions, mChainInfo);
					//私钥
					String activePrivatekey = (String) SPUtils.get("active_private_key", "null");
					EosPrivateKey eosPrivateKey = new EosPrivateKey(activePrivatekey);
					//签名
					txnBeforeSign.sign(eosPrivateKey, new TypeChainId(mChainInfo.getChain_id()));
					//--------5
					pushTransactionRetJson(new PackedTransaction(txnBeforeSign));
				}
			}

			@Override
			public void onFailed(Exception e) {
			}
		});
	}

	/**
	 * -------4 创建交易
	 *
	 * @param contract
	 * @param actionName
	 * @param dataAsHex
	 * @param permissions
	 * @param chainInfo
	 * @return
	 */
	private SignedTransaction createTransaction(String contract, String actionName, String dataAsHex,
	                                            String[] permissions, EosChainInfo chainInfo) {
		Action action = new Action(contract, actionName);
		action.setAuthorization(permissions);
		action.setData(dataAsHex);
		//签名交易
		SignedTransaction txn = new SignedTransaction();
		txn.addAction(action);
		//设置签名
		txn.putSignatures(new ArrayList<String>());
		if (null != chainInfo) {
			txn.setReferenceBlock(chainInfo.getHeadBlockId());
			//失效时间30秒
			txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(30000));
		}
		return txn;
	}

	/**
	 * ----------5
	 * @param packedTransaction
	 */
	private void pushTransactionRetJson(PackedTransaction packedTransaction) {





		RequestUtils.getInstance().rpcPushTransaction(mGson.toJson(packedTransaction), new OnResponseListener<PushTxSuccessBean>() {
			@Override
			public void onSuccess(PushTxSuccessBean pushTxSuccessBean) {
				if (pushTxSuccessBean != null) {
					mCallback.getResult(pushTxSuccessBean);
				} else {
					mCallback.getResult(null);
				}
			}

			@Override
			public void onFailed(Exception e) {



			}
		});
	}

	public interface Callback {
		void getResult(PushTxSuccessBean result);
	}
}
















































