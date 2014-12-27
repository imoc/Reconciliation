package com.compass.common.util;

import java.io.Serializable;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtil {
	
	public static final int REQUEST_CODE_VERIFY = 0x001; 
	public static final int RESULT_CODE_CANSEL = 0x000; 
	public static final int RESULT_CODE_SUCCESS = 0x001; 
	public static void start_activity(Activity activity, Class<?> cls,
			BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		for (int i = 0; i < name.length; i++) {
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		activity.startActivity(intent);
		// activity.overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_left_out);
	}

	public static void start_activity(Context mContext, Class<?> cls,String serName,Serializable ser, 
			BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(mContext, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 广播中打开acticity 必须得加
		intent.putExtra(serName, ser);
		for (int i = 0; i < name.length; i++) {
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		mContext.startActivity(intent);
		L.d("start_activity", "start_activity");
		// activity.overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_left_out);
	}
	public static void start_activity(Context mContext, Class<?> cls,
			BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(mContext, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 广播中打开acticity 必须得加
		for (int i = 0; i < name.length; i++) {
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		mContext.startActivity(intent);
		L.d("start_activity", "start_activity");
		// activity.overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_left_out);
	}

	public static void start_activityForResult(Activity activity, Class<?> cls,
			int requestCode, BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		for (int i = 0; i < name.length; i++) {
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		activity.startActivityForResult(intent, requestCode);
		// activity.overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_left_out);
	}
	public static void start_activityForResult(Context context, Class<?> cls,
			int requestCode, BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		for (int i = 0; i < name.length; i++) {
			intent.putExtra(name[i].getName(), name[i].getValue());
		}
		((Activity)context).startActivityForResult(intent, requestCode);
		// activity.overridePendingTransition(R.anim.push_left_in,
		// R.anim.push_left_out);
	}

	/**
	 * @param mobile
	 */
	public static void startDailActivity(Context context, String mobile) {
		// 进行拨号，android中提供了拨号软件，通过Activity实现了拨号功能
		Intent intent = new Intent();
		// 激活源代码,添加intent对象
		intent.setAction(Intent.ACTION_DIAL);// 跳转拨号界面
		// intent.addCategory("android.intent.category.DEFAULT");内部会自动添加类别，
		intent.setData(Uri.parse("tel:" + mobile));
		// 激活Intent
		context.startActivity(intent);
	}
}
