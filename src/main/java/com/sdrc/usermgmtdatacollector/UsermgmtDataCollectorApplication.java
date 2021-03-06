package com.sdrc.usermgmtdatacollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = { "com.sdrc.usermgmtdatacollector", "in.co.sdrc.sdrcdatacollector", "org.sdrc.usermgmt.core" })
@EnableMongoRepositories(basePackages = { "in.co.sdrc.sdrcdatacollector.mongorepositories",
		"com.sdrc.usermgmtdatacollector.repositories" })
@PropertySource(value = { "classpath:usermgmtdatacollector_notification.properties", "classpath:messages.properties" })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })

public class UsermgmtDataCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermgmtDataCollectorApplication.class, args);
	}

}
