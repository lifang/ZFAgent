package com.comdosoft.financial.user.controller.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    
    /**
     * 获取城市列表
     * @return
     */
    @RequestMapping(value = "getCity", method = RequestMethod.POST)
    public Response getCity(){
    	try {
    		 List<Map<String,Object>> citys = commentService.findAllCity();
    	        return Response.buildSuccess(citys, "");
        } catch (Exception e) {
        	return Response.getError("请求失败！");
        }
    }
    
    /**
     * 上传文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/tempImage/{id}", method = RequestMethod.POST)
    public Response tempImage(@PathVariable("id") int id,@RequestParam(value="img") MultipartFile img, HttpServletRequest request) {
        try {
        	return Response.getSuccess(commentService.saveTmpImage(id,img, request));
        } catch (IOException e) {
        	return Response.getError("请求失败！");
        }
    }
}
