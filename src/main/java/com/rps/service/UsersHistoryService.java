package com.rps.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rps.model.game.Result;
import com.rps.view.Totals;

@Service
public class UsersHistoryService {

	private static Map<String, Totals> usersTotals = Collections.synchronizedMap(new HashMap<>());

	public Map<String, Totals> getUsersTotals() {
		return usersTotals;
	}

	public Totals getTotalsForUser(String userName) {
		Totals totals = usersTotals.get(userName);
		return totals != null ? totals : new Totals(userName);
	}

	public void addNewUserResult(String userName, Result result) {
		Totals totals = getTotalsForUser(userName);

		totals.countResult(result);
		usersTotals.put(userName, totals);
	}

	public List<Totals> getTotals() {
		return new ArrayList<>(usersTotals.values());
	}
}