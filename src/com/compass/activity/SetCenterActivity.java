package com.compass.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.bean.EntityBase;
import com.compass.bean.UpdatePsdResponses;
import com.compass.common.https.HttpUtils;
import com.compass.common.https.NetWorkHelper;
import com.compass.common.util.DialogUtil;
import com.compass.common.util.DownLoadFile;
import com.compass.common.util.InputUtil;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.NetUtil;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.test.UIHelper;
import com.compass.view.BaseActivity;
import com.compass.widget.slidinglayer.SlidingLayer;
import com.compass.widget.slidinglayer.SlidingLayer.OnInteractListener;
import com.google.gson.Gson;

public class SetCenterActivity extends BaseActivity implements OnClickListener,
		OnInteractListener {

	String TAG = SetCenterActivity.class.getSimpleName();

	public enum ParameterType {
		updatePsw, babyName;
	}

	ParameterType paramType;
	static final private int GET_CODE = 0;
	static final private int Add_READ = 1;

	private LayoutInflater mInflater;
	// 滑层
	private SlidingLayer mSlidingLayer;
	// PasswordSet
	private View mPasswordSet;
	private View mPasswordSetView;
	private EditText newPasswordFirstEt;
	private EditText newPasswordConfEt;
	private EditText oldPasswordEt;
	private Button passwordSetSaveBt;
	private String oldPsd;
	private String newPsdDFir;
	private String newPsdConf;
	private String updatePsd;
	// exit
	private View mExitConfirmView;
	private Button mExitAppBtn, mComfirmExitBtn, mCancelExitBtn;;
	// 系统更新
	private View mVisionUpSet;
	private View mVisionUpSetView;
	private View mVisionUpView;
	private ImageView visionNum_statu_iv;
	private ImageView visionUp_new_statu_iv;
	private TextView visionNum_statu_tv;
	private TextView upVision_lable_tv;
	/*
	 * // 关于 private View mAboutView; // 反馈 private View mFeedBackView; private
	 * EditText mFeedBackET; private Button mFeedBackBtn;
	 */
	//
	private Context mContext;
	private PushApplication mApplication;
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private View mNetErrorView;
	private Dialog mLoginDialog;
	private UpdatePsdResponses resp;
	// bankClient
	// 使用帮助
	private View mHelpSet;
	private View mHelpView;
	// 意见反馈
	private View mSuggestSet;
	private View mSuggestView;
	private EditText etSuggest;
	private EditText etContact;
	// 关于
	private View mAboutSet;
	private View mAboutView;
	//
	// textContent (babyName，school，class,signature)----
	private View babyName_v;
	private View mEditSetView;
	private EditText mContentEdit;
	private Button UpdataBtn;
	private TextView layoutTitle;
	private String paramContent;
	// TextView Output
	private TextView babyName_statu;

	public SlidingLayer getSlidingLayer() {
		return mSlidingLayer;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_peason_center);
		//
		PushApplication.getInstance().addActivity(this);
		//
		initData();
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
		if (!NetUtil.isNetConnected(this))
			mNetErrorView.setVisibility(View.VISIBLE);
		else
			mNetErrorView.setVisibility(View.GONE);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	private void initData() {

		mContext = this;
		mApplication = PushApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();

		mInflater = LayoutInflater.from(this);
		//
		mLoginDialog = DialogUtil.getLoginDialog(this);
		//
		initExitView();
		initPasswordSetView();
		initVisionUp();
		initHelpLayout();
		initSuggestLayout();
		initAboutLayout();
		initTextContentSetView();

	}

	private void initTextContentSetView() {
		// view
		babyName_v = findViewById(R.id.babyName_v);
		babyName_v.setOnClickListener(this);
		// TextView status
		babyName_statu = (TextView) findViewById(R.id.babyName_statu);
		// init TextView from mSpUtil
		babyName_statu.setText(mSpUtil.getAlias());
		//
		mEditSetView = mInflater.inflate(R.layout.layout_edit, null);
		layoutTitle = (TextView) mEditSetView
				.findViewById(R.id.layout_title_text);
		mContentEdit = (EditText) mEditSetView.findViewById(R.id.mContentEdit);
		UpdataBtn = (Button) mEditSetView.findViewById(R.id.upData_btn);
		UpdataBtn.setOnClickListener(this);

	}

	private void initHelpLayout() {
		//
		mHelpSet = findViewById(R.id.help_set_tv);
		mHelpSet.setOnClickListener(this);
		mHelpView = mInflater.inflate(R.layout.layout_set_help, null);
		((TextView) mHelpView.findViewById(R.id.layout_title_text))
				.setText(R.string.help);
	}

	private void initSuggestLayout() {
		//
		mSuggestSet = findViewById(R.id.suggest_set_tv);
		mSuggestSet.setOnClickListener(this);
		mSuggestView = mInflater.inflate(R.layout.layout_set_suggest, null);

		etSuggest = (EditText) mSuggestView.findViewById(R.id.et_suggest);
		etContact = (EditText) mSuggestView.findViewById(R.id.et_contact);
		((Button) mSuggestView.findViewById(R.id.submit_btn))
				.setOnClickListener(this);
		((TextView) mSuggestView.findViewById(R.id.layout_title_text))
				.setText(R.string.suggest);

	}

	private void initAboutLayout() {
		//
		mAboutSet = findViewById(R.id.about_set_tv);
		mAboutSet.setOnClickListener(this);
		mAboutView = mInflater.inflate(R.layout.layout_set_about, null);
		((TextView) mAboutView.findViewById(R.id.layout_title_text))
				.setText(R.string.about);

	}

	private void initVisionUp() {
		//
		mVisionUpSet = findViewById(R.id.version_up_v);
		mVisionUpSet.setOnClickListener(this);
		visionNum_statu_iv = (ImageView) findViewById(R.id.visionNum_statu_iv);
		//
		mVisionUpSetView = mInflater.inflate(R.layout.layout_upvision, null);
		((TextView) mVisionUpSetView.findViewById(R.id.layout_title_text))
				.setText(R.string.visionUp);
		visionUp_new_statu_iv = (ImageView) mVisionUpSetView
				.findViewById(R.id.ic_new);
		upVision_lable_tv = (TextView) mVisionUpSetView
				.findViewById(R.id.upVision_lable_tv);
		visionNum_statu_tv = (TextView) mVisionUpSetView
				.findViewById(R.id.visionNum_statu_tv);
		mVisionUpView = mVisionUpSetView.findViewById(R.id.upVision_v);

		mVisionUpView.setOnClickListener(this);
		if (mSpUtil.getWebVerNum() > 0) {
			visionNum_statu_iv.setVisibility(View.VISIBLE);
			upVision_lable_tv.setText("更新版本");
			visionUp_new_statu_iv.setVisibility(View.VISIBLE);
			// visionNum_statu_tv.setText(mSpUtil.getWebVerNum()+"");
			visionNum_statu_tv.setText(mSpUtil.getVERSION());

		} else {
			visionNum_statu_iv.setVisibility(View.INVISIBLE);
			upVision_lable_tv.setText("当前版本");
			visionUp_new_statu_iv.setVisibility(View.INVISIBLE);
			visionNum_statu_tv.setText(mSpUtil.getVERSION());
			visionNum_statu_iv.setVisibility(View.INVISIBLE);
		}

	}

	private void initPasswordSetView() {
		//
		mPasswordSet = findViewById(R.id.password_set_tv);
		mPasswordSet.setOnClickListener(this);
		//
		mPasswordSetView = mInflater
				.inflate(R.layout.layout_password_set, null);
		((TextView) mPasswordSetView.findViewById(R.id.layout_title_text))
				.setText("修改密码");
		oldPasswordEt = (EditText) mPasswordSetView
				.findViewById(R.id.oldPassword_et);
		newPasswordFirstEt = (EditText) mPasswordSetView
				.findViewById(R.id.newPasswordFirst_et);
		newPasswordConfEt = (EditText) mPasswordSetView
				.findViewById(R.id.newPasswordConfirm_et);
		passwordSetSaveBt = (Button) mPasswordSetView
				.findViewById(R.id.submitBt);
		passwordSetSaveBt.setOnClickListener(this);

	}

	private void initExitView() {
		//
		mExitAppBtn = (Button) findViewById(R.id.exit_app);
		mExitAppBtn.setOnClickListener(this);
		//
		mExitConfirmView = mInflater.inflate(R.layout.layout_exit_app, null);
		((TextView) mExitConfirmView.findViewById(R.id.layout_title_text))
				.setText("退出");
		mComfirmExitBtn = (Button) mExitConfirmView
				.findViewById(R.id.confirm_exit_btn);
		mComfirmExitBtn.setOnClickListener(this);
		mCancelExitBtn = (Button) mExitConfirmView
				.findViewById(R.id.cancel_exit_btn);
		mCancelExitBtn.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	private void initView() {
		// title
		TextView title = (TextView) findViewById(R.id.title_text);
		title.setText("个人中心");
		ImageView back = (ImageView) findViewById(R.id.left_title_iv);
		back.setOnClickListener(this);
		//
		mNetErrorView = findViewById(R.id.net_status_bar_top);
		//
		mSlidingLayer = (SlidingLayer) findViewById(R.id.right_sliding_layer);
		mSlidingLayer.setOnInteractListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case IntentUtil.REQUEST_CODE_VERIFY:
			switch (resultCode) {
			case IntentUtil.RESULT_CODE_CANSEL:
				L.d(TAG, "短信验证取消");
				break;
			case IntentUtil.RESULT_CODE_SUCCESS:
				L.d(TAG, "短信验证成功");
				new UpDateAsyncTask().execute(ParameterType.updatePsw, updatePsd);
				break;

			default:
				
				break;
			}
			break;

		default:
			break;
		}
	}
		

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.left_title_iv:
			finish();
			break;
		case R.id.exit_app:
			mSlidingLayer.removeAllViews();// 先移除所有的view,不然会报错
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mExitConfirmView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.confirm_exit_btn:
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}

			logout();
			this.finish();
			break;
		case R.id.cancel_exit_btn:
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}

			break;

		case R.id.net_status_bar_info_top:
			// 跳转到网络设置
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS));
			break;
		/*----password_set ----------------------------------- */
		case R.id.password_set_tv:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mPasswordSetView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.submitBt:
			// 提交新密码
			// 一定要去空格，不能出现 /login?account=mAccount空格&pwd=mPassword
			oldPsd = oldPasswordEt.getText().toString().trim();
			newPsdDFir = newPasswordFirstEt.getText().toString().trim();
			newPsdConf = newPasswordConfEt.getText().toString().trim();
			paramType = ParameterType.updatePsw;
			upPassword(oldPsd, newPsdDFir, newPsdConf);
			break;
		/* 系统更新---------------------------- */
		case R.id.version_up_v:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mVisionUpSetView);
				mSlidingLayer.openLayer(true);
			}
			/*
			 * mVisionUpSetView.setFocusable(true);
			 * mVisionUpSetView.setFocusableInTouchMode(true);
			 * //------滑层中的列表获得焦点------2014年7月23日17:11:40
			 * mVisionUpView.setFocusable(true);
			 */
			viewInmSlidingLayerGetFocus(mVisionUpSetView, mVisionUpView, null);
			// ------滑层中的列表获得焦点======2014年7月23日17:11:40
			break;
		case R.id.upVision_v:
			L.d("upVision_v-----------", mSpUtil.getWebVerNum() + "");
			if (mSpUtil.getWebVerNum() > 0) {
				new DownLoadFile(SetCenterActivity.this,
						mSpUtil.getApkDownloadUrl(), null).downLoadDialog(
						mSpUtil.getApkIsDelete(),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (mSpUtil.getApkIsDelete()) {
									return;
								} else {
									return;
								}
							}
						});

			} else {
				T.showShort(getApplicationContext(), R.string.visionNONeedUp);
			}
			break;

		case R.id.help_set_tv:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mHelpView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.suggest_set_tv:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mSuggestView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.about_set_tv:
			mSlidingLayer.removeAllViews();
			if (!mSlidingLayer.isOpened()) {
				mSlidingLayer.addView(mAboutView);
				mSlidingLayer.openLayer(true);
			}
			break;
		case R.id.submit_btn:

			submitSuggestion();
			break;
		/* textContent (babyName，school，class,signature) start ---- */
		case R.id.babyName_v:
			textContentSet(ParameterType.babyName, "修改别名", mSpUtil.getAlias());
			break;
		case R.id.upData_btn:
			mSlidingLayer.removeAllViews();
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
			}
			// 验证,提交注册表单格式
			String newContent = mContentEdit.getText().toString().trim();
			checkSheet(paramType, newContent);

			break;

		default:
			break;

		}
	}

	/**
	 * 设置文本内容 textContent (babyName，school，class,signature)
	 * 
	 * @param paramType
	 * @param layoutTitie
	 * @param editContent
	 */
	protected void textContentSet(ParameterType Type, String layoutTitie,
			String editContent) {
		// 针对不同类型，分别格式验证
		switch (Type) {
		case babyName:
			mContentEdit
					.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			break;

		default:
			break;
		}

		mSlidingLayer.removeAllViews();
		if (!mSlidingLayer.isOpened()) {
			mSlidingLayer.addView(mEditSetView);
			mSlidingLayer.openLayer(true);
		}
		paramType = Type;
		layoutTitle.setText(layoutTitie);
		mContentEdit.setText(editContent);

		InputUtil.showsoftInput(mContentEdit);

	}

	private void submitSuggestion() {
		String suggestion = etSuggest.getText().toString().trim();
		String contact = etContact.getText().toString().trim();
		T.showLong(mContext, "suggestion = " + suggestion + "/ncontact = "
				+ contact);
		if (mSlidingLayer.isOpened()) {
			mSlidingLayer.closeLayer(true);
		}

	}

	/**
	 * 验证,提交注册表单格式
	 * 
	 * @param account
	 * @param pwdFir
	 * @param pwdConf
	 * @param relation
	 * @param telep
	 * @param babyName
	 */
	private void checkSheet(ParameterType paramType, final String newContent) {

		int lengh;
		T.showShortDebug(getApplicationContext(), paramType + "  ==  "
				+ newContent);
		if (TextUtils.isEmpty(newContent)) {
			showShortToast(getResources().getString(R.string.no_null));
			return;
		} else {
			lengh = newContent.length();
		}

		// 针对不同类型，分别格式验证
		switch (paramType) {
		case babyName:
			if (!verifyLength(lengh, 20)) {
				return;
			}
			break;
		default:
			break;
		}

		// 提交注册表单
		if (!NetUtil.isNetConnected(this)) {
			T.showLong(this, R.string.net_error_tip);
			return;
		}

		paramContent = newContent;
		/**
		 * public static final String UPD_USER_NA_URL = BASIC_URL +
		 * "/updUserNa.action?" + "SIGNATURE=%s" + "&USERNAME=%s"
		 */
		String registUser = String.format(Urls.UPD_USER_NA_URL,
				mSpUtil.getSignature(), paramContent);
		new UpDateAsyncTask().execute(paramType, registUser);
	}

	/**
	 * @param lengh
	 */
	private Boolean verifyLength(int lengh, int maxLengh) {
		Boolean isValid = true;
		if (lengh > maxLengh) {
			T.showShort(mContext, "内容超出!  " + lengh + "/" + maxLengh);
			isValid = false;
		}
		return isValid;
	}

	/**
	 * 滑层中的列表获得焦点------2014年7月23日17:11:40
	 * 
	 * @@必须在滑层显示后，才可调用
	 */
	private void viewInmSlidingLayerGetFocus(View View, View v,
			BaseAdapter adapter) {
		// ------滑层中的列表获得焦点------2014年7月23日17:11:40
		View.setFocusable(true);
		View.setFocusableInTouchMode(true);
		v.setFocusable(true);
		if (null != adapter) {
			adapter.notifyDataSetInvalidated();// 获得焦点
		}
		// ------滑层中的列表获得焦点======2014年7月23日17:11:40
	}

	/**
	 * 注销
	 */
	private void logout() {

		// 此处，应该通知服务器，用户下线----2014年8月2日23:30:37
		PushApplication.getInstance().logout();
	}

	/**
	 * 密码验证，密码提交，密码保存
	 * 
	 * @param oldPsd
	 * @param newPsdDFir
	 * @param newPsdConf
	 */
	private void upPassword(String oldPsd, String newPsdDFir, String newPsdConf) {
		if (TextUtils.isEmpty(oldPsd)) {
			showShortToast(getResources().getString(R.string.user_pwd));
			return;
		} else if (TextUtils.isEmpty(newPsdDFir)) {
			showShortToast(getResources().getString(R.string.user_pwd_new));
			return;
		} else if (TextUtils.isEmpty(newPsdConf)) {
			showShortToast(getResources().getString(R.string.user_pwd_conf));
			return;
		} else if (!newPsdDFir.equals(newPsdConf)) {
			showShortToast(getResources().getString(R.string.user_pwd_unSame));
			return;
		} else if (newPsdDFir.length() < 6) {
			showShortToast(getResources().getString(
					R.string.tip_psw_lengh_less_6));
			return;
		} else if (!NetWorkHelper.checkNetState(this)) {
			showLongToast(getResources().getString(R.string.httpisNull));
			return;
		} else {
			// showLoginDialog();

			/*
			 * "/updUserPasswd.action?" + "SIGNATURE=%s" + "&PASSWORD=%s" +
			 * "&UPASSWORD=%s"
			 */
			 updatePsd = String.format(Urls.UPD_USER_PASSWD_URL,
					mSpUtil.getSignature(), oldPsd, newPsdDFir);
			 IntentUtil.start_activityForResult(mContext, VerifyActivity.class, IntentUtil.REQUEST_CODE_VERIFY);
//			new UpDateAsyncTask().execute(ParameterType.updatePsw, updatePsd);
		}

	}

	/**
	 * 显示登录进度对话框
	 */
	protected void showLoginDialog() {
		mLoginDialog.show();
		mLoginDialog.setCancelable(false);// 返回键不能取消
	}

	/**
	 * 登录成功取消进度对话框
	 */
	protected void dismissLoginDialog() {
		if (mLoginDialog != null && mLoginDialog.isShowing())
			mLoginDialog.dismiss();

	}

	@Override
	public void onOpen() {

	}

	@Override
	public void onClose() {

		// mSlidingLayer.removeAllViews();
	}

	@Override
	public void onOpened() {

	}

	@Override
	public void onClosed() {

		mSlidingLayer.removeAllViews();
		// 关闭键盘
		InputUtil.KeyBoardCancle(mContext);

	}

	// 屏蔽返回键的退出Activity作用
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mSlidingLayer.isOpened()) {
				mSlidingLayer.closeLayer(true);
				mSlidingLayer.removeAllViews();
			} else {
				finish();
			}

			return true;// 监听不在传递

		}
		return super.onKeyDown(keyCode, event);
	}

	class UpDateAsyncTask extends AsyncTask<Object, Void, Boolean> {

		private ParameterType paramType;
		private EntityBase resp;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			showAlertDialog("温馨提示", "正在更新，请稍等一下~");
		}

		@Override
		protected Boolean doInBackground(Object... params) {
			paramType = (ParameterType) params[0];
			String result = "";
			try {

				result = HttpUtils.getByHttpClient(SetCenterActivity.this,
						(String) params[1]);
				L.d(TAG, "UpDateAsyncTask---result" + result);
				resp = mGson.fromJson(result, EntityBase.class);
				L.d(TAG, "UpDateAsyncTask---Responses" + resp.toString());

				switch (paramType) {
				case updatePsw:

					if (null != resp
							&& resp.getERRCODE() == EntityBase.ERRCODE_UPD_SUCCESS) {
						return true;

					} else {
						return false;
					}
				case babyName:

					if (null != resp
							&& resp.getERRCODE() == EntityBase.ERRCODE_UPD_SUCCESS) {
						return true;

					} else {
						return false;
					}
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			super.onPostExecute(result);
			mAlertDialog.dismiss();

			if (result) {
				// 提交服务器成功后，保存至Sp
				switch (paramType) {
				case updatePsw:

					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
						T.showLong(getApplicationContext(), resp.getERRMSG());
					} else {

						// T.showLong(getApplicationContext(), "密码修改成功!");
					}

					if (mSlidingLayer.isOpened()) {
						mSlidingLayer.closeLayer(true);
						mSlidingLayer.removeAllViews();
					}
					// 保存最新密码
					mSpUtil.SetPassWord(newPsdConf);
					oldPsd = null;
					newPsdDFir = null;
					newPsdConf = null;
					// 输入框置空
					oldPasswordEt.setText("");
					newPasswordFirstEt.setText("");
					newPasswordConfEt.setText("");
					break;
				case babyName:
					mSpUtil.setAlias(paramContent);
					babyName_statu.setText(paramContent);
					break;
				default:
					break;
				}

			} else {

				UIHelper.HandleErrcode(mContext, resp.getErrCode());
				switch (paramType) {
				case updatePsw:
					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
						T.showLong(getApplicationContext(), resp.getERRMSG());
					} else {
						T.showLong(getApplicationContext(),
								R.string.user_update_error);
					}
					// 输入框置空
					oldPasswordEt.setText("");
					newPasswordFirstEt.setText("");
					newPasswordConfEt.setText("");
					break;

				case babyName:
					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
						T.showLong(getApplicationContext(), resp.getERRMSG());
					} else {
						T.showLong(getApplicationContext(),
								R.string.Request_fail);
					}
					break;
				default:
					break;
				}
			}
		}

	}

	public void layoutFinish(View view) {
		// 关闭键盘
		InputUtil.KeyBoardCancle(mContext);
		if (mSlidingLayer.isOpened()) {
			mSlidingLayer.closeLayer(true);
			mSlidingLayer.removeAllViews();
		}
	}

}