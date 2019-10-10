package com.rps.model.game;

import java.util.HashMap;
import java.util.Map;

import com.rps.model.player.RandomPlayer;
import com.rps.model.player.RockPlayer;
import com.rps.model.player.Strategy;
import com.rps.model.user.User;

public class GameFactory {

	private static Map<Strategy, GameCreator> gameCreators = new HashMap<>();

	interface GameCreator {
		public Game createGameForUser(User user);
	}

	static {
		gameCreators.put(Strategy.PLAY_RANDOM, new GameCreator() {
			public Game createGameForUser(User user) {
				return new Game(user.getName(), new RandomPlayer(), new RockPlayer());
			}
		});

		gameCreators.put(Strategy.PLAY_ROCK, new GameCreator() {
			public Game createGameForUser(User user) {
				return new Game(user.getName(), new RockPlayer(), new RandomPlayer());
			}
		});
	}

	private GameFactory() {
	}

	public static Game createGameForUser(User user) {
		return gameCreators.get(user.getStrategy()).createGameForUser(user);
	}

}
