package de.ecclesia.monitoringdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

	@GetMapping("/{fail}")
	public String test(@RequestParam("fail") boolean fail) {
		try {

			// tut hier logik hier

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a custom message", e);
		}




		if(fail) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a custom message");
		}
		return "Success";
	}


	@GetMapping("/2/{fail}")
	public ResponseEntity<String> test2(@RequestParam("fail") boolean fail) {
		if (fail) {
			return new ResponseEntity<>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
}
