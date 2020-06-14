package uk.co.probablyfine.exercises.trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

    ArrayList players = new ArrayList();
	List<Player> newPlayers = new ArrayList<>();

    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    newPlayers.add(new Player(playerName, 0));
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {

		System.out.println(currentPlayer().name() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(currentPlayer().name() + " is getting out of the penalty box");
				newPlayers.set(currentPlayer, currentPlayer().moveForward(roll));

				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
				
				System.out.println(currentPlayer().name()
						+ "'s new location is "
						+ currentPlayer().place());
				System.out.println("The category is " + currentCategory(currentPlayer()));
				askQuestion();
			} else {
				System.out.println(currentPlayer().name() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {
			newPlayers.set(currentPlayer, currentPlayer().moveForward(roll));
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			System.out.println(currentPlayer().name()
					+ "'s new location is "
					+ currentPlayer().place());
			System.out.println("The category is " + currentCategory(currentPlayer()));
			askQuestion();
		}
		
	}

	private void askQuestion() {
		String category = currentCategory(currentPlayer());
		if (category == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (category == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (category == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (category == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	public String currentCategory(Player player) {
		int currentPlace = player.place();

		if (currentPlace == 0) return "Pop";
		if (currentPlace == 4) return "Pop";
		if (currentPlace == 8) return "Pop";
		if (currentPlace == 1) return "Science";
		if (currentPlace == 5) return "Science";
		if (currentPlace == 9) return "Science";
		if (currentPlace == 2) return "Sports";
		if (currentPlace == 6) return "Sports";
		if (currentPlace == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		Player player = currentPlayer();
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(player.name()
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(player.name()
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

	private Player currentPlayer() {
		return newPlayers.get(currentPlayer);
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
