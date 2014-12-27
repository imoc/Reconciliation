package com.compass.common.util;

import android.text.TextUtils;

public class FacePatternUtils {

	/**
	 * 接受客户端发回来的内容，过滤表情 汉字转代码
	 */
	public static String setPushContent(String content) {
		if (TextUtils.isEmpty(content)) {
			return content;
		}

		String pattern = "\\[(\\S{1,3})\\]";
		// String pattern = "\\[(.*?)\\]";
		content = content.replaceAll(pattern, "<img src=\"[$1]\">");

		return content;
	}

	public static void main(String[] args) {
		String a = "[害羞[色][害羞][色][色][害羞][色][色表情]";

		System.out.println(setPushContent(a));

	}

}
