package com.rps.controller;

import org.junit.Test;

import com.rps.controller.ClientController;

public class ClientControllerTest {
	
	ClientController controller = new ClientController();
	
	@Test
	public void show_page_should_return_index() {
		assert("index".equals(controller.showPage()));
	}

	@Test
	public void play_game_should_return_play() {
		assert("play".equals(controller.playGame()));
	}

}
