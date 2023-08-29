package com.woxsen.codex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.woxsen.codex.configuration.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CodexApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodexApplication.class, args);
	}


}
