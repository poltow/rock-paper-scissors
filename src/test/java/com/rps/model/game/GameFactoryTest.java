package com.rps.model.game;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.rps.model.game.Game;
import com.rps.model.game.GameFactory;
import com.rps.model.player.Player;
import com.rps.model.player.RandomPlayer;
import com.rps.model.player.RockPlayer;
import com.rps.model.player.Strategy;
import com.rps.model.user.User;

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
