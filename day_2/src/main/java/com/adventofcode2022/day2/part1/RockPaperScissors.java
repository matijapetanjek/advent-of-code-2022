package com.adventofcode2022.day2.part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.adventofcode2022.day2.part1.Move.PAPER;
import static com.adventofcode2022.day2.part1.Move.ROCK;
import static com.adventofcode2022.day2.part1.Move.SCISSORS;
import static com.adventofcode2022.day2.part1.RoundOutcome.DRAW;
import static com.adventofcode2022.day2.part1.RoundOutcome.LOSS;
import static com.adventofcode2022.day2.part1.RoundOutcome.WIN;

/**
 * @author Matija Petanjek
 */
public class RockPaperScissors {

	public static void main (String[] args) throws IOException {

		RockPaperScissorsEngine rockPaperScissorsEngine = new RockPaperScissorsEngine();
		int totalScore = 0;

		try (BufferedReader br
				 = new BufferedReader(
			new InputStreamReader(
				RockPaperScissors.class.getClassLoader().getResourceAsStream("strategyGuide.txt")))) {

			String line;

			while ((line = br.readLine()) != null) {
				String[] characters = line.split(" ");

				totalScore += rockPaperScissorsEngine.execute(
					Move.valueOf(characters[0].charAt(0)),
					Move.valueOf(characters[1].charAt(0))
				);
			}

		}

		System.out.println(totalScore);
	}
}

enum RoundOutcome {
	WIN(6), LOSS(0), DRAW(3);

	public int getScore() {
		return score;
	}

	private RoundOutcome(int score) {
		this.score = score;
	}

	private int score;
}

enum Move {
	ROCK('A', 'X', 1), PAPER('B', 'Y', 2), SCISSORS('C', 'Z', 3);

	public static Move valueOf(char code) {
		for (Move move: values()) {
			if (move.myMoveCode == code || move.opponentsMoveCode == code) {
				return move;
			}
		}

		throw new IllegalArgumentException("Unknown move code");
	}

	public int getScore() {
		return score;
	}

	private Move(char opponentsMoveCode, char myMoveCode, int score) {
		this.opponentsMoveCode = opponentsMoveCode;
		this.myMoveCode = myMoveCode;
		this.score = score;
	}

	private char opponentsMoveCode;
	private char myMoveCode;
	private int score;

}

class RockPaperScissorsEngine {

	private Map<Move,Map<Move, RoundOutcome>> rules = new HashMap();

	{
		rules.put(ROCK, Map.of(ROCK, DRAW, PAPER, LOSS, SCISSORS, WIN));
		rules.put(PAPER, Map.of(PAPER, DRAW, SCISSORS, LOSS, ROCK, WIN));
		rules.put(SCISSORS, Map.of(SCISSORS, DRAW, ROCK, LOSS, PAPER, WIN));
	}
	public int execute(Move opponentsMove, Move myMove) {
		RoundOutcome roundOutcome =  rules.get(myMove).get(opponentsMove);

		return roundOutcome.getScore() + myMove.getScore();
	}
}