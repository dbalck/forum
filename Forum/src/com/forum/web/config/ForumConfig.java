package com.forum.web.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.forum.web.*" })
@Import({ SecurityConfig.class })
public class ForumConfig extends WebMvcConfigurerAdapter {

	@Bean
	public TilesViewResolver TilesResolver() {
		TilesViewResolver tResolver = new TilesViewResolver();
		return tResolver;
	}

	@Bean
	public TilesConfigurer TilesConfigurer() {
		TilesConfigurer tConfigurer = new TilesConfigurer();
		tConfigurer.setDefinitions("/WEB-INF/layouts/default.xml");
		return tConfigurer;
	}

	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory
				.setPackagesToScan(new String[] { 
						"com.forum.web.dao", 
						"com.forum.web.rss", 
						"com.forum.web.atom", 
						"com.forum.web.user" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	
	Properties hibernateProperties() {
		return new Properties() {
			{
			setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
			}
		};
	}
	
	@Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/forumdb");
        return dataSource;
    } 
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("/resources/");
	}

}