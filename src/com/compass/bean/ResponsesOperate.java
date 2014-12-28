package com.compass.bean;


/**
 * 
 * 对账操作返回结果bean
 * 
 * {"CHECK":"-1","CLIENTCHECK":"2","OPERATIONTYPE":1,"ERRCODE":0}
 */
public class ResponsesOperate extends EntityBase {

	private int CHECK;// 审核状态
	private int CLIENTCHECK;// 对账状态
	private int OPERATIONTYPE;// 操作状态

	private int auditState;// 审核状态
	private int checkState;// 对账状态
	private int operateType;// 操作状态

	public int getCHECK() {
		return CHECK;
	}

	public void setCHECK(int cHECK) {
		CHECK = cHECK;
	}

	public int getCLIENTCHECK() {
		return CLIENTCHECK;
	}

	public void setCLIENTCHECK(int cLIENTCHECK) {
		CLIENTCHECK = cLIENTCHECK;
	}

	public int getOPERATIONTYPE() {
		return OPERATIONTYPE;
	}

	public void setOPERATIONTYPE(int oPERATIONTYPE) {
		OPERATIONTYPE = oPERATIONTYPE;
	}

	public int getAuditState() {
		return CHECK;
	}

	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}

	public int getCheckState() {
		return CLIENTCHECK;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public int getOperateType() {
		return OPERATIONTYPE;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

}
