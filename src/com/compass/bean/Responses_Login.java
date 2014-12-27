package com.compass.bean;


/**
 * 
 * @ClassName: Responses_GetAccounts
 * @Description: TODO(登录对应查询类型下的账号集合)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月24日 下午2:53:26
 * 
 * 
 *       { Errcode：0 signature：“o6_bmjrPTlm6_2sgVt7hMZOPfL2M” }
 */
public class Responses_Login extends EntityBase {

	// loc-----------------
	private String signature;
	private long lastLoginTime;

	// web----------------
	private String SIGNATURE;

	public Responses_Login(String signature, long lastLoginTime,int errCode, String errMs) {
		super(errCode,  errMs);
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

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	// web----------------
	public String getSIGNATURE() {
		return SIGNATURE;
	}

	public void setSIGNATURE(String sIGNATURE) {
		SIGNATURE = sIGNATURE;
	}

	@Override
	public String toString() {
		return "Responses_Login [signature=" + signature + ", lastLoginTime="
				+ lastLoginTime + ", SIGNATURE=" + SIGNATURE + "]";
	}

}
