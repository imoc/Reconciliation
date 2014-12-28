package com.compass.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.app.config.Constants.MsgWhatType;
import com.compass.bean.BalanceBeen;
import com.compass.bean.BalanceCategoryListItemEntity;
import com.compass.bean.BalanceListBean;
import com.compass.bean.EntityBase;
import com.compass.bean.QueryBean;
import com.compass.bean.ResponsesOperate;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.DecimalFormatUtil;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.test.UIHelper;
import com.compass.view.BaseListActivity;
import com.compass.widget.quick_action_bar.QuickAction;
import com.compass.widget.quick_action_bar.QuickActionBar;
import com.compass.widget.quick_action_bar.QuickActionWidget;
import com.compass.widget.quick_action_bar.QuickActionWidget.OnQuickActionClickListener;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: QueryResultActivity
 * @Description: TODO(余额对账单（单账号、未对账）：活期、定期、保证金。)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月13日 下午6:15:47
 * 
 */
public class BalanceReconciliationListActivity extends BaseListActivity
		implements OnClickListener {

	private String TAG = BalanceReconciliationListActivity.class
			.getSimpleName();

	public enum ParameterType {
		operate, detail_top, collect;
	}

	ParameterType paramType;
	public Activity mActivity = this;
	private List<BalanceBeen> items_list = new ArrayList<BalanceBeen>();
	private String more_url;
	private MyAdapter mAdapter;
	private BalanceCategoryListItemEntity loadMoreEntity = new BalanceCategoryListItemEntity();
	private PushApplication mApplication;
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private String[] queryTypeParmeter;
	//
	private QueryBean mQueryBeen;
	private Context mContext;
	private int fromType;
	private int queryType;
	//查询历史类型 ；1.已对账 0，未对账 

	private BalanceBeen currentQueryResultBeen;
	//
	private QuickActionWidget mBar;
	//
	int operateType = BalanceBeen.TYPE_OPERATE_NULL;
	private View headView;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			switch (msg.what) {
			case MsgWhatType.LOAD_OK:
				more_url = loadMoreEntity.getMore_url();
				mAdapter.appendToList(loadMoreEntity.getItems());
				break;
			case MsgWhatType.REFRESH_OK:
				more_url = loadMoreEntity.getMore_url();
				mAdapter.setList(loadMoreEntity.getItems());
				break;
			case MsgWhatType.LOAD_FAIL:
				L.d(TAG, "加载，无新数据");
				T.showShortDebug(mContext, R.string.error_noData);
				break;
			default:
				UIHelper.HandleErrcode(mContext, msg.what-10);
				break;
			}
			onLoad();
		}

	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initView();

	}

	private void initData() {
		//
		mContext = this;
		mApplication = PushApplication.getInstance();
		mGson = mApplication.getGson();
		mSpUtil = mApplication.getSpUtil();
		mQueryBeen = (QueryBean) getIntent().getSerializableExtra("QueryBeen");
		//
		if (null == mQueryBeen) {// 如果为空，直接关闭
			L.d(TAG, "mQueryBeen = null");
			T.showShortDebug(mContext, "");
			finish();
		}
		L.d(TAG, "mQueryBeen = " + mQueryBeen);

		queryTypeParmeter = getResources().getStringArray(
				R.array.queryTypeParameter);
		fromType = mQueryBeen.getFromType();
		queryType = mQueryBeen.getQueryType();
		switch (fromType) {
		case QueryBean.FROMTYPE_INDEX:
			/*
			 * + "/requestTask.action?" + "signature=%s" + "&type=%s"
			 * Type有3种可选bzjsize，hqsize，dqsize
			 */
			more_url = String.format(Urls.REQUEST_TASK_URL,
					mSpUtil.getSignature(),
					queryTypeParmeter[mQueryBeen.getQueryType()]);
			break;
		case QueryBean.FROMTYPE_QUERYTYPE:
			switch (queryType) {
			
			case QueryBean.TYPE_DZD_HZ_SQ:
				/**
				http://jhauto.gicp.net/accountclient/app/accredit.action?SIGNATURE=123
			public static final String Receipt_Authorization_List = BASIC_URL 
					+ "/accredit.action?"
					+ "SIGNATURE=%s"
					;
				 */
				more_url = String.format(Urls.Receipt_Authorization_List,
						mSpUtil.getSignature()
						);
				break;

			default:
				/**
				 * + "/accDetail.action?" + "signature=%s" + "&account=%s" +
				 * "&accs=" + "&sdate=%s" + "&edate=%s" + "&type=0"
				 */
				more_url = String.format(Urls.ACC_DETAIL_URL,
						mSpUtil.getSignature(), mQueryBeen.getAccount(),
						mQueryBeen.getStratDate(), mQueryBeen.getEndDate(),
						mQueryBeen.getHistoryType()
						);
				break;
			}
			break;
		default:
			break;
		}
		L.d(TAG, "more_url " + more_url);

	}

	/**
	 * init view
	 */
	private void initView() {

		// title
		TextView mTitle = (TextView) findViewById(R.id.title_text);
		// mTitle.setText("查询结果");
		ImageView back = (ImageView) findViewById(R.id.left_title_iv);
		back.setOnClickListener(this);

		mTitle.setText(getResources().getStringArray(R.array.queryType)[mQueryBeen
				.getQueryType()]);

		//
		listview.setPullRefreshEnable(false);
		listview.setPullLoadEnable(false);
		listview.setXListViewListener(this);
		// construct the RelativeLayout
		mAdapter = new MyAdapter();

		mAdapter.appendToList(items_list);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				BalanceBeen item = (BalanceBeen) mAdapter
						.getItem(position-1);
				if (!item.getISVIP()) {
					T.showShort(mContext, "不是重点账户，无流水账单");
					L.v(TAG, "不是重点账户，无流水账单");
					return;
				}
				QueryBean queryBean = new QueryBean();
				queryBean.setAccount(item.getAccount());
				queryBean.setQueryType(QueryBean.TYPE_HQ_MX_DZD);
				queryBean.setFromType(QueryBean.FROMTYPE_QUERYTYPE);
				queryBean.setStratDate(item.getDate().substring(0, item.getDate().lastIndexOf("-"))+"-01");
				queryBean.setEndDate(item.getDate());;
				Intent intent = new Intent(
						BalanceReconciliationListActivity.this,
						DetailedReconciliationListActivity.class);
				intent.putExtra("QueryBeen", queryBean);
				startActivity(intent);

			}
		});

		// 初次加载
		onLoadMore();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_title_iv:
			finish();

			break;

		default:
			break;
		}

	}

	class MyAdapter extends BaseAdapter {
		List<BalanceBeen> mList = new ArrayList<BalanceBeen>();

		public MyAdapter() {
		}

		public void setList(List<BalanceBeen> lists) {
			if (lists == null) {
				return;
			}
			mList = lists;
			notifyDataSetChanged();
		}

		public void appendToList(List<BalanceBeen> lists) {
			if (lists == null) {
				return;
			}
			mList.addAll(lists);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {

			return mList.size();
		}

		@Override
		public Object getItem(int position) {

			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			BalanceBeen item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_balance, null);
				holder.company_tv = (TextView) convertView
						.findViewById(R.id.company_tv);
				holder.account_tv = (TextView) convertView
						.findViewById(R.id.accountTv);
				holder.balance_tv = (TextView) convertView
						.findViewById(R.id.balance_tv);
				holder.date_tv = (TextView) convertView
						.findViewById(R.id.date_tv);
				holder.auditState_tv = (TextView) convertView
						.findViewById(R.id.auditState_tv);
				holder.operate_tv = (TextView) convertView
						.findViewById(R.id.operate_tv);
				//
				holder.subAccount_v = convertView
						.findViewById(R.id.subAccount_v);
				holder.subAccount_tv = (TextView) convertView
						.findViewById(R.id.subAccount_tv);
				holder.checkState_v = convertView
						.findViewById(R.id.checkState_v);
				holder.checkState_tv = (TextView) convertView
						.findViewById(R.id.checkState_tv);
				holder.accountType_v = convertView
						.findViewById(R.id.accountType_v);
				holder.accountType_tv = (TextView) convertView
						.findViewById(R.id.accountType_tv);
				holder.institution_v = convertView
						.findViewById(R.id.institution_v);
				holder.institutionTv = (TextView) convertView
						.findViewById(R.id.institutionTv);

				//
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			switch (fromType) {
			case QueryBean.FROMTYPE_INDEX:
				holder.accountType_v.setVisibility(View.GONE);
				holder.subAccount_v.setVisibility(View.GONE);
				switch (queryType) {
				case QueryBean.TYPE_WDZ_HQ_YE_DZD:
				case QueryBean.TYPE_WDZ_DQ_YE_DZD:
					break;
				case QueryBean.TYPE_WDZ_BZJ_YE_DZD:
					break;
				}

				break;
			case QueryBean.FROMTYPE_QUERYTYPE:
				holder.accountType_v.setVisibility(View.GONE);
				holder.institution_v.setVisibility(View.GONE);
				holder.subAccount_v.setVisibility(View.GONE);
				switch (queryType) {
				case QueryBean.TYPE_WDZ_HQ_YE_DZD:
				case QueryBean.TYPE_WDZ_DQ_YE_DZD:
					holder.institution_v.setVisibility(View.VISIBLE);
					break;
				case QueryBean.TYPE_WDZ_BZJ_YE_DZD:
					holder.subAccount_v.setVisibility(View.VISIBLE);
					holder.institution_v.setVisibility(View.VISIBLE);
					break;
				case QueryBean.TYPE_DZD_HZ_SQ:
					holder.accountType_v.setVisibility(View.VISIBLE);
					break;
				}

				break;

			default:
				break;
			}
			
			
			switch (item.getCheckState()) {
			case 0:
				holder.checkState_tv.setText("未操作");
				break;
			case 1:
				holder.checkState_tv.setText("相符");
				break;
			case 2:
				holder.checkState_tv.setText("不相符");
				break;

			default:
				break;
			}
			
			switch (item.getAuditState()) {
			case BalanceBeen.TYPE_AUDITSTATE_NO_PASS:
				holder.auditState_tv.setText("审核未通过");
				break;
			case BalanceBeen.TYPE_AUDITSTATE_PASS:
				holder.auditState_tv.setText("审核通过");
				break;
			case BalanceBeen.TYPE_AUDITSTATE_WAIT:
				holder.auditState_tv.setText("待审核");
				break;
			default:
				break;
			}
			
			switch (item.getOperateType()) {
			case 1:
				holder.operate_tv.setText("未操作");
				holder.operate_tv.setTextColor(getResources().getColor(R.color.blue_name));
				break;
			case 0:
				holder.operate_tv.setText("对账完成");
				holder.operate_tv.setTextColor(getResources().getColor(R.color.black_content));;
				break;
			default:
				break;
			}

			holder.subAccount_tv.setText(item.getSubAccount());
			holder.company_tv.setText(item.getCompany());
			holder.institutionTv.setText(item.getInstitution());
			holder.accountType_tv.setText(item.getAccountType());
			holder.account_tv.setText(item.getAccount());
			holder.balance_tv.setText(DecimalFormatUtil.groubingSize3(item.getBalance()));
			holder.date_tv.setText(item.getDate());
			holder.operate_tv.setOnClickListener(new OperateOnClickListener(
					ParameterType.operate, item));
			//
			return convertView;
		}

		class OperateOnClickListener implements OnClickListener {
			ParameterType parameterType;
			BalanceBeen item;

			public OperateOnClickListener(ParameterType parameterType,
					BalanceBeen item) {
				this.parameterType = parameterType;
				this.item = item;
			}

			@Override
			public void onClick(View v) {

				switch (parameterType) {
				// 赞图标事件
				case operate:
					L.d(TAG, "onClick operate " + item);
					if (item.getOperateType() != 1) {
						return;
					}
					currentQueryResultBeen = item;
					showChildQuickActionBar(v);
					break;

				default:
					break;
				}

			}
		}
	}

	static class ViewHolder {
		public TextView company_tv;
		public TextView account_tv;
		public TextView currency_tv;
		public TextView balance_tv;
		public TextView date_tv;
		public TextView auditState_tv;
		public TextView operate_tv;
		//
		public View subAccount_v;
		public TextView subAccount_tv;
		public View checkState_v;
		public TextView checkState_tv;
		public View accountType_v;
		public TextView accountType_tv;
		public View institution_v;
		public TextView institutionTv;

	}

	private void showChildQuickActionBar(View view) {
		mBar = new QuickActionBar(this);
		mBar.addQuickAction(new QuickAction(this, R.drawable.ico_anonymous,
				R.string.agree));
		mBar.addQuickAction(new QuickAction(this, R.drawable.ico_show,
				R.string.disagree));
		mBar.setOnQuickActionClickListener(mActionListener);
		mBar.show(view);
	}

	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			
			switch (position) {

			case 0:
				operateType = BalanceBeen.TYPE_OPERATE_ARGEE;
				break;
			case 1:
				operateType = BalanceBeen.TYPE_OPERATE_DISARGEE;
				break;
			default:
				UIHelper.HandleErrcode(mContext, position);
				break;
			}
