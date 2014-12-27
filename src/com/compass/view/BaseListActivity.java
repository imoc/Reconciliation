package com.compass.view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.compass.common.util.ImageUtil.ImageCallback;
import com.compass.reconciliation.R;
import com.compass.widget.xlistview.XListView;
import com.compass.widget.xlistview.XListView.IXListViewListener;

public abstract class BaseListActivity extends Activity implements
		IXListViewListener {

	protected XListView listview;
	protected View view;
	protected LayoutInflater mInflater;
	protected boolean mIsScroll = false;
	ObjectMapper mMapper = new ObjectMapper();
	protected BaseAdapter mAdapter;
	protected TextView title_text_tv;
	protected ImageView left_title_iv;
	protected ImageView right_title_iv;
	protected View titleBar_v;

	public ExecutorService executorService = Executors.newFixedThreadPool(5);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_list);
		mInflater = LayoutInflater.from(this);
		listview = (XListView) findViewById(R.id.list_view);
		initListView();
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		// 标题
		titleBar_v = findViewById(R.id.titleBar_v);
		title_text_tv = (TextView) findViewById(R.id.title_text);
		left_title_iv = (ImageView) findViewById(R.id.left_title_iv);
		right_title_iv = (ImageView) findViewById(R.id.right_title_iv);

	}

	private void initListView() {
	}

	public void startDetailActivity(Activity mContext, String url,
			String title, String shareTitle) {
		/*IntentUtil.start_activity(mContext, ShareDetailsActivity.class,
				new BasicNameValuePair("url", url), new BasicNameValuePair(
						"title", title), new BasicNameValuePair("sharetitle",
						shareTitle));*/
	}

	protected void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}

	protected ImageCallback callback1 = new ImageCallback() {

		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			// TODO Auto-generated method stub
			try {
				ImageView img = (ImageView) listview.findViewWithTag(imagePath);
				img.setImageBitmap(bitmap);
			} catch (NullPointerException ex) {
				Log.e("error", "ImageView = null");
			}
		}
	};

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
