package com.lifemenu.eos_pocket_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lifemenu.eos_pocket_test.blockchain.cypto.ec.EosPrivateKey;
import com.lifemenu.eos_pocket_test.utils.SPUtils;

public class MainActivity extends AppCompatActivity {
	private TextView mTvOwnerPrivateKey;
	private TextView mTvOwnerPublicKey;
	private TextView mTvActivePrivateKey;
	private TextView mTvActivePublicKey;
	private TextView mTvAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTvAccount = (TextView) findViewById(R.id.tv_account);
		mTvOwnerPrivateKey = (TextView) findViewById(R.id.tv_owner_private_key);
		mTvOwnerPublicKey = (TextView) findViewById(R.id.tv_owner_public_key);
		mTvActivePrivateKey = (TextView) findViewById(R.id.tv_active_private_key);
		mTvActivePublicKey = (TextView) findViewById(R.id.tv_active_public_key);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String account = (String) SPUtils.get("account", "null");
		String ownerPrivateKey = (String) SPUtils.get("owner_private_key", "null");
		String ownerPublicKey = (String) SPUtils.get("owner_public_key", "null");
		String activePrivatekey = (String) SPUtils.get("active_private_key", "null");
		String activePublicKey = (String) SPUtils.get("active_public_key", "null");
		//
		mTvAccount.setText(account);
		mTvOwnerPrivateKey.setText(ownerPrivateKey);
		mTvOwnerPublicKey.setText(ownerPublicKey);
		mTvActivePrivateKey.setText(activePrivatekey);
		mTvActivePublicKey.setText(activePublicKey);
		//
		Log.e("==OwnerPublic==", ownerPublicKey);
	}

	public void importAccount(View view) {
		startActivity(new Intent(this, ImportAccountActivity.class));
	}

	/**
	 * 交易
	 * @param view
	 */
	public void transaction(View view) {
		startActivity(new Intent(this, TransactionActivity.class));
	}

	public void checkAccountInfo(View view) {
//		EosPrivateKey eosPrivateKey = new EosPrivateKey();
//		BigInteger asBigInteger = eosPrivateKey.getAsBigInteger();
//		String privateKey = eosPrivateKey.toString();
//		String publicKey = eosPrivateKey.getPublicKey().toString();
//		Log.e("asBigInteger", asBigInteger+"");
//		Log.e("privateKey", privateKey );
//		Log.e("publicKey", publicKey );
		//
		EosPrivateKey eosPrivateKey1 = new EosPrivateKey("5JKsGbs4iCPDzffzp9BgRQkAkQYceV67SfbJFGhG57RGoVkGpuv");
		Log.e("privateKey", eosPrivateKey1.toString());
		Log.e("publicKey", eosPrivateKey1.getPublicKey().toString());
		//
	}


	public void buyOrSaleRam(View view) {
		Intent intent = new Intent(this, BuyOrSaleRamActivity.class);
		startActivity(intent);
	}
}

























