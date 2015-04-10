package com.comdosoft.financial.user.controller.api;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.user.domain.Response;
import com.comdosoft.financial.user.domain.zhangfu.MyOrderReq;
import com.comdosoft.financial.user.service.IndexService;
import com.comdosoft.financial.user.utils.SysUtils;

@RestController
@RequestMapping("api/index")
public class IndexController {

    @Autowired
    private IndexService indexService ;
     

    /**
     * 获取首页  收单机构列表
     * @return
     */
    @RequestMapping(value = "factory_list", method = RequestMethod.POST)
    public Response getFactoryList(){
        List<Map<String,Object>> result= indexService.getFactoryList();
        return Response.buildSuccess(result, "查询成功");
    }
    /**
     * 热卖pos
     * @return
     */
    @RequestMapping(value = "pos_list", method = RequestMethod.POST)
    public Response getPosList(){
        List<Map<String,Object>> result= indexService.getPosList();
        return Response.buildSuccess(result, "查询成功");
    }
    
    /**
     * 获取城市列表
     * @return
     */
    @RequestMapping(value = "getCity", method = RequestMethod.POST)
    public Response getCity(){
        List<Map<String,Object>> citys = indexService.findAllCities();
        return Response.buildSuccess(citys, "");
    }
    
    /**
     * 获取城市列表
     * @return
     */
    @RequestMapping(value = "getIdCity/{city_id}", method = RequestMethod.POST)
    public Response getIdCity(@PathVariable String city_id){
        Map<String,Object> citys = indexService.findCityById(city_id);
        String  result = citys.get("name").toString();
        while(!"0".equals(citys.get("parent_id").toString())){
        	citys = indexService.findCityById(citys.get("parent_id").toString());
        	result = citys.get("name").toString()+result;
        }
        return Response.buildSuccess(result, "");
    }
    
    /**
     * 获取用户详情页备注
     * @return
     */
    @RequestMapping(value = "getCustomerMarks/{customerId}", method = RequestMethod.POST)
    public Response getCustomerMarks(@PathVariable String customerId){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<Map<String,Object>> results = indexService.getCustomerMarks(customerId);
        for (Map<String, Object> map : results) {
			Date date = (Date) map.get("create_time");
			map.put("createTime", format.format(date));
		}
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("list", results);
        Response response = new Response();
        response.setResult(result);
        return response;
    }
    
    /**
     * 根据手机号发送验证码
     * @param req
     * @return
     */
    @RequestMapping(value = "getPhoneCode", method = RequestMethod.POST)
    public Response getPhoneCode(@RequestBody MyOrderReq req){
        String code = indexService.getPhoneCode(req);
        if(code.equals("-1")){
            return Response.getError("发送失败，请重新再试");
        }
        return Response.buildSuccess(code, "发送成功");
    }
    
    /**
     * 根据手机号发送验证码
     * @param req
     * @return
     */
    @RequestMapping(value = "getPhoneCodeAgent", method = RequestMethod.POST)
    public Response getPhoneCodeAgent(@RequestBody MyOrderReq req){
        String code = indexService.getPhoneCodeAgent(req);
        if(code.equals("-1")){
            return Response.getError("发送失败，请重新再试");
        }
        return Response.buildSuccess(code, "发送成功");
    }
    
    //新增用户详情备注
    @RequestMapping(value = "saveViewCustomerViews", method = RequestMethod.POST)
    public Response saveViewCustomerViews(@RequestBody MyOrderReq req){
    		indexService.saveViewCustomerViews(req);
    		return Response.getSuccess("保存备注成功！");
    }
    
    //更新手机号  根据用户id查询，更新 新手机号
    @RequestMapping(value = "changePhone", method = RequestMethod.POST)
    public Response changePhone(@RequestBody MyOrderReq req){
        indexService.changePhone(req);
        return Response.getSuccess();
    }
    
    //更新手机号  根据用户id查询，更新 新手机号
    @RequestMapping(value = "change_email", method = RequestMethod.POST)
    public Response change_email(@RequestBody MyOrderReq req,HttpServletRequest request){
        indexService.change_email(request,req);
        return Response.getSuccess();
    }
    
    //更新手机号  根据用户id查询，更新 新手机号
    @RequestMapping(value = "change_email_check", method = RequestMethod.POST)
    public Response change_email_check(@RequestBody MyOrderReq req,HttpServletRequest request){
        indexService.change_email_check(request,req);
        return Response.getSuccess();
    }
    
    //手机端用 修改邮箱 返回验证码
    @RequestMapping(value = "updateEmail", method = RequestMethod.POST)
    public Response update_email(@RequestBody MyOrderReq req,HttpServletRequest request){
    	String c = indexService.update_email(req);
    	if(c == ""){
    		return Response.getError("发送失败，请重新再试");
    	}
    	return Response.buildSuccess(c, "success");
    }
    
    //跳转页面修改邮箱
    @RequestMapping(value = "to_change_email/{id}/{name}/{str}")
    public void to_change_email(@PathVariable String id,@PathVariable String name, @PathVariable String str,HttpServletRequest request,HttpServletResponse response){
        String data = SysUtils.string2MD5(name+"zf_vc");
        String   url  = request.getScheme()+"://";  
        url+=request.getHeader("host");   
        url+=request.getContextPath();  
        if(data.equals(str)){
            try {
//                Cookie cookie_name = new Cookie("loginUserName",name);   
//                Cookie cookie_id = new Cookie("loginUserId",id);    
//                cookie_name.setMaxAge(Integer.MAX_VALUE);           
//                cookie_id.setMaxAge(Integer.MAX_VALUE);           
//                response.addCookie(cookie_id);                    
//                response.addCookie(cookie_name);                     
                response.sendRedirect(url+"/#/email_up");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                response.sendRedirect(url+"/#");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    //文件上传 返回上传后的地址
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Response upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
    		String url = request.getScheme() + "://"; // 请求协议 http 或 https
    		url += request.getHeader("host"); // 请求服务器
    		url += request.getContextPath();
    		String upload_path = url + "/uploads/";
             String realPath = request.getSession().getServletContext().getRealPath("/uploads");  
             try {
            	 String name = file.getOriginalFilename();
            	 String extName="";
            	 if (name.lastIndexOf(".") >= 0) {
 					extName = name.substring(name.lastIndexOf("."));
 				}
            		name = UUID.randomUUID().toString();
            	 upload_path +=name+extName;//绝对路径
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, name+extName));
			} catch (IOException e) {
				e.printStackTrace();
				return Response.getError("上传失败");
			}  
		// indexService.upload(request,req);
        //http://localhost:8080/zfmerchant/uploads/32246f8b-7209-4096-a57f-68524faeca00.jpg
		return Response.getSuccess(upload_path);
    }
    
    @RequestMapping(value="wxlist" ,method=RequestMethod.POST)
    public Response wxlist(@RequestBody MyOrderReq myOrderReq) {
        try{
            List<Map<String,Object>> centers = indexService.wxlist(myOrderReq);
            return Response.getSuccess(centers);
        }catch(NullPointerException e){
            return Response.buildErrorWithMissing();
        }catch(Exception e){
            return Response.getError("请求失败");
        }
    }
    
   
}
