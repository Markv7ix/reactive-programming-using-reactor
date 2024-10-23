package com.learnreactiveprogramming.util;

import static java.lang.Thread.sleep;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CommonUtil {

	public static Flux<String> splitString(String name){
		return Flux.fromArray(name.split(""));
	}

	public static Flux<String> splitStringDelay(String name){
		
		int delay = new Random().nextInt(1000);
		
		return Flux.fromArray(name.split("")).delayElements(Duration.ofMillis(delay));
	}
	
	public static Mono<List<String>> splitStringMono(String name) {
		
		String [] charArray = name.split("");
		List<String> charList = List.of(charArray);
		return Mono.just(charList);
		
	}
	
    public static void delay(int ms){
        try {
            sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
