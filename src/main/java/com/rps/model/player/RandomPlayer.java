package com.rps.model.player;

import java.util.Random;

import com.rps.model.game.Shape;

public class RandomPlayer implements Player {

	@Override
	public Shape play() {
		int pick = new Random().nextInt(Shape.values().length);
		return Shape.values()[pick];
	}

}
