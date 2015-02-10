package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.comdosoft.financial.user.domain.zhangfu.Merchant;
import com.comdosoft.financial.user.mapper.zhangfu.OpeningApplyMapper;
import com.comdosoft.financial.user.mapper.zhangfu.TerminalsMapper;
import com.comdosoft.financial.user.utils.SysUtils;

@Service
public class TerminalsService {
	@Resource
	private OpeningApplyMapper openingApplyMapper;

	@Resource
	private TerminalsMapper terminalsMapper;
	/**
	 * 获得终端列表
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getTerminalList(Integer id,
			Integer offSetPage, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("offSetPage", offSetPage);
		map.put("pageSize", pageSize);
		return terminalsMapper.getTerminalList(map);
	}

	/**
	 * 获得终端详情
	 * @param id
	 * @return
	 */
	public Map<String, String> getApplyDetails(Integer id){
		return terminalsMapper.getApplyDetails(id);
	}
	
	/**
	 * 获得该终端交易类型详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getRate(Integer id){
		return terminalsMapper.getRate(id);
	}

	/**
	 * 获得追踪记录
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getTrackRecord(Integer id){
		return terminalsMapper.getTrackRecord(id);
	}
	
	/**
	 * 开通详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getOpeningDetails(Integer id){
		return terminalsMapper.getOpeningDetails(id);
	}
	
	/**
	 * 支付通道列表
	 * @return
	 */
	public List<Map<String, String>> getChannels(){
		return terminalsMapper.channels();
	}
	
	/**
	 * 判断该终端号是否存在
	 * @param id
	 * @return
	 */
	public int isExistence(String serialNum){
		return terminalsMapper.isExistence(serialNum);
	}
	
	/**
	 * 判断商户名是否存在
	 * @param id
	 * @return
	 */
	public int isMerchantName(String title){
		return terminalsMapper.isMerchantName(title);
	}
	
	/**
	 * 添加商户
	 * @param merchant
	 */
	public void addMerchants(Merchant merchant){
		 terminalsMapper.addMerchants(merchant);
	}
	
	/**
	 * 添加终端
	 * @param map
	 */
	public void addTerminal(Map<String, String> map){
		terminalsMapper.addTerminal(map);
	}
	
	/**
	 * POS找回密码
	 * @param id
	 * @return
	 */
	public String findPassword(Integer id){
		return terminalsMapper.findPassword(id);
	}

	/**
     * author jwb
     * 查询终端开通情况
     * @param paramMap
     * @return
     */
    public List<Map<String,Object>> openStatus(Map<String, Object> paramMap) {
        List<Map<String,Object>> listmap=terminalsMapper.getTerminalListByPhone(paramMap);
        if(null!=listmap&&listmap.size()>0){
            List<Map<String,Object>> listmap2=null;
            int id=0;
            for (Map<String, Object> map : listmap) {
                id=SysUtils.String2int(""+map.get("id"));
                listmap2=terminalsMapper.getTerminalOpenStatus(id);
                map.put("openStatus", listmap2);
            }
        }
        return listmap;
    }
}
