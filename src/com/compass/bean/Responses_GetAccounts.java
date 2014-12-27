package com.compass.bean;

import java.util.List;

/**
 * 
 * @ClassName: Responses_GetAccounts
 * @Description: TODO登录对应查询类型下的账号集合)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月24日 下午2:53:26
 * 
 * 
 *       {"data":[{"ACCOUNT":"401103010300000054328"}],"errcode":0}
 */
public class Responses_GetAccounts extends EntityBase {

	public Responses_GetAccounts(int ERRCODE, String ERRMSG) {
		super(ERRCODE, ERRMSG);
		// TODO Auto-generated constructor stub
	}

	private List<ACCOUNT> DATA;

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

	public List<ACCOUNT> getDATA() {
		return DATA;
	}

	public void setData(List<ACCOUNT> DATA) {
		this.DATA = DATA;
	}

	@Override
	public String toString() {
		return "Responses_GetAccounts [data=" + DATA + super.toString() + "]";
	}

	public String[] getAccounts() {
		int size = DATA.size();
		String[] accounts = new String[size];
		for (int i = 0; i < size; i++) {
			accounts[i] = DATA.get(i).getACCOUNT();
		}
		return accounts;
	}

}
