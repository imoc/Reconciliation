package com.compass.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.baidu.mobstat.StatService;
import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.app.config.Constants.DeBug;
import com.compass.bean.Version;
import com.compass.common.https.HttpUtils;
import com.compass.common.https.NetWorkHelper;
import com.compass.common.util.DownLoadFile;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.view.ui.base.BaseActivity;
import com.google.gson.Gson;

/**
 * 启动页面，根据配置判断下一个进入的页面
 * 
 * @classDescription WelcomeActivity 1. 2014年6月9日14:29:49 增加版本升级功能；
 * @date 2014年5月20日 下午7:23:54
 * @author 李小伟
 */
public class WelcomeActivity extends BaseActivity {

	private String TAG = WelcomeActivity.class.getSimpleName();
	// 版本升级
	private int versionCode;// 版本号
	private SharePreferenceUtil mSpUtil;
	private Handler handler;
	// 客户端版本检测:第二种
	private Version upVersionBeen;

	Runnable startAct = new Runnable() {

		@Override
		public void run() {
			if (mSpUtil.getIsFirstUse()) {
				L.d(TAG, "进入NavigationActivity");
				T.showShortDebug(getApplicationContext(),
						"进入NavigationActivity");
				IntentUtil.start_activity(WelcomeActivity.this,
						NavigationActivity.class);
			}
			/*
			 * // 进入MainActivity的条件 else if
			 * (!TextUtils.isEmpty(mSpUtil.getPassword())) { // 闪屏后，跳至登录界面，自动登录
			 * L.d(TAG, "进入LoginActivity");
			 * T.showShortDebug(getApplicationContext(), "进入LoginActivity");
			 * IntentUtil.start_activity(WelcomeActivity.this,
			 * LoginActivity.class); L.d(TAG, "进入PeasonIndexActivity");
			 * T.showShort(getApplicationContext(), "进入PeasonIndexActivity");
			 * IntentUtil.start_activity(WelcomeActivity.this,
			 * PeasonIndexActivity.class); }
			 */
			else {
				L.d(TAG, "进入LoginActivity");
				T.showShortDebug(getApplicationContext(), "进入LoginActivity");
				IntentUtil.start_activity(WelcomeActivity.this,
						LoginActivity.class);
			}

			finish();
			L.d(TAG, "finish()");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		L.d(TAG, "进入WelcomeActivity");
		T.showShortDebug(getApplicationContext(), "进入WelcomeActivity");
		mSpUtil = PushApplication.getInstance().getSpUtil();
		//
		// 百度统计
		initStatService();
		// 版本更新
		checkVersion();
		// 测试直接启动
		// intentActivity();

	}

	/**
	 * app 首个界面初始化百度统计
	 */
	protected void initStatService() {
		// 设置AppKey
		// StatService.setAppKey("8b9c44f02f");//
		// 设置渠道
		// StatService.setAppChannel(this, "WodeWeb", true);
		// 设置每次启动session的间隔失效时间，可以不设置默认30S
		// 测试时，可以使用1秒钟session过期，这样不断的间隔1S启动退出会产生大量日志。
		StatService.setSessionTimeOut(1);
		// 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
		StatService.setDebugOn(DeBug.DEBUG);
		// 版本检测
	}

	public void onResume() {
		L.d(TAG, "Activity1.OnResume()");
		super.onResume();

		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		StatService.onResume(this);
	}

	public void onPause() {
		L.d(TAG, "Activity1.onPause()");
		super.onPause();

		/**
		 * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		StatService.onPause(this);
	}

	/**
	 * 
	 */
	protected void intentActivity() {
		handler = new Handler();
		handler.postDelayed(startAct, 3000);
	}

	/***
	 * 加载sdcard中的启动图片
	 */
	/*
	 * private void loadStartImgFromSd() { // 判断sd是否有启动图片，有则加载，无则跳过
	 * 
	 * }
	 */

	/**
	 * 获取服务器最新版本号
	 */
	public void checkVersion() {
		//
		if (!NetWorkHelper.checkNetState(this)) {
			T.showLong(getApplicationContext(),
					getResources().getString(R.string.httpisNull));
			
			//finish activity if no net （later 3s）2014年11月3日19:21:25
			handler = new Handler();
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					finish();
				}
			}, 5000);
			return;
		}
		//
		versionCode = getCurrentVersion();// 获取当前版本号
		String versionURL = String
				.format(Urls.UP_VERSION_NEW, versionCode + "");
		new VersionAsyncTask().execute(versionURL);
	}

	/**
	 * 获取当前应用版本号
	 * 
	 * @return
	 */
	public int getCurrentVersion() {
		L.d(TAG, "getCurrentVersion -");
		int versionCode = 0;
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		L.d(TAG, "versionCode = " + versionCode);
		return versionCode;
	}

	/**
	 * 获取当前应用版本名称
	 * 
	 * @return
	 */
	public String getCurrentVersionName() {
		L.d(TAG, "getCurrentVersionName -");
		String versionName = "";
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		L.d(TAG, "versionName = " + versionName);
		return versionName;
	}

	/**
	 * 根据服务器版本信息判断是否需要更新新版本
	 */
	private void judgeUpVersion() {
		if (versionCode < upVersionBeen.getVerNum()) {// 当前版本号小于服务器版本号

			saveVisioninf(upVersionBeen.getVerNum(), versionCode,
					upVersionBeen.getVERSION(), upVersionBeen.getDownloadUrl(),
					upVersionBeen.getIsDel());
			// 保存版本信息，用于个人中心“系统更新展示”===============2014年7月18日18:37:06
			new DownLoadFile(WelcomeActivity.this,
					upVersionBeen.getDownloadUrl(), null).downLoadDialog(
					upVersionBeen.getIsDel(), new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (upVersionBeen.getIsDel()) {
								intentActivity();
							} else {
								finish();
							}
						}
					});
		} else {

			saveVisioninf(0, versionCode, getCurrentVersionName(), "", true);
			intentActivity();
		}
	}

	/**
	 * 保存版本信息，用于个人中心“系统更新展示”--------------2014年7月18日18:37:06
	 */
	private void saveVisioninf(int webVerNum, int clientVerNum, String VERSION,
			String ApkDownloadUrl, Boolean ApkIsDelete) {
		// 保存版本信息，用于个人中心“系统更新展示”--------------2014年7月18日18:37:06
		mSpUtil.setWebVerNum(webVerNum);
		mSpUtil.setClientVerNum(clientVerNum);
		mSpUtil.setApkDownloadUrl(ApkDownloadUrl);
		mSpUtil.setApkIsDelete(ApkIsDelete);
		mSpUtil.setVERSION(VERSION);
		// 保存版本信息，用于个人中心“系统更新展示”===============2014年7月18日18:37:06
	}

	class VersionAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			L.d(TAG, "VersionAsyncTask - start ");
		}

		@Override
		protected Boolean doInBackground(String... params) {

			String result = "";
			try {

				L.d(TAG, "url --" + params[0]);
				result = HttpUtils.getByHttpClient(WelcomeActivity.this,
						params[0]);// 无参数

				L.d(TAG, "VersionAsyncTask---result" + result);
				upVersionBeen = new Gson().fromJson(result, Version.class);
				if (null != upVersionBeen) {
					L.d(TAG,
							"VersionAsyncTask---Responses"
									+ upVersionBeen.toString());
					return true;

				} else {
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				judgeUpVersion();
			} else {

				T.showShortDebug(getApplicationContext(), "版本更新服务未启");
				intentActivity();
			}
		}
	}

}
