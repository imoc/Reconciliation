package com.compass.app;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;

import com.compass.common.util.SharePreferenceUtil;
import com.google.gson.Gson;

public class PushApplication extends Application {
	public static final String TAG = PushApplication.class.getSimpleName();
	public static File cacheDir;

	public static final String SP_FILE_NAME = "push_msg_sp";
	private static PushApplication mApplication;
	private Gson mGson;
	private SharePreferenceUtil mSpUtil;
	private NotificationManager mNotificationManager;

	private List<Activity> activityList = new LinkedList<Activity>();

	public synchronized static PushApplication getInstance() {
		if (null == mApplication) {
			mApplication = new PushApplication();
		}
		return mApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		// CrashHandler.getInstance().init(this);
		// 异常捕获
		// MyExceptionHandler.getInstance().init(this);
		initData();
	}

	private void initData() {
		// mBaiduPushServer = new BaiduPush(BaiduPush.HTTP_METHOD_POST,
		// Constants.SECRIT_KEY, Constants.API_KEY);
		mGson = new Gson();
		mSpUtil = new SharePreferenceUtil(this, SP_FILE_NAME);
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish，系统退出
	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}

		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// 遍历所有Activity并finish
	public void clearActivitys() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	public synchronized Gson getGson() {
		if (mGson == null) {
			mGson = new Gson();
		}
		return mGson;
	}

	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}

	public synchronized SharePreferenceUtil getSpUtil() {
		if (mSpUtil == null)
			mSpUtil = new SharePreferenceUtil(this, SP_FILE_NAME);
		return mSpUtil;
	}

	/**
	 * 清除数据
	 */
	public void cleanDB() {
		// ------------------------------------
		// 保留上次登录账号-----
		String account = getSpUtil().getAccount();
		// 保留isFirst-----
		Boolean isFirstUse = getSpUtil().getIsFirstUse();
		// ----------------
		getSpUtil().clearSp();// 清空配置文件
		// ================
		// 重写数据
		getSpUtil().setIsFirstUse(isFirstUse);
		getSpUtil().setAccount(account);
		// ============
		// getSpUtil().clearSp();// 清空配置文件
	}

	/**
	 * 清除数据，注销账户信息
	 */
	public void logout() {

		exit();// 关闭所有Activity
	}

}
