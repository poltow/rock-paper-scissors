package com.ciklum.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ciklum.model.game.Round;

public class UsersHistory {

	private static Map<String, List<Round>> usersRounds = Collections.synchronizedMap(new HashMap<>());

	private UsersHistory() {
	}

	public static Map<String, List<Round>> getUsersRounds() {
		return usersRounds;
	}

	public static List<Round> getRoundsForUser(String userName) {
		List<Round> roundList = usersRounds.get(userName);
		return roundList != null ? roundList : new ArrayList<>();
	}

	public static void addNewUserRound(String userName, Round round) {
		List<Round> rounds = getRoundsForUser(userName);
		rounds.add(round);
		usersRounds.put(userName, rounds);
	}

}
