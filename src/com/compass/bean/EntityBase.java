package com.compass.bean;

public  class EntityBase {

	
	/**
	 * 状态码参考表（errcode）
代码	解释	错误信息
0	此次请求服务器正确	
1	操作失败	根据业务返回不同
2	参数不全	Parameter is not complete
3	登录异常需重新登录	invaild signature
4	请求正常但是无业务数据	no data
5	系统出错	System is busy
	 */
	
	public static final int ERRCODE_SUCCESS = 0;
	public static final int ERRCODE_UPD_SUCCESS = 1;
	//loc-----------------	
	private int errCode;
	private String errMsg;
	private String authCode;
	
	//web----------------
	private int ERRCODE;
	private String ERRMSG;
	private String MSG;
	//------------------
	public EntityBase() {
	}

	public EntityBase(int errCode, String errMsg) {
		//loc-----------------
		this.errCode = errCode;
		this.errMsg = errMsg;
		//web----------------
		this.ERRCODE = errCode;
		this.ERRMSG = errMsg;
	}

	//loc-----------------
	public int getErrCode() {
//		return errCode;
		return ERRCODE;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
//		return errMsg;
		return ERRMSG;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
	public String getAuthCode() {
//		return authCode;
		return MSG;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	//web----------------
	public int getERRCODE() {
		return ERRCODE;
	}

	public void setERRCODE(int ERRCODE) {
		this.ERRCODE = ERRCODE;
	}

	public String getERRMSG() {
		return ERRMSG;
	}

	public void setERRMSG(String ERRMSG) {
		this.ERRMSG = ERRMSG;
	}
	
	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	@Override
	public String toString() {
		return "EntityBase [errCode=" + errCode + ", errMsg=" + errMsg
				+ ", authCode=" + authCode + ", ERRCODE=" + ERRCODE
				+ ", ERRMSG=" + ERRMSG + ", MSG=" + MSG + "]";
	}

	
}
