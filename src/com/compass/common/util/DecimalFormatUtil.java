package com.compass.common.util;

import java.text.DecimalFormat;
/**
 * 
* @Description: 格式化输出数字  
* @author daodao ( dao.dev@qq.com ) 
* @date 2014年12月11日 上午11:22:19 
*
 */
public class DecimalFormatUtil {
	public static String groubingSize3(Number  decimal ) {
		DecimalFormat df1=(DecimalFormat) DecimalFormat.getInstance(); 
		df1.setGroupingSize(3); 
		return df1.format(decimal); 
	}
}