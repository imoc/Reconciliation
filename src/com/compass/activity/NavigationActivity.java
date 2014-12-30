package com.compass.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mobstat.StatService;
import com.compass.adapter.ViewPagerAdapter;
import com.compass.reconciliation.R;
import com.compass.view.ui.base.BaseActivity;
import com.compass.widget.FlowIndicator;

/**
 * 页面导航界面
 * 
 * @classDescription NavigationActivity
 * @date 2014年5月20日 下午6:43:27
 * @author 李小伟
 */
public class NavigationActivity extends BaseActivity implements
		OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private FlowIndicator mFlowIndicator; // 页面导航圆形指针
	private int nav_pics[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		initPics();

		// 初始化页面
		initViews();

	}

	/**
	 * 得到导航背景资源
	 */
	private void initPics() {
		nav_pics = new int[] { R.drawable.nav1, R.drawable.nav2,
				R.drawable.nav3, R.drawable.nav4 };

	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		// 初始化引导图片列表
		views = new ArrayList<View>();
		int length = nav_pics.length;
		View view;
		for (int i = 0; i < length; i++) {
			if (i == length - 1) {
				view = inflater.inflate(R.layout.nav_last, null);
			} else {
				view = inflater.inflate(R.layout.nav_first, null);
			}
			view.setBackgroundResource(nav_pics[i]);
			views.add(view);
			view = null;
		}

		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views, this);

		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		//
		mFlowIndicator = (FlowIndicator) findViewById(R.id.mFlowIndicator);
		mFlowIndicator.setCount(vpAdapter.getCount() - 1);
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int position) {
		// 设置底部小点选中状态
		if (position == vpAdapter.getCount() - 1) {
			mFlowIndicator.setVisibility(View.GONE);
		} else {
			mFlowIndicator.setVisibility(View.VISIBLE);
			mFlowIndicator.setSeletion(position);
		}
	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}