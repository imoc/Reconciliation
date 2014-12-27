package com.compass.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * �Զ���ListView���ı�item�ı�׼�߶�
 * 
 * @classDescription MyListView �Զ���ListView���ı�item�ı�׼�߶�
 * @date 2013��11��27�� ����8:30:13
 * @author �����
 */
public class ExpandListView extends ListView {

	public ExpandListView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ExpandListView(Context context) {

		super(context);
	}

	public ExpandListView(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);

	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}
}