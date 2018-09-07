package com.lifemenu.eos_pocket_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lifemenu.eos_pocket_test.bean.AccountBean;
import com.lifemenu.eos_pocket_test.blockchain.PushDatamanger;
import com.lifemenu.eos_pocket_test.blockchain.bean.BuyRamBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.PushTxSuccessBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.SealRamBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.StakeResourceBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.TableResultBean;
import com.lifemenu.eos_pocket_test.blockchain.bean.UnDelegateBean;
import com.lifemenu.eos_pocket_test.callback.OnResponseListener;
import com.lifemenu.eos_pocket_test.utils.RequestUtils;
import com.lifemenu.eos_pocket_test.utils.SPUtils;
import com.lifemenu.eos_pocket_test.utils.ToastUtil;

import java.math.BigDecimal;

public class BuyOrSaleRamActivity extends AppCompatActivity implements View.OnClickListener {
	private TextView mTvTotalBytes;
	private TextView mTvRamUsage;
	private TextView mTvPrice;
	private EditText mEdBuyForSomebody;
	private EditText mEdBuyAmountBytes;
	private EditText mEdBuyAmountEos;
	private Button mBtBuy;
	private EditText mEdSaleAmountEos;
	private Button mBtSale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_or_sale_ram);
		initView();
	}

	private void initView() {
		mTvTotalBytes = (TextView) findViewById(R.id.tv_total_bytes);
		mTvRamUsage = (TextView) findViewById(R.id.tv_ram_usage);
		mTvPrice = (TextView) findViewById(R.id.tv_price);//市场价格
		//
		mEdBuyForSomebody = (EditText) findViewById(R.id.ed_buy_for_somebody);
		mEdBuyAmountBytes = (EditText) findViewById(R.id.ed_buy_amount_bytes);
		mEdBuyAmountEos = (EditText) findViewById(R.id.ed_buy_amount_eos);
		mBtBuy = (Button) findViewById(R.id.bt_buy);
		//
		mEdSaleAmountEos = (EditText) findViewById(R.id.ed_sale_amount_eos);
		mBtSale = (Button) findViewById(R.id.bt_sale);
		//
		mBtBuy.setOnClickListener(this);
		mBtSale.setOnClickListener(this);
		requestTable();

		//
		mEdBuyAmountBytes.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String s1 = s.toString();
				if (!TextUtils.isEmpty(s1)) {
					String price = mTvPrice.getText().toString().trim();
					BigDecimal multiply = new BigDecimal(s1).multiply(new BigDecimal(price));
					String s2 = multiply.setScale(4,BigDecimal.ROUND_DOWN).toPlainString();
					mEdBuyAmountEos.setText(s2);
				}



			}
		});



	}

	private void requestTable() {
		RequestUtils.getInstance().rpcGetTableRows(new OnResponseListener<TableResultBean>() {
			@Override
			public void onSuccess(TableResultBean tableResultBean) {
				if (tableResultBean==null) {
					return;
				}
				String balanceEos = tableResultBean.getRows().get(0).getQuote().getBalance();
				String finalBalanceEos = balanceEos.substring(0, balanceEos.length() - 4);
				String balanceRam = tableResultBean.getRows().get(0).getBase().getBalance();
				String finalBalanceRam = balanceRam.substring(0, balanceRam.length() - 4);
				BigDecimal decimalPrice = new BigDecimal(finalBalanceEos).divide(new BigDecimal(finalBalanceRam), 8, BigDecimal.ROUND_HALF_UP);

				mTvPrice.setText(decimalPrice.toPlainString());//价格




			}

			@Override
			public void onFailed(Exception e) {
			}
		});



		String account = (String) SPUtils.get("account", "null");
		RequestUtils.getInstance().rpcGetAccount(account, new OnResponseListener<AccountBean>() {
			@Override
			public void onSuccess(AccountBean bean) {
				if (bean == null) {
					return;
				}
				AccountBean.TotalResourcesBean total_resources = bean.getTotal_resources();
				int ram_bytes = total_resources.getRam_bytes();
				mTvTotalBytes.setText(String.valueOf(ram_bytes));//total ram bytes
				mTvRamUsage.setText(String.valueOf(bean.getRam_usage()));



			}

			@Override
			public void onFailed(Exception e) {
			}
		});



	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bt_buy:


				//=========================买ram===================================
				String trim = mEdBuyForSomebody.getText().toString().trim();
				String account = (String) SPUtils.get("account", "null");
				BuyRamBean buyRamBean = new BuyRamBean();
				buyRamBean.setPayer(account);//付款方
				buyRamBean.setReceiver(trim);//接收方
				buyRamBean.setQuant(mEdBuyAmountEos.getText().toString()+" EOS");//数量，保留4位小数
				new PushDatamanger(new PushDatamanger.Callback() {
					@Override
					public void getResult(PushTxSuccessBean result) {
						if (result!=null) {
							ToastUtil.toast(result.getTransaction_id());


						} else {

							ToastUtil.toast("失败");

						}
					}
				}).pushAction("eosio","buyram", new Gson().toJson(buyRamBean),account);
				break;
			case R.id.bt_sale:
				//============================卖RAM=======================================
				String account_sale = (String) SPUtils.get("account", "null");
				String saleBytesAmount = mEdSaleAmountEos.getText().toString().trim();
				SealRamBean sealRamBean = new SealRamBean();
				sealRamBean.setAccount(account_sale);//账户名字
//				sealRamBean.setAccount("useraaaaaaac");//账户名字
				sealRamBean.setBytes(new BigDecimal(saleBytesAmount).intValue());
				new PushDatamanger(new PushDatamanger.Callback() {
					@Override
					public void getResult(PushTxSuccessBean result) {
						if (result!=null) {
							ToastUtil.toast(result.getTransaction_id());


						} else {

							ToastUtil.toast("失败");

						}
					}
				}).pushAction("eosio","sellram", new Gson().toJson(sealRamBean),account_sale);
				//==============================抵押CPU，NET======================================
				String account_stake = (String) SPUtils.get("account", "null");
				StakeResourceBean stakeResourceBean = new StakeResourceBean();
				stakeResourceBean.setFrom(account_stake);
				stakeResourceBean.setReceiver("useraaaaaaac");
				stakeResourceBean.setStake_cpu_quantity("1.0000 EOS");
				stakeResourceBean.setStake_net_quantity("1.0000 EOS");
				stakeResourceBean.setTransfer("0");//抵押的钱，0代表自己所有(我借给你，回头我还能赎回）1：代表抵押的钱送给对方了，不能赎回了

				new PushDatamanger(new PushDatamanger.Callback() {
					@Override
					public void getResult(PushTxSuccessBean result) {
						if (result!=null) {
							ToastUtil.toast(result.getTransaction_id());


						} else {

							ToastUtil.toast("失败");

						}


					}
				}).pushAction("eosio","delegatebw",new Gson().toJson(stakeResourceBean),account_stake);

				//============================赎回CPU，NET=======================================
				String account_unstake = (String) SPUtils.get("account", "null");
				UnDelegateBean unDelegateBean = new UnDelegateBean();

				unDelegateBean.setFrom(account_unstake);
				unDelegateBean.setReceiver("useraaaaaaac");
				unDelegateBean.setUnstake_cpu_quantity("1.0000 EOS");
				unDelegateBean.setUnstake_net_quantity("1.0000 EOS");
				new PushDatamanger(new PushDatamanger.Callback() {
					@Override
					public void getResult(PushTxSuccessBean result) {




					}
				}).pushAction("eosio", "undelegatebw", new Gson().toJson(unDelegateBean), account_unstake);


				break;
		}
	}
}
































































