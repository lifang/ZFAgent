package com.comdosoft.financial.user.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.CommentReq;
import com.comdosoft.financial.user.service.CommentService;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService ;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getGoodsList(@RequestBody  CommentReq req){
        Response response = new Response();
        Map<String,Object> pcInfo= commentService.getList(req);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(pcInfo);
        return response;
    }
}
