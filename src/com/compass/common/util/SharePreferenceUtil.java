package com.compass.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	public static final String TAG = SharePreferenceUtil.class.getSimpleName();
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private Context mContext;

	public SharePreferenceUtil(Context context, String file) {
		this.mContext = context;
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	// isFirstUse
	public void setIsFirstUse(Boolean isFirstUse) {
		editor.putBoolean("isFirstUse", isFirstUse);
		editor.commit();
	}

	public Boolean getIsFirstUse() {
		return sp.getBoolean("isFirstUse", true);
	}

	// LastLoginTime 上次登录时间
	public String getLastLoginTime() {
		return sp.getString("lastLoginTime", "");
	}

	
	public void setLastLoginTime(String lastLoginTime) {
		editor.putString("lastLoginTime", lastLoginTime);
		editor.commit();

	}

	// signature
	public String getSignature() {
		return sp.getString("signature", "");
	}

	public void setSignature(String signature) {
		editor.putString("signature", signature);
		editor.commit();

	}

	// Alias
	public String getAlias() {
		return sp.getString("alias", "");
	}

	public void setAlias(String alias) {
		editor.putString("alias", alias);
		editor.commit();

	}

	// Account
	public String getAccount() {
		return sp.getString("account", "");
	}

	public void setAccount(String account) {
		editor.putString("account", account);
		editor.commit();

	}

	// Password
	public String getPassword() {
		return sp.getString("password", "");
	}

	public void SetPassWord(String password) {
		editor.putString("password", password);
		editor.commit();

	}

	// teleph
	public String getTeleph() {
		return sp.getString("teleph", "");
	}

	public void setTeleph(String teleph) {
		editor.putString("teleph", teleph);
		editor.commit();
	}

	// 个人中心版本更新 ------------------------------------------2014年7月18日19:39:21
	// VerNum web apk 版本号

	public int getWebVerNum() {
		return sp.getInt("VerNum", 0);
	}

	public void setWebVerNum(int VerNum) {
		editor.putInt("VerNum", VerNum);
		editor.commit();
	}

	// VerNum 客户端 apk 版本号
	public int getClientVerNum() {
		return sp.getInt("ClientVerNum", 0);
	}

	public void setClientVerNum(int ClientVerNum) {
		editor.putInt("ClientVerNum", ClientVerNum);
		editor.commit();

	}

	// VERSION 版本名称---2014年8月9日9:58:34
	public String getVERSION() {
		return sp.getString("VERSION", "");
	}

	public void setVERSION(String VERSION) {
		editor.putString("VERSION", VERSION);
		editor.commit();

	}

	// apk下载地址
	public String getApkDownloadUrl() {
		return sp.getString("ApkDownloadUrl", "");
	}

	public void setApkDownloadUrl(String ApkDownloadUrl) {
		editor.putString("ApkDownloadUrl", ApkDownloadUrl);
		editor.commit();
	}

	// isFirstUse
	public void setApkIsDelete(Boolean ApkIsDelete) {
		editor.putBoolean("ApkIsDelete", ApkIsDelete);
		editor.commit();
	}

	public Boolean getApkIsDelete() {
		return sp.getBoolean("ApkIsDelete", false);
	}

	// 个人中心版本更新 ==================================2014年7月18日19:39:21

	/**
	 * 清空sp
	 */
	public void clearSp() {
		editor.clear().commit();
	}

	public void setIsDouble(Boolean isdouble) {
		editor.putBoolean("isdouble", isdouble);
		editor.commit();
		
	}
	public Boolean getIsDouble() {
		return sp.getBoolean("isdouble", false);
	}

}
