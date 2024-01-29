package de.ecclesia.monitoringdemo.controller;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class DemoConfig {

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}

	public static final String QUALIFIER_EXCEPTION_COUNTER = "demoExceptionCounter";
	@Bean(QUALIFIER_EXCEPTION_COUNTER)
	public Counter demoExceptionCounter(MeterRegistry registry) {
		return Counter.builder("demo.global.exception")
				.description("Demo Exception Counter")
				.register(registry);
	}
}
