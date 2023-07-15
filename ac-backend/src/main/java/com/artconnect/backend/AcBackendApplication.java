package com.artconnect.backend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class AcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcBackendApplication.class, args);
	}
	
	@Configuration
	@Profile("default")
	static class LocalMongoDBConfiguration {
		
		@Value("${spring.data.mongodb.host}")
		private String mongoHost;
		
		@Value("${spring.data.mongodb.port}")
		private int mongoPort;
		
		@Value("${spring.data.mongodb.database}")
		private String mongoDatabase;
		
		@Bean
		MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer() {
			return settingsBuilder -> {
				MongoClientSettings.Builder builder = settingsBuilder.applyToClusterSettings(clusterSettings -> {
					clusterSettings.hosts(Arrays.asList(new ServerAddress(mongoHost, mongoPort)));
				});
				settingsBuilder.applyConnectionString(new ConnectionString("mongodb://" + mongoHost + ":" + mongoPort));
				builder.retryWrites(true);
			};
		}
	}
	
	@Configuration
	@Profile("docker")
	static class DockerMongoDBConfiguration {
		
		@Value("${spring.data.mongodb.uri}")
		private String mongoUri;
		
		@Bean
		MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer() {
			return settingsBuilder -> {
				settingsBuilder.applyConnectionString(new ConnectionString(mongoUri));
				settingsBuilder.retryWrites(true);
			};
		}
	}

}