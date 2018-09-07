package com.lifemenu.eos_pocket_test.callback;

/**
 * Created by gyx on 2017/10/11.
 */
public interface OnResponseListener<T>  {
	void onSuccess(T t);

	void onFailed(Exception e);



}