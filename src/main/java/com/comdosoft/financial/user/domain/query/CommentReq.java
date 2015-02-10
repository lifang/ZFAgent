package com.comdosoft.financial.user.domain.query;

import com.comdosoft.financial.user.domain.Paging;

public class CommentReq {

    private int goodId;

    private Paging paging;

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    

}
