package uk.co.probablyfine.exercises.adventofcode19;

import java.util.OptionalInt;
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

            switch(program[i] % 100) {

                case Operation.ADD:
                    program[program[i+3]] = program[program[i+1]] + program[program[i+2]];
                    i += 4;
                    break;

                case Operation.MULTIPLY:

                    boolean firstArgPositionMode = ((program[i] / 100) % 10) == 0;
                    boolean secondArgPositionMode = (program[i] / 1000) == 0;

                    int firstArg = firstArgPositionMode ? program[program[i+1]] : program[i+1];
                    int secondArg = secondArgPositionMode ? program[program[i+2]] : program[i+2];

                    program[program[i+3]] = firstArg * secondArg;

                    i += 4;
                    break;

                case Operation.STORE:
                    program[program[i+1]] = input;
                    i += 2;
                    break;

                case Operation.RETURN:
                    output.accept(program[program[i+1]]);
                    i += 2;
                    break;

                case Operation.HALT:
                default:
                    break loop;
            }
        }

        return program;
    }



}
