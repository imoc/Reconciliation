package com.compass.bean;

import com.compass.api.Urls;

public class Version {
	/**
	 * {"isDelete":0,"createTime":"2014-06-22 00:00:00","VERSION":"家校通客户端",
	 * "verNum"
	 * :1,"path":"/path/school_android1.apk","fileName":"school_android.apk"
	 * ,"versionId":12}
	 */

	public final static int TYPE_STATUS_NOML = 0;
	public final static int TYPE_STATUS_STOP = 1;
	private int status;// :使用状态(0表示正常;1表示停止使用);
	private int verNum;
	private int versionId;
	private String createTime;
	private String VERSION;
	private String path;
	private String downloadUrl;
	private String fileName;
	//2014年11月2日1:47:21
	private int triggerInterval ;
	

	public int getTriggerInterval() {
		return triggerInterval;
	}

	public void setTriggerInterval(int triggerInterval) {
		this.triggerInterval = triggerInterval;
	}

	public Boolean getIsDel() {
		if (status == 1) {
			return false;
		}
		return true;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public int getVerNum() {
		return verNum;
	}

	public void setVerNum(int verNum) {
		this.verNum = verNum;
	}

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVERSION() {
		return VERSION;
	}

	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDownloadUrl() {

//		 return
//		 "http://192.168.0.88:8080/BaiduPushService/app/hscs_3.0.apk";//test
		// ------------------
		return Urls.BASIC_URL + path;
	}

	public void setDownloadUrl(String downloadUrl) {

		this.downloadUrl = downloadUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "Version [status=" + status + ", verNum=" + verNum
				+ ", versionId=" + versionId + ", createTime=" + createTime
				+ ", VERSION=" + VERSION + ", path=" + path + ", downloadUrl="
				+ downloadUrl + ", fileName=" + fileName + ", triggerInterval="
				+ triggerInterval + "]";
	}

	
	

}
