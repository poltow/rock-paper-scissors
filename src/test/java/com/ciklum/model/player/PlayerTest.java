package com.ciklum.model.player;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ciklum.model.game.Shape;
import com.ciklum.model.player.Player;
import com.ciklum.model.player.RandomPlayer;
import com.ciklum.model.player.RockPlayer;

public class PlayerTest {

	@Test
	public void rock_player_plays_rock() {
		Player player = new RockPlayer();
		assertEquals(Shape.ROCK, player.play());
	}

	@Test
	public void random_player_plays_any() {
		List<Shape> shapeList = Arrays.asList(Shape.values());
		Player player = new RandomPlayer();
		assert (shapeList.contains(player.play()));
	}
}
