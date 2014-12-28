package com.compass.bean;

/**
 * 
 * @ClassName: Responses_GetAccounts
 * @Description: TODO(登录对应查询类型下的账号集合)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月24日 下午2:53:26
 * 
 * 
 *       {"SIGNATURE":"123","LANDTIME":"2014-12-28 09:57:41.0","ERRCODE":0}
 */
public class Responses_Login extends EntityBase {

	// loc-----------------
	private String signature;
	private String lastLoginTime;
	private boolean isDouble;

	// web----------------
	private String SIGNATURE;
	private String LANDTIME;
	private String ISDOUBLE;//1双人对账，0单人对账

	public Responses_Login(String signature, String lastLoginTime, int errCode,
			String errMs) {
		super(errCode, errMs);
		this.signature = signature;
		this.lastLoginTime = lastLoginTime;
	}

	// loc-----------------
	public String getSignature() {
		// return signature;
		return SIGNATURE;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getISDOUBLE() {
		return ISDOUBLE;
	}

	public void setISDOUBLE(String iSDOUBLE) {
		ISDOUBLE = iSDOUBLE;
	}

	public String getLANDTIME() {
		return LANDTIME;
	}

	public void setLANDTIME(String lANDTIME) {
		LANDTIME = lANDTIME;
	}

	public String getLastLoginTime() {
		// return lastLoginTime;
		return LANDTIME;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	// web----------------
	public String getSIGNATURE() {
		return SIGNATURE;
	}

	public void setSIGNATURE(String sIGNATURE) {
		SIGNATURE = sIGNATURE;
	}
	
	public boolean getIsDouble() {
//		return isDouble;
		return "1".equals(ISDOUBLE);
	}

	public void setIsDouble(boolean isDouble) {
		this.isDouble = isDouble;
		
	}

	@Override
	public String toString() {
		return "Responses_Login [signature=" + signature + ", lastLoginTime="
				+ lastLoginTime + ", SIGNATURE=" + SIGNATURE + "]";
	}

}
