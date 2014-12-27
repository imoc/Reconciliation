package com.compass.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 
 * @classDescription 默认展开的GridView 适用于被嵌套关系
 * @date 2014年6月6日 下午10:23:38
 * @author 李小伟
 */
public class ExpendGridView extends GridView {

	public ExpendGridView(Context context) {
		super(context);
	}

	public ExpendGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExpendGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}