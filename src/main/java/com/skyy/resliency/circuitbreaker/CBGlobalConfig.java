package com.skyy.resliency.circuitbreaker;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class CBGlobalConfig {
	
	// Create a custom configuration for a CircuitBreaker
	@Bean(name = "cbglobalconfig")
	public CircuitBreakerConfig cbGlobalConfig() {
	return CircuitBreakerConfig.custom()
			.enableAutomaticTransitionFromOpenToHalfOpen()
			.waitDurationInOpenState(Duration.ofMillis(100))
	    .failureRateThreshold(90)
	    .waitDurationInOpenState(Duration.ofMillis(100))
	   // .ringBufferSizeInHalfOpenState(2)
	    .ringBufferSizeInClosedState(2)
	    .recordExceptions(RuntimeException.class, TimeoutException.class)
	  /*  .recordFailure(throwable -> Match(throwable).of(
                Case($(instanceOf(RuntimeException.class)), true),
                Case($(), false)))*/
	    .build();
	
	//CircuitBreakerConfig circuitBreakerConfig = 
	
	}

	

	// Create a CircuitBreakerRegistry with a custom global configuration
	@Bean(name = "cbglobalreg")
	public CircuitBreakerRegistry cbGlobalReg(@Autowired CircuitBreakerConfig  cbglobalconfig) {
		return CircuitBreakerRegistry.of(cbglobalconfig);
	
	//CircuitBreakerRegistry globalCBRegistry =
	}
	
	// Get a CircuitBreaker from the CircuitBreakerRegistry with the global default configuration
	//CircuitBreaker cb = globalCBRegistry.circuitBreaker("srv1");

}
