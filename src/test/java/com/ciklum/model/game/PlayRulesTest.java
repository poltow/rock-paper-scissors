package com.ciklum.model.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ciklum.model.game.PlayRules;
import com.ciklum.model.game.Result;
import com.ciklum.model.game.Shape;

public class PlayRulesTest {

	@Test
	public void rock_beats_scissors() {
		Result result = PlayRules.getResultForRound(Shape.ROCK, Shape.SCISSORS);
		assertEquals(Result.WIN, result);
	}

	@Test
	public void rock_is_beaten_by_paper() {
		Result result = PlayRules.getResultForRound(Shape.ROCK, Shape.PAPER);
		assertEquals(Result.LOSE, result);
	}

	@Test
	public void rock_draws_rock() {
		Result result = PlayRules.getResultForRound(Shape.ROCK, Shape.ROCK);
		assertEquals(Result.DRAW, result);
	}

	@Test
	public void paper_beats_rock() {
		Result result = PlayRules.getResultForRound(Shape.PAPER, Shape.ROCK);
		assertEquals(Result.WIN, result);
	}

	@Test
	public void paper_is_beaten_by_scissors() {
		Result result = PlayRules.getResultForRound(Shape.PAPER, Shape.SCISSORS);
		assertEquals(Result.LOSE, result);
	}

	@Test
	public void rock_draws_paper() {
		Result result = PlayRules.getResultForRound(Shape.PAPER, Shape.PAPER);
		assertEquals(Result.DRAW, result);
	}

	@Test
	public void scissors_beats_paper() {
		Result result = PlayRules.getResultForRound(Shape.SCISSORS, Shape.PAPER);
		assertEquals(Result.WIN, result);
	}

	@Test
	public void scissors_is_beaten_by_rock() {
		Result result = PlayRules.getResultForRound(Shape.SCISSORS, Shape.ROCK);
		assertEquals(Result.LOSE, result);
	}

	@Test
	public void scissors_draws_scissors() {
		Result result = PlayRules.getResultForRound(Shape.SCISSORS, Shape.SCISSORS);
		assertEquals(Result.DRAW, result);
	}
}
