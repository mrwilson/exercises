package uk.co.probablyfine.exercises.trivia;

public record Player(String name, int place, int coins) {
    public Player moveForward(int roll) {
        return new Player(name, (this.place + roll) % 12, coins);
    }

    public Player(String name) {
        this(name, 0, 0);
    }
}
