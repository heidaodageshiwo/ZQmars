package com.iekun.ef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.Queue;
import javax.sql.DataSource;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;



@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@MapperScan("com.iekun.ef.dao")
@EnableScheduling
public class Efence2Application {

	@Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

	@Bean
    public Queue queue() {
       return new ActiveMQQueue("iekunReceiveQueue");
    }

	@Bean
    public Queue queueUeInfo() {
       return new ActiveMQQueue("iekunUeInfoReceiveQueue");
    }
    
    @Bean
    public Queue queueHeartBeat() {
       return new ActiveMQQueue("iekunHeartBeatQueue");
    }
	
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/mapper/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
    	System.out.println("file.encoding: " + System.getProperty("file.encoding"));
		System.out.println("jre code:" + java.nio.charset.Charset.defaultCharset());
        return new DataSourceTransactionManager(dataSource());
    }
    
	public static void main(String[] args) {
		
		SpringApplication.run(Efence2Application.class, args);
		//SpringApplication.addListeners(new ApplicationStartup());
		/*SpringApplication springApplication =new SpringApplication(Efence2Application.class);
		springApplication.addListeners(new ApplicationStartup());
		springApplication.run(args);*/

	}
}
