package com.rps.service;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.rps.exception.GameRequestException;
import com.rps.exception.GameStateException;
import com.rps.model.game.Game;
import com.rps.model.game.Round;
import com.rps.model.player.Strategy;
import com.rps.model.user.User;

public class GameServiceTest {

	UsersHistoryService usersHistoryService = new UsersHistoryService();

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

		Whitebox.setInternalState(UsersHistoryService.class, "usersTotals", Collections.synchronizedMap(new HashMap<>()));
		Whitebox.setInternalState(gameService, "usersHistoryService", usersHistoryService);

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
		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 1);
		assert (usersHistoryService.getTotals().get(0).getTotal() == 1);
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
		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 3);
	}

	@Test
	public void delete_should_keep_round_history() {

		gameService.createGameForUser(userA);
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());
		gameService.playRoundForUserName(userA.getName());

		assert (gameService.getRoundsForUserName(userA.getName()).size() == 3);

		gameService.deleteGameForUserName(userA.getName());

		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 3);
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
		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 2);

		List<Round> roundsForUserB = gameService.getRoundsForUserName(userB.getName());
		assert (roundsForUserB.size() == 3);
		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 2);
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
		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 3);

		assert (gameService.getRoundsForUserName(userB.getName()).size() == 3);
		assert (usersHistoryService.getTotalsForUser(userB.getName()).getTotal() == 3);
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

		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 3);

		assert (gameService.getRoundsForUserName(userB.getName()).size() == 3);
		assert (usersHistoryService.getTotalsForUser(userA.getName()).getTotal() == 3);
	}
}
