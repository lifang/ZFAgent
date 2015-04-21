package com.comdosoft.financial.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.user.mapper.oldDataBase.OldDataBaseMapper;
import com.comdosoft.financial.user.mapper.trades.TradeDataBaseCopyMapper;
import com.comdosoft.financial.user.mapper.zhangfu.ZFDataBaseCopyMapper;

/**
 * 数据迁移
 * @author yyb
 *
 */
@Service
public class DataBaseCopyService {
	@Autowired
	private ZFDataBaseCopyMapper zfMapper;
	@Autowired
	private TradeDataBaseCopyMapper tradeMapper;
	@Autowired
	private OldDataBaseMapper oldMapper;
	
	private Map<String, Object> mapTemp= new HashMap<String, Object>();
	
	
	public void init(){
		zfInit();
		tradesInit();
	}
	
	@Transactional(value="transactionManager-trades",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void tradesInit(){
		System.out.println("==============================旧数据库 hx_order表数据迁移开始==================================================");
		List<Map<String, Object>> hxOrderList=oldMapper.getHxOrder();
		for(int i=0;i<hxOrderList.size();i++){
			String transtype="";
			if(null!=hxOrderList.get(i).get("transtype")){
				transtype=hxOrderList.get(i).get("transtype").toString();
			}
			String merchantid="";
			if(null!=hxOrderList.get(i).get("merchantid")){
				merchantid=hxOrderList.get(i).get("merchantid").toString();
			}
			String merchantorderid="";
			if(null!=hxOrderList.get(i).get("merchantorderid")){
				merchantorderid=hxOrderList.get(i).get("merchantorderid").toString();
			}
			String bpserialnum="";
			if(null!=hxOrderList.get(i).get("bpserialnum")){
				bpserialnum=hxOrderList.get(i).get("bpserialnum").toString();
			}
			String orderamt="";
			if(null!=hxOrderList.get(i).get("orderamt")){
				orderamt=hxOrderList.get(i).get("orderamt").toString();
			}
			
			String pan="";
			if(null!=hxOrderList.get(i).get("pan")){
				pan=hxOrderList.get(i).get("pan").toString();
			}
			String transdate="";
			if(null!=hxOrderList.get(i).get("transdate")){
				transdate=hxOrderList.get(i).get("transdate").toString();
			}
			String transstatus="";
			if(null!=hxOrderList.get(i).get("transstatus")){
				transstatus=hxOrderList.get(i).get("transstatus").toString();
			}
			String gwtype="";
			if(null!=hxOrderList.get(i).get("gwtype")){
				gwtype=hxOrderList.get(i).get("gwtype").toString();
			}
			String settledate="";
			if(null!=hxOrderList.get(i).get("settledate")){
				settledate=hxOrderList.get(i).get("settledate").toString();
			}
			String setlamt="";
			if(null!=hxOrderList.get(i).get("setlamt")){
				setlamt=hxOrderList.get(i).get("setlamt").toString();
			}
			String psam=" ";
			if(null!=hxOrderList.get(i).get("psam")){
				 psam=hxOrderList.get(i).get("psam").toString();
			}
			String repay="";
			if(null!=hxOrderList.get(i).get("repay")){
				repay=hxOrderList.get(i).get("repay").toString();
			}
			String type="";
			if(null!=hxOrderList.get(i).get("type")){
				type=hxOrderList.get(i).get("type").toString();
			}
			String shopId="";
			if(null!=hxOrderList.get(i).get("shopId")){
				shopId=hxOrderList.get(i).get("shopId").toString();
			}
			List<Map<String, Object>> mapTemp2=zfMapper.getCustomerIdBySerialNum(psam);
			if(null!=mapTemp2&& mapTemp2.size()>0){
				Map<String, Object> mapTemp3=mapTemp2.get(0);
				int customerId=Integer.parseInt(mapTemp2.get(0).get("customerId").toString());
				List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
				if(null!=listTemp && listTemp.size()>0){
					String agentId=listTemp.get(0).get("id").toString();
					tradeMapper.tradeRecordsInit("", "", psam, "", agentId, merchantid, "", "1", repay,
							orderamt, "", transdate, "", "", "", setlamt, "", transstatus, "", transtype, "", "",
							customerId+"", transtype, "", "", pan);
				}
			}
		}
		System.out.println("==============================旧数据库 hx_order表数据迁移完毕==================================================");
	
	
		System.out.println("==============================旧数据库 hx_phoneorder表数据迁移开始==================================================");
		List<Map<String, Object>> hxPhoneOrderList=oldMapper.getHxPhoneorder();
		for(int i=0;i<hxPhoneOrderList.size();i++){
			String transtype=hxPhoneOrderList.get(i).get("transtype").toString();
			String merchantid=hxPhoneOrderList.get(i).get("merchantid").toString();
			String merchantorderid=hxPhoneOrderList.get(i).get("merchantorderid").toString();
			String bpserialnum=hxPhoneOrderList.get(i).get("bpserialnum").toString();
			String orderamt=hxPhoneOrderList.get(i).get("orderamt").toString();
			String pan=hxPhoneOrderList.get(i).get("pan").toString();
			String transdate=hxPhoneOrderList.get(i).get("transdate").toString();
			String transstatus=hxPhoneOrderList.get(i).get("transstatus").toString();
			String gwtype=hxPhoneOrderList.get(i).get("gwtype").toString();
			String settledate=hxPhoneOrderList.get(i).get("settledate").toString();
			String setlamt=hxPhoneOrderList.get(i).get("setlamt").toString();
			String psam=hxPhoneOrderList.get(i).get("psam").toString();
			String repay=hxPhoneOrderList.get(i).get("repay").toString();
			String type=hxPhoneOrderList.get(i).get("type").toString();
			String shopId=hxPhoneOrderList.get(i).get("shopId").toString();
			String customerId=zfMapper.getCustomerIdBySerialNum(psam).toString();
			List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
			if(null!=listTemp && listTemp.size()>0){
				String agentId=listTemp.get(0).get("id").toString();
				tradeMapper.tradeRecordsInit("", "", psam, "", agentId, merchantid, "", "1", repay,
						orderamt, "", transdate, "", "", "", setlamt, "", transstatus, "", transtype, "", "",
						customerId, transtype, "","", pan);
			}
		}
		System.out.println("==============================旧数据库 hx_phoneorder表数据迁移完毕==================================================");
	
		System.out.println("==============================旧数据库 hx_transferorder表数据迁移开始==================================================");
		List<Map<String, Object>> hxTransferorderList=oldMapper.getHxTransferorder();
		for(int i=0;i<hxTransferorderList.size();i++){
			String transtype=hxTransferorderList.get(i).get("transtype").toString();
			String merchantid=hxTransferorderList.get(i).get("merchantid").toString();
			String merchantorderid=hxTransferorderList.get(i).get("merchantorderid").toString();
			String bpserialnum=hxTransferorderList.get(i).get("bpserialnum").toString();
			String orderamt=hxTransferorderList.get(i).get("orderamt").toString();
			String pan=hxTransferorderList.get(i).get("pan").toString();
			String transdate=hxTransferorderList.get(i).get("transdate").toString();
			String transstatus=hxTransferorderList.get(i).get("transstatus").toString();
			String gwtype=hxTransferorderList.get(i).get("gwtype").toString();
			String settledate=hxTransferorderList.get(i).get("settledate").toString();
			String setlamt=hxTransferorderList.get(i).get("setlamt").toString();
			String psam=hxTransferorderList.get(i).get("psam").toString();
			String repay=hxTransferorderList.get(i).get("repay").toString();
			String type=hxTransferorderList.get(i).get("type").toString();
			String shopId=hxTransferorderList.get(i).get("shopId").toString();
			String customerId=zfMapper.getCustomerIdBySerialNum(psam).toString();
			List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
			if(null!=listTemp && listTemp.size()>0){
				String agentId=listTemp.get(0).get("id").toString();
				tradeMapper.tradeRecordsInit("", "", psam, "", agentId, merchantid, "", "1", repay,
						orderamt, "", transdate, "", "", "", setlamt, "", transstatus, "", transtype, "", "",
						customerId, transtype, "","", pan);
			}
			
		}
		System.out.println("==============================旧数据库 hx_transferorder表数据迁移完毕==================================================");
	
		System.out.println("==============================旧数据库 payForCreditCard表数据迁移开始==================================================");
		List<Map<String, Object>> payForCreditCardList=oldMapper.getPayForCreditCard();
		for(int i=0;i<payForCreditCardList.size();i++){
			String forCardId=payForCreditCardList.get(i).get("forCardId").toString();
			String ShopId=payForCreditCardList.get(i).get("ShopId").toString();
			String version=payForCreditCardList.get(i).get("version").toString();
			String keyDeviceSerialNo=payForCreditCardList.get(i).get("keyDeviceSerialNo").toString();
			String payCardNo=payForCreditCardList.get(i).get("payCardNo").toString();
			String userPhone=payForCreditCardList.get(i).get("userPhone").toString();
			String partnerNo=payForCreditCardList.get(i).get("partnerNo").toString();
			String orderId=payForCreditCardList.get(i).get("orderId").toString();
			String qdOrderId=payForCreditCardList.get(i).get("qdOrderId").toString();
			String toAccount=payForCreditCardList.get(i).get("toAccount").toString();
			String feePhone=payForCreditCardList.get(i).get("feePhone").toString();
			
			String transferAmount=payForCreditCardList.get(i).get("transferAmount").toString();
			String price=payForCreditCardList.get(i).get("price").toString();
			String faceValue=payForCreditCardList.get(i).get("faceValue").toString();
			String rebateMoney=payForCreditCardList.get(i).get("rebateMoney").toString();
			String payStatus=payForCreditCardList.get(i).get("payStatus").toString();
			
			String payResultCode=payForCreditCardList.get(i).get("payResultCode").toString();
			String payResultDes=payForCreditCardList.get(i).get("payResultDes").toString();
			String deliveryStatus=payForCreditCardList.get(i).get("deliveryStatus").toString();
			String deliveryResultCode=payForCreditCardList.get(i).get("deliveryResultCode").toString();
			String deliveryResultDes=payForCreditCardList.get(i).get("deliveryResultDes").toString();
			String refundStatus=payForCreditCardList.get(i).get("refundStatus").toString();
			String refundResultCode=payForCreditCardList.get(i).get("refundResultCode").toString();
			String refundResultDes=payForCreditCardList.get(i).get("refundResultDes").toString();
			String remark=payForCreditCardList.get(i).get("remark").toString();
			String signature=payForCreditCardList.get(i).get("signature").toString();
			String proofread=payForCreditCardList.get(i).get("proofread").toString();
			String createTime=payForCreditCardList.get(i).get("createTime").toString();
			String finalTime=payForCreditCardList.get(i).get("finalTime").toString();
			String syncTime=payForCreditCardList.get(i).get("syncTime").toString();
			
			String customerId=zfMapper.getCustomerIdBySerialNum(keyDeviceSerialNo).toString();
			List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
			if(null!=listTemp && listTemp.size()>0){
				String agentId=listTemp.get(0).get("id").toString();
				tradeMapper.tradeRecordsInit("", "", keyDeviceSerialNo, "", agentId, "", "", "3", rebateMoney, faceValue, "",
						createTime, "", "","", price, "", payResultCode, payResultDes, "", "", "", customerId, "", "", "", payCardNo);
			}
		}
		System.out.println("==============================旧数据库 payForCreditCard表数据迁移完毕==================================================");
		
	
	
		System.out.println("==============================旧数据库 transfer表数据迁移开始==================================================");
		List<Map<String, Object>> transferList=oldMapper.getTransfer();
		for(int i=0;i<transferList.size();i++){
			String ShopId=transferList.get(i).get("ShopId").toString();
			String version=transferList.get(i).get("version").toString();
			String keyDeviceSerialNo=transferList.get(i).get("keyDeviceSerialNo").toString();
			String payCardNo=transferList.get(i).get("payCardNo").toString();
			String userPhone=transferList.get(i).get("userPhone").toString();
			String partnerNo=transferList.get(i).get("partnerNo").toString();
			String orderId=transferList.get(i).get("orderId").toString();
			String qdOrderId=transferList.get(i).get("qdOrderId").toString();
			String toAccount=transferList.get(i).get("toAccount").toString();
			String feePhone=transferList.get(i).get("feePhone").toString();
			
			String transferAmount=transferList.get(i).get("transferAmount").toString();
			String price=transferList.get(i).get("price").toString();
			String faceValue=transferList.get(i).get("faceValue").toString();
			String rebateMoney=transferList.get(i).get("rebateMoney").toString();
			String payStatus=transferList.get(i).get("payStatus").toString();
			
			String payResultCode=transferList.get(i).get("payResultCode").toString();
			String payResultDes=transferList.get(i).get("payResultDes").toString();
			String deliveryStatus=transferList.get(i).get("deliveryStatus").toString();
			String deliveryResultCode=transferList.get(i).get("deliveryResultCode").toString();
			String deliveryResultDes=transferList.get(i).get("deliveryResultDes").toString();
			String refundStatus=transferList.get(i).get("refundStatus").toString();
			String refundResultCode=transferList.get(i).get("refundResultCode").toString();
			String refundResultDes=transferList.get(i).get("refundResultDes").toString();
			String remark=transferList.get(i).get("remark").toString();
			String signature=transferList.get(i).get("signature").toString();
			String proofread=transferList.get(i).get("proofread").toString();
			String createTime=transferList.get(i).get("createTime").toString();
			String finalTime=transferList.get(i).get("finalTime").toString();
			String syncTime=transferList.get(i).get("syncTime").toString();
			
			String customerId=zfMapper.getCustomerIdBySerialNum(keyDeviceSerialNo).toString();
			List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
			if(null!=listTemp && listTemp.size()>0){
				String agentId=listTemp.get(0).get("id").toString();
				tradeMapper.tradeRecordsInit("", "", keyDeviceSerialNo, "", agentId, "", "", "3", rebateMoney, faceValue, "",
						createTime, "", "","", price, "", payResultCode, payResultDes, "", "", "", customerId, "", "", "", payCardNo);
			}
			
		}
		System.out.println("==============================旧数据库 transfer表数据迁移完毕==================================================");
		
	
		System.out.println("==============================旧数据库 feePhone表数据迁移开始==================================================");
		List<Map<String, Object>> feePhoneList=oldMapper.getFeePhone();
		for(int i=0;i<feePhoneList.size();i++){
			String ShopId=feePhoneList.get(i).get("ShopId").toString();
			String version=feePhoneList.get(i).get("version").toString();
			String keyDeviceSerialNo=feePhoneList.get(i).get("keyDeviceSerialNo").toString();
			String payCardNo=feePhoneList.get(i).get("payCardNo").toString();
			String userPhone=feePhoneList.get(i).get("userPhone").toString();
			String partnerNo=feePhoneList.get(i).get("partnerNo").toString();
			String orderId=feePhoneList.get(i).get("orderId").toString();
			String qdOrderId=feePhoneList.get(i).get("qdOrderId").toString();
			String feePhone=feePhoneList.get(i).get("feePhone").toString();
			
			String price=feePhoneList.get(i).get("price").toString();
			String faceValue=feePhoneList.get(i).get("faceValue").toString();
			String rebateMoney=feePhoneList.get(i).get("rebateMoney").toString();
			String payStatus=feePhoneList.get(i).get("payStatus").toString();
			
			String payResultCode=feePhoneList.get(i).get("payResultCode").toString();
			String payResultDes=feePhoneList.get(i).get("payResultDes").toString();
			String deliveryStatus=feePhoneList.get(i).get("deliveryStatus").toString();
			String deliveryResultCode=feePhoneList.get(i).get("deliveryResultCode").toString();
			String deliveryResultDes=feePhoneList.get(i).get("deliveryResultDes").toString();
			String refundStatus=feePhoneList.get(i).get("refundStatus").toString();
			String refundResultCode=feePhoneList.get(i).get("refundResultCode").toString();
			String refundResultDes=feePhoneList.get(i).get("refundResultDes").toString();
			String remark=feePhoneList.get(i).get("remark").toString();
			String signature=feePhoneList.get(i).get("signature").toString();
			String proofread=feePhoneList.get(i).get("proofread").toString();
			String createTime=feePhoneList.get(i).get("createTime").toString();
			String finalTime=feePhoneList.get(i).get("finalTime").toString();
			String syncTime=feePhoneList.get(i).get("syncTime").toString();
			
			String customerId=zfMapper.getCustomerIdBySerialNum(keyDeviceSerialNo).toString();
			List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
			if(null!=listTemp && listTemp.size()>0){
				String agentId=listTemp.get(0).get("id").toString();
				tradeMapper.tradeRecordsInit("", "", keyDeviceSerialNo, "", agentId, "", "", "3", rebateMoney, faceValue, "",
						createTime, "", "","", price, "", payResultCode, payResultDes, "", "", "", customerId, "", "", "", payCardNo);
			}
		}
		System.out.println("==============================旧数据库 feePhone表数据迁移完毕==================================================");
	
	
	
		System.out.println("==============================旧数据库zf300_order表数据迁移开始==================================================");
		List<Map<String, Object>> zf300OrderList=oldMapper.getZF300Order();
		for(int i=0;i<zf300OrderList.size();i++){
			String platforms=zf300OrderList.get(i).get("platforms").toString();
			String shopId=zf300OrderList.get(i).get("shopId").toString();
			String ksn_no=zf300OrderList.get(i).get("ksn_no").toString();
			String transNo=zf300OrderList.get(i).get("transNo").toString();
			String transTime=zf300OrderList.get(i).get("transTime").toString();
			String transAmount=zf300OrderList.get(i).get("transAmount").toString();
			String rebateMoney=zf300OrderList.get(i).get("rebateMoney").toString();
			String payCardNo=zf300OrderList.get(i).get("payCardNo").toString();
			String transType=zf300OrderList.get(i).get("transType").toString();
			String tranStatus=zf300OrderList.get(i).get("tranStatus").toString();
			String merchNo=zf300OrderList.get(i).get("merchNo").toString();
			String merchName=zf300OrderList.get(i).get("merchName").toString();
			String traceNo=zf300OrderList.get(i).get("traceNo").toString();
			String transId=zf300OrderList.get(i).get("transId").toString();
			
			String customerId=zfMapper.getCustomerIdBySerialNum(ksn_no).toString();
			List<Map<String, Object>> listTemp= zfMapper.getAgentIdByCustomerId(customerId+"");
			if(null!=listTemp && listTemp.size()>0){
				String agentId=listTemp.get(0).get("id").toString();
				tradeMapper.tradeRecordsInit("", "", ksn_no, "", agentId, merchNo, merchName, "2", rebateMoney, 
						transAmount, "", transTime, "", "", payCardNo, "", "", "", "", "", "", "", customerId, "",
						"", "", payCardNo);
			}
		}
		System.out.println("==============================旧数据库 zf300_order表数据迁移完毕==================================================");
	
	}
	
	@Transactional(value="transactionManager-zhangfu",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void zfInit(){
		System.out.println("==============================初始化goods,channelName以及relation关系==================================================");
		//初始化goods表数据
		Map<String, Object> goodsMap=zfMapper.getGoodsById(1);
		if(null!=goodsMap && goodsMap.size()>0){
			zfMapper.updateGoodsById(1, "ZF300");
		}else{
			zfMapper.goodsInit(1, "ZF300");
		}
		Map<String, Object> goodsMap1=zfMapper.getGoodsById(2);
		if(null!=goodsMap1 &&goodsMap1.size()>0){
			zfMapper.updateGoodsById(2, "ZF300S");
		}else{
			zfMapper.goodsInit(2, "ZF300S");
		}
		Map<String, Object> goodsMap2=zfMapper.getGoodsById(3);
		if(null!=goodsMap2 &&goodsMap2.size()>0){
			zfMapper.updateGoodsById(3, "翰鑫");
		}else{
			zfMapper.goodsInit(3, "翰鑫");
		}
		//初始化channel表数据
		Map<String, Object> channelsMap=zfMapper.getPayChannelById(1);
		if(null!=channelsMap &&channelsMap.size()>0){
			zfMapper.updatePayChannelById(1,"翰鑫");
		}else{
			zfMapper.channelNameInit(1,"翰鑫");
		}
		Map<String, Object> channelsMap1=zfMapper.getPayChannelById(2);
		if(null!=channelsMap1 &&channelsMap1.size()>0){
			zfMapper.updatePayChannelById(2,"中惠ZF300");
		}else{
			zfMapper.channelNameInit(2,"中惠ZF300");
		}
		Map<String, Object> channelsMap2=zfMapper.getPayChannelById(3);
		if(null!=channelsMap2 &&channelsMap2.size()>0){
			zfMapper.updatePayChannelById(3,"中惠ZF300S");
		}else{
			zfMapper.channelNameInit(3,"中惠ZF300S");
		}
		//初始化goods-channel关联关系表
		Map<String, Object> goodsChannelRelationMap=zfMapper.getGoodPayChannelById(1, 1);
		if(null!=goodsChannelRelationMap &&goodsChannelRelationMap.size()>0){
			
		}else{
			zfMapper.goodsChannelRelationInit(1,1);
		}
		Map<String, Object> goodsChannelRelationMap1=zfMapper.getGoodPayChannelById(2,2);
		if(null!=goodsChannelRelationMap1 &&goodsChannelRelationMap1.size()>0){
			
		}else{
			zfMapper.goodsChannelRelationInit(2,2);
		}
		Map<String, Object> goodsChannelRelationMap2=zfMapper.getGoodPayChannelById(3, 3);
		if(null!=goodsChannelRelationMap2 && goodsChannelRelationMap2.size()>0){
			
		}else{
			zfMapper.goodsChannelRelationInit(3,3);
		}
		System.out.println("==============================goods,channelName以及relation关系初始化完毕==================================================");
	

		System.out.println("==============================开始将旧数据库person表数据移入customers表==================================================");
		//与新系统相关表： customers， merchants 
		//字段对照关系如下：
		//Person的 userid   ->  customers 的id
		//Person的 phonenum    customers 的username， phone； 且account_type初始化为1,（1手机，2 邮箱）
		//Person 的 realname  ->  customers 的name
		//Person的status    customers的status （1 未激活  2 正常 3 停用）
		//Person的password    customers的password
		//Person的idnumber   merchants 的legal_person_card_id
		//Customers表的types初始化为1 （ 1 商户/普通用户 2 代理商 3 运营 4 超级管理员 5 第三方机构 6 代理商员工）
		//Customers表的City_id请初始化，具体id请查找表cities
		//userId,phoneNum,realName,status,password,idNumber
		
		List<Map<String, Object>> personList=oldMapper.getPersons();
		for(int i=0;i<personList.size();i++){
			int userId=Integer.parseInt(personList.get(i).get("userId").toString());
			String phoneNum=personList.get(i).get("phoneNum").toString();
			String realName=personList.get(i).get("realName").toString();
			String oldStatus=personList.get(i).get("status").toString();
			String pwd=personList.get(i).get("password").toString();
			String idNumber=personList.get(i).get("idNumber").toString();
			
			int status=2;
			if(oldStatus.equals("4444")){
				status=3;
			}
			//account_type初始化为1  Customers表的types初始化为1
			Map<String,Object> mapTemp=zfMapper.getCustomersByUserName(phoneNum);
			if(null!=mapTemp && mapTemp.size()>0){
				System.out.println("已存在该手机作为登录号记录,号码为"+phoneNum);
			}else{
				zfMapper.customersInit(phoneNum,1,phoneNum,realName,status,pwd,1);
				Map<String,Object> mapTemp1=zfMapper.getCustomersByUserName(phoneNum);
				int customerId=Integer.parseInt(mapTemp1.get("id").toString());
				zfMapper.merchantsInit(idNumber, customerId);
			}
		}
		System.out.println("==============================将旧数据库person表数据移入customers表完毕==================================================");
	
		
		
		System.out.println("==============================开始将旧数据库ZH_user表数据迁移==================================================");
		//与新系统相关表：
		//商品及相关表：goods，good_brands，pos_categories，dictionary_credit_types，dictionary_encrypt_card_ways，dictionary_sign_order_ways，goods_pay_channels
		//支付通道及相关表：pay_channels，supprot_trade_types，dictionary_trade_types ，pay_channel_billing_cycles，pay_channel_standard_rates，dictionary_trade_standard_rates，support_areas
		//终端及相关表：terminals，terminal_trade_type_infos，dictionary_billing_cycles，dictionary_trade_types
		//支付通道申请开通所需资料表：opening_requirements， opening_requirement_lists，dictionary_open_private_infos
		//用户开通支付通道提交资料表：opening_applies，terminal_opening_infos

		//解释一下：这张表主要是对应新系统的终端表（terminals），新系统中与终端相关的必须内容是商品（goods）和支付通道（pay_channels），与其他都是与这两个表相关的子表
		//字段对照关系如下：
		//Zh_user表中的userid    terminals中的id
		//Zh_user表中的status   terminals表中的status（1 已开通  2 部分开通  3 未开通 4 已注销  5 已停用）
		//Zh_user表中的company_name  opening_applies表中的merchant_name
		//Zh_user表中的reg_place，bank_deposit ，name，account_no
		//并将数据库对应值存到opening_applies，terminal_opening_infos表中
		//Zh_user表中的psam  terminals表中的serial_num
		//Zh_user表中的serial_type  terminals表中的base_rate
		//Zh_user表中product_type  对应的是商品信息（goods），请新建所有的商品信息，并新建支付通道（pay_channels）的信息，初始化好后将对应的good_id， pay_channel_id 放到terminals表中
		//Termnals表中需初始化的字段： merchant_id（商户id）， customer_id，agent_id（代理商id），account（终端账号）， password（终端密码），type，billing_cycles_id（到账日期id）， opened_at（开通日期）
		
		
//		List<Map<String, Object>> zhUserList=oldMapper.getZHUSERs();
//		for(int i=0;i<zhUserList.size();i++){
//			int userId=Integer.parseInt(zhUserList.get(i).get("userId").toString());
//			String oldStatus=zhUserList.get(i).get("status").toString();
//			String companyName=zhUserList.get(i).get("companyName").toString();
//			String regPlace=zhUserList.get(i).get("regPlace").toString();
//			String bankDeposit=zhUserList.get(i).get("bankDeposit").toString();
//			String name=zhUserList.get(i).get("name").toString();
//			String accountNo=zhUserList.get(i).get("accountNo").toString();
//			String psam=zhUserList.get(i).get("psam").toString();
//			String serialType=zhUserList.get(i).get("serialType").toString();
//			float temp=0;
//			if(serialType.contains("--")){
//				String[] serialTypeTemp=serialType.split("\\--");
//				temp=Float.parseFloat(serialTypeTemp[0].toString());
//				temp=temp*1000;
//			}else{
//				temp=Float.parseFloat(serialType);
//				temp=temp*1000;
//			}
//			int productType=Integer.parseInt(zhUserList.get(i).get("productType").toString());
//			String bankNo=zhUserList.get(i).get("bankNo").toString();
//			
//			int status=2;
//			if(oldStatus.equals("4444")){
//				status=3;
//			}
//			if(productType==0){
//				productType=2;
//			}else if(productType==1){
//				productType=3;
//			}
//			
//			Map<String, Object> channelGoodRelationMap=zfMapper.getChannelIdByGoodId(productType);
//			int channelId=Integer.parseInt(channelGoodRelationMap.get("channelId").toString());
//			
//			
//			//获得旧数据库personId 对应customerId
//			//List<Map<String, Object>> getUserPsamMap=oldMapper.getUserPsam1(psam);
//			//int personId=Integer.parseInt(getUserPsamMap.get("userId").toString());
//			//根据customerId获取merchant表的Id
//			List<Map<String, Object>> listTemp=oldMapper.getPhoneNumById(userId);
//			String phoneNum="";
//			if(null!=listTemp && listTemp.size()>0){
//				phoneNum=listTemp.get(0).get("phoneNum").toString();
//			}
//			mapTemp.clear();
//			mapTemp=new HashMap<String, Object>();
//			mapTemp=zfMapper.getCustomersByUserName(phoneNum);
//			if(null!=mapTemp && mapTemp.size()>0){
//				int customerId=Integer.parseInt(mapTemp.get("id").toString());
//				Map<String, Object> getMerchantsIdByCustomerIdMap=zfMapper.getMerchantsIdByCustomerId(customerId);
//				if(null!=getMerchantsIdByCustomerIdMap){
//					int merChantId=Integer.parseInt(getMerchantsIdByCustomerIdMap.get("id").toString());
//					
//					int terminalId=zfMapper.getMaxTerminalId();
//					zfMapper.terminalInit(status, psam, temp+"", productType, channelId,merChantId,userId);
//					//根据merChantId 更新数据
//					zfMapper.updateMerchantsById(customerId, companyName,bankDeposit,accountNo);
//					//Zh_user表中的userid    terminals中的id
//					zfMapper.openingAppliesInit(terminalId, merChantId,companyName,accountNo,name,bankNo,customerId,"1","","");
//				}
//			}
//		}
		System.out.println("==============================旧数据库ZH_user表数据迁移完毕==================================================");
	
	
		System.out.println("==============================旧数据库hx_person表数据迁移开始==================================================");
		//与zh_user表操作一致
		//Hx_person表的settle_account_type  opening_applies的types（1 对公 2 对私）
		//Hx_person表中的taxno， occn需要初始化opening_requirements和opening_requirement_lists和dictionary_open_private_infos，并将数据库对应值存到opening_applies，terminal_opening_infos表中
		
		List<Map<String, Object>> getHXPersonMap=oldMapper.getHXPerson();
		for(int i=0;i<getHXPersonMap.size();i++){
			int userId=Integer.parseInt(getHXPersonMap.get(i).get("userId").toString());
			String psam=getHXPersonMap.get(i).get("terminalId").toString();
			String merchantName=getHXPersonMap.get(i).get("merchantName").toString();
			int settleAccountType=1;
			if(null==getHXPersonMap.get(i).get("settleAccountType")||getHXPersonMap.get(i).get("settleAccountType").toString().trim().equals("")||getHXPersonMap.get(i).get("settleAccountType").toString().trim().equals("null")){
				
			}else{
				settleAccountType=Integer.parseInt(getHXPersonMap.get(i).get("settleAccountType").toString());
			}
			String settleAccount=getHXPersonMap.get(i).get("settleAccount").toString();
			String settleAccountNo=getHXPersonMap.get(i).get("settleAccountNo").toString();
			int status=0;
			if(getHXPersonMap.get(i).get("accountstatus").toString().trim().equals("")){
				
			}else{
				status=Integer.parseInt(getHXPersonMap.get(i).get("accountstatus").toString());
			}
			String taxno=getHXPersonMap.get(i).get("taxno").toString();
			String occno=getHXPersonMap.get(i).get("occno").toString();
			//根据customerId获取merchant表的Id
			String phoneNum=oldMapper.getPhoneNumById(userId).get(0).get("phoneNum").toString();

			Map<String,Object> mapTemp=zfMapper.getCustomersByUserName(phoneNum);
			int customerId=Integer.parseInt(mapTemp.get("id").toString());
			Map<String, Object> getMerchantsIdByCustomerIdMap=zfMapper.getMerchantsIdByCustomerId(customerId);
			if(null!=getMerchantsIdByCustomerIdMap && getMerchantsIdByCustomerIdMap.size()>0){
				int merChantId=Integer.parseInt(getMerchantsIdByCustomerIdMap.get("id").toString());
				
				int terminalId=zfMapper.getMaxTerminalId();
//				int terminalInit(@Param("id") int id,@Param("status") int status,@Param("serialNum") String serialNum,
//						@Param("baseRate") String baseRate,@Param("goodId") int goodId,@Param("channelId") int channelId,
//						@Param("merchantId") int merchantId,@Param("customerId") int customerId);
				zfMapper.terminalInit( status, psam,0+"", 0, 0,merChantId,customerId);
				
				//Zh_user表中的userid    terminals中的id
				zfMapper.openingAppliesInit(terminalId, merChantId,merchantName,settleAccountNo,settleAccount,"",customerId,settleAccountType+"",
						taxno,occno);
			}
		}
		System.out.println("==============================旧数据库hx_person表数据迁移完毕==================================================");
		
		
		System.out.println("==============================旧数据库userPsam表数据迁移开始==================================================");
		//与新数据库中terminals表相关
		//id  terminals 的id
		//userid –> terminals 的customer_id
		//psam –> terminals 的serial_num
		//status –> terminals 的 status
		List<Map<String, Object>> getUserPsamList=oldMapper.getUserPsam();
		for(int i=0;i<getUserPsamList.size();i++){
			int userId=Integer.parseInt(getUserPsamList.get(i).get("userid").toString());
			int status=Integer.parseInt(getUserPsamList.get(i).get("status").toString());
			String psam=getUserPsamList.get(i).get("psam").toString();
			List<Map<String, Object>> temp1=oldMapper.getPhoneNumById(userId);
			if(null!=temp1 && temp1.size()>0){
				String phoneNum=temp1.get(0).get("phoneNum").toString();
				Map<String,Object> mapTemp=zfMapper.getCustomersByUserName(phoneNum);
				int customerId=Integer.parseInt(mapTemp.get("id").toString());
				zfMapper.updateTerminalBySerialNum(customerId, status, psam);
			}
		}
		System.out.println("==============================旧数据库userPsam表数据迁移完毕==================================================");
		
		
		
		System.out.println("==============================旧数据库shop表数据迁移开始==================================================");
		//与新数据库中customers，agents， customer_addresses表相关
		//shop的 loginName    customers 的username， phone； 且account_type初始化为1,（1手机，2 邮箱）
		//shop的ShopName   agents的company_name
		//shop的Licence   agents的business_license
		//shop的Province ，City ，County，Address   整理成customer_addresses的city_id和address
		//shop的Post    customer_addresses的zip_code
		//shop的Contact    customer_addresses的receiver
		//shop的IdCard    agents的card_id
		//shop的MobilePhone   customer_addresses的moblephone
		//shop的PhoneNumber   customer_addresses的telphone
		//shop的Fid  agents 的parent_id
		
		List<Map<String, Object>> shopsList=oldMapper.getShops();
		for(int i=0;i<shopsList.size();i++){
			String loginName=shopsList.get(i).get("loginName").toString();
			String shopName=shopsList.get(i).get("shopName").toString();
			String licence=shopsList.get(i).get("licence").toString();
			String city=shopsList.get(i).get("city").toString();
			String county=shopsList.get(i).get("county").toString();
			String address=shopsList.get(i).get("address").toString();
			String post=shopsList.get(i).get("post").toString();
			String contact=shopsList.get(i).get("contact").toString();
			String idCard=shopsList.get(i).get("idCard").toString();
			String mobilePhone=shopsList.get(i).get("mobilePhone").toString();
			String phoneNumber=shopsList.get(i).get("phoneNumber").toString();
			String fid=shopsList.get(i).get("fid").toString();
			
			Map<String, Object> mapTemp=zfMapper.getCustomerIdByUserName(loginName);
			if(null!=mapTemp && mapTemp.size()>0){
				int customerId=Integer.parseInt(mapTemp.get("id").toString());
				
				zfMapper.agentsInit(shopName, licence, customerId, idCard,fid,mobilePhone);
				Map<String, Object> mapTemp1=zfMapper.getCityIdByCityName(city);
				if(null!=mapTemp1 && mapTemp1.size()>0){
					if(null!=mapTemp1.get("id") &&mapTemp1.get("id").equals("null")){
						String cityId=mapTemp1.get("id").toString();
						zfMapper.customerAddressesInit(cityId,county+address, post, contact,mobilePhone, phoneNumber, customerId);
						//更新customers表的cityId
						zfMapper.updateCustomerCityId(Integer.parseInt(cityId),customerId);
					}
				}
			}
		}
		System.out.println("==============================旧数据库shop表数据迁移完毕==================================================");
	
		
		System.out.println("==============================旧数据库shipment表数据迁移开始==================================================");
		//与新数据库的goods，terminals，cs_out_storages表相关
		//Amount  更新goods表中id与 productId一致的商品，字段为quantity
		//库存现在是用terminals这个表记录的，入库也是这张表，所以要造完整所有商品的终端记录
		//Cs_out_storages是出库单，增加对应的出口记录
		//ShopId  terminals表中的agent_id
		//productId  terminals中的good_id
		//Amount  cs_out_storages的quantity
		//apply_num生成机制：年月日时分秒毫秒
		
		List<Map<String, Object>> shipMentList=oldMapper.getShipMent();
		for(int i=0;i<shipMentList.size();i++){
			String productId=shipMentList.get(i).get("productId").toString();
			String amount=shipMentList.get(i).get("amount").toString();
			String shopId=shipMentList.get(i).get("shopId").toString();
			Map<String,Object> mapTemp=oldMapper.getLoginNameByShopId(shopId);
			if(null!=mapTemp){
				String loginName=mapTemp.get("loginName").toString();
				Map<String, Object> mapTemp1=zfMapper.getCustomersByUserName(loginName);
				if(null!=mapTemp1 && mapTemp1.size()>0){
					String customerId=mapTemp1.get("id").toString();
					String agentId=zfMapper.getAgentIdByCustomerId(customerId).get(0).get("id").toString();
					zfMapper.updateGoodsQuantity(productId, amount);
					zfMapper.prepareGoodRecordsInit(agentId, productId, amount);
				}
			}
		}
		System.out.println("==============================旧数据库shipment表数据迁移完毕==================================================");
		
		
		
		
		System.out.println("==============================旧数据库sellList表数据迁移开始==================================================");
		//与新数据库terminals表相关
		//Status  terminals中的status
		//ShopId  terminals表中的agent_id
		//productId  terminals中的good_id
		
		List<Map<String, Object>> sellList=oldMapper.getSellList();
		for(int i=0;i<sellList.size();i++){
			String shopId=sellList.get(i).get("shopId").toString();
			String productId=sellList.get(i).get("productId").toString();
			String ProductNumber=sellList.get(i).get("ProductNumber").toString();
			String status=sellList.get(i).get("status").toString();
			Map<String, Object> loginNameObj=oldMapper.getLoginNameByShopId(shopId);
			if(null!=loginNameObj && loginNameObj.size()>0){
				String loginName=loginNameObj.get("loginName").toString();
				Map<String, Object> mapTemp1=zfMapper.getCustomersByUserName(loginName);
				if(null!=mapTemp1 && mapTemp1.size()>0){
					String customerId=mapTemp1.get("id").toString();
					String agentId=zfMapper.getAgentIdByCustomerId(customerId).get(0).get("id").toString();
					zfMapper.updateTerminalBySerialNum1(status, ProductNumber, productId,agentId);
				}
			}
		}
		System.out.println("==============================旧数据库sellList表数据迁移完毕==================================================");
		
		System.out.println("==============================旧数据库zf300_rate表数据迁移开始==================================================");
		//与新数据库的pay_channels ，support_trade_types（支付通道支持交易类型），dictionary_trade_types（交易类型枚举表）表相关
		//Type  pay_channels表的id，support_trade_types表的pay_channel_id
		//Transtype，rate  根据交易类型新建dictionary_trade_types记录，并更新support_trade_types表关联关系
		//1,5
		List<Map<String, Object>> zf300RateList=oldMapper.getZF300Rate();
		for(int i=0;i<zf300RateList.size();i++){
			String type=zf300RateList.get(i).get("type").toString();
			String transtype=zf300RateList.get(i).get("transtype").toString();
			String rate=zf300RateList.get(i).get("rate").toString();
			if(type.equals("zhonghuifu")){
				zfMapper.dictionaryTradeTypesInit(transtype, rate);
				int tempId=zfMapper.getMaxIdOfdictionaryTradeTypes();
				zfMapper.supportTradeTypesInit(transtype, 1+"", tempId);
			}else if(type.equals("hx_kuaihuitong")){
				zfMapper.dictionaryTradeTypesInit(transtype, rate);
				int tempId=zfMapper.getMaxIdOfdictionaryTradeTypes();
				zfMapper.supportTradeTypesInit(transtype, 5+"", tempId);
			}
		}
		System.out.println("==============================旧数据库zf300_rate表数据迁移完毕==================================================");
			
		
		System.out.println("==============================旧数据库shop_hx_rate表数据迁移开始==================================================");
		//与新数据库的agents，agent_profit_settings（代理商分润）表相关
		//Type –> agent_profit_settings表的pay_channel_id
		//Rate 暂不明，这个要根据实际情况判断是agents的default_profit（默认分润字段），还是要添加agent_profit_settings表记录
		//shop_id  agent_profit_settings表的agent_id
		System.out.println("==============================旧数据库shop_hx_rate表数据迁移完毕==================================================");
		
		
		System.out.println("==============================旧数据库shop_profitrate表数据迁移开始==================================================");
		//这个与一级代理商分润设置shop_hx_rate一致
				//Agents有parent_id字段，记录上级代理商的id
				//Code 代理商code需要生成，生成规则如下：
				//如：一级代理商编号 001，则他的二级代理商编号为001001，三级代理商的编号为001001001，以此类推
		System.out.println("==============================旧数据库shop_profitrate表数据迁移完毕==================================================");
		
		
		System.out.println("==============================旧数据库shop_zf300rate表数据迁移开始==================================================");
		//与shop_hx_rate的处理方式一致
		System.out.println("==============================旧数据库shop_zf300rate表数据迁移完毕==================================================");
		
		System.out.println("==============================旧数据库 show_repay表数据迁移开始==================================================");
		//与新数据库agents相关
		//这里面的记录，通过agents里面的字段is_have_profit（是否有分润），来区分当前代理商是否设置了分润
		System.out.println("==============================旧数据库 show_repay表数据迁移完毕==================================================");
		
		
	
	
	}	
}
