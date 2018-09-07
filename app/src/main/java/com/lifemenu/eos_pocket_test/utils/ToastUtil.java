package com.lifemenu.eos_pocket_test.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.lifemenu.eos_pocket_test.PocketApplication;
import com.lifemenu.eos_pocket_test.R;

/**
 * Created by GYX on 2016/12/11.
 */
public class ToastUtil {
	private static Toast toast;

	public static void toast( CharSequence content) {
		//view = LayoutInflater.from(CoinWallApp.getContext()).inflate(R.layout.toast_item,null);
		View view = View.inflate(PocketApplication.getContext(), R.layout.toast_item, null);
		TextView textView=(TextView) view.findViewById(R.id.toast_text);    // 得到textview
		textView.setText(content);     //设置文本内容，就是你想给用户看的提示数据
//		if (null==toast) {
//			//创建一个toast
//			toast = new Toast(CoinWallApp.getContext());
//		}
		toast = new Toast(PocketApplication.getContext());
		toast.setDuration(Toast.LENGTH_SHORT);          //设置toast显示时间，整数值
		toast.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,400);    //toast的显示位置，这里居中显示

		toast.setView(view);     //設置其显示的view,
		toast.show();             //显示toast
	}
}
