package com.compass.bean;

import java.util.List;

/**
 * 
 * @ClassName: Responses_GetAccounts
 * @Description: TODO （流水对账查询结果号集合)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月24日 下午2:53:26
 * 
 * 
 *       {"data":[{"PASSED":"0","ACCOUNT":"401103010300000054328","CLINAME":
 *       "山西太行永达装饰工程有限公司"
 *       ,"DATES":"2014-09-30","LASTTIME":"2014-09-29T00:00:00","BALANCE"
 *       :141026.45
 *       ,"EXITEMNO":"2001","CLIENTCHECK":"1","TYPE":"1","CHECKE":null,
 *       "ATTR":null,"ACCS":null}],"errcode":0}
 */
public class Responses_AccbookAccount extends EntityBase {

	public Responses_AccbookAccount(int ERRCODE, String ERRMSG) {
		super(ERRCODE, ERRMSG);
		// TODO Auto-generated constructor stub
	}

	private List<DetailBean> DATA;

	public class ACCOUNT {
		private String ACCOUNT;

		public String getACCOUNT() {
			return ACCOUNT;
		}

		public void setACCOUNT(String aCCOUNT) {
			ACCOUNT = aCCOUNT;
		}

		@Override
		public String toString() {
			return "ACCOUNT [ACCOUNT=" + ACCOUNT + "]";
		}

	}

	public List<DetailBean> getDATA() {
		return DATA;
	}

	public void setDATA(List<DetailBean> DATA) {
		this.DATA = DATA;
	}

	@Override
	public String toString() {
		return "Responses_GetAccounts [data=" + DATA + super.toString() + "]";
	}

}
