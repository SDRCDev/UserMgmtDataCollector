package com.sdrc.usermgmtdatacollector.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.usermgmtdatacollector.domain.EnginesRoleFormMapping;

import in.co.sdrc.sdrcdatacollector.models.AccessType;
import in.co.sdrc.sdrcdatacollector.util.Status;

public interface EngineRoleFormMappingRepository extends MongoRepository<EnginesRoleFormMapping,String>{

	EnginesRoleFormMapping findByRoleRoleIdAndFormFormIdAndAccessTypeAndStatus(Integer roleId, Integer formId,
			AccessType dataEntry, Status active);

	EnginesRoleFormMapping findByRoleRoleIdAndFormFormIdAndAccessType(Integer roleId, Integer formId,
			AccessType dataEntry);
	
	
}