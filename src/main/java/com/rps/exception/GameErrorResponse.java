package com.rps.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameErrorResponse {

	private int status;
	private String message;
	private long timeStamp;
}
