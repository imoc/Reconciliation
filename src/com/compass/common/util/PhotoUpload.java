package com.compass.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class PhotoUpload {

	private static final Integer UPLOADPHOTOFAIL = 1;// 图片上传失败
	private static final Integer UPLOADPHOTOSOBIG = 2;// 图片过大
	private static final Integer UPLOADPHOTOSUCCESS = 3;// 图片上传成功
	private static Context context;
	private static ProgressDialog pregressDialog;

	// private static CrashApplication crp;
	/* 上传文件至Server的方法 */
	// static public int uploadFile(String actionUrl,String photoPath,String
	// photodir,String imgName,Context cxt)
	// {
	// context = cxt;
	// crp = (CrashApplication) context.getApplicationContext();
	// String end ="\r\n";
	// String twoHyphens ="--";
	// String boundary ="*****";
	// String newName="image.jpg";
	// int filesize = 0;
	// int upfilesize = 0;
	// try
	// {
	// System.out.println("能否得到当前用户ID======="+crp.getPid());
	// actionUrl+=photodir;
	// actionUrl+="&uid="+crp.getPid();
	// URL url =new URL(actionUrl);
	//
	// HttpURLConnection con=(HttpURLConnection)url.openConnection();
	// /* 允许Input、Output，不使用Cache */
	// con.setDoInput(true);
	// con.setDoOutput(true);
	// con.setUseCaches(false);
	// /* 设置传送的method=POST */
	// con.setRequestMethod("POST");
	// /* setRequestProperty */
	// con.setRequestProperty("Connection", "Keep-Alive");
	// con.setRequestProperty("Charset", "UTF-8");
	// con.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
	// /* 设置DataOutputStream */
	// DataOutputStream ds = new DataOutputStream(con.getOutputStream());
	// File file =new File(photoPath);
	// ds.writeBytes(twoHyphens + boundary + end);
	// //
	// ds.writeBytes("Content-Disposition: form-data; "+"name=\"file1\";filename=\image.jpg\"+
	// end);" +
	// ds.writeBytes("Content-Disposition: form-data; "+"name=\"file1\";filename=\""+imgName
	// +"\""+ end);
	// ds.writeBytes(end);
	// /* 取得文件的FileInputStream */
	// FileInputStream fStream =new FileInputStream(file);
	// // filesize = fStream.available();//获得上传图片的大小
	// if((fStream.available()/1024) > 1024)
	// {
	// return UPLOADPHOTOSOBIG;
	// }
	// /* 设置每次写入1024bytes */
	// int bufferSize =1024;
	// byte[] buffer =new byte[bufferSize];
	// int length =-1;
	// int cout=0;
	// int count =0;
	// /* 从文件读取数据至缓冲区 */
	// while((length = fStream.read(buffer)) !=-1)
	// {
	// /* 将资料写入DataOutputStream中 */
	// ds.write(buffer, 0, length);
	// }
	// ds.writeBytes(end);
	// ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	// /* close streams */
	// fStream.close();
	// ds.flush();
	// /* 取得Response内容 */
	// InputStream is = con.getInputStream();
	// Log.v("photoUpload_errorCode",
	// String.valueOf(con.getResponseCode()));//后台打印错误代码
	// if(con.getResponseCode() == 500)//如果返回500则代表图片上传失败
	// {
	// return UPLOADPHOTOFAIL;//
	// }
	// Log.v("photoUpload_errorCode", String.valueOf(con.getResponseCode()));
	//
	// ds.close();
	// return UPLOADPHOTOSUCCESS;
	// }
	// catch(Exception e)
	// {
	// return UPLOADPHOTOFAIL;
	// }
	// }
	public static int uploadFile(String actionUrl, String photoPath,
			String photodir, String imgName, String uid) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String newName = "image.jpg";
		int filesize = 0;
		int upfilesize = 0;
		try {
			actionUrl += photodir;
			actionUrl += "&uid=" + uid;
			URL url = new URL(actionUrl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			File file = new File(photoPath);
			ds.writeBytes(twoHyphens + boundary + end);
			// ds.writeBytes("Content-Disposition: form-data; "+"name=\"file1\";filename=\image.jpg\"+
			// end);" +
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + imgName + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(file);
			System.out.println(fStream.available());// 获得上传图片的大小
			filesize = fStream.available();// 获得上传图片的大小
			if ((fStream.available() / 1024) > 2048) {
				return UPLOADPHOTOSOBIG;
			}
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			int cout = 0;
			int count = 0;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();

			ds.close();
			return UPLOADPHOTOSUCCESS;
		} catch (Exception e) {
			return UPLOADPHOTOFAIL;
		}
	}

	// 获得SD卡路径
	public static String getSDPath() {
		File sdDir = null;
		String sDPath = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {

		}
		if (null != sdDir) {
			sDPath = sdDir.toString();
		}

		return sDPath;
	}

	// 获得SD卡路径
	public static String getSDPath(Context context) {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			return context.getFilesDir() + "";// 获取跟目录

		}

		return sdDir.toString();
	}

	// 获得SD卡路径
	public static boolean getSDPathYesOrNo() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		return sdCardExist;
	}

	/**
	 * 各手机厂商android框架封装不同，具备SD卡的情况下，拍照上传情况分两种: 1.拍照后直接上传如三星、HTC. 2.部分机型不支持如魅族.
	 * 第二种情况出现时,拍照后的图片需要本地保存再上传
	 * 
	 */
	public static void saveBitmapToFile(Bitmap bp, String _file)
			throws IOException {

		// File file = null;
		// try {
		// file = new File(_file);
		// if (!file.exists()) {
		// file.createNewFile();// 创建文件
		// }
		// FileOutputStream fos = new FileOutputStream(_file);
		// bp = Bitmap.createScaledBitmap(bp, 240, 320,false);
		// bp.compress(Bitmap.CompressFormat.PNG, 20, fos);
		// fos.flush();
		// fos.close();
		// // ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// // bp.compress(CompressFormat.PNG, 100, stream);
		// //
		// // FileOutputStream os = new FileOutputStream(file);
		// // os.write(stream.toByteArray());
		// // os.close();
		// } catch (Exception ex) {
		// file = null;
		// }
		//
		BufferedOutputStream os = null;
		try {
			File file = new File(_file);
			// String _filePath_file.replace(File.separatorChar +
			// file.getName(), "");
			int end = _file.lastIndexOf(File.separator);
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bp.compress(Bitmap.CompressFormat.JPEG, 100, os);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static void bitmapToSaving(String filePath) throws IOException {

		Bitmap bm = getSmallBitmap(filePath);
		File file = null;
		// try{
		file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();// 创建文件
		}
		FileOutputStream fos = new FileOutputStream(filePath);
		// bm = Bitmap.createScaledBitmap(bp, 240, 320,false);
		if (null != bm) {
			bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
			if (null != bm)
				bm.recycle();
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);
			// byte[] b = baos.toByteArray();
			// return Base64.encodeToString(b, Base64.DEFAULT);
		}
	}

	/* 上传文件至Server的方法 */
	public static String uploadFile(String uploadUrl, String filepath) {
		String result = null;
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ filepath.substring(filepath.lastIndexOf("/") + 1)
					+ "\""
					+ end);
			dos.writeBytes(end);

			FileInputStream fis = new FileInputStream(filepath);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);

			}
			fis.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			L.i(result);

			dos.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
