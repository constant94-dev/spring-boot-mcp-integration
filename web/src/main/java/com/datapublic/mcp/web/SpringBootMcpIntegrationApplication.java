package com.datapublic.mcp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = "com.datapublic.mcp")
@EnableCaching
@EnableJpaRepositories(basePackages = "com.datapublic.mcp.storage.repository")
@EnableRedisHttpSession
public class SpringBootMcpIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMcpIntegrationApplication.class, args);
	}

}
