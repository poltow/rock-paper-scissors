package com.ciklum.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ciklum.exception.GameRequestException;
import com.ciklum.exception.GameStateException;
import com.ciklum.model.game.Game;
import com.ciklum.model.game.GameFactory;
import com.ciklum.model.game.Round;
import com.ciklum.model.user.User;
import com.ciklum.model.user.UsersHistory;

@Service
public class GameService {

	Map<String, Game> userGames = Collections.synchronizedMap(new HashMap<>());

	public void createGameForUser(User user) {
		validateUser(user);
		if (userGames.get(user.getName()) != null)
			return;
		Game game = GameFactory.createGameForUser(user);
		userGames.put(user.getName(), game);
	}

	public void restartGameForUserName(String userName) {
		validateUserName(userName);
		Game userGame = userGames.get(userName);
		validateGame(userName, userGame);
		userGame.restartGame();
	}

	public void deleteGameForUserName(String userName) {
		validateUserName(userName);
		validateGame(userName, userGames.get(userName));
		userGames.remove(userName);
	}

	public void playRoundForUserName(String userName) {
		validateUserName(userName);
		Game userGame = userGames.get(userName);
		validateGame(userName, userGame);
		Round newRound = userGame.playNewRound();
		UsersHistory.addNewUserRound(userName, newRound);
	}

	public List<Round> getRoundsForUserName(String userName) {
		validateUserName(userName);
		Game userGame = userGames.get(userName);
		validateGame(userName, userGame);
		return userGame.getRounds();
	}

	private void validateGame(String userName, Game userGame) {
		if (userGame == null)
			throw new GameStateException("Game not found for " + userName //
					+ ". Create a new game and try again");
	}

	private void validateUser(User user) {
		validateUserName(user.getName());
		if (user.getStrategy() == null)
			throw new GameRequestException("Strategy cannot be null");
	}

	private void validateUserName(String userName) {
		if (userName == null || userName.isEmpty())
			throw new GameRequestException("User Name cannot be null/empty");
	}

}
