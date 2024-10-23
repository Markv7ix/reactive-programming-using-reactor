package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.learnreactiveprogramming.util.CommonUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	public Flux<String> namesFlux() {
		return Flux.fromIterable(Arrays.asList("Adam", "Anna", "Jack", "Jenny")).log(); // db or remote call
	}

	public Flux<String> namesFluxMap() {
		return Flux.fromIterable(Arrays.asList("Adam", "Anna", "Jack", "Jenny")).map(String::toUpperCase).log();
	}

	public Flux<String> namesFluxFlatMap(int stringLength) {
		return Flux.fromIterable(Arrays.asList("adam", "anna", "ben", "jack", "jenny"))
				.map(String::toUpperCase)
				.filter(s -> s.length() >= stringLength)
				.flatMap(s -> CommonUtil.splitString(s))
				.log();
	}

	/*
	 * Use flatMap if elements generation is async and ordering doesn't matters
	 */
	public Flux<String> namesFluxFlatMap_async(int stringLength) {
		return Flux.fromIterable(Arrays.asList("adam", "anna", "ben", "jack", "jenny"))
				.map(String::toUpperCase)
				.filter(s -> s.length() >= stringLength)
				.flatMap(s -> CommonUtil.splitStringDelay(s))
				.log();
	}

	/* 
	 * Use concatMap if ordering matters
	 */
	public Flux<String> namesFluxConcatMap(int stringLength) {
		return Flux.fromIterable(Arrays.asList("adam", "anna", "ben", "jack", "jenny"))
				.map(String::toUpperCase)
				.filter(s -> s.length() >= stringLength)
				.concatMap(s -> CommonUtil.splitStringDelay(s))
				.log();
	}
	
	public Flux<String> namesFluxMapFilter(int length) {
		return Flux.fromIterable(Arrays.asList("Adam", "Anna", "ben", "Jack", "Jenny", "Jon", "Lee", "yi"))
				.map(String::toUpperCase)
				.filter(s -> s.length() >= length)
				.map(s -> s.length() + "-" + s)
				.log();
	}

	public Mono<String> nameMono() {
		return Mono.just("Adam").log();
	}

	public Mono<String> namesMono_map_filter(int stringLength) {
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength)
				.defaultIfEmpty("default")
				.log();
	}

	public Mono<String> namesMono_map_filter_switchIfEmpty(int stringLength) {
		
		Mono<String> defaultMono = Mono.just("default");
		
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength)
				.switchIfEmpty(defaultMono)
				.log();
	}
	
	/* 
	 * We can use flatMap in Mono if transformation returns another Mono
	 */
	public Mono<List<String>> nameMonoFlatMap(int stringLength) {
		return Mono.just("alex").
				map(String::toUpperCase)
				.filter(s -> s.length() >= stringLength)
				.flatMap(CommonUtil::splitStringMono)
				.log();
	}
	
	/* 
	 * We can use flatMapMany in Mono if transformation returns a Flux
	 */
	public Flux<String> nameMonoFlatMapMany(int stringLength) {
		return Mono.just("alex").
				map(String::toUpperCase)
				.filter(s -> s.length() >= stringLength)
				.flatMapMany(CommonUtil::splitString)
				.log();
	}
	
	
	public Flux<String> namesFluxTransform(int stringLength) {
		
		Function<Flux<String>, Flux<String>> filterMap = 
				flux -> flux.map(String::toUpperCase).filter(s -> s.length() >= stringLength);
		
		return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
				.transform(filterMap)
				.flatMap(CommonUtil::splitString)
				.defaultIfEmpty("default")
				.log();
	}
	
	public Flux<String> namesFluxTransform_switchIfEmpty(int stringLength) {
		
		Function<Flux<String>, Flux<String>> filterMap = 
				flux -> flux.map(String::toUpperCase)
				.filter(s -> s.length() >= stringLength)
				.flatMap(CommonUtil::splitString);
		
		Flux<String> defaultFlux = Flux.just("default").transform(filterMap);
				
		return Flux.fromIterable(Arrays.asList("alex", "ben", "chloe"))
				.transform(filterMap)
				.switchIfEmpty(defaultFlux)
				.log();
	}

	/*
	 * 1) defaultIfEmpty
	 * 
	 * Use the function namesMono_map_filter to implement the defaultIfEmpty in Mono
	 * 
	 * Pass the stringLength as 4 and use the defaultIfEmpty to return a value.
	 * 
	 * 2) switchIfEmpty
	 * 
	 * Create another function named namesMono_map_filter_switchIfEmpty and copy
	 * code from the namesMono_map_filter function and implement the switchIfEmpty
	 * in Mono
	 * 
	 * Pass the stringLength as 4 and use the switchIfEmpty to return a value.
	 * 
	 * Questions for this assignment 
	 * Use the defaultIfEmpty to return a default
	 * value in case of an empty response.
	 * 
	 * Use the switchIfEmpty to return a default value in case of an empty response.
	 */
	
	
	public Flux<String> explore_concat() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");

		return Flux.concat(flux1, flux2).log();
	}

	public Flux<String> explore_concatWith() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");

		return flux1.concatWith(flux2).log();
	}

	public Flux<String> explore_concatWithMono() {
		Mono<String> flux1 = Mono.just("A");
		Flux<String> flux2 = Flux.just("B");

		return flux1.concatWith(flux2).log();
	}
	
	public Flux<String> explore_merge() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

		return Flux.merge(flux1, flux2).log();
	}

	public Flux<String> explore_mergeWith() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

		return flux1.mergeWith(flux2).log();
	}
	
	public Flux<String> explore_mergeWith_mono() {
		Mono<String> mono1 = Mono.just("A");
		Mono<String> mono2 = Mono.just("B");

		return mono1.mergeWith(mono2).log();
	}

	public Flux<String> explore_mergeSequential() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

		return Flux.mergeSequential(flux1, flux2).log();
	}
	
	public Flux<String> explore_zip() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");

		return Flux.zip(flux1, flux2, (first, second) -> first + second).log();
	}
	
	public Flux<String> explore_zip2() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> flux3 = Flux.just("1", "2", "3");
		Flux<String> flux4 = Flux.just("4", "5", "6");

		return Flux.zip(flux1, flux2, flux3, flux4).map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4()).log();
	}
	
	public static void main(String[] args) {
		FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
		fluxAndMonoGeneratorService.namesFlux().subscribe(name -> System.out.println("Flux name: " + name));
		fluxAndMonoGeneratorService.namesFluxMap().subscribe(name -> System.out.println("Flux Map name: " + name));
		fluxAndMonoGeneratorService.nameMono().subscribe(name -> System.out.println("Mono name: " + name));
	}

}
