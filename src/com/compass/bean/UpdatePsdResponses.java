package com.compass.bean;

public class UpdatePsdResponses {

	{/*
	 * "result":false,"old_password":"034e1003cfefe657","Message":"很抱歉，您的输入的旧密码不正确!"
	 * ,"personId":"1","new_password":"323b453885f5181f"
	 * 
	 * =========================================
	 * {"MESSAGE":"密码修改成功!","old_password"
	 * :"b430e0ebbd15e76b","RESULT":true,"parentStudentContactId"
	 * :"1","new_password":"b430e0ebbd15e76b"}
	 */
	}

	private Boolean RESULT;
	private String MESSAGE;
	private String old_password;
	private String new_password;
	private String personId;

	public Boolean getRESULT() {
		return RESULT;
	}

	public void setRESULT(Boolean RESULT) {
		this.RESULT = RESULT;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String MESSAGE) {
		this.MESSAGE = MESSAGE;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Override
	public String toString() {
		return "UpdatePsdResponses [RESULT=" + RESULT + ", MESSAGE=" + MESSAGE
				+ ", old_password=" + old_password + ", new_password="
				+ new_password + ", personId=" + personId + "]";
	}

}
