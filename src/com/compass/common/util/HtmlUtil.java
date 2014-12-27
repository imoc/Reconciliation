package com.compass.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 
 * @classDescription 消息工具
 * @date 2014年7月29日 上午11:02:10
 * @author 李小伟
 */
public class HtmlUtil {
	/**
	 * 去除html标记
	 * 
	 * @param message
	 *            传入的需要处理的String
	 * @return
	 */

	public static String replaceHtmlTag(String message) {

		if (TextUtils.isEmpty(message)) {
			return "";
		}
		String hackTxt;

		Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(message);
		hackTxt = matcher.replaceAll("");

		// 去除&nbsp;&amp;---2014年8月4日23:15:03
		Pattern pattern2 = Pattern.compile("&.+?;", Pattern.DOTALL);
		Matcher matcher2 = pattern2.matcher(hackTxt);
		hackTxt = matcher2.replaceAll("");

		return hackTxt;
	}

}