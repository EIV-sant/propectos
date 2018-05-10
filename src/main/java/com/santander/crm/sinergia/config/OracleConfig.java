package com.santander.crm.sinergia.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class OracleConfig {
	
	@Value("${url.oracle}")
	private String urlOracle = "jdbc:oracle:thin:@180.181.159.40:1521:sinbande";     

	@Value("${user.oracle}")
	private String userOracle = "sin";

	@Value("${pass.oracle}")
	private String passOracle = "sinergia";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OracleConfig.class);
	
	@Bean
	DataSource dataSource() throws SQLException{
		
		if(urlOracle.equals(null) || urlOracle.isEmpty() || urlOracle.equals("") || urlOracle.equals("${url.oracle}")) {
			LOGGER.error("NO SE ENCUENTRA LA CONFIGURACIÓN DE ORACLE. REVISAR CONFIG-SERVICE - url.oracle. LA APLICACION SE TERMINARÁ");
			System.exit(0);
		} else if(userOracle.equals(null) || userOracle.isEmpty() || userOracle.equals("") || userOracle.equals("${user.oracle}")) {
			LOGGER.error("NO SE ENCUENTRA LA CONFIGURACIÓN DE ORACLE. REVISAR CONFIG-SERVICE - user.oracle");
			System.exit(0);
		} else if(passOracle.equals(null) || passOracle.isEmpty() || passOracle.equals("") || passOracle.equals("${pass.oracle}")) {
			LOGGER.error("NO SE ENCUENTRA LA CONFIGURACIÓN DE ORACLE. REVISAR CONFIG-SERVICE - pass.oracle");
			System.exit(0);
		}
		
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(userOracle);
		dataSource.setPassword(passOracle);
		dataSource.setURL(urlOracle);
		dataSource.setImplicitCachingEnabled(true);
		dataSource.setFastConnectionFailoverEnabled(true);
		
		return dataSource;
	}

}
