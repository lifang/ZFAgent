package com.comdosoft.financial.user.utils.page;

/**
 * 分页请求
 * @author wu
 *
 */
public class PageRequest {

	private int page;
	private int rows;
	
	public PageRequest(int page, int rows) {
		this.page = page;
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public int getRows() {
		return rows;
	}
	
	public int getOffset() {
		if(page>0){
			return (page - 1) * rows;
		}
		return 0;
	}
}
