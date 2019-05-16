package com.skyy.resliency.backpressure;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

public class RateLimiterPressureConfig {
	
	/*restrict the calling rate of some method to be not higher than 1000 req/10 ms.*/
	@Bean(name = "ratelimiterconfig")
	public RateLimiterConfig config() {
	
	 return RateLimiterConfig.custom()
		    .limitRefreshPeriod(Duration.ofMillis(10))
		    .limitForPeriod(100)
		    .timeoutDuration(Duration.ofMillis(150))
		    .build();
	 
	}
	
	@Bean(name = "ratelimiterRegig")
	public RateLimiterRegistry limiter(@Autowired CircuitBreakerConfig  cbglobalconfig) {
		// Create ratelimiterRegig
		RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config());
		return rateLimiterRegistry;
	}

}
