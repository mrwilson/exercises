package uk.co.probablyfine.exercises.trivia;

public record Player(String name, int place) {
    public Player moveForward(int roll) {
        return new Player(name, (this.place + roll) % 12);
    }
}