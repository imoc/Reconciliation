package com.compass.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.compass.bean.DetailBean;
import com.compass.bean.EntityBase;
import com.compass.bean.QueryAccbookAccountCategoryListItemEntity;
import com.compass.bean.QueryBean;
import com.compass.bean.Responses_AccbookAccount;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.view.BaseListActivity;
import com.compass.widget.quick_action_bar.QuickActionWidget;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: QueryResultActivity
 * @Description: TODO(get data and show)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月13日 下午6:15:47
 * 
 */
public class QueryAccbookAccountActivity extends BaseListActivity implements
		OnClickListener {

	private String TAG = QueryAccbookAccountActivity.class.getSimpleName();

	public enum ParameterType {
		operate, detail_top, collect;
	}

	ParameterType paramType;
	public Activity mActivity = this;
	private List<DetailBean> items_list = new ArrayList<DetailBean>();
	private String more_url;
	private MyAdapter mAdapter;
	private QueryAccbookAccountCategoryListItemEntity loadMoreEntity = new QueryAccbookAccountCategoryListItemEntity();
	private PushApplication mApplication;
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private String[] queryTypeParmeter;
	//
	private QueryBean mQueryBeen;
	private Context mContext;
	private TextView accountTv;
	private TextView queryTypeTv;
	private TextView currencyTv;
	private int amount;
	private int fromtype;
	private DetailBean currentAccbookAccounttBeen;
	//
	private QuickActionWidget mBar;
	//
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
				break;
			}
			onLoad();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_homeschool_share);

		initData();
		initView();

	}

	private void initData() {
		mContext = this;
		mApplication = PushApplication.getInstance();
		mGson = mApplication.getGson();
		mSpUtil = mApplication.getSpUtil();
		mQueryBeen = (QueryBean) getIntent().getSerializableExtra("QueryBeen");//
		if (null == mQueryBeen) {// 如果为空，直接关闭
			L.d(TAG, "mQueryBeen = null");
			T.showShortDebug(mContext, "");
			finish();
		}
		L.d(TAG, "mQueryBeen = " + mQueryBeen);

		queryTypeParmeter = getResources().getStringArray(
				R.array.queryTypeParameter);
		fromtype = mQueryBeen.getFromType();

		if (fromtype == QueryBean.FROMTYPE_INDEX) {
			/*
			 * + "/requestTask.action?" + "signature=%s" + "&type=%s"
			 * Type有3种可选bzjsize，hqsize，dqsize
			 */
			more_url = String.format(Urls.REQUEST_TASK_URL,
					mSpUtil.getSignature(),
					queryTypeParmeter[mQueryBeen.getQueryType()]);
		} else {

			/**
			 * +public static final String GET_ACC_BOOK_ACCOUNT_URL = BASIC_URL 
			+ "/getAccbookAccount.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&STARTDATE=%s"
			+ "&ENDDATE=%s"
			+ "&TYPE=%s"
			 */
			more_url = String.format(Urls.Detailed_Reconciliation,
					mSpUtil.getSignature(),
					mQueryBeen.getAccount(),
					mQueryBeen.getStratDate(),
					mQueryBeen.getEndDate(),
					mQueryBeen.getAccountType()+""
					);
			
		}
		L.d(TAG, "more_url " + more_url);

	}

	/**
	 * init view
	 */
	private void initView() {

		// title
		TextView mTitle = (TextView) findViewById(R.id.title_text);
		mTitle.setText("查询结果");
		ImageView back = (ImageView) findViewById(R.id.left_title_iv);
		back.setOnClickListener(this);
		if (mQueryBeen.getQueryType()!=QueryBean.TYPE_HQ_MX_DZD) {
			// 将日历控件添加为listview的头部
			headView = mInflater.inflate(R.layout.layout_querylisttitle, null);
			// 将日历控件添加为listview的头部
			listview.addHeaderView(headView);
			if (fromtype == QueryBean.FROMTYPE_QUERYTYPE) {
				((TextView) headView.findViewById(R.id.accountTv))
						.setText("账        号：" + mQueryBeen.getAccount());
			}
			((TextView) headView.findViewById(R.id.queryTypeTv))
					.setText("查询类型 : "
							+ getResources().getStringArray(R.array.queryType)[mQueryBeen
									.getQueryType()]);

		} else {
			mTitle.setText("明细查询结果");
			// 将日历控件添加为listview的头部
			headView = mInflater
					.inflate(R.layout.layout_query_detail_top, null);
			// 将日历控件添加为listview的头部
			listview.addHeaderView(headView);
			((TextView) headView.findViewById(R.id.accountTv))
					.setText("账        号：" + mQueryBeen.getAccount());
			((TextView) headView.findViewById(R.id.queryTypeTv))
					.setText("查询类型 : "
							+ getResources().getStringArray(R.array.queryType)[mQueryBeen
									.getQueryType()]);

			// String detailTopUrl = String.format(
			// Urls.DETAIL_TOP_URL,
			// mQueryBeen.getQueryType(),
			// mQueryBeen.getAccount(),
			// mQueryBeen.getEndDate(),
			// "1"
			// );
			// new LoginAsyncTask().execute(detailTopUrl,
			// ParameterType.detail_top);

		}

		//
		listview.setPullRefreshEnable(false);
		listview.setXListViewListener(this);
		// construct the RelativeLayout
		mAdapter = new MyAdapter();

		mAdapter.appendToList(items_list);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mQueryBeen.getQueryType()!=QueryBean.TYPE_HQ_MX_DZD) {
					return;
				}
				// AccbookAccounttBeen item = (AccbookAccounttBeen) mAdapter
				// .getItem(position-2);
				// QueryBean queryBean = new QueryBean();
				// queryBean.setAccount(item.getAccount());
				// queryBean.setQueryType(QueryBean.type_detail);
				// queryBean.setFromType(QueryBean.FROMTYPE_QUERYTYPE);
				// Intent intent = new
				// Intent(QueryAccbookAccountActivity.this,QueryAccbookAccountActivity.class);
				// intent.putExtra("QueryBeen", queryBean);
				// startActivity(intent);

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
		List<DetailBean> mList = new ArrayList<DetailBean>();

		public MyAdapter() {
		}

		public void setList(List<DetailBean> lists) {
			if (lists == null) {
				return;
			}
			mList = lists;
			notifyDataSetChanged();
		}

		public void appendToList(List<DetailBean> lists) {
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
			DetailBean item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_balance, null);
				holder.D31TXRMKS_tv = (TextView) convertView
						.findViewById(R.id.D31TXRMKS_tv);
				holder.D31VOUN_tv = (TextView) convertView
						.findViewById(R.id.D31VOUN_tv);
				holder.D31ACCSTv = (TextView) convertView
						.findViewById(R.id.accountTv);
				holder.D31AMT_tv = (TextView) convertView
						.findViewById(R.id.D31AMT_tv);
				holder.date_tv = (TextView) convertView
						.findViewById(R.id.date_tv);
				holder.D31TELID_tv = (TextView) convertView
						.findViewById(R.id.D31TELID_tv);
				holder.auditState_tv = (TextView) convertView
						.findViewById(R.id.auditState_tv);
				holder.operate_tv = (TextView) convertView
						.findViewById(R.id.operate_tv);
				//
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.D31TXRMKS_tv.setText(item.getD31TXRMKS());
			holder.D31VOUN_tv.setText(item.getD31VOUN());
			holder.D31ACCSTv.setText(item.getD31ACCS());
			holder.D31AMT_tv.setText(item.getD31AMT());
			holder.date_tv.setText(item.getD31TXDT());
			holder.D31TELID_tv.setText(item.getD31TELID());
			holder.auditState_tv.setText("");

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView D31TXRMKS_tv;
		public TextView D31VOUN_tv;
		public TextView D31ACCSTv;
		public TextView D31AMT_tv;
		public TextView D31BALE_tv;
		public TextView date_tv;
		public TextView D31TELID_tv;
		public TextView auditState_tv;
		public TextView operate_tv;

	}

	@Override
	public void onRefresh() {
		L.d(TAG, "下拉刷新");
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
								QueryAccbookAccountActivity.this, more_url);
						L.d(TAG, "LoginAsyncTask---result" + result);
						Responses_AccbookAccount resp = mGson.fromJson(result,
								Responses_AccbookAccount.class);
						if (resp != null
								&& resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
							L.d(TAG, "Responses_AccDetail = " + resp.toString());
							loadMoreEntity.setItems(resp.getDATA());
							loadMoreEntity.setMore_url("url");
							mHandler.sendEmptyMessage(ok);
						} else {
							mHandler.sendEmptyMessage(fail);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						mHandler.sendEmptyMessage(fail);
					}

					super.run();

					// 测试数据--------------------------
					// loadMoreEntity = getData();
					// mHandler.sendEmptyMessage(ok);
					// super.run();
					// 测试数据==========================
				}
			}.start();

		}
	}

	public void onResume() {
		super.onResume();
	}


}
