package com.ciklum.view;

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

	public void addWin() {
		wins++;
	}

	public void addLose() {
		loses++;
	}

	public void addDraw() {
		draws++;
	}
	
	public Long getTotal() {
		return wins+loses+draws;
	}
}
