package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ddd.Order;
import impl.OrderRepositoryImpl;

@Configuration
@ComponentScan(basePackageClasses={
		Order.class,
		OrderRepositoryImpl.class
})
@EnableLoadTimeWeaving
@EnableSpringConfigured
@EnableTransactionManagement
public class AppConfig { // implements LoadTimeWeavingConfigurer {
	
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
	JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
	
	@Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(findPackages());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        
//        factoryBean.setLoadTimeWeaver(reflectiveLoadTimeWeaver());
//        <property name="loadTimeWeaver">
//        <bean class="org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver"/>
//    </property>
        
        return factoryBean;
    }
	
	String[] findPackages() {
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

	Properties jpaProperties() {
		Properties jpaProperties = new Properties();
        jpaProperties.put(AvailableSettings.SHOW_SQL, true);
        jpaProperties.put(AvailableSettings.FORMAT_SQL, true);
        jpaProperties.put(AvailableSettings.USE_SQL_COMMENTS, true);
        jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, "create");
        jpaProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.H2Dialect");
        jpaProperties.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "true");
		return jpaProperties;
	}

//	@Override
//	public LoadTimeWeaver getLoadTimeWeaver() {
//		return new SimpleLoadTimeWeaver();
//	}
}
