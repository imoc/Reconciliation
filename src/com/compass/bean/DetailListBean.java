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
 *       {"DATA":
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

	private List<DetailBean> DATA;


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
