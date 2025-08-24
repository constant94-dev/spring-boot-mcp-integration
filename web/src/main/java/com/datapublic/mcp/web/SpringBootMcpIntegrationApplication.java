package com.datapublic.mcp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.datapublic.mcp")
public class SpringBootMcpIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMcpIntegrationApplication.class, args);
	}

}
