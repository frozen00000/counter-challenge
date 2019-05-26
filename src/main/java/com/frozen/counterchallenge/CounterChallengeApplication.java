package com.frozen.counterchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.LongAccumulator;

@SpringBootApplication
@RestController
@ResponseBody
public class CounterChallengeApplication {

	private final LongAccumulator atomicLong = new LongAccumulator(Long::sum, 0);

	public static void main(String[] args) {
		SpringApplication.run(CounterChallengeApplication.class, args);
	}

	@GetMapping("/count")
	public Mono<Long> get() {
		return Mono.just(atomicLong.get());
	}

	@GetMapping("/")
	public void inc() {
		atomicLong.accumulate(1);
	}

}
