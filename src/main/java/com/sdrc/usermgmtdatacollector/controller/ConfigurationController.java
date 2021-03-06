package com.sdrc.usermgmtdatacollector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.usermgmtdatacollector.services.ConfigurationService;

import in.co.sdrc.sdrcdatacollector.engine.UploadFormConfigurationService;

/**
 * @author Debiprasad Parida (debiprasad@sdrc.co.in)
 */

@RestController
@RequestMapping("/api")
public class ConfigurationController {

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UploadFormConfigurationService uploadFormConfigurationService;

	

	@GetMapping("/mongoClient")
	public String createMongoOauth2Client() {

		return configurationService.createMongoOauth2Client();

	}



	@GetMapping("/area")
	public ResponseEntity<String> area() {
		return configurationService.importAreas();
	}

	@GetMapping("/config")
	public ResponseEntity<String> config() {
		return configurationService.config();
	}

	@GetMapping("/configureRoleFormMappingOfEngine2")
	public Boolean configureRoleFormMappingOfEngine() {
		configurationService.configureRoleFormMapping();
		return true;
	}

	@GetMapping("/formsValue")
	public ResponseEntity<String> formsValue() {
		return configurationService.formsValue();
	}

	
	
	
	@GetMapping("/importForms")
	public ResponseEntity<String> importForms(){
		return configurationService.importForms();
	}
	@PostMapping("/setUpdatedDate")
	public ResponseEntity<String> setUpdatedDate(@RequestParam String ids){
		return configurationService.setUpdatedDate(ids);
	}
	
	
}
