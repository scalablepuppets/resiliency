package com.skyy.resliency.circuitbreaker.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;

@RestController
@RequestMapping("/circuit")
public class CBRestController {
	
	@Autowired
	@Qualifier("cbglobalreg")
	CircuitBreakerRegistry cbGlobalReg;
	
	@Autowired
	CBService cbService;
	
	
	 @GetMapping("/cb")
	    public String func(@RequestParam(required = false) String str) {
		// Get a CircuitBreaker from the CircuitBreakerRegistry with the global default configuration
			CircuitBreaker cb = cbGlobalReg.circuitBreaker("srv1");
	       /* return Try.ofSupplier(CircuitBreaker.decorateSupplier(cb, () -> cbService.test()))
	                .recover(CircuitBreakerOpenException.class, "Circuit is Open!!")
	                .recover(RuntimeException.class, "Please try again after sometime !!").get();*/
			Boolean boo =   Try.ofSupplier(CircuitBreaker.decorateSupplier(cb, () -> cbService.test()))
	                .recover(CircuitBreakerOpenException.class, "Circuit is Open!!")
	                .isFailure();
			
			return cb.getState().toString();
	    }

}
