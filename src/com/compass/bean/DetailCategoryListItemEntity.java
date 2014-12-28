package com.compass.bean;

import java.util.List;

import com.compass.bean.DetailListBean.TOPMAP;

/**
 * 
* @ClassName: QueryCategoryListItemEntity 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author daodao ( dao.dev@qq.com ) 
* @date 2014年11月14日 下午2:38:11 
*
 */
public class DetailCategoryListItemEntity extends BaseContentList {

	public DetailCategoryListItemEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailCategoryListItemEntity(List<DetailBean> items,
			String more_url) {
		super();
		this.items = items;
		super.setMore_url(more_url);
	}

	private List<DetailBean> items;

	public List<DetailBean> getItems() {
		return items;
	}

	public void setItems(List<DetailBean> items) {
		this.items = items;

	}

	@Override
	public String toString() {
		return "QueryCategoryListItemEntity [items=" + items + "] "
				+ super.getMore_url();
	}

	private TOPMAP TOPMAP;
	
	public TOPMAP getTOPMAP() {
		return TOPMAP;
	}

	public void setTOPMAP(TOPMAP tOPMAP) {
		TOPMAP = tOPMAP;
	}

}
