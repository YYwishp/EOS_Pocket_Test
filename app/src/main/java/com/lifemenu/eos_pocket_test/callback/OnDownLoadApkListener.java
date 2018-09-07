package com.lifemenu.eos_pocket_test.callback;

/**
 * Created by gyx on 2017/10/11.
 */
public interface OnDownLoadApkListener<T>  {
	void onSuccess(T t);

	void onFailed(Exception e);

	void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed);

}