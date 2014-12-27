package com.compass.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.compass.reconciliation.R;
import com.compass.widget.CircularProgressBarView;

/**
 * @classDescription DownLoadFile APK文件下载
 *                   1.问题，下载后，如何自动安装，打开,2,问题；成功下载后，未安装成功退出，下次又要重新下载；
 * @date 2013年12月16日 下午5:10:13
 * @author 邢如更
 * 
 */
public class DownLoadFile {
	private String TAG = DownLoadFile.class.getSimpleName();
	private String webUrl;// web服务器返回的URL
	private String destination;// 根目录
	private Context context;// 上下文
	private CircularProgressBarView mProgress;// 进度条
	private static final int DOWN_UPDATE = 1, DOWN_OVER = 2;// 1:更新进度条,2:更新完成
	private int progress;// 进度条当前值
	private boolean interceptFlag = false;// 是否取消下载 true:取消,false:下载
	private File apkFile;// APK文件
	private String apkSavePath = "/GoTOSchool/download/";// APK保存路径
	private String filename;// APK文件名
	private LayoutInflater inflater;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:

				defaultInstall(apkFile);
				break;

			}
		}

	};

	public DownLoadFile(Context context, String webUrl, String destination) {
		this.context = context;
		this.webUrl = webUrl;
		// 根据downloadUrl 获得本地保存文件名；
		filename = webUrl.substring(webUrl.lastIndexOf("/") + 1);
		this.destination = destination;
		inflater = LayoutInflater.from(context);
	}

	/**
	 * 下载dialog
	 * 
	 * @param isDelete
	 *            是否必须下载新版本false:必须,true:可以不更新
	 * @param listener
	 */
	public void downLoadDialog(boolean isDelete, final OnClickListener listener) {
		// isDelete:true可更新，也可以不更新，false:必须更新
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("更新");// 设置标题
		builder.setMessage("已有新版本发布,请您及时更新！");
		builder.setIcon(R.drawable.ic_launcher);// 设置标题图标
		builder.setCancelable(false);// 设置是否能取消
		builder.setPositiveButton("立即更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 缓存目录
				if (!CommonUtil.sdCardIsAvailable())/* true 为可用 */{
					destination = context.getFilesDir() + File.separator
							+ filename; // data里的缓存
					L.d("sdCard", "不可用");
					L.d("destination", destination);
				} else {
					destination = getExternalCacheDir(context) + apkSavePath
							+ filename; // sd卡
					L.d("sdCard", "可用");

				}
				dialog.dismiss();
				showDownloadDialog(listener);
			}
		});
		if (isDelete) {// true可更新，也可以不更新，false:必须更新
			builder.setNegativeButton("稍后更新", listener);
			builder.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (KeyEvent.KEYCODE_BACK == keyCode) {
						return true;
					}
					return false;
				}
			});
		}
		try {
			builder.create().show();
		} catch (Exception e) {
			e.printStackTrace();
			L.d(TAG, "WelcomeActivity has Finished !!!");
		}

	}

	/**
	 * 下载进度对话框
	 * 
	 * @param listener
	 */
	private void showDownloadDialog(OnClickListener listener) {

		View view = inflater.inflate(R.layout.activity_progress, null);
		mProgress = (CircularProgressBarView) view
				.findViewById(R.id.idCircularProBar);
		mProgress.setProgress(0);
		AlertDialog builder = new AlertDialog.Builder(context)
				.setNegativeButton("取消", listener).setTitle("下载进度").create();
		builder.setInverseBackgroundForced(true);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setView(view);
		builder.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (KeyEvent.KEYCODE_BACK == keyCode) {
					dialog.dismiss();
					return true;
				}
				return false;
			}
		});
		builder.show();

		downloadApk();// 下载APK

	}

	/**
	 * 开启线程下载APK
	 */
	private void downloadApk() {
		new Thread(downApkRunnable).start();
	}

	private Runnable downApkRunnable = new Runnable() {
		@Override
		public void run() {
			FileOutputStream fos = null;
			InputStream is = null;
			try {
				URL url = new URL(webUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				if (length > 0) {
					is = conn.getInputStream();
					File file = new File(destination);
					if (!file.exists()) {
						file.getParentFile().mkdirs();
					} else {
						file.delete();
						// file.createNewFile();
						file.getParentFile().mkdirs();
					}
					apkFile = new File(destination);
					// fos = new FileOutputStream(apkFile);
					// fos =
					// context.openFileOutput("test.apk",context.MODE_WORLD_READABLE);
					if (!CommonUtil.sdCardIsAvailable()) {
						L.d("apkFile.getName()", apkFile.getName());
						fos = context.openFileOutput(apkFile.getName(),
								context.MODE_WORLD_READABLE);
					} else {
						fos = new FileOutputStream(apkFile);
					}
					int count = 0;
					byte buf[] = new byte[1024 * 2];
					int numRead = 0;
					do {
						numRead = is.read(buf);
						count += numRead;
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						handler.sendEmptyMessage(DOWN_UPDATE);
						if (numRead <= 0) {
							// 下载完成通知安装
							handler.sendEmptyMessage(DOWN_OVER);
							break;
						}
						fos.write(buf, 0, numRead);
					} while (!interceptFlag);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {// 关闭流
					if (fos != null) {
						fos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	@SuppressLint("NewApi")
	private static String getExternalCacheDir(Context context) {
		// android 2.2 以后才支持的特性
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir().getPath() + File.separator
					+ "request";
		}

		// Before Froyo we need to construct the external cache dir ourselves
		// 2.2以前我们需要自己构造
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/request/";
		return Environment.getExternalStorageDirectory().getPath() + cacheDir;
	}

	/**
	 * 下载完成后进入安装页面
	 * 
	 * @param file
	 *            APK存储路径
	 */
	public void defaultInstall(File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		//
		// ((Activity)context).finish();
		// PushApplication.getInstance().clearActivitys();
		// L.d(TAG, "---------------clearActivitys");
		// T.showLong(context, "clearActivitys");
		context.startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
