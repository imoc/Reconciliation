package com.compass.bean;


/**
 * 
 * @ClassName: BacklogInforResult
 * @Description: TODO(请求代办任务总数)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月24日 上午11:33:36
 * 
 */
public class BanklogInforResult extends EntityBase {
	public BanklogInforResult(int ERRCODE, String ERRMSG) {
		super(ERRCODE, ERRMSG);
		// TODO Auto-generated constructor stub
	}


	/**
	 * {"data":{"bzjsize":13,"hqsize":5,"dqsize":0},"errcode":0}
	 * 2,0,1
	 * <item>活期余额对账单</item>
        <item>定期余额对账单</item>
        <item>保证金账户余额对账单</item>
        <item>明细对账单</item>
        <item>对帐单回执授权</item>
        <item>未达账务查询</item>
	 */



	private BanklogInfor DATA;


	public BanklogInfor getDATA() {
		return DATA;
	}


	public void setDATA(BanklogInfor DATA) {
		this.DATA = DATA;
	}


	@Override
	public String toString() {
		return "BacklogInforResult [data=" + DATA + super.toString()+"]";
	}



	
	
}
