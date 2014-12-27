package com.compass.bean;

import java.util.List;

/**
 * 
* @ClassName: QueryCategoryListItemEntity 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author daodao ( dao.dev@qq.com ) 
* @date 2014年11月14日 下午2:38:11 
*
 */
public class NoTrCategoryListItemEntity extends BaseContentList {

	public NoTrCategoryListItemEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoTrCategoryListItemEntity(List<NoTrBean> items,
			String more_url) {
		super();
		this.items = items;
		super.setMore_url(more_url);
	}

	private List<NoTrBean> items;

	public List<NoTrBean> getItems() {
		return items;
	}

	public void setItems(List<NoTrBean> items) {
		this.items = items;

	}

	@Override
	public String toString() {
		return "QueryCategoryListItemEntity [items=" + items + "] "
				+ super.getMore_url();
	}

}
