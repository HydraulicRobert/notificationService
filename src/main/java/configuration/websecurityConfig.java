package configuration;


import java.util.Arrays;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class websecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	   return null;

	}
	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
		manager.setAllowNullValues(false);
		manager.setCacheNames(Arrays.asList("sqlChkNotAllTop",
											"sqlChkNotAllBottom",
											"sqlChkNotTop1",
											"sqlChkSet",
											"sqlChk6Sec",
											"notificationList",
											"sixSecondTimestamp",
											"settingsTimestamp",
											"mostCurrentNotificationTimestamp",
											"mostCurrentNotificationTimestampAll",
											"userList"));
		return manager;
	}
}
