package com.qavi.carmaintanence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarmaintanenceApplication {
	public static final String secretJWT = "mosqueapplicationlkajjqieojqiojeksmndvjasfjhasdifjiwef";
	public static final Long loginExpiryTimeMinutes = 120L;
	public static void main(String[] args) {
		SpringApplication.run(CarmaintanenceApplication.class, args);
	}

}
