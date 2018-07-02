
package com.zl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhouliang
 */
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan("com.zl")
public class BootApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("服务启动完成!");
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApplication.class, args);
	}


}
