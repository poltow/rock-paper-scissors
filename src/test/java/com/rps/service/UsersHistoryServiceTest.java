package com.rps.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.rps.model.game.Result;
import com.rps.service.UsersHistoryService;
import com.rps.view.Totals;

public class UsersHistoryServiceTest {

	
	UsersHistoryService usersHistoryService;
	
	private Map<String, Totals> expectedTotals;

	@Before
	public void setup() {

		expectedTotals = new HashMap<>();

		Totals totals = new Totals("USER_A");
		totals.countResult(Result.WIN);
		totals.countResult(Result.LOSE);
		totals.countResult(Result.DRAW);
		expectedTotals.put("USER_A", totals);

		totals = new Totals("USER_B");
		totals.countResult(Result.WIN);
		totals.countResult(Result.LOSE);
		expectedTotals.put("USER_B", totals);

		totals = new Totals("USER_C");
		totals.countResult(Result.WIN);
		expectedTotals.put("USER_C", totals);
		
		usersHistoryService = new UsersHistoryService();
		
		Whitebox.setInternalState(UsersHistoryService.class, "usersTotals", Collections.synchronizedMap(new HashMap<>()));
	}
	
	
	@Test
	public void get_users_rounds_for_empty_history() {
		List<Totals> totals = usersHistoryService.getTotals();
		assert (totals.isEmpty());
	}
	
	@Test
	public void get_totals_for_non_existing_user() {
		usersHistoryService.addNewUserResult("USER_A", Result.WIN);

		assert (usersHistoryService.getTotals().size() == 1);
		assert (usersHistoryService.getTotalsForUser("USER_B").getTotal()==0);
	}
	
	@Test
	public void add_one_round_for_one_user() {
		usersHistoryService.addNewUserResult("USER_A", Result.WIN);

		assert (usersHistoryService.getUsersTotals().keySet().size() == 1);
		assert (usersHistoryService.getUsersTotals().keySet().contains("USER_A"));
		assert (usersHistoryService.getTotalsForUser("USER_A").getWins()==1);
	}

	@Test
	public void add_three_rounds_for_one_user() {

		for (int i = 0; i < 3; i++) {
			usersHistoryService.addNewUserResult("USER_A", Result.WIN);
		}

		assert (usersHistoryService.getUsersTotals().keySet().size() == 1);
		assert (usersHistoryService.getTotalsForUser("USER_A").getWins()== 3);
	}

	@Test
	public void add_five_rounds_for_thow_user() {

		for (int i = 0; i < 3; i++) {
			usersHistoryService.addNewUserResult("USER_A", Result.WIN);
		}

		for (int i = 0; i < 2; i++) {
			usersHistoryService.addNewUserResult("USER_B", Result.LOSE);
		}

		assert (usersHistoryService.getUsersTotals().keySet().size() == 2);
		assert (usersHistoryService.getTotalsForUser("USER_A").getWins()== 3);
		assert (usersHistoryService.getTotalsForUser("USER_B").getLoses()== 2);
	}
	

	@Test
	public void get_rounds_for_non_existing_user() {
		

		
		usersHistoryService.addNewUserResult("USER_A", Result.WIN);
		usersHistoryService.addNewUserResult("USER_A", Result.LOSE);
		usersHistoryService.addNewUserResult("USER_A", Result.DRAW);

		usersHistoryService.addNewUserResult("USER_B", Result.WIN);
		usersHistoryService.addNewUserResult("USER_B", Result.LOSE);

		usersHistoryService.addNewUserResult("USER_C", Result.WIN);

		List<Totals> totalsResultsList = usersHistoryService.getTotals();

		assert (totalsResultsList.size() == 3);

		for (Totals totals : totalsResultsList) {
			Totals userTotals = expectedTotals.get(totals.getUserName());

			assert (totals.getWins() == userTotals.getWins());
			assert (totals.getLoses() == userTotals.getLoses());
			assert (totals.getDraws() == userTotals.getDraws());

		}
	}

}
