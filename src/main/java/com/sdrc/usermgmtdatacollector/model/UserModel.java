package com.sdrc.usermgmtdatacollector.model;

import java.util.List;
import java.util.Set;

import com.sdrc.usermgmtdatacollector.domain.Area;

import lombok.Data;

/**
 * @author subham
 *
 */
@Datapublic class UserModel {

	private String userId;

	private Set<String> roleIds;

	private Set<String> roles;

	private List<Area> areas;

	private String emailId;

	private String orgName;

	private String designation;

	private String desgnName;

	private List<Integer> desgSlugIds;

	private String areaLevel;
	
	private List<Integer> areaIds;
	
	private List<String> partnerId;

}
