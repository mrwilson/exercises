package uk.co.probablyfine.exercises.trivia;

public record Player(String name, int place, int coins) {
    public Player moveForward(int roll) {
        int newPlace = (this.place + roll) % 12;
        System.out.println(name + "'s new location is " + newPlace);
        return new Player(name, newPlace, coins);
    }

    public Player(String name) {
        this(name, 0, 0);
    }

    public Player addCoin() {
        System.out.println(name + " now has " + (coins + 1) + " Gold Coins.");
        return new Player(name, place, coins + 1);
    }
}
