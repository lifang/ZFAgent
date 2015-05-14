package com.comdosoft.financial.user.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.query.PosReq;
import com.comdosoft.financial.user.service.GoodService;
import com.comdosoft.financial.user.utils.SysUtils;

@RestController
@RequestMapping("api/good")
public class GoodsController {

    @Autowired
    private GoodService goodService ;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response getGoodsList(@RequestBody  PosReq posreq){
        Response response = new Response();
        Map<String,Object>  goods=  goodService.getGoodsList(setPosReq(posreq));
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(goods);
        return response;
    }
    
    private PosReq setPosReq(PosReq req){
        if(null!=req.getBrandsId()&&0!=req.getBrandsId().length){
            req.setBrandsIds(SysUtils.Arry2Str(req.getBrandsId()));
        }
        if(0!=req.getCategory()){
            req.setCategorys(goodService.categorys(req.getCategory()));
        }
        if(null!=req.getPayChannelId()&&0!=req.getPayChannelId().length){
            req.setPayChannelIds(SysUtils.Arry2Str(req.getPayChannelId()));
        }
        if(null!=req.getPayCardId()&&0!=req.getPayCardId().length){
            req.setPayCardIds(SysUtils.Arry2Str(req.getPayCardId()));
        }
        if(null!=req.getTradeTypeId()&&0!=req.getTradeTypeId().length){
            req.setTradeTypeIds(SysUtils.Arry2Str(req.getTradeTypeId()));
        }
        if(null!=req.getSaleSlipId()&&0!=req.getSaleSlipId().length){
            req.setSaleSlipIds(SysUtils.Arry2Str(req.getSaleSlipId()));
        }
        if(null!=req.gettDate()&&0!=req.gettDate().length){
            req.settDates(SysUtils.Arry2Str(req.gettDate()));
        }
        if(null!=req.getKeys()&&!"".equals(req.getKeys().trim())){
            req.setKeys(req.getKeys().trim());
        }else{
            req.setKeys(null);
        }
        return req;
    }
    
    @RequestMapping(value = "goodinfo", method = RequestMethod.POST)
    public Response getGoodInfo(@RequestBody PosReq posreq){
        Response response = new Response();
        response.setCode(Response.ERROR_CODE);
        if(posreq.getGoodId()>0){
            Map<String, Object> goodInfoMap;
            try {
                goodInfoMap = goodService.getGoodInfo(posreq);
                response.setCode(Response.SUCCESS_CODE);
                response.setResult(goodInfoMap);
            } catch (Exception e) {
                e.printStackTrace();
                return response;
            }
        }
        return response;
    }
    
    @RequestMapping(value = "getGoodImgUrl", method = RequestMethod.POST)
    public Response getGoodImgUrl(@RequestBody Map<String, Object> map){
    	Response response = new Response();
    	List<Map<String, Object>> temp=goodService.getGoodImgUrl(Integer.parseInt(map.get("goodId").toString()));
    	if(null==temp || temp.size()<1){
    		response.setCode(Response.ERROR_CODE);
    		response.setMessage("该商品不存在图片文件！");
    	}else{
    		response.setResult(temp);
    		response.setCode(Response.SUCCESS_CODE);
    	}
    	return response;
        
    }
    
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Response getSearchCondition(@RequestBody PosReq posreq){
        Response response = new Response();
        Map<String,Object> searchMap= goodService.getSearchCondition(posreq);
        response.setCode(Response.SUCCESS_CODE);
        response.setResult(searchMap);
        return response;
    }
    
   
}
