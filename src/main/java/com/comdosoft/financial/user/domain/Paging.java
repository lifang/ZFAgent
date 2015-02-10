package com.comdosoft.financial.user.domain;

/**
 * 分页对象<br>
 * <功能描述>
 *
 * @author zengguang
 *
 */
public class Paging {

    private int page;
    private int rows;
    private int offset;

    public Paging(int page, int rows) {
        this.page = page;
        this.rows = rows;
    }
    public Paging() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        if (rows <= 0) {
            rows = 10;
        }
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        if (page > 0 && rows > 0) {
            offset = (page - 1) * rows;
        } else {
            offset = 0;
        }
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}