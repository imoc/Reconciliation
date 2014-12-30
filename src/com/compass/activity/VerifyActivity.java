package com.compass.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.bean.EntityBase;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.test.TestDataUtil;
import com.compass.test.UIHelper;
import com.compass.view.ui.base.BaseActivity;
import com.google.gson.Gson;

/**
 * 
 * @Description: TODO(短信验证码处理)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月13日 上午11:53:17
 * 
 */
public class VerifyActivity extends BaseActivity implements OnClickListener {
	private final String TAG = VerifyActivity.class.getSimpleName();
	public enum ParameterType {
		GET, SUBMIT;
	}

	ParameterType paramType;
	// ininData
	private Context mContext;
	private PushApplication mApplication;
	private Gson mGson;
	private SharePreferenceUtil mSpUtil;
	// view
	private TextView mTipTv;
	private EditText mAuthCode_et;
	private Button mGetAuthCodeBt;
	private Button mSubmitBt;
	//
	private String smsAuthCode;//验证码
    private Timer timer;//计时器
    private int jishi=60;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify);
		ininData();
		ininView();
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	/**
	 * 
	 * @Description: 获取短信验证码
	 * @param 设定文件
	 * @return void 返回类型
	 */
	private void getSmsAuthCode() {
		L.d(TAG, " 获取短信验证码。。");
	/**
	 * http://jhauto.gicp.net/accountclient/app/remindcheckagin.action?SIGNATURE=123
	 */
		String url = String.format(Urls.GET_SMS_AUTH_CODE,
				mSpUtil.getSignature());
		new VerifyCodeAsyncTask().execute(url,ParameterType.GET);
	}

	private void ininView() {
		// title
		TextView mTitle = (TextView) findViewById(R.id.title_text);
		mTitle.setText(R.string.smsVerify);
		ImageView back = (ImageView) findViewById(R.id.left_title_iv);
		back.setOnClickListener(this);
		//
		mTipTv = (TextView) findViewById(R.id.tipTv);
		mAuthCode_et = (EditText) findViewById(R.id.authCode_et);
		mGetAuthCodeBt= (Button) findViewById(R.id.getAuthCodeBt);
		mSubmitBt= (Button) findViewById(R.id.submitBt);
		mGetAuthCodeBt.setOnClickListener(this);
		mSubmitBt.setOnClickListener(this);


	     
	     
	       
	    
	}

	private void ininData() {

		mApplication = PushApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();
		mContext = this;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_title_iv:
			setResult(IntentUtil.RESULT_CODE_CANSEL);
			finish();
			break;
		case R.id.getAuthCodeBt:
			getSmsAuthCode();
			//TODO 60秒后重发
			timerClick();
			break;
		case R.id.submitBt:
			submitAuthCode();
			break;

		default:
			break;
		}

	}

    public void timerClick(){
            jishi=60;
            mGetAuthCodeBt.setEnabled(false);
            timer=new Timer();
            timer.schedule(new TimerTask()
            {
	            
	            @Override
	            public void run()
	            {
	            	handler.sendEmptyMessage(jishi--);
	            }
            }, 0, 1000);
    }

    
    private Handler  handler=new Handler(){
    public void handleMessage(android.os.Message msg) {
            if(msg.what==0){
            	mGetAuthCodeBt.setEnabled(true);
            	mGetAuthCodeBt.setText("发送验证码");
                    timer.cancel();
                    }else{
                    	mGetAuthCodeBt.setText(msg.what+"秒后重发");
                    }
    };
    };
    
    @Override
    public void onDestroy()
    {
            if(timer!=null)
                    timer.cancel();
            super.onDestroy();
    }
	private void submitAuthCode() {
		L.d(TAG, " 获取短信验证码。。");
		/**
		 * http://jhauto.gicp.net/accountclient/app/remindcheck.action?SIGNATURE=123&MSG=123
		 * public static final String SUBMIT_AUTH_CODE = BASIC_URL 
			+ "/remindcheck.action?"
			+ "SIGNATURE=%s"
			+ "&MSG=%s"
		 */
		smsAuthCode = mAuthCode_et.getText().toString().trim();
		if (TextUtils.isEmpty(smsAuthCode)) {
			T.showShort(mContext, R.string.tip_auth_no_empt);
			return;
		}
			String url = String.format(Urls.SUBMIT_AUTH_CODE,
					mSpUtil.getSignature(),smsAuthCode);
			new VerifyCodeAsyncTask().execute(url,ParameterType.SUBMIT);
	}


	class VerifyCodeAsyncTask extends AsyncTask<Object, Void, Boolean> {

		private ParameterType paramType;
		private EntityBase resp;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			L.d(TAG, "LoginAsyncTask--onPreExecute");

		}

		@Override
		protected Boolean doInBackground(Object... params) {

			paramType = (ParameterType) params[1];
			String result = "";
			try {

//				result = HttpUtils.getByHttpClient(VerifyActivity.this,
//						(String)params[0]);// 无参数

				L.d(TAG, "VerifyCodeAsyncTask---result" + result);
				
				switch (paramType) {
				case GET:
					L.d(TAG, "GET operate");
//					resp = mGson.fromJson(result, EntityBase.class);
					resp = TestDataUtil.getSmsAuthCodeResp();
					L.d(TAG, "VerifyCodeAsyncTask---resp" + resp);
					if (resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
						return true;
					} else {
						return false;	
					}
				case SUBMIT:
					L.d(TAG, "SUBMIT operate");
//					resp = mGson.fromJson(result, EntityBase.class);
					 resp = TestDataUtil.getVerifySmsAuthCodeResp();
					 L.d(TAG, "VerifyCodeAsyncTask---resp" + resp);
					if (resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
						return true;
					} else {
						return false;
					}
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
				switch (paramType) {
				case GET:
					smsAuthCode = resp.getAuthCode();
					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
//						T.showLong(getApplicationContext(), resp.getERRMSG());
					} else {
						T.showLong(getApplicationContext(), "请求验证码成功");
					}
					break;
				case SUBMIT:
					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
//						T.showLong(getApplicationContext(), resp.getERRMSG());
					} else {
						T.showLong(getApplicationContext(), "验证成功");
						setResult(IntentUtil.RESULT_CODE_SUCCESS);
						finish();
					}
					break;
				}
			} else {
				switch (paramType) {
				case GET:
					if (null != resp && resp.getErrCode() == 1) {
						T.showLong(mContext,resp.getErrMsg());
						handler.sendEmptyMessage(0);
					} else {
						if (null != resp ) {
							UIHelper.HandleErrcode(mContext, resp.getErrCode());
						}
					}
					break;
				case SUBMIT:
					if (null != resp && resp.getErrCode() == 1) {
						T.showLong(mContext,resp.getErrMsg());
//						handler.sendEmptyMessage(0);
					} else {
						if (null != resp ) {
							UIHelper.HandleErrcode(mContext, resp.getErrCode());
						}
					}
					break;
				}
			}
		}

	}

	

}
