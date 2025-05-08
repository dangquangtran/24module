//package org.example.userservice.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.AllArgsConstructor;
//import org.example.userservice.cluster.DataSourceContextHolder;
//import org.example.userservice.cluster.DataSourceRouting;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@AllArgsConstructor
//public class DataSourceConfig {
//
//    private Environment env;
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.write")
//    public DataSource writeDataSource() {
//        return DataSourceBuilder.create()
//                .type(HikariDataSource.class)
//                .url(env.getProperty("spring.datasource.write.url"))
//                .username(env.getProperty("spring.datasource.write.username"))
//                .password(env.getProperty("spring.datasource.write.password"))
//                .build();
//    }
//
//    @Bean
//    public DataSource readDataSource() {
//        DataSourceRouting dataSourceRouting = new DataSourceRouting();
//
//        DataSource writeDataSource = writeDataSource();
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("WRITE", writeDataSource);
//
//        String[] readUrls = env.getProperty("spring.datasource.read.urls", String[].class);
//        for (int i = 0; i < readUrls.length; i++) {
//            DataSource readDataSource = createReadDataSource(readUrls[i]);
//            String key = "READ" + i;
//            targetDataSources.put(key, readDataSource);
//            DataSourceContextHolder.addReadDataSourceKey(key);
//        }
//
//        dataSourceRouting.setTargetDataSources(targetDataSources);
//        dataSourceRouting.setDefaultTargetDataSource(writeDataSource);
//
//        return dataSourceRouting;
//    }
//
//    private DataSource createReadDataSource(String url) {
//        return DataSourceBuilder.create()
//                .type(HikariDataSource.class)
//                .url(url)
//                .username(env.getProperty("spring.datasource.read.username"))
//                .password(env.getProperty("spring.datasource.read.password"))
//                .build();
//    }
//}
