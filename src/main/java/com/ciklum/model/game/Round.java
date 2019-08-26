package com.ciklum.model.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Round {

	@NonNull
	private Shape playerShape;
	
	@NonNull
	private Shape opponentShape;

	private Result result;

	public void calculateResult() {
		result = PlayRules.getResultForRound(this.playerShape, this.opponentShape);
	}

}
