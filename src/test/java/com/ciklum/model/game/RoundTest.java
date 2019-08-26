package com.ciklum.model.game;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class RoundTest {

	@Test
	public void calculate_should_set_result() {
		Round round = new Round(Shape.PAPER, Shape.ROCK);

		assert(Shape.PAPER.equals(round.getPlayerShape()));
		assert(Shape.ROCK.equals(round.getOpponentShape()));
		assertNull(round.getResult());
		round.calculateResult();

		assert(Result.WIN.equals(round.getResult()));
	}

}
