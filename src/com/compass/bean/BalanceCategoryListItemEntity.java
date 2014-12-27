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
public class BalanceCategoryListItemEntity extends BaseContentList {

	public BalanceCategoryListItemEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BalanceCategoryListItemEntity(List<BalanceBeen> items,
			String more_url) {
		super();
		this.items = items;
		super.setMore_url(more_url);
	}

	private List<BalanceBeen> items;

	public List<BalanceBeen> getItems() {
		return items;
	}

	public void setItems(List<BalanceBeen> items) {
		this.items = items;

	}

	@Override
	public String toString() {
		return "QueryCategoryListItemEntity [items=" + items + "] "
				+ super.getMore_url();
	}

}
