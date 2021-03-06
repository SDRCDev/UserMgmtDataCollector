package com.sdrc.usermgmtdatacollector.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sdrc.usermgmtdatacollector.domain.EngineRole;

@Repository
public interface EnginesRoleRepoitory extends MongoRepository<EngineRole, String>{

	EngineRole findByRoleCode(String trimWhitespace);

}
