package com.ciklum.model.player;

import com.ciklum.model.game.Shape;

public class RockPlayer implements Player {

	@Override
	public Shape play() {
		return Shape.ROCK;
	}

}
