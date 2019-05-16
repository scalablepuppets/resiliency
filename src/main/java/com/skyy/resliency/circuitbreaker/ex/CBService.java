package com.skyy.resliency.circuitbreaker.ex;

import org.springframework.stereotype.Service;

@Service
public class CBService {
	
	private static int a=0;

	
    public String test() {
         
        if(a < 3){
        	
           return "Successful response Value of A:= "+a++;
        }
       /* try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        if(a>4 && a<15) {
        	a++;
        	System.out.println("#### value of A : "+a);
        	throw new RuntimeException();
        }
    	
        //return "doSomethingThrowException";
    	return "Successful response Value of A:= "+a++;
    }
}
