package com.sdrc.usermgmtdatacollector.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdrc.usermgmtdatacollector.model.UserModel;
import com.sdrc.usermgmtdatacollector.utils.TokenInfoExtracter;

/**
 * @author Sarita Panigrahi 
 */

@RestController
@RequestMapping("/me")
public class PrincipalResource {

	@Autowired
	private TokenInfoExtracter tokenInfoExtracter;
	
	@GetMapping
	public UserModel getPrincipal(OAuth2Authentication auth) {
		
		return tokenInfoExtracter.getUserModelWithOutRoleDesig(auth);
		
//		return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
