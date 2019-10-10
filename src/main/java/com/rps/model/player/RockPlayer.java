package com.rps.model.player;

import com.rps.model.game.Shape;

public class RockPlayer implements Player {

	@Override
	public Shape play() {
		return Shape.ROCK;
	}

}
