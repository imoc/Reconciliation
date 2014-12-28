package com.compass.bean;

import java.util.List;

/**
 * 
 * @ClassName: DetailListBean
 * @Description: 明细对账列表集合
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年12月9日 下午2:53:26
 * 
 * 

 *       {"TOPMAP":{"BANKNAME":"营业部","EXITEMNO":"2001","ACCOUNT":"401103010300000048249","CLINAME":"平定县财政局"},
 *       "DATA":
 *       	[
		 *       {"D31ACCS":15000,"D31AMT":0,"D31BALE":-15000,"D31VOUN":
		 *       "20141004947302"
		 *       ,"D31TXDT":"2014-09-05","D31ATUNTT":"401103","D31TXRMKS"
		 *       :"函校办公费","D31TELID"
		 *       :"420362","D31CHKTEL":null,"ACCS":null},
 *       
		 *       {"D31ACCS":0,
		 *       "D31AMT":15000,"D31BALE"
		 *       :0,"D31VOUN":null,"D31TXDT":"2014-09-05","D31ATUNTT"
		 *       :"401103","D31TXRMKS"
		 *       :"贷清算资金","D31TELID":"420495","D31CHKTEL":null,"ACCS"
		 *       :null}
		 *  ],
 *       
 *       "ERRCODE":0}
 */
public class DetailListBean extends EntityBase {

	public DetailListBean(int ERRCODE, String ERRMSG) {
		super(ERRCODE, ERRMSG);
		// TODO Auto-generated constructor stub
	}

	private TOPMAP TOPMAP;
	
	private List<DetailBean> DATA;


	public TOPMAP getTOPMAP() {
		return TOPMAP;
	}

	public void setTOPMAP(TOPMAP tOPMAP) {
		TOPMAP = tOPMAP;
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
	
	public class TOPMAP{
		 //{"TOPMAP":{"BANKNAME":"营业部","EXITEMNO":"2001","ACCOUNT":"401103010300000048249","CLINAME":"平定县财政局"},
		String BANKNAME ;//  机 构 名 称
		String EXITEMNO ;//科 目 号
		String ACCOUNT ;// 账  号
		String CLINAME ;//户  名
		
		private String institution;// 机构
		private String currency;// 币种
		private String account;// 账号
		private String company;// 户  名
		private String subject;// 科 目 号
		
		public String getBANKNAME() {
			return BANKNAME;
		}
		public void setBANKNAME(String bANKNAME) {
			BANKNAME = bANKNAME;
		}
		public String getEXITEMNO() {
			return EXITEMNO;
		}
		public void setEXITEMNO(String eXITEMNO) {
			EXITEMNO = eXITEMNO;
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
		
		public String getAgencyTv() {
			return BANKNAME;
		}
		public String getAccount() {
			return ACCOUNT;
		}
		public String getName() {
			return CLINAME;
		}
		public String getSubject() {
			return EXITEMNO;
		}
		
		
		
		
	}

}
