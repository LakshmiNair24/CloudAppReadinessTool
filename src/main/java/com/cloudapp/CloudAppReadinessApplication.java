package com.cloudapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CloudAppReadinessApplication implements  CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CloudAppReadinessApplication.class, args);
	}
	
	@Bean
	public ScoringAlgorithm getScoringAlgorithm() {
		return new ScoringAlgorithm();
	}
	/**
	 * Scoring Algorithm Call
	 */
	@Override
	public void run(String... args) throws Exception {
		getScoringAlgorithm().baseCriteria();
	}

}
