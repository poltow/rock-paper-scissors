package com.ciklum.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.ciklum.model.game.Round;
import com.ciklum.model.game.Shape;
import com.ciklum.model.user.UsersHistory;
import com.ciklum.view.Totals;

public class UsersHistoryServiceTest {

	private Map<String, Totals> expectedTotals;

	@Before
	public void setup() {

		expectedTotals = new HashMap<>();

		Totals totals = new Totals("USER_A");
		totals.addWin();
		totals.addLose();
		totals.addDraw();
		expectedTotals.put("USER_A", totals);

		totals = new Totals("USER_B");
		totals.addWin();
		totals.addLose();
		expectedTotals.put("USER_B", totals);

		totals = new Totals("USER_C");
		totals.addWin();
		expectedTotals.put("USER_C", totals);
	}

	@Test
	public void get_rounds_for_non_existing_user() {
		Whitebox.setInternalState(UsersHistory.class, "usersRounds", Collections.synchronizedMap(new HashMap<>()));

		Round round_win = new Round(Shape.PAPER, Shape.ROCK);
		round_win.calculateResult();

		Round round_lose = new Round(Shape.PAPER, Shape.SCISSORS);
		round_lose.calculateResult();

		Round round_draw = new Round(Shape.ROCK, Shape.ROCK);
		round_draw.calculateResult();

		UsersHistory.addNewUserRound("USER_A", round_win);
		UsersHistory.addNewUserRound("USER_A", round_lose);
		UsersHistory.addNewUserRound("USER_A", round_draw);

		UsersHistory.addNewUserRound("USER_B", round_win);
		UsersHistory.addNewUserRound("USER_B", round_lose);

		UsersHistory.addNewUserRound("USER_C", round_win);

		List<Totals> totalsResultsList = UsersHistoryService.getTotalsResultsList();

		assert (totalsResultsList.size() == 3);

		for (Totals totals : totalsResultsList) {
			Totals userTotals = expectedTotals.get(totals.getUserName());

			assert (totals.getWins() == userTotals.getWins());
			assert (totals.getLoses() == userTotals.getLoses());
			assert (totals.getDraws() == userTotals.getDraws());

		}
	}

}
