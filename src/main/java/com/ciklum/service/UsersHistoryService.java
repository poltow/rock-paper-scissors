package com.ciklum.service;

import java.util.ArrayList;
import java.util.List;

import com.ciklum.model.game.Round;
import com.ciklum.model.user.UsersHistory;
import com.ciklum.view.Totals;

public class UsersHistoryService {

	private UsersHistoryService() {
	}

	static public List<Totals> getTotalsResultsList() {

		List<Totals> resultList = new ArrayList<Totals>();

		for (String name : UsersHistory.getUsersRounds().keySet()) {
			Totals userTotals = new Totals(name);

			for (Round round : UsersHistory.getRoundsForUser(name)) {
				switch (round.getResult()) {
				case WIN:
					userTotals.addWin();
					break;
				case DRAW:
					userTotals.addDraw();
					break;
				case LOSE:
					userTotals.addLose();
					break;
				default:
					throw new IllegalStateException();
				}
			}
			resultList.add(userTotals);
		}
		return resultList;
	}
}