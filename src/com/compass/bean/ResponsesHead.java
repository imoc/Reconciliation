package com.compass.bean;

public class ResponsesHead {
	/* {"MESSAGE":"信息保存操作成功!","RESULT":true} */
	private Boolean RESULT;
	private String MESSAGE;
	private String headUrl;

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Boolean getRESULT() {
		return RESULT;
	}

	public void setRESULT(Boolean rESULT) {
		RESULT = rESULT;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}

	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

	@Override
	public String toString() {
		return "Responses [RESULT=" + RESULT + ", MESSAGE=" + MESSAGE + "]";
	}

}
