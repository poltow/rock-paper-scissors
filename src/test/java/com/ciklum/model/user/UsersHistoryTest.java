package com.ciklum.model.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.ciklum.model.game.Round;
import com.ciklum.model.game.Shape;

public class UsersHistoryTest {

	@Test
	public void get_users_rounds_for_empty_history() {
		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));
		Map<String, List<Round>> usersRounds = UsersHistory.getUsersRounds();
		assert (usersRounds.keySet().isEmpty());
	}

	@Test
	public void get_rounds_for_non_existing_user() {
		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));
		Round round = new Round(Shape.PAPER, Shape.ROCK);
		round.calculateResult();
		UsersHistory.addNewUserRound("USER_A", round);

		assert (UsersHistory.getUsersRounds().keySet().size() == 1);
		assert (UsersHistory.getRoundsForUser("USER_B").isEmpty());
	}
	
	@Test
	public void add_one_round_for_one_user() {
		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));
		Round round = new Round(Shape.PAPER, Shape.ROCK);
		round.calculateResult();
		UsersHistory.addNewUserRound("USER_A", round);

		assert (UsersHistory.getUsersRounds().keySet().size() == 1);
		assert (UsersHistory.getUsersRounds().keySet().contains("USER_A"));
		assert (UsersHistory.getRoundsForUser("USER_A").contains(round));
	}

	@Test
	public void add_three_rounds_for_one_user() {
		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));

		for (int i = 0; i < 3; i++) {
			Round round = new Round(Shape.PAPER, Shape.ROCK);
			round.calculateResult();
			UsersHistory.addNewUserRound("USER_A", round);
		}

		assert (UsersHistory.getUsersRounds().keySet().size() == 1);
		assert (UsersHistory.getRoundsForUser("USER_A").size() == 3);
	}

	@Test
	public void add_five_rounds_for_thow_user() {
		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));

		for (int i = 0; i < 3; i++) {
			Round round = new Round(Shape.PAPER, Shape.ROCK);
			round.calculateResult();
			UsersHistory.addNewUserRound("USER_A", round);
		}

		for (int i = 0; i < 2; i++) {
			Round round = new Round(Shape.ROCK, Shape.PAPER);
			round.calculateResult();
			UsersHistory.addNewUserRound("USER_B", round);
		}

		assert (UsersHistory.getUsersRounds().keySet().size() == 2);
		assert (UsersHistory.getRoundsForUser("USER_A").size() == 3);
		assert (UsersHistory.getRoundsForUser("USER_B").size() == 2);
	}
}
