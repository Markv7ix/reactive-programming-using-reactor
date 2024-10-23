package com.learnreactiveprogramming.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {

	FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

	@Test
	public void namesFlux_test1() {
		Flux<String> namesFlux = service.namesFlux();
		StepVerifier.create(namesFlux).expectNext("Adam", "Anna", "Jack", "Jenny").verifyComplete();
	}

	@Test
	public void namesFlux_test2() {
		Flux<String> namesFlux = service.namesFlux();
		StepVerifier.create(namesFlux).expectNext("Adam").expectNextCount(3).verifyComplete();
	}

	@Test
	public void namesFluxMap_test1() {
		Flux<String> namesFluxMap = service.namesFluxMap();
		StepVerifier.create(namesFluxMap).expectNext("ADAM", "ANNA", "JACK", "JENNY").verifyComplete();
	}

	@Test
	public void namesFluxMapFilter_test1() {
		Flux<String> namesFluxMap = service.namesFluxMapFilter(4);
		StepVerifier.create(namesFluxMap).expectNext("4-ADAM", "4-ANNA", "4-JACK", "5-JENNY").verifyComplete();
	}
	
	@Test
	public void namesMono_test1() {
		var nameMono = service.nameMono();
		StepVerifier.create(nameMono).expectNext("Adam").verifyComplete();
	}

	@Test
	public void namesMonoMapFilter_test() {
		var nameMono = service.namesMono_map_filter(3);
		StepVerifier.create(nameMono).expectNext("ALEX").verifyComplete();
	}
	
	@Test
	public void namesMonoMapFilter_default_test() {
		var nameMono = service.namesMono_map_filter(4);
		StepVerifier.create(nameMono).expectNext("default").verifyComplete();
	}
	
	@Test
	public void namesMonoMapFilter_switchIfEmpty_test() {
		var nameMono = service.namesMono_map_filter_switchIfEmpty(4);
		StepVerifier.create(nameMono).expectNext("default").verifyComplete();
	}
	
	@Test
	public void namesFluxFlatMap_test() {
		
		//Given
		int stringLength = 4;
		
		//When
		Flux<String> namesFluxFlatMap = service.namesFluxFlatMap(stringLength);
		//var namesFluxFlatMap = service.namesFluxFlatMap();
		
		//Then
		StepVerifier.create(namesFluxFlatMap)
			.expectNext("A","D","A","M", "A","N","N","A","J","A","C","K", "J","E","N","N","Y")
			.verifyComplete();
	}
	
	@Test
	public void namesFluxFlatMap_async_test() {
		
		//Given
		int stringLength = 4;
		
		//When
		Flux<String> namesFluxFlatMap = service.namesFluxFlatMap_async(stringLength);
		//var namesFluxFlatMap = service.namesFluxFlatMap();
		
		//Then
		StepVerifier.create(namesFluxFlatMap)
			.expectNextCount(17)
			.verifyComplete();
	}
	
	@Test
	public void namesFluxConcatMap_test() {
		
		//Given
		int stringLength = 4;
		
		//When
		Flux<String> namesFluxFlatMap = service.namesFluxConcatMap(stringLength);
		//var namesFluxFlatMap = service.namesFluxFlatMap();
		
		//Then
		StepVerifier.create(namesFluxFlatMap)
			.expectNext("A","D","A","M", "A","N","N","A","J","A","C","K", "J","E","N","N","Y")
			.verifyComplete();
	}
	
	@Test
	public void nameMonoFlatMap_test() {
		
		//Given
		int stringLength = 4;
		
		//When
		Mono<List<String>> namesMonoFlatMap= service.nameMonoFlatMap(stringLength);
		
		//Then
		StepVerifier.create(namesMonoFlatMap)
			.expectNext(List.of("A","L","E","X"))
			.verifyComplete();
	}
	
	@Test
	public void nameMonoFlatMapMany_test() {
		
		//Given
		int stringLength = 4;
		
		//When
		Flux<String> namesMonoFlatMapMany= service.nameMonoFlatMapMany(stringLength);
		
		//Then
		StepVerifier.create(namesMonoFlatMapMany)
			.expectNext("A","L","E","X")
			.verifyComplete();
	}
	
	@Test
	public void nameFluxTransform_test() {
		
		//Given
		int stringLength = 4;
		
		//When
		Flux<String> namesFluxTransform = service.namesFluxTransform(stringLength);
		
		//Then
		StepVerifier.create(namesFluxTransform)
			.expectNext("A","L","E","X","C","H","L","O","E")
			.verifyComplete();
	}
	
	@Test
	public void nameFluxTransform_empty_test() {
		
		//Given
		int stringLength = 6;
		
		//When
		Flux<String> namesFluxTransform = service.namesFluxTransform(stringLength);
		
		//Then
		StepVerifier.create(namesFluxTransform)
			.expectNext("default")
			.verifyComplete();
	}
	
	@Test
	public void namesFluxTransform_switchIfEmpty_test() {
		
		//Given
		int stringLength = 6;
		
		//When
		Flux<String> namesFluxTransform = service.namesFluxTransform_switchIfEmpty(stringLength);
		
		//Then
		StepVerifier.create(namesFluxTransform)
			.expectNext("D","E","F","A","U","L","T")
			.verifyComplete();
	}
	
	@Test
	public void explore_concat_test() {

		// When
		Flux<String> concatFlux = service.explore_concat();

		// Then
		StepVerifier.create(concatFlux).expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
	}
	
	@Test
	public void explore_concatWith_test() {

		// When
		Flux<String> concatFlux = service.explore_concatWith();

		// Then
		StepVerifier.create(concatFlux).expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
	}
	
	@Test
	public void explore_concatWithMono_test() {

		// When
		Flux<String> concatFlux = service.explore_concatWithMono();

		// Then
		StepVerifier.create(concatFlux).expectNext("A", "B").verifyComplete();
	}
	
	@Test
	public void explore_merge_test() {

		// When
		Flux<String> mergeFlux = service.explore_merge();

		// Then
		StepVerifier.create(mergeFlux).expectNext("A", "D", "B", "E", "C", "F").verifyComplete();
	}
	
	@Test
	public void explore_mergeWith_test() {

		// When
		Flux<String> mergeWithFlux = service.explore_mergeWith();

		// Then
		StepVerifier.create(mergeWithFlux).expectNext("A", "D", "B", "E", "C", "F").verifyComplete();
	}
	
	@Test
	public void explore_mergeWith_mono_test() {

	 	// When
		Flux<String> mergeWithFlux = service.explore_mergeWith_mono();

		// Then
		StepVerifier.create(mergeWithFlux).expectNext("A", "B").verifyComplete();
	}
	
	@Test
	public void explore_mergeSequential_test() {

		// When
		Flux<String> mergeWithFlux = service.explore_mergeSequential();

		// Then
		StepVerifier.create(mergeWithFlux).expectNext("A", "B", "C", "D", "E", "F").verifyComplete();
	}
	
	@Test
	public void explore_zip_test() {

		// When
		Flux<String> zippedFlux = service.explore_zip();

		// Then
		StepVerifier.create(zippedFlux).expectNext("AD", "BE", "CF").verifyComplete();
	}
	
	
	@Test
	public void explore_zip2_test() {

		// When
		Flux<String> zippedFlux = service.explore_zip2();

		// Then
		StepVerifier.create(zippedFlux).expectNext("AD14", "BE25", "CF36").verifyComplete();
	}
}
