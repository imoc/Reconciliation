package com.compass.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.bean.QueryBean;
import com.compass.bean.Responses_GetAccounts;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.TimeUtil;
import com.compass.reconciliation.R;
import com.compass.test.UIHelper;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: AccountEnquiryActivity
 * @Description: TODO(account enquiry ，include "my account","detailed enquriy")
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月13日 上午11:53:17
 * 
 */
public class AccountEnquiryActivity extends Activity implements OnClickListener {
	private final String TAG = AccountEnquiryActivity.class.getSimpleName();
	//
	private Context mContext;
	private PushApplication mApplication;
	private Gson mGson;
	private SharePreferenceUtil mSpUtil;
	// view
	private ListView mQueryTypeLv;
	private Spinner mAccountspinner;
	private TextView mTypeTv;
	private Spinner mFastSectionsSpinner;
	private Button mFastQueryBt;
	private Button mSectionQueryBt;
	private View mSectionPickView;
	private View mStartDateV;
	private View mEndDateV;
	private TextView mStartDateTv;
	private TextView mEndDateTv;
	private Button mQueryBt;
	// data
	private String[] accounts;
	private String[] types;
	private String[] typeParameters;
	private int[] typeIcons;

	private String[] sections;
	private final long DAY_LONG = 1000 * 60 * 60 * 24 * 1;
	private long[] sectionslong;
	//
	private QueryBean queryBeen;
	private View mQueryTypeLayout;
	private View mQueryConditionLayout;
	// 历史查询类型选择 1.已对账 0，未对账
	private View mHistoryTypeChoseView;
	private Spinner mHistoryTypeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_account_enquriy);
		ininData();
		ininView();
	}

	private void ininView() {

		// title
		TextView mTitle = (TextView) findViewById(R.id.title_text);
		mTitle.setText("对账查询");
		ImageView back = (ImageView) findViewById(R.id.left_title_iv);
		back.setOnClickListener(this);
		//
		mQueryTypeLayout = findViewById(R.id.queryType_v);
		mQueryConditionLayout = findViewById(R.id.queryCondition_v);
		mQueryTypeLayout.setVisibility(View.VISIBLE);
		mQueryConditionLayout.setVisibility(View.INVISIBLE);
		mQueryTypeLv = (ListView) findViewById(R.id.queryType_lv);
		mAccountspinner = (Spinner) findViewById(R.id.accountSpinner);
		mTypeTv = (TextView) findViewById(R.id.queryType_tv);
		mFastSectionsSpinner = (Spinner) findViewById(R.id.fastSectionSpinner);
		mHistoryTypeChoseView = findViewById(R.id.historyTypyChoseView);
		mHistoryTypeSpinner = (Spinner) findViewById(R.id.historyTypySpinner);

		//
		mFastQueryBt = (Button) findViewById(R.id.fastQuery_bt);
		mSectionQueryBt = (Button) findViewById(R.id.sectionQuery_bt);
		mQueryBt = (Button) findViewById(R.id.query_bt);
		mSectionQueryBt.setOnClickListener(this);
		mTypeTv = (TextView) findViewById(R.id.queryType_tv);
		mSectionPickView = findViewById(R.id.sectionQueryView);
		mStartDateV = findViewById(R.id.startDate_v);
		mStartDateTv = (TextView) findViewById(R.id.startDate_statu);
		mEndDateV = findViewById(R.id.endDate_v);
		mEndDateTv = (TextView) findViewById(R.id.endDate_statu);

		mFastQueryBt.setOnClickListener(this);
		mQueryBt.setOnClickListener(this);
		mStartDateV.setOnClickListener(this);
		mEndDateV.setOnClickListener(this);

		List<Map<String, ?>> data = new ArrayList();
		Map map;
		for (int i = 0; i < types.length; i++) {
			map = new HashMap();
			map.put("icon", typeIcons[i]);
			map.put("type", types[i]);
			data.add(map);
			map = null;
		}
		SimpleAdapter typeAdapter = new SimpleAdapter(mContext, data,
				R.layout.item_query_type, new String[] { "icon", "type" },
				new int[] { R.id.iconIv, R.id.typeTv });
		mQueryTypeLv.setAdapter(typeAdapter);
		mQueryTypeLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				queryBeen.setQueryType(position);
				if (queryBeen.getQueryType() == QueryBean.TYPE_DZD_HZ_SQ) {
					IntentUtil.start_activity(mContext,
							BalanceReconciliationListActivity.class,
							"QueryBeen", queryBeen);
					return;
				}

				getAccounts(position);

			}
		});
		ArrayAdapter accountAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, accounts);
		accountAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAccountspinner.setAdapter(accountAdapter);
		mAccountspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				queryBeen.setAccount(accounts[position]);
				L.d(TAG, "accounts = " + accounts[position]);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ArrayAdapter<String> sectiontAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, sections);
		sectiontAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mFastSectionsSpinner.setAdapter(sectiontAdapter);
		mFastSectionsSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						queryBeen.setStratDate(TimeUtil.getYMDTime((System
								.currentTimeMillis() - sectionslong[position])));
						queryBeen.setEndDate(TimeUtil.getYMDTime(System
								.currentTimeMillis()));
						L.d(TAG,
								"StratDate = "
										+ TimeUtil.getYMDTime((System
												.currentTimeMillis() - sectionslong[position])));
						L.d(TAG,
								"EndDate = "
										+ TimeUtil.getYMDTime(System
												.currentTimeMillis()));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		mHistoryTypeSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						queryBeen.setHistoryType(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		showFastQueryView();
	}

	private void ininData() {

		mContext = this;
		mApplication = PushApplication.getInstance();
		mGson = mApplication.getGson();
		mSpUtil = mApplication.getSpUtil();
		//
		queryBeen = new QueryBean();
		queryBeen.setFromType(QueryBean.FROMTYPE_QUERYTYPE);
		accounts = new String[] {};

		types = getResources().getStringArray(R.array.queryType);
		typeParameters = getResources().getStringArray(
				R.array.queryTypeParameter);
		typeIcons = new int[] { R.drawable.leftico01, R.drawable.dqye,
				R.drawable.yedz, R.drawable.hqmx, R.drawable.hqmx,
				R.drawable.sq, R.drawable.ss };
		sections = getResources().getStringArray(R.array.querySections);
		sectionslong = new long[] { 7 * DAY_LONG, 30 * DAY_LONG, 90 * DAY_LONG };

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_title_iv:
			if (mQueryConditionLayout.getVisibility() == View.VISIBLE) {
				mQueryConditionLayout.setVisibility(View.INVISIBLE);
				mQueryTypeLayout.setVisibility(View.VISIBLE);

			} else {
				finish();
			}
			break;
		case R.id.fastQuery_bt:
			showFastQueryView();
			break;
		case R.id.sectionQuery_bt:
			showSectionQueryView();
			break;
		case R.id.query_bt:
			L.d(TAG, queryBeen.toString());
			Intent toQuetyIntent = null;
			switch (queryBeen.getQueryType()) {
			case QueryBean.TYPE_HQ_CK_DZD:
			case QueryBean.TYPE_DQ_CK_DZD:
			case QueryBean.TYPE_BZJ_CK_DZD:
				toQuetyIntent = new Intent(this,
						BalanceReconciliationListActivity.class);
				break;
			case QueryBean.TYPE_HQ_MX_DZD:
				toQuetyIntent = new Intent(this,
						DetailedReconciliationListActivity.class);
				break;
			case QueryBean.TYPE_WD_ZW_CX:
				toQuetyIntent = new Intent(this,
						AllNotTransitListActivity.class);
				break;

			default:
				break;
			}
			toQuetyIntent.putExtra("QueryBeen", queryBeen);
			startActivity(toQuetyIntent);
			break;
		case R.id.startDate_v:
			showDatePicker(new OnDateSetListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					String date = year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth;
					mStartDateTv.setText(TimeUtil.getYMDTime(new Date(
							year - 1900, monthOfYear, dayOfMonth).getTime()));
					queryBeen.setStratDate(date);
				}
			});
			break;
		case R.id.endDate_v:
			showDatePicker(new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					String date = year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth;

					mEndDateTv.setText(TimeUtil.getYMDTime(new Date(
							year - 1900, monthOfYear, dayOfMonth).getTime()));
					queryBeen.setEndDate(date);
				}
			});

			break;

		default:
			break;
		}

	}

	/**
	 * 显示时间段查询控件
	 */
	private void showSectionQueryView() {
		mSectionQueryBt.setEnabled(false);
		mFastQueryBt.setEnabled(true);
		if (mSectionPickView.getVisibility() == View.GONE) {
			mSectionPickView.setVisibility(View.VISIBLE);
		}
		if (mFastSectionsSpinner.getVisibility() == View.VISIBLE) {
			mFastSectionsSpinner.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示快速查询控件
	 */
	private void showFastQueryView() {
		mSectionQueryBt.setEnabled(true);
		mFastQueryBt.setEnabled(false);
		if (mFastSectionsSpinner.getVisibility() == View.GONE) {
			mFastSectionsSpinner.setVisibility(View.VISIBLE);
		}
		if (mSectionPickView.getVisibility() == View.VISIBLE) {
			mSectionPickView.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 * @Title: refreshBlcklogInfors
	 * @Description: TODO(获得对于查询类型下的账号集合)
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void getAccounts(int position) {
		L.d(TAG, "获得对于查询类型下的账号集合。。");
		String url;
		if (queryBeen.getQueryType() == QueryBean.TYPE_DZD_HZ_SQ
				|| queryBeen.getQueryType() == QueryBean.TYPE_WD_ZW_CX) {
			/**
			 * http://jhauto.gicp.net/accountclient/app/getAccountAll.action?
			 * SIGNATURE=123
			 * 
			 * public static final String ACCOUNT_ALL = BASIC_URL +
			 * "/getAccountAll.action?" + "SIGNATURE=%s"
			 */
			url = String.format(Urls.ACCOUNT_ALL, mSpUtil.getSignature());
		} else {
			url = String.format(Urls.GET_ACCOUNTS_URL, mSpUtil.getSignature(),
					typeParameters[position]);
		}
		new GetAccountsAsyncTask().execute(url);
	}

	// 屏蔽返回键的退出Activity作用
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mQueryConditionLayout.getVisibility() == View.VISIBLE) {
				mQueryConditionLayout.setVisibility(View.INVISIBLE);
				mQueryTypeLayout.setVisibility(View.VISIBLE);

			} else {
				finish();
			}

			return true;// 事件不再传递

		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDatePicker(OnDateSetListener dateListener) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dialog = new DatePickerDialog(this, dateListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dialog.show();
	}

	/**
	 * 显示账号列表
	 */
	private void initAccountsData() {
		ArrayAdapter accountAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,
				accounts);
		accountAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAccountspinner.setAdapter(accountAdapter);

		mQueryTypeLayout.setVisibility(View.INVISIBLE);
		mQueryConditionLayout.setVisibility(View.VISIBLE);
		mTypeTv.setText(types[queryBeen.getQueryType()]);
		queryBeen.setAccount(accounts[0]);
		
		if (queryBeen.getQueryType() == QueryBean.TYPE_DZD_HZ_SQ
				|| queryBeen.getQueryType() == QueryBean.TYPE_WD_ZW_CX
				|| queryBeen.getQueryType() == QueryBean.TYPE_HQ_MX_DZD) {
			mHistoryTypeChoseView.setVisibility(View.GONE);
		} else {
			mHistoryTypeChoseView.setVisibility(View.VISIBLE);
		}
	}

	class GetAccountsAsyncTask extends AsyncTask<String, Void, Boolean> {

		private Responses_GetAccounts responses_GetAccounts;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			L.d(TAG, "LoginAsyncTask--onPreExecute");

		}

		@Override
		protected Boolean doInBackground(String... params) {

			String result = "";
			try {

				result = HttpUtils.getByHttpClient(AccountEnquiryActivity.this,
						params[0]);// 无参数

				L.d(TAG, "GetAccountsAsyncTask---result" + result);
				responses_GetAccounts = mGson.fromJson(result,
						Responses_GetAccounts.class);
				// accounts = result.split(",");
				accounts = responses_GetAccounts.getAccounts();
				// accounts = TestDataUtil.getAccoutlist();
				L.d(TAG,
						"GetAccountsAsyncTask---accounts" + accounts.toString());
				if (accounts != null && accounts.length > 0) {
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
				initAccountsData();

			} else {
				UIHelper.HandleErrcode(mContext,
						responses_GetAccounts.getErrCode());

			}
		}

	}

}
