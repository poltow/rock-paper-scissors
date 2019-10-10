package com.rps.view;

import com.rps.model.game.Result;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Totals {

	@NonNull
	private String userName;
	private Long wins = 0l;
	private Long loses = 0l;
	private Long draws = 0l;

	public Long getTotal() {
		return wins + loses + draws;
	}

	public void countResult(Result result) {
		switch (result) {
		case WIN:
			wins++;
			break;
		case DRAW:
			draws++;
			break;
		case LOSE:
			loses++;
			break;
		default:
			throw new IllegalStateException();
		}
	}
}
