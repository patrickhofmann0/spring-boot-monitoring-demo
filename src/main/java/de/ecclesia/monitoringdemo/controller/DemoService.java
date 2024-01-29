package de.ecclesia.monitoringdemo.controller;

import java.util.Random;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

	@Timed(value = "demo.method.doSomethingInteresting")
	public void doSomethingInteresting() {
		try {
			Thread.sleep(new Random().nextInt(100) * 10);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
