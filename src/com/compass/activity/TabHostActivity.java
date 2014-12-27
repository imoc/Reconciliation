package com.compass.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.compass.app.PushApplication;
import com.compass.common.util.L;
import com.compass.common.util.T;
import com.compass.reconciliation.R;

/**
 * 
* @ClassName: SchoolItemActivity 
* @Description: TODO() 
* @author daodao ( dao.dev@qq.com ) 
* @date 2014年11月12日 下午3:02:50 
*
 */
@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity implements OnClickListener {

	private String TAG = TabHostActivity.class.getSimpleName();
	public static View tabBar;
	public static String TAB_TAG_HOME = "home";
	public static String TAB_TAG_CHANNEL = "channel";
	public static String TAB_TAG_ACCOUNT = "account";
	public static String TAB_TAG_SEARCH = "search";
	public static String TAB_TAB_MORE = "more";
	public static TabHost mTabHost;
	public static View mTabcontent;
	static final int COLOR1 = Color.parseColor("#787878");
	static final int COLOR2 = Color.parseColor("#ffffff");
	ImageView mBut1, mBut2, mBut3, mBut4, mBut5;
	TextView mCateText1, mCateText2, mCateText3, mCateText4, mCateText5;

	Intent mIndexItent, mBankQueryIntent,
			mLearningProgressIntent, mSchoolInformationIntent,
			mSetCenterIntent;

	int mCurTabId = R.id.chart_v;

	// Animation
	private Animation left_in, left_out;
	private Animation right_in, right_out;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		//
		PushApplication.getInstance().addActivity(this);
		//
		initData();
		prepareAnim();
		prepareIntent();
		setupIntent();
		prepareView();
		// 默认首页-----------------
		mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
		mBut1.setImageResource(R.drawable.png_47);
		mCateText1.setTextColor(COLOR2);

	}

	private void initData() {

	}

	public static void setTabBarVisibility(int visibility) {
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		switch (visibility) {
		case View.VISIBLE:
			tabBar.setVisibility(View.VISIBLE);
			lp.bottomMargin = 47;
			mTabcontent.setLayoutParams(lp);
			break;
		case View.INVISIBLE:
			tabBar.setVisibility(View.INVISIBLE);
			lp.bottomMargin = 0;
			mTabcontent.setLayoutParams(lp);

			break;

		default:
			break;
		}
	}

	private void prepareAnim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);

		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}

	private void prepareView() {
		tabBar = findViewById(R.id.tab1);
		mTabcontent = findViewById(android.R.id.tabcontent);
		mBut1 = (ImageView) findViewById(R.id.chart_iv);
		mBut2 = (ImageView) findViewById(R.id.share_iv);
//		mBut3 = (ImageView) findViewById(R.id.school_iv);
		mBut4 = (ImageView) findViewById(R.id.progress_iv);
		mBut5 = (ImageView) findViewById(R.id.curriculum_iv);
		findViewById(R.id.chart_v).setOnClickListener(this);
		findViewById(R.id.share_v).setOnClickListener(this);
//		findViewById(R.id.school_v).setOnClickListener(this);
		findViewById(R.id.progress_v).setOnClickListener(this);
		findViewById(R.id.curriculum_v).setOnClickListener(this);
		mCateText1 = (TextView) findViewById(R.id.chart_tv);
		mCateText2 = (TextView) findViewById(R.id.share_tv);
//		mCateText3 = (TextView) findViewById(R.id.school_tv);
		mCateText4 = (TextView) findViewById(R.id.progress_tv);
		mCateText5 = (TextView) findViewById(R.id.curriculum_tv);
	}

	private void prepareIntent() {
		mIndexItent = new Intent(this, IndexActivity.class);
//		mBankQueryIntent = new Intent(this, BankMainActivity.class);
		mBankQueryIntent = new Intent(this, AccountEnquiryActivity.class);
		mLearningProgressIntent = new Intent(this,
				SetCenterActivity.class);
		mSetCenterIntent = new Intent(this, SetCenterActivity.class);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			mBut1.performClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setupIntent() {
		mTabHost = getTabHost();
		mTabHost.addTab(buildTabSpec(TAB_TAG_HOME,
				R.string.category_communication, R.drawable.png_06,
				mIndexItent));
		mTabHost.addTab(buildTabSpec(TAB_TAG_CHANNEL, R.string.category_share,
				R.drawable.png_03, mBankQueryIntent));
		mTabHost.addTab(buildTabSpec(TAB_TAG_SEARCH, R.string.category_school,
				R.drawable.png_57, mSchoolInformationIntent));
		mTabHost.addTab(buildTabSpec(TAB_TAG_ACCOUNT,
				R.string.category_progress, R.drawable.png_09,
				mLearningProgressIntent));
		mTabHost.addTab(buildTabSpec(TAB_TAB_MORE,
				R.string.category_curriculum, R.drawable.png_12,
				mSetCenterIntent));
	}

	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	public static void setCurrentTabByTag(String tab) {
		mTabHost.setCurrentTabByTag(tab);
	}

	@Override
	public void onClick(View v) {
		 
		if (mCurTabId == v.getId()) {
			return;
		}
		mBut1.setImageResource(R.drawable.png_06);
		mBut2.setImageResource(R.drawable.png_03);
//		mBut3.setImageResource(R.drawable.png_57);
		mBut4.setImageResource(R.drawable.png_09);
		mBut5.setImageResource(R.drawable.png_12);
		mCateText1.setTextColor(COLOR1);
		mCateText2.setTextColor(COLOR1);
//		mCateText3.setTextColor(COLOR1);
		mCateText4.setTextColor(COLOR1);
		mCateText5.setTextColor(COLOR1);
		int checkedId = v.getId();
		final boolean o;
		if (mCurTabId < checkedId)
			o = true;
		else
			o = false;
		if (o)
			mTabHost.getCurrentView().startAnimation(left_out);
		else
			mTabHost.getCurrentView().startAnimation(right_out);
		switch (checkedId) {
		case R.id.chart_v:
			//
			L.d(TAG, "进入HomeSchoolCommunicationActivity");
			T.showShortDebug(getApplicationContext(),
					"进入HomeSchoolCommunicationActivity");
			//
			mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			mBut1.setImageResource(R.drawable.png_47);
			mCateText1.setTextColor(COLOR2);
			break;
		case R.id.share_v:
			//
			L.d(TAG, "进入HomeSchoolShareActivity");
			T.showShortDebug(getApplicationContext(),
					"进入HomeSchoolShareActivity");
			//
			mTabHost.setCurrentTabByTag(TAB_TAG_CHANNEL);
			mBut2.setImageResource(R.drawable.png_48);
			mCateText2.setTextColor(COLOR2);
			break;
		case R.id.progress_v:
			//
			L.d(TAG, "进入LearningProgressActivity");
			T.showShortDebug(getApplicationContext(),
					"进入LearningProgressActivity");
			//
			mTabHost.setCurrentTabByTag(TAB_TAG_ACCOUNT);
			mBut4.setImageResource(R.drawable.png_49);
			mCateText4.setTextColor(COLOR2);
			break;
		case R.id.curriculum_v:
			//
			L.d(TAG, "进入CurriculumActivity");
			T.showShortDebug(getApplicationContext(), "进入CurriculumActivity");
			//
			mTabHost.setCurrentTabByTag(TAB_TAB_MORE);
			mBut5.setImageResource(R.drawable.png_50);
			mCateText5.setTextColor(COLOR2);
			break;
		default:
			break;
		}

		if (o)
			mTabHost.getCurrentView().startAnimation(left_in);
		else
			mTabHost.getCurrentView().startAnimation(right_in);
		mCurTabId = checkedId;
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
