package com.ciklum.service;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.ciklum.exception.GameRequestException;
import com.ciklum.exception.GameStateException;
import com.ciklum.model.game.Game;
import com.ciklum.model.game.Round;
import com.ciklum.model.player.Strategy;
import com.ciklum.model.user.User;
import com.ciklum.model.user.UsersHistory;

public class GameServiceTest {

	GameService gameService;
	User userA;
	User userB;

	@Before
	public void setUp() {
		gameService = new GameService();

		userA = new User();
		userA.setName("USER_A");
		userA.setStrategy(Strategy.PLAY_RANDOM);

		userB = new User();
		userB.setName("USER_B");
		userB.setStrategy(Strategy.PLAY_ROCK);

		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));
	}

	@Test(expected = GameRequestException.class)
	public void create_game_for_null_user_name() {
		User user = new User();
		user.setStrategy(Strategy.PLAY_RANDOM);
		gameService.createGameForUser(user);
	}

	@Test(expected = GameRequestException.class)
	public void create_game_for_empty_user_name() {
		User user = new User();
		user.setName("");
		user.setStrategy(Strategy.PLAY_RANDOM);
		gameService.createGameForUser(user);
	}

	@Test(expected = GameRequestException.class)
	public void create_game_for_null_strategy() {
		User user = new User();
		user.setName("USER");
		gameService.createGameForUser(user);
	}

	@Test(expected = GameRequestException.class)
	public void play_round_for_null_user_name() {
		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(null);
	}

	@Test(expected = GameRequestException.class)
	public void play_round_for_empty_user_name() {
		gameService.createGameForUser(userA);
		gameService.playRoundForUserName("");
	}

	@Test(expected = GameStateException.class)
	public void play_round_for_not_initialized_game() {
		gameService.playRoundForUserName(userA.getName());
	}

	@Test(expected = GameRequestException.class)
	public void restart_game_for_null_user_name() {
		gameService.createGameForUser(userA);
		gameService.restartGameForUserName(null);
	}

	@Test(expected = GameRequestException.class)
	public void restart_game_for_empty_user_name() {
		gameService.createGameForUser(userA);
		gameService.restartGameForUserName("");
	}

	@Test(expected = GameStateException.class)
	public void restart_game_for_not_initialized_game() {
		gameService.restartGameForUserName(userA.getName());
	}

	@Test(expected = GameRequestException.class)
	public void delete_for_null_user_name() {
		gameService.createGameForUser(userA);
		gameService.deleteGameForUserName(null);
	}

	@Test(expected = GameRequestException.class)
	public void delete_for_empty_user_name() {
		gameService.createGameForUser(userA);
		gameService.deleteGameForUserName("");
	}

	@Test(expected = GameStateException.class)
	public void delete_for_not_initialized_game() {
		gameService.deleteGameForUserName(userA.getName());
	}

	@Test
	public void create_game_for_one_user() {

		gameService.createGameForUser(userA);

		assertNotNull(gameService.getRoundsForUserName(userA.getName()));
		assert (gameService.getRoundsForUserName(userA.getName()).size() == 0);
	}

	@Test
	public void create_game_dont_override_already_created_game() {

		gameService.createGameForUser(userA);

		Map<String, Game> userGames = Whitebox.getInternalState(gameService, "userGames");

		Game gameA = userGames.get(userA.getName());

		gameService.createGameForUser(userA);

		Game gameB = userGames.get(userA.getName());

		assert (gameA.equals(gameB));
	}

	@Test
	public void play_round_for_one_user() {

		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(userA.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 1);
		assert (UsersHistory.getRoundsForUser(userA.getName()).size() == 1);
		assert (UsersHistory.getRoundsForUser(userA.getName())
				.contains(gameService.getRoundsForUserName(userA.getName()).get(0)));
	}

	@Test(expected = GameStateException.class)
	public void play_round_for_deleted_game() {

		gameService.createGameForUser(userA);
		gameService.deleteGameForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());
	}

	@Test
	public void restart_should_keep_round_history() {

		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 3);

		gameService.restartGameForUserName(userA.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 0);
		assert (UsersHistory.getRoundsForUser(userA.getName()).size() == 3);
	}

	@Test
	public void delete_should_keep_round_history() {

		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 3);

		gameService.deleteGameForUserName(userA.getName());

		assert (UsersHistory.getRoundsForUser(userA.getName()).size() == 3);
	}

	@Test
	public void create_game_for_two_users() {

		gameService.createGameForUser(userA);
		gameService.createGameForUser(userB);

		assertNotNull(gameService.getRoundsForUserName(userA.getName()));
		assertNotNull(gameService.getRoundsForUserName(userB.getName()));
		assert (gameService.getRoundsForUserName(userA.getName()).size() == 0);
		assert (gameService.getRoundsForUserName(userB.getName()).size() == 0);
	}

	@Test
	public void play_rounds_for_two_user() {

		gameService.createGameForUser(userA);
		gameService.createGameForUser(userB);
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userB.getName());
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userB.getName());
		gameService.playRoundForUserName(userB.getName());

		List<Round> roundsForUserA = gameService.getRoundsForUserName(userA.getName());
		assert (roundsForUserA.size() == 2);
		for (Round round : roundsForUserA) {
			assert (UsersHistory.getRoundsForUser(userA.getName()).contains(round));
		}

		List<Round> roundsForUserB = gameService.getRoundsForUserName(userB.getName());
		assert (roundsForUserB.size() == 3);
		for (Round round : roundsForUserB) {
			assert (UsersHistory.getRoundsForUser(userB.getName()).contains(round));
		}
	}

	@Test
	public void restart_rounds_separately() {

		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());

		gameService.createGameForUser(userB);
		gameService.playRoundForUserName(userB.getName());
		gameService.playRoundForUserName(userB.getName());
		gameService.playRoundForUserName(userB.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 3);

		gameService.restartGameForUserName(userA.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 0);
		assert (UsersHistory.getRoundsForUser(userA.getName()).size() == 3);

		assert (gameService.getRoundsForUserName(userB.getName()).size() == 3);
		assert (UsersHistory.getRoundsForUser(userB.getName()).size() == 3);
	}

	@Test
	public void delete_games_separately() {

		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());

		gameService.createGameForUser(userB);
		gameService.playRoundForUserName(userB.getName());
		gameService.playRoundForUserName(userB.getName());
		gameService.playRoundForUserName(userB.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 3);
		assert (gameService.getRoundsForUserName(userA.getName()).size() == 3);

		gameService.deleteGameForUserName(userA.getName());

		assert (UsersHistory.getRoundsForUser(userA.getName()).size() == 3);

		assert (gameService.getRoundsForUserName(userB.getName()).size() == 3);
		assert (UsersHistory.getRoundsForUser(userB.getName()).size() == 3);
	}
}
