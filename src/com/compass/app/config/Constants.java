package com.compass.app.config;

/**
 * 
 * @classDescription 系统常量
 * @date 2014年8月6日 下午6:12:37
 * @author 李小伟
 */
public class Constants {

	public static final class DBContentType {
		public static final String Content_list = "list";
		public static final String Content_content = "content";
		public static final String Discuss = "discuss";
	}

	public static final class WebSourceType {
		public static final String Json = "json";
		public static final String Xml = "xml";
	}

	// msg.what ---2014年7月29日15:37:41
	/**
	 * handle接收的message的状态字
	 */
	public static final class MsgWhatType {
		public final static int LOAD_OK = 0x0001;
		public final static int REFRESH_OK = 0x0002;
		public final static int LOAD_FAIL = 0x0003;
		public final static int LOAD_NUll = 0x0004;
	}

	/**
	 * baiduPush api
	 * 
	 */
	public static final class PushApi {
		
		 //测试api 
//		 public final static String API_KEY ="R4uGDeArIgCiFhUOih0NBTyd"; 
//		 public final static String SECRIT_KEY ="";
//		 
		
		
		
		// 正式api
		public final static String API_KEY = "2GjXn4GITdycKa1jsimYpKb5";
		public final static String SECRIT_KEY = "";
	}

	/**
	 * DataBase
	 * 
	 */
	public static final class DataBase {
		public static final int DB_VISION = 2;
	}

	/**
	 * Cache
	 * 
	 */
	public static final class Cache {
		public static final boolean UseCache = false;
		public static final boolean SubIdCache = false;//获得分校学生和家长id缓存控制
	}

	/**
	 * DeBug
	 * 
	 */
	public static final class DeBug {
		public static final boolean DEBUG = true;
		public static final boolean DEBUG_TOAST = true;
	}

	/**
	 * HeadUrl
	 * 
	 */
	public static final class HeadUrl {
		public static int PIXELS = 100;
		public static final String PLATFORM = "ANDROID_PLAT";
		// public static final String DEAF_HEADURL_TEACHRT =
		// "http://t10.baidu.com/it/u=1402163586,1144680497&fm=56";
		// public static final String DEAF_HEADURL_BABY =
		// "http://t11.baidu.com/it/u=2810113051,115881354&fm=56";
		public static final String DEAF_HEADURL_BABY = "";
		public static final String DEAF_HEADURL_TEACHRT = "";
	}

	/**
	 * other
	 * 
	 */
	public static final class Other {
		public static int PIXELS = 100;
		public static final String PLATFORM = "ANDROID_PLAT";
		//
	}
	/**
	 * other
	 * 
	 */
	public static final class PullMsg {
//		public static final long FirstPullInterval = 24*60*60*1000;
//		public static final long TriggerInterval = 10*60*1000;
		//
		public static final long FirstPullInterval = 12*60*60;
		public static final long TriggerInterval = 5*60;
	}
	/**
	 * other
	 * 
	 */
	public static final class Action {
		//
		public static final String ACTION_PULL_MSG = "com.wode.app.ation.pullMsg";
		public static final String ACTION_PUSH_MSG = "com.wode.app.ation.pushMsg";
		public static final String ACTION_PUSH_BLIND = "com.wode.app.ation.pushBlind";
	}
	/**
	 * tuling123
	 * 
	 */
	public static final class Tuling123 {
		//
		public static final String APIKEY = "ff23ad4d1ab0ad8400e5c120c9e1c636";
	}
}
