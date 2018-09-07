package com.lifemenu.eos_pocket_test.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ====================================
 * - Gson转换工具类
 * <p>
 * <p>
 * <p>
 * <p>
 * Created by GYX on 2016/10/27.
 */
public class Convert {
	private static Gson create() {
		return Convert.GsonHolder.gson;
	}

	private static class GsonHolder {
		private static Gson gson = new Gson();
	}

	/**
	 * json串解析成对象
	 */
	public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException {
		return create().fromJson(json, type);
	}

	/**
	 * json串解析成对象
	 */
	public static <T> T fromJson(String json, Type type) {
		return create().fromJson(json, type);
	}

	/**
	 * json串解析成对象
	 */
	public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
		return create().fromJson(reader, typeOfT);
	}

	/**
	 * json串解析成对象
	 */
	public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
		return create().fromJson(json, classOfT);
	}

	/**
	 * json串解析成对象
	 */
	public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
		return create().fromJson(json, typeOfT);
	}

	/**
	 * 转成list
	 * 解决泛型问题
	 *
	 * @param json
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> jsonToList(String json, Class<T> cls) {
		//异常处理
		try {
			if (json.equals("[]")) {
				return null;
			}
			Gson gson = new Gson();
			List<T> list = new ArrayList<T>();
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			for (JsonElement elem : array) {
				list.add(gson.fromJson(elem, cls));
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}
//	public static Map<String, CountryMoneyBean_2> toMap(String json) {
//		Map<String, CountryMoneyBean_2> map = create().fromJson(json, new TypeToken<Map<String, CountryMoneyBean_2>>() {}.getType());
//		return map;
//	}
//
//	public static Map<String, LegacyBean> toMapNormal(String json) {
//		Map<String,LegacyBean> citys = create().fromJson(json, new TypeToken<Map<String,LegacyBean >>() {}.getType());
//		return citys;
//	}
	/**
	 * 全球货币种类
	 *
	 * @param json
	 * @return
	 */
//	public static Map<String, GlobalBean> toGlobalMap(String json) {
//		TreeMap<String, GlobalBean> treeMap = new TreeMap<>();
//		JSONObject jsonObject = null;
//		try {
//			jsonObject = new JSONObject(json);
//			Iterator iterator = jsonObject.keys();  // 应用迭代器Iterator 获取所有的key值
//			while (iterator.hasNext()) { // 遍历每个key
//				String key = iterator.next() + "";
//				if ("refreshrate".equals(key) || "updatedAt".equals(key)) {
//					continue;
//				}
//				JSONObject jsb = jsonObject.getJSONObject(key);
//				GlobalBean ttt = new GlobalBean();
//				ttt.setLast(jsb.getString("last"));
//				ttt.setSymbol(jsb.getString("symbol"));
//				treeMap.put(key, ttt);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return treeMap;
//	}
	/**
	 * tokenAddress : 0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0
	 * tokenId : 6
	 * tokenSymbol : EOS
	 * balance : 0
	 * price : {"JPY":1213.2,"USD":11.03,"USDT":11.05,"CNY":76.05}
	 * decimals : 18
	 * tokenName : EOS
	 * tokenIcon :
	 */
	/**
	 * 解析币种
	 *
	 * @param json string
	 * @return map
	 */
	/*public static LinkedHashMap<String, HomeListBean.DataBean.EthTokensBean> toCoinType(String json) {


		if (TextUtils.isEmpty(json)) {
			return null;
		}
		LinkedHashMap<String, HomeListBean.DataBean.EthTokensBean> linkedHashMap = new LinkedHashMap<>();
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(json);
//			JSONObject data = (JSONObject) jsonObject.get("data");
//            JSONObject data_jsonObject = new JSONObject(data);
			Iterator iterator = jsonObject.keys();  // 应用迭代器Iterator 获取所有的key值
			while (iterator.hasNext()) { // 遍历每个key
				String key = (String) iterator.next();
				JSONObject jsb = jsonObject.getJSONObject(key);
				HomeListBean.DataBean.EthTokensBean tokensBean = new HomeListBean.DataBean.EthTokensBean();
				HomeListBean.DataBean.EthTokensBean.PriceBeanX priceBeanX = new HomeListBean.DataBean.EthTokensBean.PriceBeanX();

				JSONObject priceObj = jsb.getJSONObject("price");
				if (null!=priceObj) {
					priceBeanX.setCNY(Double.valueOf(priceObj.getString("CNY")));
					priceBeanX.setJPY(Double.valueOf(priceObj.getString("JPY")));
					priceBeanX.setUSD(Double.valueOf(priceObj.getString("USD")));
					priceBeanX.setUSDT(Double.valueOf(priceObj.getString("USDT")));
					tokensBean.setPrice(priceBeanX);
				}

				tokensBean.setTokenId(jsb.getString("tokenId"));
				tokensBean.setBalance(jsb.getString("balance"));
				tokensBean.setTokenName(jsb.getString("tokenName"));
				tokensBean.setTokenSymbol(jsb.getString("tokenSymbol"));
				tokensBean.setTokenIcon(jsb.getString("tokenIcon"));
				tokensBean.setTokenAddress(jsb.getString("tokenAddress"));
				tokensBean.setDecimals(jsb.getString("decimals"));
				tokensBean.setChoosed(jsb.getBoolean("isChoosed"));
				linkedHashMap.put(key, tokensBean);

			}
			return linkedHashMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return linkedHashMap;
	}*/

	/**
	 * 解析成json串
	 *
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return create().toJson(src);
	}

	/**
	 * 解析成json串
	 *
	 * @param src
	 * @return
	 */
	public static String toJson(Object src, Type typeOfSrc) {
		return create().toJson(src, typeOfSrc);
	}
}
