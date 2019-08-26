package com.ciklum.model.game;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.ciklum.model.player.Player;
import com.ciklum.model.player.RandomPlayer;
import com.ciklum.model.player.RockPlayer;
import com.ciklum.model.player.Strategy;
import com.ciklum.model.user.User;

public class GameFactoryTest {

	@Test
	public void create_random_game_should() {

		User user = new User();
		user.setName("USER");
		user.setStrategy(Strategy.PLAY_RANDOM);

		Game game = GameFactory.createGameForUser(user);

		String userName = Whitebox.getInternalState(game, "userName");
		assert ("USER".equals(userName));

		Player userPlayer = Whitebox.getInternalState(game, "userPlayer");
		assert (userPlayer instanceof RandomPlayer);

		Player opponent = Whitebox.getInternalState(game, "opponent");
		assert (opponent instanceof RockPlayer);
	}

	@Test
	public void create_rock_game_should() {

		User user = new User();
		user.setName("USER");
		user.setStrategy(Strategy.PLAY_ROCK);

		Game game = GameFactory.createGameForUser(user);

		String userName = Whitebox.getInternalState(game, "userName");
		assert ("USER".equals(userName));

		Player userPlayer = Whitebox.getInternalState(game, "userPlayer");
		assert (userPlayer instanceof RockPlayer);

		Player opponent = Whitebox.getInternalState(game, "opponent");
		assert (opponent instanceof RandomPlayer);
	}
}
