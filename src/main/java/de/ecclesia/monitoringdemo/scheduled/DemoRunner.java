package de.ecclesia.monitoringdemo.scheduled;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.ecclesia.monitoringdemo.service.DemoService;

@Component
public class DemoRunner  {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DemoService demoService;
	private final MeterRegistry registry;

	public DemoRunner(DemoService demoService, MeterRegistry registry) {
		this.demoService = demoService;
		this.registry = registry;
	}

	@Scheduled(fixedRate = 5000)
	public void run() {
		logger.info("Demo Runner is running");

		final Counter demoRunnerExceptionCounter = Counter.builder("demo.runner.exception")
				.description("Demo Runner Exception Counter")
				.register(registry);

		demoService.doSomethingInteresting();

		try {
			if (Math.random() * 10 > 5) {
				throw new IllegalArgumentException("Demo Exception");
			}
		} catch (Exception e) {
			logger.error("Illegal argument in demo runner");
			demoRunnerExceptionCounter.increment();
		}




	}
}
