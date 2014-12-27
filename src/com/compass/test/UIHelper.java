package com.compass.test;

import android.app.Activity;
import android.content.Context;

import com.compass.activity.LoginActivity;
import com.compass.common.util.IntentUtil;
import com.compass.common.util.T;

public class UIHelper {
	private final static String TAG = UIHelper.class.getSimpleName();

	/**
	 * 
	* @Title: LoginResp 
	* @Description: TODO(登录返回) 
	* @return Responses_Login    返回类型 
	 */
	public static void HandleErrcode(Context context,int errCode) {
		
		/**
		 * 	 * 状态码参考表（errcode）
代码	解释	错误信息
0	此次请求服务器正确	
1	操作失败	根据业务返回不同
2	参数不全	Parameter is not complete
3	登录异常需重新登录	invaild signature
4	请求正常但是无业务数据	no data
5	系统出错	System is busy
	 */
		switch (errCode) {
		case 0:
			T.showShortDebug(context, "此次请求服务器正确");
			break;
		case 1:
			T.showShortDebug(context, "操作失败");
			break;
		case 2:
			T.showShortDebug(context, "参数不全");
			
			break;
		case 3:
			T.showShortDebug(context, "登录异常需重新登录");
			IntentUtil.start_activity(context, LoginActivity.class);
			((Activity)context).finish();
			
			break;
		case 4:
			T.showShortDebug(context, "暂无相关业务数据");
			break;
		case 5:
			T.showShortDebug(context, "系统出错");
			IntentUtil.start_activity(context, LoginActivity.class);
			((Activity)context).finish();
			break;

		default:
			break;
		}
		
	}


}
