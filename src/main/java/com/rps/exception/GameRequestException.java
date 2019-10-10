package com.rps.exception;

public class GameRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameRequestException(String message) {
		super(message);
	}

}
