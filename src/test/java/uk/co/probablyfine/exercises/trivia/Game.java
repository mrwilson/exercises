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
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast("Rock Question " + i);
    	}
    }

	public boolean add(String playerName) {
	    newPlayers.add(new Player(playerName));

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + newPlayers.size());
		return true;
	}

	public void roll(int roll) {

		System.out.println(currentPlayer().name() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (currentPlayer().inPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(currentPlayer().name() + " is getting out of the penalty box");
				updatePlayer(currentPlayer().moveForward(roll));

				askQuestion();
			} else {
				System.out.println(currentPlayer().name() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {
			updatePlayer(currentPlayer().moveForward(roll));
			askQuestion();
		}
		
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
		if (currentPlayer().inPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");

				updatePlayer(currentPlayer().addCoin());

				boolean winner = didPlayerWin();
				nextPlayer();

				return winner;
			} else {
				nextPlayer();
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			updatePlayer(currentPlayer().addCoin());

			boolean winner = didPlayerWin();
			nextPlayer();

			return winner;
		}
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer().name() + " was sent to the penalty box");
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
