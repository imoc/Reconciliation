package com.compass.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: QueryBeen
 * @Description: TODO(accout query javabeen)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月13日 下午3:36:31
 * 
 */
public class QueryBean implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <string-array name="queryType">
        <item>活期余额对账单</item>
        <item>定期余额对账单</item>
        <item>保证金余额对账单</item>
        <item>活期明细对账单</item>
        <item>保证金明细对账单</item>
        <item>对账单回执授权</item>
        <item>未达账务查询</item>
        =========================
        <item>未对账的活期账户余额对账单有</item>
        <item>未对账的定期账户余额对账单有</item>
        <item>未对账的保证金账户余额对账单有</item>
	 */
	public final static int  TYPE_HQ_YE_DZD = 0;
	public final static int  TYPE_DQ_YE_DZD = 1;
	public final static int  TYPE_BZJ_YE_DZD = 2;
	public final static int  TYPE_HQ_MX_DZD = 3;
	public final static int  TYPE_BZJ_MX_DZD = 4;
	public final static int  TYPE_DZD_HZ_SQ = 5;
	public final static int  TYPE_WD_ZW_CX = 6;
	//-----------------
	public final static int  TYPE_WDZ_HQ_YE_DZD = 0;
	public final static int  TYPE_WDZ_DQ_YE_DZD = 1;
	public final static int  TYPE_WDZ_BZJ_YE_DZD = 2;
	//---------------------
	public final static int  FROMTYPE_INDEX = 0;	
	public final static int  FROMTYPE_QUERYTYPE = 1;
	public final static int  FROMTYPE_DETAIL = 2;


	
	private String account;
	private int queryType;
	private int fromType;
	private String startDate;
	private String endDate;
	//
	private int accountType;// 用户类型（0网点用户，1企业用户）
	
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	//
	public int getFromType() {
		return fromType;
	}
	public void setFromType(int fromType) {
		this.fromType = fromType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	public String getStratDate() {
		return startDate;
	}
	public void setStratDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "QueryBeen [account=" + account + ", queryType=" + queryType
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
	
}
