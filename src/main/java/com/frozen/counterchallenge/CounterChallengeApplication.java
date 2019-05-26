package com.frozen.counterchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.LongAccumulator;

@SpringBootApplication
@RestController
public class CounterChallengeApplication {

	private LongAccumulator atomicLong = new LongAccumulator(Long::sum, 0);

	public static void main(String[] args) {
		SpringApplication.run(CounterChallengeApplication.class, args);
	}

	@PostMapping
	public void inc() {
		atomicLong.accumulate(1);
	}

	@GetMapping
	public Mono<Long> get() {
		return Mono.just(atomicLong.get());
	}

}
