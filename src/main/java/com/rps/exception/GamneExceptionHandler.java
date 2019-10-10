package com.rps.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GamneExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<GameErrorResponse> handleException(GameStateException exc) {
		
		GameErrorResponse errorResponse = new GameErrorResponse();
		errorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		errorResponse.setMessage(exc.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler
	public ResponseEntity<GameErrorResponse> handleException(GameRequestException exc) {
		
		GameErrorResponse errorResponse = new GameErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(exc.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
