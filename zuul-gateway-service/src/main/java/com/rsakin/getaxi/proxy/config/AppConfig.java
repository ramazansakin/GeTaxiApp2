package com.rsakin.getaxi.proxy.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

	@Primary
	@Bean
	public SwaggerResourcesProvider swaggerResourcesProvider() {
		ZuulProperties properties = new ZuulProperties();
		return () -> {
			List<SwaggerResource> resources = new ArrayList<>();
			properties.getRoutes().values()
					.forEach(route -> resources.add(createResource(route.getServiceId(), route.getId())));
			return resources;
		};
	}

	private SwaggerResource createResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation("/" + location + "/v2/api-docs");
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
	}

}
