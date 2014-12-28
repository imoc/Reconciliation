package com.compass.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.bean.EntityBase;
import com.compass.bean.Responses_Login;
import com.compass.common.https.HttpUtils;
import com.compass.common.https.NetWorkHelper;
import com.compass.common.util.DialogUtil;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.view.BaseActivity;
import com.google.gson.Gson;

/**
 * 账号登陆页面
 * 
 * @classDescription LoginActivity
 * @date 2014年5月20日 下午7:39:49
 * @author 李小伟
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private String TAG = LoginActivity.class.getSimpleName();
	static final private int GET_CODE = 0;
	public static final String LOGIN_ACTION = "com.way.action.LOGIN";
	private static final int LOGIN_OUT_TIME = 0;
	private Gson mGson;
	private View mNetErrorView;
	private Button mLoginBtn;
	private TextView mRegistTv;
//	private TextView mForgetTv;
	private EditText mAliasEt;
	private EditText mAccountEt;
	private EditText mPasswordEt;
	private SharePreferenceUtil mSpUtil;
	private PushApplication mApplication;
	private Dialog mLoginDialog;
	private ConnectionOutTimeProcess mLoginOutTimeProcess;
	private Responses_Login resp;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOGIN_OUT_TIME:
				dismissLoginDialog();
				T.showShort(LoginActivity.this, R.string.timeout_try_again);
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PushApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_login);
		initData();
		initView();
		autoLogin();

	}

	/**
	 * 自动登录
	 */
	private void autoLogin() {

		L.d(TAG, "autoLogin...");
		if (!TextUtils.isEmpty(mSpUtil.getPassword())) {
			showLoginDialog();
			String loginUser = String.format(Urls.USER_LOGIN,
					mSpUtil.getAlias(), mSpUtil.getAccount(),
					mSpUtil.getPassword());
			new LoginAsyncTask().execute(loginUser);

		}

	}

	private void initData() {
		mApplication = PushApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();

	}

	private void initView() {
		//
		mAliasEt = (EditText) findViewById(R.id.alias_input);
		mAccountEt = (EditText) findViewById(R.id.account_input);
		mPasswordEt = (EditText) findViewById(R.id.password);
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		String alias = mSpUtil.getAlias();
		String account = mSpUtil.getAccount();
		String password = mSpUtil.getPassword();
		mAliasEt.setText(alias);
		mAccountEt.setText(account);
		mPasswordEt.setText(password);

//		 mAliasEt.setText("sxthydzsgcyxgs");
//		 mAccountEt.setText("4011034326");
//		 mPasswordEt.setText("123456");
		/**
		 * 00140011055  pdxczj 123     
			4010714662    pdxllchglaxjzh  123
			4011214029   shljtmc 123 (双人对账)
		 */
		 mAliasEt.setText("pdxczj");
		 mAccountEt.setText("00140011055");
		 mPasswordEt.setText("123");

		mLoginDialog = DialogUtil.getLoginDialog(this, R.string.tip_logining);
		mLoginOutTimeProcess = new ConnectionOutTimeProcess();
		mNetErrorView = findViewById(R.id.net_status_bar_top);

		setListener();

	}

	/**
	 * 监听
	 */
	private void setListener() {
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mRegistTv = (TextView) findViewById(R.id.regist_tv);
//		mForgetTv = (TextView) findViewById(R.id.forget_tv);
		mLoginBtn.setOnClickListener(this);
		mRegistTv.setOnClickListener(this);
//		mForgetTv.setOnClickListener(this);
		mNetErrorView.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mLoginOutTimeProcess != null) {
			mLoginOutTimeProcess.stop();
			mLoginOutTimeProcess = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 登录验证
		case R.id.login_btn:
			// 一定要去空格，不能出现 /login?account=mAccount空格&pwd=mPassword
			String mAlias = mAliasEt.getText().toString().trim();
			String mAccount = mAccountEt.getText().toString().trim();
			String mPassword = mPasswordEt.getText().toString().trim();

			checkUsername(mAlias, mAccount, mPassword);
			break;
		// 忘记密码
		/*case R.id.forget_tv:
			L.d(TAG, "进入ForgetActivity");
			T.showShortDebug(getApplicationContext(), "进入ForgetActivity");
			startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
			break;
			*/
		// 跳转到网络设置
		case R.id.net_status_bar_top:
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS));
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 当前只有一个返回值
		if (resultCode == RESULT_OK && requestCode == GET_CODE) {
			// 注册成功后，LoginActivity 自动关闭
			Boolean result = data.getExtras().getBoolean("result");
			if (result == true) {
				this.finish();
				T.showShortDebug(getApplicationContext(), "注册成功，登录页面Finnish");
				return;
			}
		}
	}

	/**
	 * 
	 * @classDescription 登录超时处理线程
	 * @date 2014年5月16日 下午4:26:13
	 * @author 李小伟
	 */
	class ConnectionOutTimeProcess implements Runnable {
		public boolean running = false;
		private long startTime = 0L;
		private Thread thread = null;

		ConnectionOutTimeProcess() {
		}

		public void run() {
			while (true) {
				if (!this.running)
					return;
				if (System.currentTimeMillis() - this.startTime > 20 * 1000L) {
					mHandler.sendEmptyMessage(LOGIN_OUT_TIME);
				}
				try {
					Thread.sleep(10L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void start() {
			try {
				this.thread = new Thread(this);
				this.running = true;
				this.startTime = System.currentTimeMillis();
				this.thread.start();
			} finally {
			}
		}

		public void stop() {
			try {
				this.running = false;
				this.thread = null;
				this.startTime = 0L;
			} finally {
			}
		}
	}

	/**
	 * 更新本地数据
	 */
	private void upDateLocalData() {

		if (null == resp) {
			L.d("login--upDateLocalData", "null");
			return;
		}

		// 更新配置文件（最后执行，如以上执行有误，要重新登录）
		upDateSp();

	}

	/**
	 * 更新配置文件
	 */
	private void upDateSp() {

		mSpUtil.setAlias(mAliasEt.getText().toString().trim());
		mSpUtil.setAccount(mAccountEt.getText().toString().trim());
		mSpUtil.SetPassWord(mPasswordEt.getText().toString().trim());
		mSpUtil.setSignature(resp.getSIGNATURE());
		mSpUtil.setLastLoginTime(resp.getLastLoginTime());
		mSpUtil.setIsDouble(resp.getIsDouble());

	}

	/**
	 * 检查，提交表单
	 * 
	 * @param name
	 * @param pwd
	 */
	private void checkUsername(String alias, String name, String pwd) {
		if (TextUtils.isEmpty(alias)) {
			showShortToast(getResources().getString(R.string.user_alias));
			return;
		} else if (TextUtils.isEmpty(name)) {
			showShortToast(getResources().getString(R.string.user_username));
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			showShortToast(getResources().getString(R.string.user_pwd));
			return;
		} else if (!NetWorkHelper.checkNetState(this)) {
			showLongToast(getResources().getString(R.string.httpisNull));
			return;
		} else {
			showLoginDialog();
			/**
			 * http://jhauto.gicp.net/accountclient/api
			 * /login.action?openid=4011034326
			 * &passwd=123&userna=sxthydzsgcyxgs&token=123
			 */
			String loginUser = String.format(Urls.USER_LOGIN, alias, name, pwd);
			new LoginAsyncTask().execute(loginUser);
		}

	}

	class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			L.d(TAG, "LoginAsyncTask--onPreExecute");

		}

		@Override
		protected Boolean doInBackground(String... params) {

			String result = "";
			try {

				result = HttpUtils.getByHttpClient(LoginActivity.this,
						params[0]);// 无参数

				L.d(TAG, "LoginAsyncTask---result" + result);
				resp = mGson.fromJson(result, Responses_Login.class);
//				resp = TestDataUtil.LoginResp();
				L.d(TAG, "LoginAsyncTask---Responses" + resp.toString());
				if (resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
					upDateLocalData();
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
			dismissLoginDialog();
			if (result) {
				T.showShortDebug(mApplication, R.string.login_scuess);

				// 跳转个人主页
				IntentUtil.start_activity(LoginActivity.this,
						TabHostActivity.class);
				finish();
			} else {
				if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
					T.showLong(getApplicationContext(), resp.getERRMSG());

				} else {
					T.showLong(getApplicationContext(),
							R.string.user_login_outTime);
				}
				// 输入框置空
				/*
				 * mAliasEt.setText(""); mAccountEt.setText("");
				 * mPasswordEt.setText("");
				 */
			}
		}

	}

	/**
	 * 显示登录进度对话框
	 */
	protected void showLoginDialog() {
		mLoginDialog.show();
		mLoginDialog.setCancelable(false);// 返回键不能取消
		if (mLoginOutTimeProcess != null && !mLoginOutTimeProcess.running) {
			mLoginOutTimeProcess.start();
		}
	}

	/**
	 * 登录成功取消进度对话框
	 */
	protected void dismissLoginDialog() {
		if (mLoginDialog != null && mLoginDialog.isShowing())
			mLoginDialog.dismiss();

		if (mLoginOutTimeProcess != null && mLoginOutTimeProcess.running)
			mLoginOutTimeProcess.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
