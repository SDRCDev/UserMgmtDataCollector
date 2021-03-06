package com.sdrc.usermgmtdatacollector.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class CollectionAudit {
	
	private String id;
	
	private String collectionName;
	
	private Date createdDate;
	
	private Date updateDate;
	

}
