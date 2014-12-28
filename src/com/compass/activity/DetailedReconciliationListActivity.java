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
import com.compass.bean.DetailCategoryListItemEntity;
import com.compass.bean.DetailListBean;
import com.compass.bean.DetailListBean.TOPMAP;
import com.compass.bean.EntityBase;
import com.compass.bean.QueryBean;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.DecimalFormatUtil;
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
 * @ClassName: QueryResultActivity
 * @Description: TODO(明细对账单：活期、保证金)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年12月9日 下午6:15:47
 * 
 */
public class DetailedReconciliationListActivity extends BaseListActivity
		implements OnClickListener {

	private String TAG = DetailedReconciliationListActivity.class
			.getSimpleName();

	public enum ParameterType {
		operate, detail_top, collect;
	}

	ParameterType paramType;
	public Activity mActivity = this;
	private List<DetailBean> items_list = new ArrayList<DetailBean>();
	private String more_url;
	private MyAdapter mAdapter;
	private DetailCategoryListItemEntity loadMoreEntity = new DetailCategoryListItemEntity();
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

	private DetailBean currentQueryResultBeen;
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
				initTopView(loadMoreEntity.getTOPMAP());
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

	protected void initTopView(DetailListBean.TOPMAP topmap) {
		((TextView) headView.findViewById(R.id.accountTv))
		.setText("账        号：" + topmap.getAccount());
		((TextView) headView.findViewById(R.id.agencyTv))
		.setText("机构名称：" + topmap.getAgencyTv());
		((TextView) headView.findViewById(R.id.subjectTv))
		.setText("科目号   ：" + topmap.getSubject());
		((TextView) headView.findViewById(R.id.nameTv))
		.setText("户       名：" + topmap.getName());

		
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
		 * http://jhauto.gicp.net/accountclient/app/getAccbookAccount.action?
		 * SIGNATURE
		 * =123&ACCOUNT=401103010300000019467&STARTDATE=2014-09-01&ENDDATE
		 * =2014-09-30&TYPE=0
		 * 
		 * http://jhauto.gicp.net/accountclient/app/getAccbookAccount.action?
		 * SIGNATURE
		 * =123&ACCOUNT=401103010300000019467&STARTDATE=2014-09-01&ENDDATE
		 * =2014-09-30&TYPE=0
		 * 
		 * public static final String Detailed_Reconciliation = BASIC_URL +
		 * "/getAccbookAccount.action?" + "SIGNATURE=%s" + "&ACCOUNT=%s" +
		 * "&STARTDATE=%s" + "&ENDDATE=%s" + "&TYPE=0"
		 * 
		   http://jhauto.gicp.net/accountclient/app/getAccbookAccount.action?
		   SIGNATURE
		   =123&ACCOUNT=401103010300000040930&STARTDATE=2014-8-1&ENDDATE
		   =2014-8-31&TYPE=0

		 * 
		 */
		more_url = String.format(Urls.Detailed_Reconciliation,
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
		headView = mInflater.inflate(R.layout.layout_query_detail_top, null);
		listview.addHeaderView(headView);
		
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

				DetailBean item = (DetailBean) mAdapter.getItem(position - 2);

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
				convertView = mInflater.inflate(R.layout.item_detail, null);
				holder.note_tv = (TextView) convertView
						.findViewById(R.id.note_tv);
				holder.date_tv = (TextView) convertView
						.findViewById(R.id.date_tv);
				holder.voucherNo_tv = (TextView) convertView
						.findViewById(R.id.voucherNo_tv);
				holder.debitAmount_tv = (TextView) convertView
						.findViewById(R.id.debitAmount_tv);
				holder.creditAmount_tv = (TextView) convertView
						.findViewById(R.id.creditAmount_tv);
				holder.transactionMechanism_tv = (TextView) convertView
						.findViewById(R.id.transactionMechanism_tv);
				holder.balance_tv = (TextView) convertView
						.findViewById(R.id.balance_tv);
				holder.operatorId_tv = (TextView) convertView
						.findViewById(R.id.operatorId_tv);
				holder.reviewId_tv = (TextView) convertView
						.findViewById(R.id.reviewId_tv);
				holder.directTv = (TextView) convertView
						.findViewById(R.id.directTv);
				//
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.date_tv.setText(item.getDate());
			holder.note_tv.setText(item.getNote());
			holder.voucherNo_tv.setText(item.getVoucherNo());
			holder.debitAmount_tv.setText(DecimalFormatUtil.groubingSize3(item.getDebitAmount()) );
			holder.creditAmount_tv.setText(DecimalFormatUtil.groubingSize3(item.getCreditAmount()));
			holder.balance_tv.setText(DecimalFormatUtil.groubingSize3(item.getBalance()));
			holder.transactionMechanism_tv.setText(item
					.getTransactionMechanism());
			holder.operatorId_tv.setText(item.getOperatorId());
			holder.reviewId_tv.setText(item.getReviewId());
			if (item.getDebitAmount()>0) {
				holder.directTv.setText("贷");
			}else {
				holder.directTv.setText("借");
			}
			//
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView date_tv;
		public TextView note_tv;
		public TextView voucherNo_tv;
		public TextView debitAmount_tv;
		public TextView creditAmount_tv;
		public TextView transactionMechanism_tv;
		public TextView balance_tv;
		public TextView operatorId_tv;
		public TextView reviewId_tv;
		public TextView directTv;

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
								DetailedReconciliationListActivity.this,
								more_url);
						L.d(TAG, "LoginAsyncTask---result" + result);
						DetailListBean resp = mGson.fromJson(result,
								DetailListBean.class);
						if (resp != null
								&& resp.getERRCODE() == EntityBase.ERRCODE_SUCCESS) {
							L.d(TAG, "DetailListBean = " + resp.toString());
							loadMoreEntity.setItems(resp.getDATA());
							loadMoreEntity.setTOPMAP(resp.getTOPMAP());
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
//					 TestDataUtil.getDetailCategoryListItemEntity();
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
