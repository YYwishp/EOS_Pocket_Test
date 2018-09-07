package com.lifemenu.eos_pocket_test.blockchain;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lifemenu.eos_pocket_test.bean.TransferEosMessageBean;
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
import com.lifemenu.eos_pocket_test.ese.Ese;
import com.lifemenu.eos_pocket_test.utils.Convert;
import com.lifemenu.eos_pocket_test.utils.RequestUtils;
import com.lifemenu.eos_pocket_test.utils.SPUtils;
import com.lifemenu.eos_pocket_test.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pocketEos on 2018/5/2.
 * eosX适配
 */

public class EosDataManger {
    static String EOSCONTRACT = Constants.EOSCONTRACT;
    static String OCTCONTRACT =  Constants.OCTCONTRACT;//erctoken
    static String ACTIONTRANSFER = Constants.ACTIONTRANSFER;
    static String PERMISSONION = Constants.PERMISSONION;
    private String message, contract, action;
    SignedTransaction txnBeforeSign;

    private String[] permissions;
    private EosChainInfo mChainInfoBean;
    Gson mGson = new GsonBuilder()
            .registerTypeAdapterFactory(new GsonEosTypeAdapterFactory())
            .excludeFieldsWithoutExposeAnnotation().create();
    private Callback mCallback;
    public EosDataManger(Callback callback) {
        mCallback = callback;
    }
    public interface Callback {
        void getResult(PushTxSuccessBean result);
    }
    /**
     * --------1
     * @param message
     * @param permissionAccount
     */
    public void pushAction(String message, String permissionAccount) {
        this.message = message;
//        if (message.contains("EOS")) {
//            this.contract = EOSCONTRACT;
//        } else {
//            this.contract = OCTCONTRACT;
//        }

        this.contract = EOSCONTRACT;
        this.action = ACTIONTRANSFER;

        permissions = new String[]{permissionAccount + "@" + PERMISSONION};

        //-------------2
        getChainInfo();
    }

    /**
     * ---------2
     */
    private void getChainInfo() {
        RequestUtils.getInstance().rpcGetInfo(new OnResponseListener<EosChainInfo>() {
            @Override
            public void onSuccess(EosChainInfo bean) {
                if (bean != null) {
                    mChainInfoBean = bean;
                    //---------3
                    getabi_json_to_bin();
                }
            }

            @Override
            public void onFailed(Exception e) {
            }
        });


    }

    /**
     * json_to_bin
     * ------3
     */
    private void getabi_json_to_bin() {
        JsonToBinRequest jsonToBinRequest = new JsonToBinRequest(contract, action, message.replaceAll("\\r|\\n", ""));
        String json = mGson.toJson(jsonToBinRequest);
        RequestUtils.getInstance().rpcAbiJsonToBin(json, new OnResponseListener<JsonToBeanResultBean>() {
            @Override
            public void onSuccess(JsonToBeanResultBean bean) {
                if (bean!=null) {
                    //------4




                    txnBeforeSign = createTransaction(contract, action, bean.getBinargs(), permissions, mChainInfoBean);
                    String activePrivatekey = (String) SPUtils.get("active_private_key", "null");
                    EosPrivateKey eosPrivateKey = new EosPrivateKey(activePrivatekey);
                    //签名
                    txnBeforeSign.sign(eosPrivateKey, new TypeChainId(mChainInfoBean.getChain_id()));
                    //广播出去----5
                    pushTransactionRetJson(new PackedTransaction(txnBeforeSign));
                }


            }

            @Override
            public void onFailed(Exception e) {
            }
        });
//        TransferEosMessageBean transferEosMessageBean = Convert.fromJson(message, TransferEosMessageBean.class);
//        String from = transferEosMessageBean.getFrom();
//        String memo = transferEosMessageBean.getMemo();
//        String quantity = transferEosMessageBean.getQuantity();
//        String to = transferEosMessageBean.getTo();
//        String data = Ese.parseTransferData(from, to, quantity, memo);
//        txnBeforeSign = createTransaction(contract, action, data, permissions, mChainInfoBean);
//        String activePrivatekey = (String) SPUtils.get("active_private_key", "null");
//        EosPrivateKey eosPrivateKey = new EosPrivateKey(activePrivatekey);
//        //签名
//        txnBeforeSign.sign(eosPrivateKey, new TypeChainId(mChainInfoBean.getChain_id()));
//        //广播出去----5
//        pushTransactionRetJson(new PackedTransaction(txnBeforeSign));
    }

    /**
     * --------5
     * @param packedTransaction
     */
    private void pushTransactionRetJson(PackedTransaction packedTransaction) {
        String json = mGson.toJson(packedTransaction);
        Log.e("最后的发送的参数", json);
        RequestUtils.getInstance().rpcPushTransaction(json, new OnResponseListener<PushTxSuccessBean>() {
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

    /**
     *   创建交易 ------4
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


        SignedTransaction txn = new SignedTransaction();
        txn.addAction(action);
        txn.putSignatures(new ArrayList<String>());


        if (null != chainInfo) {
            txn.setReferenceBlock(chainInfo.getHeadBlockId());
            txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(30000));
        }
        return txn;
    }
}










































