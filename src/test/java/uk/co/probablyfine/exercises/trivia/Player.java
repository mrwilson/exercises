package uk.co.probablyfine.exercises.trivia;

public record Player(String name, int place, int coins, boolean inPenaltyBox) {
    public Player moveForward(int roll) {
        int newPlace = (this.place + roll) % 12;
        System.out.println(name + "'s new location is " + newPlace);
        return new Player(name, newPlace, coins, inPenaltyBox);
    }

    public Player(String name) {
        this(name, 0, 0, false);
    }

    public Player addCoin() {
        System.out.println(name + " now has " + (coins + 1) + " Gold Coins.");
        return new Player(name, place, coins + 1, inPenaltyBox);
    }

    public Player sendToPenaltyBox() {
        System.out.println(name + " was sent to the penalty box");
        return new Player(name, place, coins, true);
    }
}
