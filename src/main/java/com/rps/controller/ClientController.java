package com.rps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {
	
	@RequestMapping("/")
	public String showPage() {
		return "index";
	}

	@RequestMapping("/play")
	public String playGame() {
		return "play";
	}
}
