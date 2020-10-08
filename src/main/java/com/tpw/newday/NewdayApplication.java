package com.tpw.newday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.tpw.newday"})    //Controller,Service,Component扫描
@SpringBootApplication
public class NewdayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewdayApplication.class, args);
	}

}
