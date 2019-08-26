package com.ciklum.exception;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GameExceptionHandlerTest {

	GamneExceptionHandler handler = new GamneExceptionHandler();

	@Test
	public void game_state_exception_should_return_406() {
		GameStateException exc = new GameStateException("GameStateException");
		ResponseEntity<GameErrorResponse> response = handler.handleException(exc);

		assert (HttpStatus.NOT_ACCEPTABLE.equals(response.getStatusCode()));
		assert (HttpStatus.NOT_ACCEPTABLE.value() == response.getBody().getStatus());
		assert ("GameStateException".equals(response.getBody().getMessage()));
		assert ( response.getBody().getTimeStamp()>0);
	}

	@Test
	public void game_request_exception_should_return_406() {
		GameRequestException exc = new GameRequestException("GameRequestException");
		ResponseEntity<GameErrorResponse> response = handler.handleException(exc);

		assert (HttpStatus.BAD_REQUEST.equals(response.getStatusCode()));
		assert (HttpStatus.BAD_REQUEST.value() == response.getBody().getStatus());
		assert ("GameRequestException".equals(response.getBody().getMessage()));
		assert ( response.getBody().getTimeStamp()>0);
	}
}
