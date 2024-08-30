package com.organisation.digisegtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.organisation.digisegtest")
//@ImportResource({"classpath:springSecurityConfig.xml"})
public class DigSegTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigSegTestApplication.class, args);
	}

}
