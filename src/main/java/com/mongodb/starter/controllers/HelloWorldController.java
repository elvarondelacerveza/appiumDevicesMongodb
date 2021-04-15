package com.mongodb.starter.controllers;

//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1")
//@RequestMapping(value = "/api/v1")
//@RequestMapping(value="/hello")
public class HelloWorldController {

	
//		@GetMapping(value="/")
//	@RequestMapping("/hello")
	@RequestMapping("/hello")
		public String hello(@RequestParam(value="name",defaultValue="World") String name) {
			return "Hello " + name + "!!";
		}
		
}
