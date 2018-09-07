package com.lifemenu.eos_pocket_test.utils;


import com.lifemenu.eos_pocket_test.bean.AccountBean;
import com.lifemenu.eos_pocket_test.blockchain.api.EosChainInfo;
import com.lifemenu.eos_pocket_test.blockchain.bean.JsonToBeanResultBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.PushTxSuccessBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.TableResultBean;
import com.lifemenu.eos_pocket_test.callback.DialogCallback;
import com.lifemenu.eos_pocket_test.callback.OnResponseListener;
import com.lifemenu.eos_pocket_test.url.Urls;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gyx on 2017/10/11.
 */
public class RequestUtils {
	private static RequestUtils instance;

	private RequestUtils() {
	}

	/**
	 * 单一实例
	 */
	public static RequestUtils getInstance() {
		if (instance == null) {
			instance = new RequestUtils();
		}
		return instance;
	}

	/**
	 * 获取账户信息
	 * @param account
	 * @param onResponseListener
	 */
	public void rpcGetAccount( String account,final OnResponseListener<AccountBean> onResponseListener) {


		HashMap<String, String> params = new HashMap<>();
		params.put("account_name", account);

		JSONObject jsonObject = new JSONObject(params);

		//请求网络
		OkGo.post(Urls.GET_ACCOUNT)//
				.tag(this)//
				.upJson(jsonObject.toString())
				.execute(new DialogCallback<AccountBean>() {
					@Override
					public void onSuccess(AccountBean bean, Call call, Response response) {
						onResponseListener.onSuccess(bean);

					}
					@Override
					public AccountBean convertSuccess(Response response) throws Exception {
						String string = response.body().string();
						//
						AccountBean bean = Convert.fromJson(string, AccountBean.class);
						return bean;
					}

					@Override
					public void onError(Call call, Response response, Exception e) {

						super.onError(call, response, e);
						onResponseListener.onFailed(e);
					}
				});
	}

	/**
	 * rpc——get_info
	 * @param onResponseListener
	 */
	public void rpcGetInfo( final OnResponseListener<EosChainInfo> onResponseListener) {

		//请求网络
		OkGo.get(Urls.GET_INFO)//
				.tag(this)//
				.execute(new DialogCallback<EosChainInfo>() {
					@Override
					public void onSuccess(EosChainInfo bean, Call call, Response response) {
						onResponseListener.onSuccess(bean);

					}
					@Override
					public EosChainInfo convertSuccess(Response response) throws Exception {
						String string = response.body().string();
						//
						EosChainInfo bean = Convert.fromJson(string, EosChainInfo.class);
						return bean;
					}

					@Override
					public void onError(Call call, Response response, Exception e) {

						super.onError(call, response, e);
						onResponseListener.onFailed(e);
					}
				});
	}





	public void rpcAbiJsonToBin(String json,final OnResponseListener<JsonToBeanResultBean> onResponseListener) {

		//请求网络
		OkGo.post(Urls.ABI_JSON_TO_BIN)//
				.tag(this)//
				.upJson(json)
				.execute(new DialogCallback<JsonToBeanResultBean>() {
					@Override
					public void onSuccess(JsonToBeanResultBean bean, Call call, Response response) {
						onResponseListener.onSuccess(bean);

					}
					@Override
					public JsonToBeanResultBean convertSuccess(Response response) throws Exception {
						String string = response.body().string();
						//
						JsonToBeanResultBean bean = Convert.fromJson(string, JsonToBeanResultBean.class);
						return bean;
					}

					@Override
					public void onError(Call call, Response response, Exception e) {

						super.onError(call, response, e);
						onResponseListener.onFailed(e);
					}
				});
	}




	public void rpcPushTransaction(String json,final OnResponseListener<PushTxSuccessBean> onResponseListener) {

		//请求网络
		OkGo.post(Urls.PUSH_TRANSACTION)//
				.tag(this)//
				.upJson(json)
				.execute(new DialogCallback<PushTxSuccessBean>() {
					@Override
					public void onSuccess(PushTxSuccessBean bean, Call call, Response response) {
						onResponseListener.onSuccess(bean);

					}
					@Override
					public PushTxSuccessBean convertSuccess(Response response) throws Exception {
						String string = response.body().string();
						//
						PushTxSuccessBean bean = Convert.fromJson(string, PushTxSuccessBean.class);
						return bean;
					}

					@Override
					public void onError(Call call, Response response, Exception e) {

						super.onError(call, response, e);
						onResponseListener.onFailed(e);
					}
				});
	}





	public void rpcGetTableRows(final OnResponseListener<TableResultBean> onResponseListener) {
		String json = "{\"json\":true,\"code\":\"eosio\",\"scope\":\"eosio\",\"table\":\"rammarket\",\"table_key\":\"\",\"lower_bound\":\"\",\"upper_bound\":\"\",\"limit\":10}";
		//请求网络
		OkGo.post(Urls.GET_TABLE_ROWS)//
				.tag(this)//
				.upJson(json)
				.execute(new DialogCallback<TableResultBean>() {
					@Override
					public void onSuccess(TableResultBean bean, Call call, Response response) {
						onResponseListener.onSuccess(bean);

					}
					@Override
					public TableResultBean convertSuccess(Response response) throws Exception {
						String string = response.body().string();
						//
						TableResultBean bean = Convert.fromJson(string, TableResultBean.class);
						return bean;
					}

					@Override
					public void onError(Call call, Response response, Exception e) {

						super.onError(call, response, e);
						onResponseListener.onFailed(e);
					}
				});
	}












}



























