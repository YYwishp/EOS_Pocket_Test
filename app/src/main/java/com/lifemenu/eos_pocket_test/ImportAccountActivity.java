package com.lifemenu.eos_pocket_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lifemenu.eos_pocket_test.bean.AccountBean;
import com.lifemenu.eos_pocket_test.blockchain.cypto.ec.EosPrivateKey;
import com.lifemenu.eos_pocket_test.blockchain.cypto.ec.EosPublicKey;
import com.lifemenu.eos_pocket_test.callback.OnResponseListener;
import com.lifemenu.eos_pocket_test.utils.RequestUtils;
import com.lifemenu.eos_pocket_test.utils.SPUtils;
import com.lifemenu.eos_pocket_test.utils.ToastUtil;
import com.quincysx.crypto.CoinTypes;
import com.quincysx.crypto.ECKeyPair;
import com.quincysx.crypto.bip32.ExtendedKey;
import com.quincysx.crypto.bip32.ValidationException;
import com.quincysx.crypto.bip39.SeedCalculator;
import com.quincysx.crypto.bip44.AddressIndex;
import com.quincysx.crypto.bip44.BIP44;
import com.quincysx.crypto.bip44.CoinPairDerive;

import java.util.Arrays;
import java.util.List;

public class ImportAccountActivity extends AppCompatActivity {
	private EditText mTvAccount;
	private EditText mTvOwner;
	private EditText mTvActive;
	private String accountOwnerKey;
	private String accountActiveKey;
	private EditText mEdAccount;
	private EditText mEdMnemonicCode;
	private Button mBtMnemonicImport;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_import_account);
		//
		mTvAccount = (EditText) findViewById(R.id.tv_account);
		mTvOwner = (EditText) findViewById(R.id.tv_owner);
		mTvActive = (EditText) findViewById(R.id.tv_active);
		//


		mEdAccount = (EditText) findViewById(R.id.ed_account);
		mEdMnemonicCode = (EditText) findViewById(R.id.ed_mnemonic_code);

	}

	public void import_Account(View view) {
		String account = mTvAccount.getText().toString().trim();
		final String owner = mTvOwner.getText().toString().trim();
		final String active = mTvActive.getText().toString().trim();
		RequestUtils.getInstance().rpcGetAccount(account, new OnResponseListener<AccountBean>() {
			@Override
			public void onSuccess(AccountBean bean) {
				if (bean == null) {
					return;
				}
				for (int i = 0; i < bean.getPermissions().size(); i++) {
					if (bean.getPermissions().get(i).getPerm_name().equals("owner")) {
						accountOwnerKey = bean.getPermissions().get(i).getRequired_auth().getKeys().get(0).getKey();
					} else {
						accountActiveKey = bean.getPermissions().get(i).getRequired_auth().getKeys().get(0).getKey();
					}
				}
				EosPrivateKey eosOwnPrivateKey = new EosPrivateKey(owner);
				EosPrivateKey eosActivePrivateKey = new EosPrivateKey(active);
				String ownerPublicKey = eosOwnPrivateKey.getPublicKey().toString();
				String activePublicKey = eosActivePrivateKey.getPublicKey().toString();

				//是否与本地相同
				if (ownerPublicKey.equals(accountOwnerKey) && activePublicKey.equals(accountActiveKey)) {
					ToastUtil.toast("导入成功");


					SPUtils.put("account", bean.getAccount_name());
					SPUtils.put("owner_private_key", owner);
					SPUtils.put("owner_public_key", ownerPublicKey);
					SPUtils.put("active_private_key", active);
					SPUtils.put("active_public_key", activePublicKey);



				} else {
					ToastUtil.toast("请检查私钥");
				}
			}

			@Override
			public void onFailed(Exception e) {
			}
		});
	}

	public void mnemonicImport(View view) {
		String mnemonicWords = mEdMnemonicCode.getText().toString().trim();
		String account = mEdAccount.getText().toString().trim();
		String[] split = mnemonicWords.split("\\s+");
		List<String> strings = Arrays.asList(split);
		byte[] seed = new SeedCalculator().calculateSeed(strings, "");
		try {
			ExtendedKey extendedKey = ExtendedKey.create(seed);
			AddressIndex address = BIP44.m().purpose44()
					.coinType(CoinTypes.EOS)
					.account(0)
					.external()
					.address(0);
			CoinPairDerive coinPairDerive = new CoinPairDerive(extendedKey);
			ECKeyPair ecKeyPair = coinPairDerive.derive(address);
			Log.e("=1221=", "==" + address.toString());
			Log.e("=1221private", ecKeyPair.getPrivateKey());
			Log.e("=1221public=", ecKeyPair.getPublicKey());
			Log.e("=1221address=", ecKeyPair.getAddress());







		} catch (ValidationException e) {
			e.printStackTrace();
		}
	}
}











































