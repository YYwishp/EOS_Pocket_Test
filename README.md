# EOS 钱包测试
```
包含从助记词导入，私钥导入，交易转账，RAM买卖，NET，CPU抵押赎回
```

## 助记词，私钥导入


```
详情见
eos_pocket_test/ImportAccountActivity.java
```


## 交易转账，RAM买卖，NET，CPU抵押赎回
```
交易转账详情见：
eos_pocket_test/TransactionActivity.java


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



```

## RAM买卖
```
详情见：
eos_pocket_test/BuyOrSaleRamActivity.java


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



```

## NET，CPU抵押赎回
```
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
```