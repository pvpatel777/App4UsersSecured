package test.my.app4UsersSecured;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class App4UsersSecured {

	public static void main(String[] args) {
		SpringApplication.run(App4UsersSecured.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	private static final Logger LOG = LoggerFactory
			.getLogger(App4UsersSecured.class);

	@GetMapping("/resource")
	public String resource(@AuthenticationPrincipal Jwt jwt) {
		LOG.trace("***** JWT Headers: {}", jwt.getHeaders());
		LOG.trace("***** JWT Claims: {}", jwt.getClaims().toString());
		LOG.trace("***** JWT Token: {}", jwt.getTokenValue());
		return String.format("Resource accessed by: %s (with subjectId: %s)",
				jwt.getClaims().get("user_name"), jwt.getSubject());
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		final CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeHeaders(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setAfterMessagePrefix("REQUEST DATA : ");

		return loggingFilter;
	}
}
