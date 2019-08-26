package com.ciklum.model.game;

import java.util.ArrayList;
import java.util.List;

import com.ciklum.model.player.Player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Game {

	@Getter
	@NonNull
	private String userName;

	@NonNull
	private Player userPlayer;

	@NonNull
	private Player opponent;

	@Getter
	private List<Round> rounds = new ArrayList<>();

	public Round playNewRound() {
		Round round = new Round(userPlayer.play(), opponent.play());
		round.calculateResult();
		rounds.add(round);
		return round;
	}
	
	public void restartGame() {
		rounds = new ArrayList<>();
	}
}
