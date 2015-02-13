package com.comdosoft.financial.user.utils.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 分页结果
 * 
 * @author wu
 *
 * @param <T>
 */
public class Page<T> {
	
	private PageRequest pageRequest;
	private List<T> content = new ArrayList<>();
	
    private long total;

	public Page(PageRequest pageRequest, List<T> content, long total) {
		this.pageRequest = pageRequest;
		this.content = content;
		this.total = total;
	}
	
    @SuppressWarnings("unchecked")
    public Page(PageRequest pageRequest, List<Map<String,Object>> contents,int total) {
        this.pageRequest = pageRequest;
        this.content =  (List<T>) contents;
        this.total = total;
    } 

    public long getTotal(){
		return total;
	}
	
	public List<T> getContent(){
		return Collections.unmodifiableList(content);
	}
	
	public int getCurrentPage(){
		return pageRequest.getPage();
	}
	
	public int getPageSize() {
		return pageRequest.getPageSize();
	}
	
	public int getTotalPage() {
		return getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getPageSize());
	}
}
