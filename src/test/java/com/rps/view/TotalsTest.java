package com.rps.view;

import org.junit.Test;

import com.rps.model.game.Result;
import com.rps.view.Totals;

public class TotalsTest {

	@Test
	public void totals_should_add_each_result() {
		Totals totals = new Totals("user");
		assert (totals.getDraws() == 0);
		assert (totals.getLoses() == 0);
		assert (totals.getWins() == 0);
		assert (totals.getTotal() == 0);

		totals.countResult(Result.DRAW);
		totals.countResult(Result.LOSE);
		totals.countResult(Result.WIN);

		assert (totals.getDraws() == 1);
		assert (totals.getLoses() == 1);
		assert (totals.getWins() == 1);
		assert (totals.getTotal() == 3);
	}

}
