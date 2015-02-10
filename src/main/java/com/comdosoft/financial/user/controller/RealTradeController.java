package com.comdosoft.financial.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("real/trade")
public class RealTradeController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String realTradePage(){
		return "real_trade";
	}

	@RequestMapping(value="map",method=RequestMethod.POST)
	public void map(){
		
	}
}
