package com.quesito.webstore.configuration;


//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class RootApplicationContextConfig {
//    @Value("${datasource.driver-class-name}")
//    private  String driverName;
//    @Value("${datasource.jdbc-url}")
//    private  String url;
//    @Value("${datasource.maximum-pool-size}")
//    private  int pool;
//    @Value("${datasource.username}")
//    private  String username;
//    @Value("${datasource.password}")
//    private  String password;



    @Bean
//    @ConfigurationProperties("datasource")
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("create-table.sql")
                .addScript("insert-data.sql")
                .build();

//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setUsername(username);
//        hikariDataSource.setPassword(password);
//        hikariDataSource.setJdbcUrl(url);
//        hikariDataSource.setMaximumPoolSize(pool);
//        hikariDataSource.setDriverClassName(driverName);
//        return hikariDataSource;

    }
    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
