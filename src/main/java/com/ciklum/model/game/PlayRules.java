package com.ciklum.model.game;

import java.util.HashMap;
import java.util.Map;

public class PlayRules {

	static private Map<Shape, Map<Shape, Result>> resultsMap = new HashMap<>();

	static {
		Map<Shape, Result> rockMap = new HashMap<>();
		rockMap.put(Shape.ROCK, Result.DRAW);
		rockMap.put(Shape.SCISSORS, Result.WIN);
		rockMap.put(Shape.PAPER, Result.LOSE);
		resultsMap.put(Shape.ROCK, rockMap);

		Map<Shape, Result> paperMap = new HashMap<>();
		paperMap.put(Shape.PAPER, Result.DRAW);
		paperMap.put(Shape.ROCK, Result.WIN);
		paperMap.put(Shape.SCISSORS, Result.LOSE);
		resultsMap.put(Shape.PAPER, paperMap);

		Map<Shape, Result> scissorsMap = new HashMap<>();
		scissorsMap.put(Shape.SCISSORS, Result.DRAW);
		scissorsMap.put(Shape.PAPER, Result.WIN);
		scissorsMap.put(Shape.ROCK, Result.LOSE);
		resultsMap.put(Shape.SCISSORS, scissorsMap);
	}

	private PlayRules() {
	}

	public static Result getResultForRound(Shape play, Shape against) {
		Map<Shape, Result> shapeMap = resultsMap.get(play);
		return shapeMap.get(against);
	}
}
