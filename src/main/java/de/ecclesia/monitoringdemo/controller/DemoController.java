package de.ecclesia.monitoringdemo.controller;

import io.micrometer.core.instrument.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final DemoService demoService;
	private final Counter exceptionCounter;

	public DemoController(@Autowired DemoService demoService,
						  @Autowired @Qualifier(DemoConfig.QUALIFIER_EXCEPTION_COUNTER) Counter exceptionCounter) {
		this.demoService = demoService;
		this.exceptionCounter = exceptionCounter;
	}

	@GetMapping("/demo")
	public ResponseEntity<DemoResponseDto> getDemo() {
		demoService.doSomethingInteresting();
		return new ResponseEntity<>(new DemoResponseDto("Hello World"), HttpStatus.OK);
	}

	@GetMapping("/withRandomException")
	public ResponseEntity<DemoResponseDto> withRandomException() {
		try {
			demoService.doSomethingInteresting();
			if (Math.random() > 0.5) {
				throw new IllegalArgumentException("Demo Exception");
			}
		} catch (Exception e) {
			logger.error("Illegal argument in getDemo2 Controller", e);
			exceptionCounter.increment();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new DemoResponseDto("Hello World 2"), HttpStatus.OK);
	}

	public record DemoResponseDto(String message) {

	}
}
