package com.ciklum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ciklum.model.game.Round;
import com.ciklum.model.user.User;
import com.ciklum.service.GameService;
import com.ciklum.service.UsersHistoryService;
import com.ciklum.view.Totals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1")
public class GameController {

	Logger logger = LoggerFactory.getLogger(GameController.class);

	@Autowired
	GameService gameService;

	@RequestMapping(method = RequestMethod.POST, value = "/game")
	public void initGame(@RequestBody User user) {
		gameService.createGameForUser(user);
		logger.debug("Game created for: " + user.getName() + " - " + user.getStrategy());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/round")
	public void playRound(@RequestBody User user) {
		String userName = user.getName();
		gameService.playRoundForUserName(userName);
		logger.debug("Round played for user: " + userName);
		logger.debug(userName + " played " + gameService.getRoundsForUserName(userName).size() + " rounds");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/rounds/{userName}")
	public List<Round> getRounds(@PathVariable String userName) {
		return gameService.getRoundsForUserName(userName);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/rounds/{userName}")
	public void restartGame(@PathVariable String userName) {
		gameService.restartGameForUserName(userName);
		logger.debug("Game restarted for user: " + userName);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/game/{userName}")
	public void endGame(@PathVariable String userName) {
		gameService.deleteGameForUserName(userName);
		logger.debug("Game deleted for user: " + userName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/history")
	public List<Totals> getHistory() {
		return UsersHistoryService.getTotalsResultsList();
	}
}
