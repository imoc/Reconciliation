package com.compass.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: BacklogInforItem
 * @Description: TODO(首页待办信息Bean)
 * @author daodao ( dao.dev@qq.com )
 * @date 2014年11月18日 下午2:47:57
 * 
 */
public class BanklogInforItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private int type;
	private int count;

	
	
	public BanklogInforItem(int type, int count) {
		this.type = type;
		this.count = count;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "BacklogInforItem [type=" + type + ", count=" + count + "]";
	}

}
