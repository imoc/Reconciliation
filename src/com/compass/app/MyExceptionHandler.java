package com.compass.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.compass.api.Urls;
import com.compass.common.https.HttpUtils;
import com.compass.common.util.L;
import com.compass.reconciliation.R;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 * 
 * @author way
 * 
 */
public class MyExceptionHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private Thread.UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
	private static MyExceptionHandler INSTANCE = new MyExceptionHandler();// CrashHandler实例
	private Context mContext;// 程序的Context对象
	private Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息和异常信息
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss");// 用于格式化日期,作为日志文件名的一部分
	private PushApplication crp;

	/** 保证只有一个CrashHandler实例 */
	private MyExceptionHandler() {

	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static MyExceptionHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		crp = (PushApplication) mContext;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
		Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
	}

	/**
	 * 当UncaughtException发生时会转入该重写的方法来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();
		try {
			if (!handleException(ex) && mDefaultHandler != null) {
				// 如果自定义的没有处理则让系统默认的异常处理器来处理
				mDefaultHandler.uncaughtException(thread, ex);
			} else {
				try {
					Thread.sleep(5000);// 如果处理了，让程序继续运行5秒再退出，保证异常信息上传到服务器数据库中
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 退出程序
				PushApplication.getInstance().exit();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 *            异常信息
	 * @return true 如果处理了该异常信息;否则返回false.
	 * @throws IOException
	 */
	public boolean handleException(Throwable ex) throws IOException {
		if (ex == null)
			return false;
		new Thread() {
			public void run() {
				Looper.prepare();

				Toast toast = Toast.makeText(mContext, R.string.crash_tips,
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0); // 把toast 显示到页面的中央
				toast.show();

				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		// saveCrashInfo2File(ex);
		sendException(ex);
		return true;
	}

	/**
	 * 关闭项目
	 */
	public void killSystem() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	/**
	 * 发送异常信息至服务器数据库
	 * 
	 * @throws IOException
	 * */
	public void sendException(final Throwable ex) throws IOException {
		int version = 3;
		String manufacturer = "";
		String model = "";
		String device = "";
		try {

			Class<android.os.Build.VERSION> build_version_class = android.os.Build.VERSION.class;
			// 取得 android 版本
			java.lang.reflect.Field field = build_version_class
					.getField("SDK_INT");
			version = (Integer) field.get(new android.os.Build.VERSION());

			Class<android.os.Build> build_class = android.os.Build.class;
			// 取得牌子
			java.lang.reflect.Field manu_field = build_class
					.getField("MANUFACTURER");
			manufacturer = (String) manu_field.get(new android.os.Build());
			// 取得型號
			java.lang.reflect.Field field2 = build_class.getField("MODEL");
			model = (String) field2.get(new android.os.Build());
			// 模組號碼
			java.lang.reflect.Field device_field = build_class
					.getField("DEVICE");
			device = (String) device_field.get(new android.os.Build());
			// Log.e("android", "牌子:" + manufacturer + " 型號:" + model +
			// " SDK版本:" + version + " 模組號碼:" + device);
		} catch (NoSuchFieldException e) {
			version = 3;
		} catch (Exception e) {
			version = 3;
		}

		final String device_detail = "牌子:" + manufacturer + " 型号:" + model
				+ " SDK版本:" + version + " 手机模组号码:" + device;
		// final Map<String,String>map = new HashMap<String,String>();
		// map.put("SqlId", "phone_exception");
		// map.put("device_type","1");
		// map.put("ver","1.4");
		// map.put("device_detail", device_detail);
		// map.put("exception_content",getException(ex));//
		// map.put("userId",crp.getSpUtil().getStudentContactId());//用户id
		// map.put("schoolId", crp.getSpUtil().getStudentId());
		new Thread(new Runnable() {
			@Override
			public void run() {
				// String url = HttpUtil.BASE_URL;
				// url+="Common/Add.do";
				// try
				// {
				// HttpUtil.postRequest(url, map);
				// }
				// catch (Exception e1)
				// {
				// e1.printStackTrace();
				// }

				try {
					L.d(TAG, "Urls.DEBUG_URL=" + Urls.DEBUG_URL + "SqlId="
							+ "insertException" + "type=" + "1" + "verNum="
							+ crp.getSpUtil().getClientVerNum() + ""
							+ "device_detail=" + device_detail
							+ "exceptionContent=" + getException(ex)
							);
					String result = HttpUtils.postByHttpClient(crp,
							Urls.DEBUG_URL, new BasicNameValuePair("SqlId",
									"insertException"), new BasicNameValuePair(
									"type", "1"), new BasicNameValuePair(
									"verNum", crp.getSpUtil().getClientVerNum()
											+ ""), new BasicNameValuePair(
									"device_detail", device_detail),
							new BasicNameValuePair("exceptionContent",
									getException(ex)));
					L.d(TAG, "result-" + result);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param context
	 */
	public void collectDeviceInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();// 获得包管理器
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);// 得到该应用的信息，即主Activity
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				info.put("versionName", versionName);
				info.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		Field[] fields = Build.class.getDeclaredFields();// 反射机制
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				info.put(field.getName(), field.get("").toString());
				L.d(TAG, field.getName() + ":" + field.get(""));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : info.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\r\n");
		}
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		ex.printStackTrace(pw);
		Throwable cause = ex.getCause();
		// 循环着把所有的异常信息写入writer中
		while (cause != null) {
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close();// 记得关闭
		String result = writer.toString();
		sb.append(result);
		// 保存文件
		long timetamp = System.currentTimeMillis();
		String time = format.format(new Date());
		String fileName = "crash-" + time + "-" + timetamp + ".log";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + "crash");
				Log.i("CrashHandler", dir.toString());
				if (!dir.exists())
					dir.mkdir();
				FileOutputStream fos = new FileOutputStream(new File(dir,
						fileName));
				fos.write(sb.toString().getBytes());
				fos.close();
				return fileName;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * 将异常信息转化成字符串
	 * 
	 * @param t
	 * @return
	 * @throws IOException
	 */

	private static String getException(Throwable t) throws IOException {

		if (t == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			t.printStackTrace(new PrintStream(baos));
		} finally {
			baos.close();
		}

		return baos.toString();

	}
}