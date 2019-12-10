package uk.co.probablyfine.exercises.adventofcode19;

import java.util.function.Consumer;

class IntCode {

    public interface Operation {
        int ADD = 1;
        int MULTIPLY = 2;
        int STORE = 3;
        int RETURN = 4;
        int HALT = 99;
    }

    static int[] runIntcode(int[] program) {
        return runIntcode(program, 0, i -> {});
    }

    static int[] runIntcode(int[] program, int input, Consumer<Integer> output) {

        int i = 0;

        loop: while (i < program.length) {

            boolean firstArgPositionMode = ((program[i] / 100) % 10) == 0;
            boolean secondArgPositionMode = (program[i] / 1000) == 0;

            int operation = program[i] % 100;

            switch(operation) {

                case Operation.ADD:
                    program[program[i+3]] = arg(program, i+1, firstArgPositionMode)
                            + arg(program, i+2, secondArgPositionMode);

                    i += 4;
                    break;

                case Operation.MULTIPLY:

                    program[program[i+3]] = arg(program, i+1, firstArgPositionMode)
                            * arg(program, i+2, secondArgPositionMode);

                    i += 4;
                    break;

                case Operation.STORE:
                    program[program[i+1]] = input;
                    i += 2;
                    break;

                case Operation.RETURN:
                    output.accept(arg(program, i+1, firstArgPositionMode));
                    i += 2;
                    break;

                case Operation.HALT:
                default:
                    break loop;
            }
        }

        return program;
    }

    private static int arg(int[] program, int index, boolean positionMode) {
        return positionMode ? program[program[index]] : program[index];
    }


}
