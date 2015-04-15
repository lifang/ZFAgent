package com.comdosoft.financial.user.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.CsAgent;
import com.comdosoft.financial.user.domain.zhangfu.CsCancel;
import com.comdosoft.financial.user.domain.zhangfu.Customer;
import com.comdosoft.financial.user.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.user.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsWebMapper;
import com.comdosoft.financial.user.utils.ZipUtils;

@Service
public class TerminalsWebService {
	@Resource
	private OpeningApplyMapper openingApplyMapper;

	@Resource
	private TerminalsWebMapper terminalsWebMapper;
	
	 @Value("${uploadPictureTempsPath}")
	    private String uploadPictureTempsPath;
	
	/**
	 * 获得终端列表
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getTerminalList(Integer id,
			Integer offSetPage, Integer pageSize,Integer frontStatus,String serialNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("frontStatus", frontStatus);
		map.put("serialNum", serialNum);
		return terminalsWebMapper.getTerminalList(map);
	}
	
	/**
	 * 获得终端列表总记录数
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public int getTerminalListNums(Integer id,
			Integer offSetPage, Integer pageSize,Integer frontStatus,String serialNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		map.put("frontStatus", frontStatus);
		map.put("serialNum", serialNum);
		return terminalsWebMapper.getTerminalListPage(map);
	}
	
	/**
	 * 搜索所有用户
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<Object, Object>> searchUser(Map<Object, Object> map) {
		return terminalsWebMapper.searchUser(map);
	}
	
	/**
	 * 查看该终端号是否存在
	 * @param terminalsNum
	 * @return
	 */
	public Object getTerminalsNum(String terminalsNum){
		return terminalsWebMapper.getTerminalsNum(terminalsNum);
	}
	
	/**
	 * 查看该终端号是否已经绑定
	 * @param terminalsNum
	 * @return
	 */
	public int numIsBinding(String terminalsNum){
		return terminalsWebMapper.numIsBinding(terminalsNum);
	}
	
	/**
	 * 查看该用户是否已有绑定终端
	 * @param erchantsId
	 * @return
	 */
	public int merchantsIsBinding(int merchantsId){
		return terminalsWebMapper.merchantsIsBinding(merchantsId);
	}
	
	
	/**
	 * 给用户绑定终端号
	 * @param map
	 */
	public void Binding(Map<Object, Object> map){
		terminalsWebMapper.Binding(map);
	}
	
	/**
	 * 判断用户是否存在
	 * @param map
	 */
	public int findUname(Map<Object, Object> map){
		return terminalsWebMapper.findUname(map);
	}
	
	/**
	 * 添加新用户
	 * @param map
	 */
	public void addUser(Customer customer){
		terminalsWebMapper.addUser(customer);
	}
	
	/**
	 * 检查终端号是否存在
	 * @param map
	 */
	public int checkTerminalCode(String str){
		return  terminalsWebMapper.checkTerminalCode(str);
	}
	
	/**
	 * 添加申请售后记录
	 * @param csAgent
	 */
	public  void submitAgent(CsAgent csAgent){
		terminalsWebMapper.submitAgent(csAgent);
	}
	
	/**
	 * 收件人信息
	 * @param customerId
	 * @return
	 */
	public  List<Map<String, Object>> getAddressee(int customerId){
		return terminalsWebMapper.getAddressee(customerId);
	}
	
	/**
	 * 添加联系地址
	 * 
	 * @param customerAddress
	 * @return
	 */
	public void addCostometAddress(CustomerAddress customerAddress) {
		terminalsWebMapper.addCostometAddress(customerAddress);
	}
	
	/**
	 * 添加联系地址
	 * 
	 * @param customerAddress
	 * @return
	 */
	public void addCsAgentMark(Map<Object, Object> map) {
		terminalsWebMapper.addCsAgentMark(map);
	}
	
	/**
	 * 获得终端详情
	 * @param id
	 * @return
	 */
	public Map<String, String> getApplyDetails(Integer id){
		return terminalsWebMapper.getApplyDetails(id);
	}
	
