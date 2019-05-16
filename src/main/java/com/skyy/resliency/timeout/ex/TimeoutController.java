package com.skyy.resliency.timeout.ex;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.control.Try;

@RestController
@RequestMapping("/timeout")
public class TimeoutController {
	
	@Autowired
	@Qualifier("timelimiter")
	TimeLimiter limiter;
	
	/*@Autowired
	TimeoutService service;*/
	
	@GetMapping("/test")
	    public String func() {
		
		System.out.println(LocalDateTime.now());
		
		 Supplier<CompletableFuture<String>> futureSupplier = () -> CompletableFuture.supplyAsync(() -> {
			   try {
			       TimeUnit.SECONDS.sleep(2);
			   } catch (InterruptedException e) {
			       //throw new IllegalStateException(e);
			   }
			   return "Test";
			});
		 
		// Or decorate your supplier so that the future can be retrieved and executed upon
		 Callable<String> restrictedCall = TimeLimiter.decorateFutureSupplier(limiter, futureSupplier);
		 
		 try {
			/*Try.of(()-> restrictedCall.call())
			    .onFailure(throwable -> System.out.println("A timeout occurred.")).isFailure();*/
			Try<String> t = Try.of(()-> restrictedCall.call());
			if(t.isFailure()) {
				System.out.println(LocalDateTime.now());
				return "Request Timeout ! ";
			}
			if(t.isSuccess()) {
				System.out.println(LocalDateTime.now());
				return "Request Completed ! ";
			}
			/*if(Try.of(()-> restrictedCall.call()).isSuccess()) {
				System.out.println(LocalDateTime.now());
				return "Request Completed ! ";
			}*/
		   return "";
		  } catch (Exception e) {
				
				//e.printStackTrace();
			  System.out.println(LocalDateTime.now());
				return "Request Timeout ! ";
			}
	 }


}
