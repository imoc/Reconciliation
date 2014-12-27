package com.compass.test;

import java.util.ArrayList;
import java.util.List;

import com.compass.bean.BalanceBeen;
import com.compass.bean.BalanceCategoryListItemEntity;
import com.compass.bean.BanklogInforItem;
import com.compass.bean.DetailBean;
import com.compass.bean.DetailCategoryListItemEntity;
import com.compass.bean.EntityBase;
import com.compass.bean.NoTrBean;
import com.compass.bean.NoTrCategoryListItemEntity;
import com.compass.bean.ResponsesDetailTop;
import com.compass.bean.Responses_Login;
import com.compass.common.util.L;

public class TestDataUtil {
	private final static String TAG = TestDataUtil.class.getSimpleName();

	/**
	 * 
	* @Title: LoginResp 
	* @Description: TODO(登录返回) 
	* @return Responses_Login    返回类型 
	 */
	public static Responses_Login LoginResp() {
		// TODO Auto-generated method stub
		return new Responses_Login("123", System.currentTimeMillis()-3600*24*1000, EntityBase.ERRCODE_SUCCESS, "ok");
	}


	public static EntityBase getOperateResp() {
		// TODO Auto-generated method stub
		return new EntityBase(EntityBase.ERRCODE_UPD_SUCCESS, "ok");
	}


	public static ResponsesDetailTop getRespDetailTopTestData() {
		return new ResponsesDetailTop("account", "agency", "subject", "currency", "subAccount", "name");
	}


	public static BalanceCategoryListItemEntity getQueryCategoryListItemEntity() {
		
		 List<BalanceBeen> listItems_list = new ArrayList<BalanceBeen>();
		 BalanceCategoryListItemEntity listItem = new BalanceCategoryListItemEntity();
		 BalanceBeen contentItem;
		 String buf;
		 String more_url ="http://fanyi.baidu.com/";
		 for (int i = 0; i < 10; i++) {
		 buf = "_" + i;
		
		 contentItem = new BalanceBeen("company "+buf, "accountType "+buf, "account"+buf, "subAccount "+buf, "currency", "993.99 "+buf, 1, "date", "checkState", "auditState", BalanceBeen.TYPE_OPERATE_NULL);
		 listItems_list.add(contentItem);
		 contentItem = null;
		 }
		
		 listItem.setItems(listItems_list);
		 listItem.setMore_url(more_url);
		 L.d(TAG,"getData() - "+listItem);
		 return listItem;
		 }


	public static List<BanklogInforItem> getBanklogInforList() {
		List<BanklogInforItem> list = new ArrayList<BanklogInforItem>();
		for (int i = 0; i < 3; i++) {
			list.add(new BanklogInforItem(i, i + 1));
		}
		return list;
	}


	public static DetailCategoryListItemEntity getDetailCategoryListItemEntity() {
		 List<DetailBean> listItems_list = new ArrayList<DetailBean>();
		 DetailCategoryListItemEntity listItem = new DetailCategoryListItemEntity();
		 DetailBean contentItem;
		 String buf;
		 String more_url ="http://fanyi.baidu.com/";
		 for (int i = 0; i < 10; i++) {
		 buf = "_" + i;
		
//		 contentItem = new DetailBeen(date, note, voucherNo, debitAmount, creditAmount, transactionMechanism, operatorId, reviewId, balance);
		 contentItem = new DetailBean("2014年12月10日", "note "+buf, "voucherNo"+buf, "debitAmount "+buf, "creditAmount"+buf, "transactionMechanism "+buf,  "operatorId"+buf, "reviewId"+buf,  "balance"+buf);
		 listItems_list.add(contentItem);
		 contentItem = null;
		 }
		
		 listItem.setItems(listItems_list);
		 listItem.setMore_url(more_url);
		 L.d(TAG,"getData() - "+listItem);
		 return listItem;
	}


	public static NoTrCategoryListItemEntity getNoTrCategoryListItemEntity() {
		 List<NoTrBean> listItems_list = new ArrayList<NoTrBean>();
		 NoTrCategoryListItemEntity listItem = new NoTrCategoryListItemEntity();
		 NoTrBean contentItem;
		 String buf;
		 String more_url ="http://fanyi.baidu.com/";
		 for (int i = 0; i < 10; i++) {
		 buf = "_" + i;
		
//		 contentItem = new NoTrBeen(noticeId, account, company, date, additional, hasNoticed, checkState, companyBalance, balance, accountSign)
		 contentItem = new NoTrBean("noticeId", "account "+buf, "company "+buf,"2014年12月10日"+buf, "additional "+buf, i%2+"", i%3+"",  "companyBalance"+buf, "balance"+buf,  "accountSign"+buf);
		 listItems_list.add(contentItem);
		 contentItem = null;
		 }
		
		 listItem.setItems(listItems_list);
		 listItem.setMore_url(more_url);
		 L.d(TAG,"getData() - "+listItem);
		 return listItem;
	}


	public static String[] getAccoutlist() {
			Long time = System.currentTimeMillis();
			String[] accounts = new String[] { time + "1", time + "2", time + "3",
					time + "4", time + "5" };
			return accounts;
	}


	public static EntityBase getSmsAuthCodeResp() {
		EntityBase base = new EntityBase(0, "send failed");
		base.setMSG("123456");
		return base;
	}
	public static EntityBase getVerifySmsAuthCodeResp() {
		EntityBase base = new EntityBase(1, "verify failed");
		return base;
	}

}
