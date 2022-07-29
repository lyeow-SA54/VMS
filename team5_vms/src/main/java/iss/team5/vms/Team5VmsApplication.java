package iss.team5.vms;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import iss.team5.vms.services.SeedDBService;

@SpringBootApplication
public class Team5VmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team5VmsApplication.class, args);
	}
	
	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {
	
		@Autowired
		private SeedDBService seedDBService;

		@Override
		public void run(String...args) throws Exception {
			seedDBService.databaseInit();
	
		}
	}
	
	@Bean
  public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
      return new BufferedImageHttpMessageConverter();
  }
}
