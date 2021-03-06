package com.sdrc.usermgmtdatacollector.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sdrc.usermgmtdatacollector.domain.CollectionAudit;

public interface CollectionAuditRepository extends MongoRepository<CollectionAudit, String> {

	CollectionAudit findByCollectionName(String string);

	//@Query("{{'collectionName':?0},'updateDate':{ $gte:?1}}")
	/*@Query(value = "{ $and: [{'collectionName' :{$in:?0 }},{'updatedDate':{ $gte:?1}}]}")
	CollectionAudit getByCollectionNameAndLastUpdatedDate(String collectionName, Date date);*/

}
