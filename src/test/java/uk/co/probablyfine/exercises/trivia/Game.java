package uk.co.probablyfine.exercises.trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

    final List<Player> newPlayers;
    final LinkedList<String> popQuestions;
    final LinkedList<String> scienceQuestions;
    final LinkedList<String> sportsQuestions;
    final LinkedList<String> rockQuestions;

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        popQuestions = addQuestions("Pop");
        scienceQuestions = addQuestions("Science");
        sportsQuestions = addQuestions("Sports");
        rockQuestions = addQuestions("Rock");
        newPlayers = new ArrayList<>();
    }

    private LinkedList<String> addQuestions(String category) {
        LinkedList<String> questions = new LinkedList<>();

        for (int questionNumber = 0; questionNumber < 50; questionNumber++) {
            questions.addLast(category + " Question " + questionNumber);
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
                System.out.println(
                        currentPlayer().name() + " is not getting out of the penalty box");
                return;
            }
        }

        updatePlayer(currentPlayer().moveForward(roll));
        askQuestion();
    }

    private void askQuestion() {
        System.out.println("The category is " + currentCategory(currentPlayer()));

        String question =
                switch (currentCategory(currentPlayer())) {
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
        if (currentPlayer().inPenaltyBox() && !isGettingOutOfPenaltyBox) {
            nextPlayer();
            return true;
        }

        System.out.println("Answer was correct!!!!");
        updatePlayer(currentPlayer().addCoin());

        boolean winner = didPlayerWin();
        nextPlayer();

        return winner;
    }

    public boolean wrongAnswer() {
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
