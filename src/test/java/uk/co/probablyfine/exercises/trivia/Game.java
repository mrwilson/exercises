package uk.co.probablyfine.exercises.trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

	List<Player> newPlayers = new ArrayList<>();

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public Game(){
		popQuestions = addQuestions(popQuestions, "Pop");
		scienceQuestions = addQuestions(scienceQuestions, "Science");
		sportsQuestions = addQuestions(sportsQuestions, "Sports");
		rockQuestions = addQuestions(rockQuestions, "Rock");
	}

	private LinkedList<String> addQuestions(LinkedList<String> questions, String s) {
		for (int i = 0; i < 50; i++) {
			questions.addLast(s + " Question " + i);
		}
		return questions;
	}

	public void add(String playerName) {
	    newPlayers.add(new Player(playerName));

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + newPlayers.size());
	}

	public void roll(int roll) {

		System.out.println(currentPlayer().name() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (currentPlayer().inPenaltyBox()) {
			boolean gettingOutOfPenaltyBox = roll % 2 != 0;

			isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;

			if (gettingOutOfPenaltyBox) {
				System.out.println(currentPlayer().name() + " is getting out of the penalty box");
			} else {
				System.out.println(currentPlayer().name() + " is not getting out of the penalty box");
				return;
			}

		}

		updatePlayer(currentPlayer().moveForward(roll));
		askQuestion();
	}

	private void askQuestion() {
		System.out.println("The category is " + currentCategory(currentPlayer()));

		String question = switch(currentCategory(currentPlayer())) {
			case "Pop" -> popQuestions.removeFirst();
			case "Science" -> scienceQuestions.removeFirst();
			case "Sports" -> sportsQuestions.removeFirst();
			default -> rockQuestions.removeFirst();
		};

		System.out.println(question);
	}
	
	public String currentCategory(Player player) {
		return switch (player.place() % 4) {
			case 0 -> "Pop";
			case 1 -> "Science";
			case 2 -> "Sports";
			default -> "Rock";
		};
	}

	public boolean wasCorrectlyAnswered() {
    	if(currentPlayer().inPenaltyBox() && !isGettingOutOfPenaltyBox) {
			nextPlayer();
			return true;
		}

		System.out.println("Answer was correct!!!!");
		updatePlayer(currentPlayer().addCoin());

		boolean winner = didPlayerWin();
		nextPlayer();

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		updatePlayer(currentPlayer().sendToPenaltyBox());

		nextPlayer();
		return true;
	}

	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % newPlayers.size();
	}

	private void updatePlayer(Player player) {
    	newPlayers.set(currentPlayer, player);
	}

	private Player currentPlayer() {
		return newPlayers.get(currentPlayer);
	}

	private boolean didPlayerWin() {
		return !(currentPlayer().coins() == 6);
	}
}
