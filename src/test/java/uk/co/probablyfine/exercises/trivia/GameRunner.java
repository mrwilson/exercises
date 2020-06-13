
package uk.co.probablyfine.exercises.trivia;

import java.util.Random;

public class GameRunner {

	private static boolean notAWinner;

	public interface BoundedRandomness {
		int randomWithMax(int maximum);
	}

	public static void main(String[] args) {
		runGame(new Random()::nextInt);
	}

	public static void runGame(BoundedRandomness provider) {
		Game aGame = new Game();

		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		do {
			aGame.roll(provider.randomWithMax(5) + 1);

			if (provider.randomWithMax(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}

		} while (notAWinner);
	}
}
