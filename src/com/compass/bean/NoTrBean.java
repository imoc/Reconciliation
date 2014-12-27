package com.compass.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: NoTrBeen
 * @Description: TODO(未达账务查询结果JavaBean)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月14日 下午12:03:29
 * 
 */
public class NoTrBean implements Serializable {

	/**
	 * {"DATA":[{"MESSAGEID":"201411242014090000091","ACCOUNT":"401103010300000019467","CLINAME":"平定县财政局","DATES":"2014-09-30",
	 * "RESTORECONTENT":null,"SIGN":"0","STATES":"0","PROJECTBALANCE":0,"BALANCE":0,"ACCOUNTSIGN":"0"}],"ERRCODE":0}
	 * 
	 * 
	 * 参数	说明
ERRCODE	状态码
MESSAGEID	通知编号
ACCOUNT	账号
CLINAME	单位名
DATES	生成对账单日
RESTORECONTENT	附言
SIGN	是否下通知（1已下，0未下）
STATES	状态（0只查看，1已查复，2未查复）
PROJECTBALANCE	单位账户余额
BALANCE	余额
ACCOUNTSIGN	默认为0
	 */
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	//
	private String MESSAGEID;//通知编号
	private String ACCOUNT;// 账号
	private String CLINAME;// 单位名
	private String DATES;// 生成对账单日
	private String RESTORECONTENT;// 附言
	private String SIGN;//是否下通知（1已下，0未下）
	private String STATES;//状态（0只查看，1已查复，2未查复）
	private String PROJECTBALANCE;// 单位账户余额
	private String BALANCE;// 余额
	private String ACCOUNTSIGN;// 默认为0
	//
	private String noticeId;// 通知编号
	private String account;// 账号
	private String company;// 单位名
	private String date;// 生成对账单日
	private String additional;// 附言
	private boolean hasNoticed;//是否下通知（1已下，0未下）
	private int checkState;//状态（0只查看，1已查复，2未查复）
	private String companyBalance;// 单位账户余额
	private String balance;// 余额
	private String accountSign;// 默认为0
	
	public NoTrBean(String noticeId, String account, String company,
			String date, String additional, String hasNoticed,
			String checkState, String companyBalance, String balance,
			String accountSign) {
		super();
		MESSAGEID = this.noticeId = noticeId;
		ACCOUNT = this.account = account;
		CLINAME = this.company = company;
		DATES = this.date = date;
		RESTORECONTENT = this.additional = additional;
		SIGN  = hasNoticed;
		STATES  = checkState;
		PROJECTBALANCE = this.companyBalance = companyBalance;
		BALANCE = this.balance = balance;
		ACCOUNTSIGN = this.accountSign = accountSign;
	}
	//----------

	public String getMESSAGEID() {
		return MESSAGEID;
	}

	public void setMESSAGEID(String mESSAGEID) {
		MESSAGEID = mESSAGEID;
	}

	public String getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(String aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	public String getCLINAME() {
		return CLINAME;
	}

	public void setCLINAME(String cLINAME) {
		CLINAME = cLINAME;
	}

	public String getDATES() {
		return DATES;
	}

	public void setDATES(String dATES) {
		DATES = dATES;
	}

	public String getRESTORECONTENT() {
		return RESTORECONTENT;
	}

	public void setRESTORECONTENT(String rESTORECONTENT) {
		RESTORECONTENT = rESTORECONTENT;
	}

	public String getSIGN() {
		return SIGN;
	}

	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}

	public String getSTATES() {
		return STATES;
	}

	public void setSTATES(String sTATES) {
		STATES = sTATES;
	}

	public String getPROJECTBALANCE() {
		return PROJECTBALANCE;
	}

	public void setPROJECTBALANCE(String pROJECTBALANCE) {
		PROJECTBALANCE = pROJECTBALANCE;
	}

	public String getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}

	public String getACCOUNTSIGN() {
		return ACCOUNTSIGN;
	}

	public void setACCOUNTSIGN(String aCCOUNTSIGN) {
		ACCOUNTSIGN = aCCOUNTSIGN;
	}
//------------------
	
	
	public String getNoticeId() {
//		return noticeId;
		return MESSAGEID;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getAccount() {
//		return account;
		return ACCOUNT;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCompany() {
//		return company;
		return CLINAME;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDate() {
//		return date;
		return DATES;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAdditional() {
//		return additional;
		return RESTORECONTENT;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public boolean getHasNoticed() {
//		return hasNoticed;
		return SIGN.equals("1");
	}

	public void setHasNoticed(boolean hasNoticed) {
		this.hasNoticed = hasNoticed;
	}

	public int getCheckState() {
//		return checkState;
		return Integer.parseInt(STATES);
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public String getCompanyBalance() {
//		return companyBalance;
		return PROJECTBALANCE;
	}

	public void setCompanyBalance(String companyBalance) {
		this.companyBalance = companyBalance;
	}

	public String getBalance() {
//		return balance;
		return BALANCE;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAccountSign() {
//		return accountSign;
		return ACCOUNTSIGN;
	}

	public void setAccountSign(String accountSign) {
		this.accountSign = accountSign;
	}

	@Override
	public String toString() {
		return "NoTrBeen [MESSAGEID=" + MESSAGEID + ", ACCOUNT=" + ACCOUNT
				+ ", CLINAME=" + CLINAME + ", DATES=" + DATES
				+ ", RESTORECONTENT=" + RESTORECONTENT + ", SIGN=" + SIGN
				+ ", STATES=" + STATES + ", PROJECTBALANCE=" + PROJECTBALANCE
				+ ", BALANCE=" + BALANCE + ", ACCOUNTSIGN=" + ACCOUNTSIGN
				+ ", noticeId=" + noticeId + ", account=" + account
				+ ", company=" + company + ", date=" + date + ", additional="
				+ additional + ", hasNoticed=" + hasNoticed + ", checkState="
				+ checkState + ", companyBalance=" + companyBalance
				+ ", balance=" + balance + ", accountSign=" + accountSign + "]";
	}
	
	

	
	
	
	
}
