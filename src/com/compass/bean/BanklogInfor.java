package com.compass.bean;


/**
 * 
 * @ClassName: BacklogInfor
 * @Description: TODO({"bzjsize":13,"hqsize":5,"dqsize":0})
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月18日 下午2:47:57
 * 
 */
public class BanklogInfor {
	
	/**
	 * {"data":{"bzjsize":13,"hqsize":5,"dqsize":0},"errcode":0}
	 *  * 2,0,1
	 * <item>活期余额对账单</item>
        <item>定期余额对账单</item>
        <item>保证金账户余额对账单</item>
        <item>明细对账单</item>
        <item>对帐单回执授权</item>
        <item>未达账务查询</item>
	 */

	private int BZJSIZE;
	private int HQSIZE;
	private int DQSIZE;
	
	public int getBZJSIZE() {
		return BZJSIZE;
	}
	public void setBZJSIZE(int BZJSIZE) {
		this.BZJSIZE = BZJSIZE;
	}
	public int getHQSIZE() {
		return HQSIZE;
	}
	public void setHQSIZE(int HQSIZE) {
		this.HQSIZE = HQSIZE;
	}
	public int getDQSIZE() {
		return DQSIZE;
	}
	public void setDQSIZE(int DQSIZE) {
		this.DQSIZE = DQSIZE;
	}
	@Override
	public String toString() {
		return "BacklogInfor [bzjsize=" + BZJSIZE + ", hqsize=" + HQSIZE
				+ ", dqsize=" + DQSIZE + "]";
	}

	

}
