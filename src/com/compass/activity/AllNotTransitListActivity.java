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
import com.compass.bean.EntityBase;
import com.compass.bean.NoTrBean;
import com.compass.bean.NoTrCategoryListItemEntity;
import com.compass.bean.NoTrListBean;
import com.compass.bean.QueryBean;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.T;
import com.compass.reconciliation.R;
import com.compass.test.UIHelper;
import com.compass.view.BaseListActivity;
import com.compass.widget.quick_action_bar.QuickActionWidget;
import com.google.gson.Gson;

/**
 * 
 * @Description: 未达账务查询
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年12月10日 下午6:15:47
 * 
 */
public class AllNotTransitListActivity extends BaseListActivity
		implements OnClickListener {

	private String TAG = AllNotTransitListActivity.class
			.getSimpleName();

	public enum ParameterType {
		operate, detail_top, collect;
	}

	ParameterType paramType;
	public Activity mActivity = this;
	private List<NoTrBean> items_list = new ArrayList<NoTrBean>();
	private String more_url;
	private MyAdapter mAdapter;
	private NoTrCategoryListItemEntity loadMoreEntity = new NoTrCategoryListItemEntity();
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
	private int fromType;
	private int queryType;

	private NoTrBean currentQueryResultBeen;
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
		/**
		 *http://jhauto.gicp.net/accountclient/app/allnotTransit.action?SIGNATURE=123&ACCOUNT=401103010300000019467&STARTDATE=2014-09-01&ENDDATE=2014-09-30
		 * 
		 *public static final String ALL_NOT_TRANSIT_LIST = BASIC_URL 
			+ "/allnotTransit.action?"
			+ "SIGNATURE=%s"
			+ "&ACCOUNT=%s"
			+ "&STARTDATE=%s"
			+ "&ENDDATE=%s"
			;
		 * 
		 */
		more_url = String.format(Urls.ALL_NOT_TRANSIT_LIST,
				mSpUtil.getSignature(), mQueryBeen.getAccount(),
				mQueryBeen.getStratDate(), mQueryBeen.getEndDate());

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
		
		/*headView = mInflater.inflate(R.layout.layout_query_detail_top, null);
		listview.addHeaderView(headView);
		((TextView) headView.findViewById(R.id.accountTv))
				.setText("账        号：" + mQueryBeen.getAccount());
		 */
		
		/*
		 * String detailTopUrl = String.format( Urls.DETAIL_TOP_URL,
		 * mQueryBeen.getQueryType(), mQueryBeen.getAccount(),
		 * mQueryBeen.getEndDate(), "1" ); new
		 * LoginAsyncTask().execute(detailTopUrl, ParameterType.detail_top);
		 */

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

				NoTrBean item = (NoTrBean) mAdapter.getItem(position - 2);

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
		List<NoTrBean> mList = new ArrayList<NoTrBean>();

		public MyAdapter() {
		}

		public void setList(List<NoTrBean> lists) {
			if (lists == null) {
				return;
			}
			mList = lists;
			notifyDataSetChanged();
		}

		public void appendToList(List<NoTrBean> lists) {
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
			NoTrBean item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_no_tr, null);
				holder.account_tv = (TextView) convertView
						.findViewById(R.id.account_tv);
				holder.noticeId_tv = (TextView) convertView
						.findViewById(R.id.noticeId_tv);
				holder.company_tv = (TextView) convertView
						.findViewById(R.id.company_tv);
				holder.date_tv = (TextView) convertView
						.findViewById(R.id.date_tv);
				holder.type_tv = (TextView) convertView
						.findViewById(R.id.type_tv);
				holder.checkContent_tv = (TextView) convertView
						.findViewById(R.id.checkContent_tv);
				holder.checkState_tv = (TextView) convertView
						.findViewById(R.id.checkState_tv);
				//
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.noticeId_tv.setText(item.getNoticeId());
			holder.account_tv.setText(item.getAccount());
			holder.company_tv.setText(item.getCompany());
			holder.date_tv.setText(item.getDate());
			//状态（0只查看，1已查复，2未查复
			switch (item.getCheckState()) {
			case 0:
				holder.checkState_tv.setText("只查看");
				break;
			case 1:
				holder.checkState_tv.setText("已查复");
				break;
			case 2:
				holder.checkState_tv.setText("未查复");
				break;
			}
			holder.type_tv.setText("");
			holder.checkContent_tv.setText("");
			//
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView noticeId_tv;
		public TextView account_tv;
		public TextView company_tv;
		public TextView date_tv;
		public TextView type_tv;
		public TextView checkContent_tv;
		public TextView checkState_tv;

	}

	@Override
	public void onRefresh() {
		L.d(TAG, "下拉刷新");

		// load(more_url, MsgWhatType.REFRESH_OK, MsgWhatType.LOAD_FAIL);
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
								AllNotTransitListActivity.this,
								more_url);
						L.d(TAG, "LoginAsyncTask---result" + result);
						NoTrListBean resp = mGson.fromJson(result,
								NoTrListBean.class);
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
					

//					 测试数据--------------------------
//					 loadMoreEntity =
//					 TestDataUtil.getNoTrCategoryListItemEntity();
//					 mHandler.sendEmptyMessage(ok);
//					 super.run();
//					 测试数据==========================
				}
			}.start();

		}
	}

	public void onResume() {
		super.onResume();
	}

}
