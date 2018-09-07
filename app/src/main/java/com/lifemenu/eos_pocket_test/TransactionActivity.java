package com.lifemenu.eos_pocket_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lifemenu.eos_pocket_test.bean.TransferEosMessageBean;
import com.lifemenu.eos_pocket_test.blockchain.EosDataManger;
import com.lifemenu.eos_pocket_test.blockchain.bean.PushTxSuccessBean;
import com.lifemenu.eos_pocket_test.utils.Convert;
import com.lifemenu.eos_pocket_test.utils.SPUtils;
import com.lifemenu.eos_pocket_test.utils.StringUtils;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {


	private EditText mTvReceiveAccount;
	private EditText mEdAmount;
	private TextView mTvBalance;
	private EditText mEdMemo;
	private Button mBtGoTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);
		initView();
	}

	private void initView() {

		mTvReceiveAccount = (EditText) findViewById(R.id.tv_receive_account);
		mEdAmount = (EditText) findViewById(R.id.ed_amount);
		mTvBalance = (TextView) findViewById(R.id.tv_balance);
		mEdMemo = (EditText) findViewById(R.id.ed_memo);
		mBtGoTransaction = (Button) findViewById(R.id.bt_go_transaction);

		mBtGoTransaction.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		EosDataManger manager = new EosDataManger(new EosDataManger.Callback() {
			@Override
			public void getResult(PushTxSuccessBean result) {

			}
		});
		String memo = mEdMemo.getText().toString();
		String receiveAccount = mTvReceiveAccount.getText().toString();
		String quantity = StringUtils.addZero(mEdAmount.getText().toString().trim());
		String account = (String) SPUtils.get("account", "null");
		String json = Convert.toJson(new TransferEosMessageBean( memo ,receiveAccount, quantity+" EOS", account));
		manager.pushAction(json,account);

	}
}
