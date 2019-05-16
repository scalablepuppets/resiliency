package com.skyy.resliency.timeout.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class TimeoutConfig {
	
	public int timeoutinSeconds = 1;
	
	// For example, you want to restrict the execution of a long running task to 60 seconds.
	@Bean(name = "timeoutconfig")
	public TimeLimiterConfig config() {
		return TimeLimiterConfig.custom()
	    .timeoutDuration(Duration.ofSeconds(timeoutinSeconds))
	    .cancelRunningFuture(true)
	    .build();
	}
	
	@Bean(name = "timelimiter")
	public TimeLimiter limiter(@Autowired CircuitBreakerConfig  cbglobalconfig) {
		// Create TimeLimiter
		TimeLimiter timeLimiter = TimeLimiter.of(config());
		return timeLimiter;
	}

	

}
