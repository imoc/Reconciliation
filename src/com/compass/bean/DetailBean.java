package com.compass.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: QueryResultBeen
 * @Description: TODO(流水对账查询结果JavaBean)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月14日 下午12:03:29
 * 
 */
public class DetailBean implements Serializable {

	/**
	 * {"D31ACCS":15000,"D31AMT":0,"D31BALE":-15000,"D31VOUN":"20141004947302","D31TXDT":"2014-09-05",
	 * "D31ATUNTT":"401103","D31TXRMKS":"函校办公费","D31TELID":"420362","D31CHKTEL":null,"ACCS":null}
	 * 
参数	说明
Errcode	状态码
D31TXDT	记账日期
D31TXRMKS	内容摘要
D31VOUN	凭证号
D31ACCS	借方发生额
D31AMT	贷方发生额
D31ATUNTT	交易机构
D31TELID	操作员号
D31BALE	余额
D31CHKTEL	
ACCS	
	 */
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;


	private String D31TXDT;// 记账日期
	private String D31TXRMKS;// 内容摘要
	private String D31VOUN;// 凭证号
	private String D31ACCS;// 借方发生额
	private String D31AMT;// 贷方发生额
	private String D31ATUNTT;//交易机构
	private String D31TELID;// 操作员号
	private String D31BALE;// 余额
	private String D31CHKTEL;// 
	private String ACCS;// 
	//
	private String date;// 记账日期
	private String note;// 内容摘要
	private String voucherNo;// 凭证号
	private String debitAmount;// 借方发生额
	private String creditAmount;// 贷方发生额
	private String transactionMechanism;//交易机构
	private String operatorId;// 操作员号
	private String reviewId;// 复核号
	private String balance;// 余额
//	private String D31CHKTEL;// 
//	private String ACCS;// 
	//
	
	//
	
	public String getD31TXDT() {
		return D31TXDT;
	}
	public DetailBean(String date, String note, String voucherNo,
			String debitAmount, String creditAmount,
			String transactionMechanism, String operatorId, String reviewId,
			String balance) {
		super();
		
		D31TXDT =this.date = date;
		D31TXRMKS = this.note = note;
		D31VOUN = this.voucherNo = voucherNo;
		D31ACCS = this.debitAmount = debitAmount;
		D31AMT = this.creditAmount = creditAmount;
		D31ATUNTT = this.transactionMechanism = transactionMechanism;
		D31TELID = this.operatorId = operatorId;
		D31CHKTEL = this.reviewId = reviewId;
		D31BALE = this.balance = balance;
	}
	public void setD31TXDT(String d31txdt) {
		D31TXDT = d31txdt;
	}
	public String getD31TXRMKS() {
		return D31TXRMKS;
	}
	public void setD31TXRMKS(String d31txrmks) {
		D31TXRMKS = d31txrmks;
	}
	public String getD31VOUN() {
		return D31VOUN;
	}
	public void setD31VOUN(String d31voun) {
		D31VOUN = d31voun;
	}
	public String getD31ACCS() {
		return D31ACCS;
	}
	public void setD31ACCS(String d31accs) {
		D31ACCS = d31accs;
	}
	public String getD31AMT() {
		return D31AMT;
	}
	public void setD31AMT(String d31amt) {
		D31AMT = d31amt;
	}
	public String getD31ATUNTT() {
		return D31ATUNTT;
	}
	public void setD31ATUNTT(String d31atuntt) {
		D31ATUNTT = d31atuntt;
	}
	public String getD31TELID() {
		return D31TELID;
	}
	public void setD31TELID(String d31telid) {
		D31TELID = d31telid;
	}
	public String getD31BALE() {
		return D31BALE;
	}
	public void setD31BALE(String d31bale) {
		D31BALE = d31bale;
	}
	public String getD31CHKTEL() {
		return D31CHKTEL;
	}
	public void setD31CHKTEL(String d31chktel) {
		D31CHKTEL = d31chktel;
	}
	public String getACCS() {
		return ACCS;
	}
	public void setACCS(String aCCS) {
		ACCS = aCCS;
	}
	//------------------
	
	public String getDate() {
//		return date;
		return D31TXDT;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNote() {
//		return note;
		return D31TXRMKS;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getVoucherNo() {
//		return voucherNo;
		return D31VOUN;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public double getDebitAmount() {
//		return debitAmount;
		return Double.parseDouble(D31ACCS);
	}
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	public double getCreditAmount() {
//		return creditAmount;
		return Double.parseDouble(D31AMT);
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getTransactionMechanism() {
//		return transactionMechanism;
		return D31ATUNTT;
	}
	public void setTransactionMechanism(String transactionMechanism) {
		this.transactionMechanism = transactionMechanism;
	}
	public String getOperatorId() {
//		return operatorNo;
		return D31TELID;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public double getBalance() {
//		return balance;
		return Double.parseDouble(D31BALE);
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	public String getReviewId() {
//		return reviewIdId;
		return D31CHKTEL;
	}
	public void setReviewId(String reviewIdId) {
		this.reviewId = reviewIdId;
	}
	@Override
	public String toString() {
		return "DetailBeen [D31TXDT=" + D31TXDT + ", D31TXRMKS=" + D31TXRMKS
				+ ", D31VOUN=" + D31VOUN + ", D31ACCS=" + D31ACCS + ", D31AMT="
				+ D31AMT + ", D31ATUNTT=" + D31ATUNTT + ", D31TELID="
				+ D31TELID + ", D31BALE=" + D31BALE + ", D31CHKTEL="
				+ D31CHKTEL + ", ACCS=" + ACCS + ", date=" + date + ", note="
				+ note + ", voucherNo=" + voucherNo + ", debitAmount="
				+ debitAmount + ", creditAmount=" + creditAmount
				+ ", transactionMechanism=" + transactionMechanism
				+ ", operatorId=" + operatorId + ", reviewIdId=" + reviewId
				+ ", balance=" + balance + "]";
	}
	
	
	
	
	
	
}
