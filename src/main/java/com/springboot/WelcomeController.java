package com.springboot;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.configuration.BasicConfiguration;

@RestController
public class WelcomeController {

	@Autowired
	private WelcomeService service;

	@Autowired
	private BasicConfiguration basicConfigurationService;
	
	@RequestMapping("/welcome")
	public String welcome() {
		return service.retrieveWelcomeMessage();
	}
	
	@RequestMapping("/dynamic-configuration")
	public Map dynamicConfiguration() {
		Map map = new HashMap();
		map.put("message",basicConfigurationService.getMessage());
		map.put("number",basicConfigurationService.getNumber());
		map.put("value",basicConfigurationService.isValue());
		
		return map;
	}
}