//			IntentUtil.start_activityForResult(mContext, VerifyActivity.class,IntentUtil.REQUEST_CODE_VERIFY );
			submitOperate();

		}

	};
	private void submitOperate() {
		/*
		 * public static final String OPERATE_URL = BASIC_URL +
		 * "/app/accUpdate.action?" + "signature=%s" + "&account=%s" +
		 * "&accs=null" + "&dates=%s" + "&clientcheck=%s" + "&type=0"
		 * 
		 * 	+ "/accUpdate.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&ACCS=null"
			+ "&DATES=%s"
			+ "&CLIENTCHECK=%s"
			+ "&CHECKE=%s"
			+ "&TYPE=0"
		 */
		
		String operateUrl = String.format(Urls.OPERATE_URL,
				mSpUtil.getSignature(),
				currentQueryResultBeen.getAccount(),
				currentQueryResultBeen.getDate(),
				operateType,
				currentQueryResultBeen.getAuditState()
				);
		new LoginAsyncTask().execute(operateUrl, ParameterType.operate,
				currentQueryResultBeen, operateType);
	}

	@Override
	public void onRefresh() {
		L.d(TAG, "下拉刷新");
		load(more_url, MsgWhatType.REFRESH_OK, MsgWhatType.LOAD_FAIL);
	}

	@Override
	public void onLoadMore() {
		load(more_url, MsgWhatType.LOAD_OK, MsgWhatType.LOAD_FAIL);

	}

	/**
	 * @deprecated加载数据
	 * @date 2014年7月29日15:42:35
	 * @author 李小伟
	 * @param more_url
	 *            接口
	 * @param ok
	 *            成功
	 * @param fail
	 *            失败
	 */
	private void load(final String more_url, final int ok, final int fail) {
		L.i(TAG, "onLoadMore() " + more_url);
		if (TextUtils.isEmpty(more_url)) {
			mHandler.sendEmptyMessage(1);
			return;
		} else {
			L.i(TAG, "onLoadMore() " + more_url);
			new Thread() {

				@Override
				public void run() {

					L.d(TAG, "more_url = " + more_url);
					String result;
					try {
						result = HttpUtils.getByHttpClient(
								BalanceReconciliationListActivity.this,
								more_url);
						L.d(TAG, "LoginAsyncTask---result" + result);
						BalanceListBean resp = mGson.fromJson(result,
								BalanceListBean.class);
						if (resp != null
								&& resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
							L.d(TAG, "Responses_AccDetail = " + resp.toString());
							loadMoreEntity.setItems(resp.getDATA());
							loadMoreEntity.setMore_url("url");
							mHandler.sendEmptyMessage(ok);
						} else {
							if (resp != null) {
								mHandler.sendEmptyMessage(resp.getErrCode()+10);
							}else {
								mHandler.sendEmptyMessage(fail);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						mHandler.sendEmptyMessage(fail);
					}

					super.run();
					// 测试数据--------------------------
					// loadMoreEntity =
					// TestDataUtil.getQueryCategoryListItemEntity();
					// mHandler.sendEmptyMessage(ok);
					// super.run();
					// 测试数据==========================
				}
			}.start();

		}
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
				submitOperate();
				break;

			default:
				
				break;
			}
			break;

		default:
			break;
		}
	}

	class LoginAsyncTask extends AsyncTask<Object, Void, Boolean> {

		private BalanceBeen item;
		private ParameterType paramType;
		private int operateType;
		private ResponsesOperate resp;

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
				result = HttpUtils.getByHttpClient(
						BalanceReconciliationListActivity.this,
						params[0].toString());// 无参数
				L.d(TAG, "LoginAsyncTask---result" + result);
				switch (paramType) {
				case operate:
					L.d(TAG, "LoginAsyncTask operate");
					item = (BalanceBeen) params[2];
					operateType = (Integer) params[3];
					resp = mGson.fromJson(result, ResponsesOperate.class);
					// resp = TestDataUtil.getOperateResp();
					if (resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
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
			if (result) {
				switch (paramType) {
				case operate:
					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
						T.showLong(getApplicationContext(), resp.getERRMSG());
					} else {
						T.showLong(getApplicationContext(), "操作成功");
					}
					item.setHasOperated(true);
					item.setOperateType(resp.getOperateType());
					item.setCheckState(resp.getCheckState());
					item.setAuditState(resp.getAuditState());
					mAdapter.notifyDataSetChanged();
					break;

				default:
					break;

				}
			} else {
				switch (paramType) {
				case operate:
					UIHelper.HandleErrcode(mContext, resp.getErrCode());
					if (null != resp && !TextUtils.isEmpty(resp.getERRMSG())) {
						T.showLong(getApplicationContext(), resp.getERRMSG());

					} else {

						T.showLong(getApplicationContext(), "操作失败");
					}
					break;

				default:
					break;

				}
			}

			// 返回内容置空
			resp = null;
		}

	}

	public void onResume() {
		super.onResume();
	}

}
