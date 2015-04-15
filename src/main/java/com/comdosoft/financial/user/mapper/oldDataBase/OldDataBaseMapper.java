package com.comdosoft.financial.user.mapper.oldDataBase;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

public interface OldDataBaseMapper {
	List<Map<String, Object>> getPersons();
	
	List<Map<String, Object>> getZHUSERs();
	
	Map<String, Object> getUserPsam1(@Param("psam") String psam);
	
	List<Map<String, Object>> getHXPerson();
	
	List<Map<String, Object>> getUserPsam();

	List<Map<String, Object>> getShops();
	
	List<Map<String, Object>> getShipMent();
	
	List<Map<String, Object>> getZF300Rate();
	
	List<Map<String, Object>> getShopHxRate();
	
	List<Map<String, Object>> getHxOrder();
	
	List<Map<String, Object>> getHxPhoneorder();
	
	List<Map<String, Object>> getHxTransferorder();
	
	List<Map<String, Object>> getPayForCreditCard();
	List<Map<String, Object>> getTransfer();
	List<Map<String, Object>> getFeePhone();
	List<Map<String, Object>> getZF300Order();
}
