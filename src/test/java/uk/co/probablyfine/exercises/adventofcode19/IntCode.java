package uk.co.probablyfine.exercises.adventofcode19;

class IntCode {

    public interface Operation {
        int ADD = 1;
        int MULTIPLY = 2;
        int HALT = 99;
    }

    static int[] runIntcode(int[] input) {

        loop: for (int i = 0; i < input.length; i += 4) {

            switch(input[i]) {

                case Operation.ADD:
                    input[input[i+3]] = input[input[i+1]] + input[input[i+2]]; break;

                case Operation.MULTIPLY:
                    input[input[i+3]] = input[input[i+1]] * input[input[i+2]]; break;

                case Operation.HALT:
                default:
                    break loop;
            }
        }

        return input;
    }



}
