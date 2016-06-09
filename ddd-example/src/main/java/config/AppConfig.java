package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ddd.Order;
import ddd.OrderItem;

@Configuration
//@ComponentScan(basePackageClasses={
//		OrderItem.class
//})
@EnableLoadTimeWeaving
@EnableSpringConfigured
@EnableJpaRepositories(basePackageClasses={
		Order.class
})
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
public class AppConfig {
	
	@Bean()
	OrderItem orderItem() {
		return new OrderItem();
	}
	
	@Bean
	String[] packages() {
		EntityAwareResolver entityAwareResolver = 
				new EntityAwareResolver() {
					@Override
					public List<String> entityPackages() {
						List<String> packages = new ArrayList<>();
						packages.add("ddd");
						return packages;
					}
				};
				
		return entityAwareResolver.entityPackages()
				.toArray(new String[entityAwareResolver.entityPackages().size()]);
	}

	@Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/ddd");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
	
	@Bean
    PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
	
	@Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(packages());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(AvailableSettings.SHOW_SQL, true);
        jpaProperties.put(AvailableSettings.FORMAT_SQL, true);
        jpaProperties.put(AvailableSettings.USE_SQL_COMMENTS, true);
        jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, "create");
//        jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, "create-drop");
        jpaProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
        jpaProperties.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "true");
//        jpaProperties.setProperty(AvailableSettings.HBM2DDL_IMPORT_FILES_SQL_EXTRACTOR, "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
        
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }
}
