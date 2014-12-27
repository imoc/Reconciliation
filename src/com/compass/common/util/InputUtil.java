package com.compass.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 输入法工具类
 * 
 * @classDescription InputUtil
 * @date 2014年7月23日 上午10:55:50
 * @author 李小伟
 */
public class InputUtil {

	/**
	 * 关闭键盘
	 * 
	 * @param context
	 */
	public static void KeyBoardCancle(Context context) {
		View view = ((Activity) context).getWindow().peekDecorView();
		if (view != null) {
			// 得到InputMethodManager的实例
			InputMethodManager inputmanger = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 弹出软键盘 2014年7月26日17:16:49
	 */
	public static void showsoftInput(EditText et) {
		//
		et.setFocusableInTouchMode(true);
		et.requestFocus();
		et.setSelection(et.getText().length());
		InputMethodManager inputManager = (InputMethodManager) et.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(et, 0);
	}

}
