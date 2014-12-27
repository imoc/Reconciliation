package com.compass.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 
 * @classDescription 自动的跑马灯,普通的TextView可以实现跑马灯，但是只有当焦点在它上面时才有效。 如何做一个自动的跑马灯呢？
 *                   第一种：继承TextView，然后重写isFocused()方法就可以了，简单！
 * @date 2014年7月7日 上午11:55:46
 * @author 李小伟
 */
public class ScrollForeverTextView extends TextView {

	public ScrollForeverTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScrollForeverTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollForeverTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}