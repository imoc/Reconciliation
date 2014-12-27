package com.compass.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.compass.api.Urls;
import com.compass.app.PushApplication;
import com.compass.bean.BanklogInfor;
import com.compass.bean.BanklogInforItem;
import com.compass.bean.BanklogInforResult;
import com.compass.bean.QueryBean;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.L;
import com.compass.common.util.SharePreferenceUtil;
import com.compass.common.util.TimeUtil;
import com.compass.reconciliation.R;
import com.google.gson.Gson;

/**
 * 
 * @ClassName: AccountEnquiryActivity
 * @Description: TODO(account enquiry ，include "my account","detailed enquriy")
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月13日 上午11:53:17
 * 
 */
public class IndexActivity extends Activity implements OnClickListener {
	private final String TAG = IndexActivity.class.getSimpleName();
	// ininData
	private Context mContext;
	private PushApplication mApplication;
	private Gson mGson;
	private SharePreferenceUtil mSpUtil;
	private String[] types;
	// view
	private TextView mAliasTv;
	private TextView mLastLoginTimeTv;
	private ListView mBacklogInforsLv;
	private BacklogInforsAdapter adapter;
	private List<BanklogInforItem> mBanklogInforList;
	private BanklogInforResult backlogInforResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		ininData();
		ininView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshBlnklogInfors();
	}

	/**
	 * 
	 * @Title: refreshBlnklogInfors
	 * @Description: TODO(更新待办信息)
	 * @param 设定文件
	 * @return void 返回类型
	 */
	private void refreshBlnklogInfors() {
		L.d(TAG, "正在获取待办信息。。");
		String url = String.format(Urls.GET_BACKLOG_INFORS_URL,
				mSpUtil.getSignature());
		new GetBanklogInforsAsyncTask().execute(url);
	}

	private void ininView() {
		// title
		TextView mTitle = (TextView) findViewById(R.id.title_text);
		mTitle.setText("首页");
		ImageView back = (ImageView) findViewById(R.id.left_title_iv);
		back.setOnClickListener(this);
		//
		mAliasTv = (TextView) findViewById(R.id.aliasTv);
		mLastLoginTimeTv = (TextView) findViewById(R.id.lastLoginTime_statu);
		mBacklogInforsLv = (ListView) findViewById(R.id.banklog_inforsLv);

		mAliasTv.setText(mSpUtil.getAlias());
		mLastLoginTimeTv.setText(TimeUtil.getTime(mSpUtil.getLastLoginTime()));
		//
		mBanklogInforList = new ArrayList<BanklogInforItem>();
		adapter = new BacklogInforsAdapter();
		adapter.setList(mBanklogInforList);
		mBacklogInforsLv.setAdapter(adapter);
		mBacklogInforsLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BanklogInforItem item = (BanklogInforItem) mBacklogInforsLv
						.getItemAtPosition(position);
				QueryBean queryBeen = new QueryBean();
				queryBeen.setFromType(QueryBean.FROMTYPE_INDEX);
				queryBeen.setQueryType(item.getType());
				IntentUtil.start_activity(mContext, BalanceReconciliationListActivity.class,
						"QueryBeen", queryBeen);
			}
		});

	}

	private void ininData() {

		mApplication = PushApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();
		mContext = this;
		types = getResources().getStringArray(R.array.queryType);

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

	class BacklogInforsAdapter extends BaseAdapter {
		List<BanklogInforItem> mList = new ArrayList<BanklogInforItem>();

		public void setList(List<BanklogInforItem> lists) {
			if (lists == null) {
				return;
			}
			mList = lists;
			notifyDataSetChanged();
		}

		public void appendToList(List<BanklogInforItem> lists) {
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
			BanklogInforItem item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_banklog_infor, null);
				holder.typeTv = (TextView) convertView
						.findViewById(R.id.typeTv);
				holder.countTv = (TextView) convertView
						.findViewById(R.id.conutTv);
				//
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}
			holder.typeTv.setText(types[item.getType()]);
			holder.countTv.setText("有 " + item.getCount() + "条");
			//
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView typeTv;
		public TextView countTv;

	}

	class GetBanklogInforsAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			L.d(TAG, "LoginAsyncTask--onPreExecute");

		}

		@Override
		protected Boolean doInBackground(String... params) {

			String result = "";
			try {

				result = HttpUtils.getByHttpClient(IndexActivity.this,
						params[0]);// 无参数

				L.d(TAG, "GetBacklogInforsAsyncTask---result" + result);
				backlogInforResult = mGson.fromJson(result,
						BanklogInforResult.class);
				mBanklogInforList = toList(backlogInforResult);
				// mBanklogInforList = TestDataUtil.getBanklogInforList();
				L.d(TAG, "GetBacklogInforsAsyncTask---mBacklogInforList"
						+ mBanklogInforList.toString());
				if (mBanklogInforList != null && mBanklogInforList.size() > 0) {
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
				adapter.setList(mBanklogInforList);
			} else {
			}
		}

	}

	public List<BanklogInforItem> toList(BanklogInforResult backlogInforResult) {
		BanklogInfor backlogInfor = backlogInforResult.getDATA();
		List<BanklogInforItem> list = new ArrayList<BanklogInforItem>();
		list.add(new BanklogInforItem(QueryBean.TYPE_WDZ_BZJ_YE_DZD,
				backlogInfor.getBZJSIZE()));
		list.add(new BanklogInforItem(QueryBean.TYPE_WDZ_HQ_YE_DZD,
				backlogInfor.getHQSIZE()));
		list.add(new BanklogInforItem(QueryBean.TYPE_WDZ_DQ_YE_DZD,
				backlogInfor.getDQSIZE()));
		return list;
	}

}
