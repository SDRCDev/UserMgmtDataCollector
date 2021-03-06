package com.sdrc.usermgmtdatacollector.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sdrc.usermgmtdatacollector.domain.AreaLevel;

@Repository
public interface AreaLevelRepository extends MongoRepository<AreaLevel, String>{

	AreaLevel findByAreaLevelId(Integer areaLevelId);

	AreaLevel findByAreaLevelName(String string);

}
