package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import common.Registrar;
import ddd.Order;

@Configuration
@ComponentScan(basePackageClasses={
		Registrar.class,
		Order.class
})
@EnableLoadTimeWeaving
@EnableSpringConfigured
public class AppConfig {

}
