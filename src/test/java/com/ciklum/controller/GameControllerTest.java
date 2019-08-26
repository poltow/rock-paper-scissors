package com.ciklum.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ciklum.model.game.Round;
import com.ciklum.model.player.Strategy;
import com.ciklum.model.user.User;
import com.ciklum.service.GameService;
import com.ciklum.service.UsersHistoryService;
import com.ciklum.view.Totals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UsersHistoryService.class})
public class GameControllerTest {

	@Mock
	private GameService gameService;

	@InjectMocks
	private GameController gameController;

	User user;

	@Before
	public void setUp() {
		user = new User();
		user.setName("USER");
		user.setStrategy(Strategy.PLAY_RANDOM);
		
        mockStatic(UsersHistoryService.class);

	}

	@Test
	public void test_initGame() {
		PowerMockito.doNothing().when(gameService).createGameForUser(any(User.class));
		
		gameController.initGame(user);
		
		verify(gameService).createGameForUser(any(User.class));
	}

	@Test
	public void test_playRound() {
		PowerMockito.doNothing().when(gameService).playRoundForUserName(anyString());
		
		gameController.playRound(user);
		
		verify(gameService).playRoundForUserName(anyString());
	}

	@Test
	public void test_getRoundsForUserName() {
		List<Round> rounds = new ArrayList<>();
		PowerMockito.doReturn(rounds).when(gameService).getRoundsForUserName(anyString());
		
		List<Round> result = gameController.getRounds(user.getName());
		
		verify(gameService).getRoundsForUserName(anyString());
		assert (rounds.equals(result));
	}
	
	@Test
	public void test_restartGame() {
		PowerMockito.doNothing().when(gameService).restartGameForUserName(anyString());
		
		gameController.restartGame(user.getName());
		
		verify(gameService).restartGameForUserName(anyString());
	}
	
	@Test
	public void test_endGame() {
		PowerMockito.doNothing().when(gameService).deleteGameForUserName(anyString());
		
		gameController.endGame(user.getName());
		
		verify(gameService).deleteGameForUserName(anyString());
	}
	
	@Test
	public void test_getHistory() {
		List<Totals> totals = new ArrayList<>();
		when(UsersHistoryService.getTotalsResultsList()).thenReturn(totals);
		
		List<Totals> result = gameController.getHistory();
		
		PowerMockito.verifyStatic(UsersHistoryService.class); // 1
		UsersHistoryService.getTotalsResultsList();
		assert (totals.equals(result));
	}
}
