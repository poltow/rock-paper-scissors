package com.rps.model.game;

import java.util.List;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.rps.model.game.Game;
import com.rps.model.game.Round;
import com.rps.model.player.RandomPlayer;
import com.rps.model.player.RockPlayer;

@SuppressWarnings("unchecked")
public class GameTest {
    
    @Test
    public void play_round_should_add_rounds() {
    	Game game = new Game("USER", new RockPlayer(), new RockPlayer());
    	List<Round> rounds = (List<Round>)Whitebox.getInternalState(game, "rounds");
    	assert(rounds.size()==0);
    	
    	game.playNewRound();
    	rounds = (List<Round>)Whitebox.getInternalState(game, "rounds");
    	assert(rounds.size()==1);
    	assert("USER".equals(game.getUserName()));
    	
    }
    
	@Test
    public void restart_game_should_reinitialize_rounds() {
    	Game game = new Game("USER", new RandomPlayer(), new RandomPlayer());
    	game.playNewRound();
    	game.playNewRound();
    	
    	List<Round> rounds = (List<Round>)Whitebox.getInternalState(game, "rounds");
    	assert(rounds.size()==2);
    	game.restartGame();
    	rounds = (List<Round>)Whitebox.getInternalState(game, "rounds");
    	assert(rounds.size()==0);
    }
}
