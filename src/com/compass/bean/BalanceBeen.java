package com.compass.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: QueryResultBeen
 * @Description: TODO(对账查询结果JavaBean)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月14日 下午12:03:29
 * 
 */
public class BalanceBeen implements Serializable {

	/**
	 * {"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":
	 * "山西太行永达装饰工程有限公司","DATES":"2014-06-30","LASTTIME":"2014-06-
	 * 21T00:00:00","BALANCE
	 * ":148351.88,"EXITEMNO":"2001","CLIENTCHECK":"0","TYPE
	 * ":"1","CHECKE":null,"ATTR":null,"ACCS":null}
	 * 
	 * 
	 *--------------------
	 *{"accountsign":1,"DATA":[{"PASSED":"0","ACCOUNT":"401103010300000040930",
	 *"CLINAME":"平定县财政局","DATES":"2014-08-31",
	 *"LASTTIME":"2014-08-29T00:00:00","BALANCE":18304578,"EXITEMNO":"2001",
	 *"CLIENTCHECK":"3","TYPE":"1","CHECKE":null,"ATTR":null,"ACCS":null}],"ERRCODE":0}

	 *
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	public final static int TYPE_OPERATE_DISARGEE = 2;
	public final static int TYPE_OPERATE_ARGEE = 1;
	public final static int TYPE_OPERATE_NULL = 2;
	public final static int TYPE_VIP = 1;

	private String company;// 企业名称
	private String institution;// 机构
	private String accountType;// 账号类型
	private String account;// 账号
	private String subAccount;// 分账号
	private String currency;// 币种
	private String balance;// 账号余额
	private int queryType;// 查询类型
	private String date;// 日期
	private int checkState;// 对账状态
	private String auditState;// 审核状态
	private int operateType;// 操作
	// -----2014年11月24日14:05:54
	private String PASSED;// 对账是否通过
	private String ACCOUNT;// 账号
	private String CLINAME;// 该账户企业名称
	private String DATES;// 生成对账单日期
	private String LASTTIME;// 最后动帐日期
	private String BALANCE;// 余额
	private String EXITEMNO;// 科目号
	private String CLIENTCHECK;// 网络对账情况
	private int TYPE;// 用户类型（0网点用户，1企业用户）
	private String CHECKE;// 对账授权标志
	private String ATTR;//
	private String ACCS;// 分账号
	private int ACCOUNTSIGN;// 如果为1是重点账户，需要列明细，不是的话为0

	public BalanceBeen(String company, String accountType, String account,
			String subAccount, String currency, String balance, int queryType,
			String date, String checkState, String auditState, int operate) {
		super();
		this.company = company;
		this.accountType = accountType;
		this.account = account;
		this.subAccount = subAccount;
		this.currency = currency;
		this.balance = balance;
		this.queryType = queryType;
		this.date = date;
		this.CLIENTCHECK = checkState;
		this.auditState = auditState;
		this.operateType = operate;
	}

	public String getCompany() {
		// return company;
		if (getACCOUNTSIGN() == TYPE_VIP) {
			return CLINAME + "(重点)";
		}
		return CLINAME;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getAccountType() {
		// 用户类型（0网点用户，1企业用户）

		if (getTYPE() == 1) {
			return "企业用户";
		}
		return "网点用户";
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccount() {
		// return account;

		return ACCOUNT;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSubAccount() {
		// return subAccount;
		return ACCS;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getBalance() {
		// return balance;
		return  Double.parseDouble(BALANCE);
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	public String getDate() {
		// return date;
		return DATES;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCheckState() {
		// return checkState;
		return Integer.parseInt(CLIENTCHECK);
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public int getOperateType() {

		// return operateType;
		return Integer.parseInt(CLIENTCHECK);
	}

	public void setOperateType(int operateType) {
		this.CLIENTCHECK = operateType + "";
		// this.operateType = operateType;
	}

	// -----------2014年11月24日14:05:54
	public String getPASSED() {
		return PASSED;
	}

	public void setPASSED(String pASSED) {
		PASSED = pASSED;
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

	public String getLASTTIME() {
		return LASTTIME;
	}

	public void setLASTTIME(String lASTTIME) {
		LASTTIME = lASTTIME;
	}

	public String getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}

	public String getEXITEMNO() {
		return EXITEMNO;
	}

	public void setEXITEMNO(String eXITEMNO) {
		EXITEMNO = eXITEMNO;
	}

	public String getCLIENTCHECK() {
		return CLIENTCHECK;
	}

	public void setCLIENTCHECK(String cLIENTCHECK) {
		CLIENTCHECK = cLIENTCHECK;
	}

	public int getTYPE() {
		return TYPE;
	}

	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}

	public String getCHECKE() {
		return CHECKE;
	}

	public void setCHECKE(String cHECKE) {
		CHECKE = cHECKE;
	}

	public String getATTR() {
		return ATTR;
	}

	public void setATTR(String aTTR) {
		ATTR = aTTR;
	}

	public String getACCS() {
		return ACCS;
	}

	public void setACCS(String aCCS) {
		ACCS = aCCS;
	}

	public int getACCOUNTSIGN() {
		return ACCOUNTSIGN;
	}

	public void setACCOUNTSIGN(int aCCOUNTSIGN) {
		ACCOUNTSIGN = aCCOUNTSIGN;
	}

	@Override
	public String toString() {
		return "QueryResultBeen [PASSED=" + PASSED + ", ACCOUNT=" + ACCOUNT
				+ ", CLINAME=" + CLINAME + ", DATES=" + DATES + ", LASTTIME="
				+ LASTTIME + ", BALANCE=" + BALANCE + ", EXITEMNO=" + EXITEMNO
				+ ", CLIENTCHECK=" + CLIENTCHECK + ", TYPE=" + TYPE
				+ ", CHECKE=" + CHECKE + ", ATTR=" + ATTR + ", ACCS=" + ACCS
				+ ", ACCOUNTSIGN=" + ACCOUNTSIGN + "]";
	}


}