	/**
	 * 获得该终端交易类型详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getRate(Integer id){
		return terminalsWebMapper.getRate(id);
	}
	
	/**
	 * 获得租赁信息
	 * @param id
	 * @return
	 */
	public Map<String, String> getTenancy(Integer id){
		return terminalsWebMapper.getTenancy(id);
	}
	
	/**
	 * 获得申请开通已有基本信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getOppinfo(Integer terminalsId) {
		OpeningApplie openingApplie =new OpeningApplie();
		openingApplie.setTerminalId(terminalsId);
		
		 SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd" );
		 Map<String, Object> map = terminalsWebMapper.getOppinfo(openingApplie);
		 if(map!=null){
			 map.put("birthday", sdf.format(map.get("birthday")));
				map.put("created_at", sdf.format(map.get("created_at")));
				map.put("updated_at", sdf.format(map.get("updated_at")));
		 }
		return map;
	}
	
	/**
	 * 获得注销模板
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>>  getModule(Integer  terminalsId,int type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalsId",terminalsId);
		map.put("type", type);
		return terminalsWebMapper.getModule(map);
	}
	
	/**
	 * 提交注销
	 * @param map
	 */
	public void subRentalReturn(CsCancel csCancel){
		terminalsWebMapper.subRentalReturn(csCancel);
	}
	
	/**
	 * 注销申请判断
	 * 
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int JudgeRentalReturnStatus(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalsWebMapper.JudgeRentalReturnStatus(map);
	}
	
	/**
	 * 提交更新申请
	 * @param map
	 */
	public void subToUpdate(Map<Object, Object> map){
		terminalsWebMapper.subToUpdate(map);
	}
	
	/**
	 * 跟新申请判断
	 * 
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int judgeUpdateStatus(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalsWebMapper.JudgeUpdateStatus(map);
	}
	
	/**
	 * 获得该用户收获地址
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getCustomerAddress(Integer id) {
		 SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd" );
		List<Map<Object, Object>> list = terminalsWebMapper.getCustomerAddress(id);
		for(Map<Object, Object> map:list){
			map.put("created_at", sdf.format(map.get("created_at")));
		}
		return list;
	}
	
	/**
	 * 换货申请判断
	 * 
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int JudgeChangStatus(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalsWebMapper.JudgeChangStatus(map);
	}
	
	/**
	 * 获得终端状态
	 * @param id
	 * @return
	 */
	public List<Map<Object, Object>> getFrontPayStatus(){
		return terminalsWebMapper.getTerminStatus();
	}
	
	/**
	 * 获得追踪记录
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getTrackRecord(Integer id){
		return terminalsWebMapper.getTrackRecord(id);
	}
	
	/**
	 * 开通详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getOpeningDetails(Integer id){
		return terminalsWebMapper.getOpeningDetails(id);
	}
	
	/**
	 * 支付通道列表
	 * @return
	 */
	public List<Map<Object, Object>> getChannels(){
		return terminalsWebMapper.channels();
	}
	
	/**
	 * POS找回密码
	 * @param id
	 * @return
	 */
	public String findPassword(Integer id){
		return terminalsWebMapper.findPassword(id);
	}
	
	/**
	 * 申请资料下载
	 * @param request
	 * @param id
	 * @param response
	 * @return
	 * @throws IOException
	 */
    public String downloadPdf(HttpServletRequest request, String id, HttpServletResponse response) throws IOException {
		// 保存上传的实体文件
        String rootDir = request.getServletContext().getRealPath(uploadPictureTempsPath);
        List<String> fileName = new ArrayList<String>();
        File aFile = null;
                aFile = new File(rootDir + File.separator + id);
                if (aFile.exists()) {
                    fileName.add(rootDir + File.separator + id  +"/opengImg");
                } 
        String zipPath = rootDir + "zipFile/"+ id + File.separator ;
        File fileExists = new File(zipPath);
        fileExists.mkdirs();
        String zipName = zipPath + new Date().getTime() + ".zip";
        if (ZipUtils.createZip(fileName, zipName)) {
            response.reset();
            response.setContentType("Content-Type:application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("开通资料.zip").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            InputStream in = null;
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                in = new FileInputStream(new File(zipName));
                bis = new BufferedInputStream(in);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } else {
            // zip创建失败
            // return"系统出错，下载失败!";
        }
        return null;
    }
}
