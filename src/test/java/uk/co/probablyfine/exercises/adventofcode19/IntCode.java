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

        loop: for (int i = 0; i < program.length; i += 4) {

            switch(program[i]) {

                case Operation.ADD:
                    program[program[i+3]] = program[program[i+1]] + program[program[i+2]]; break;

                case Operation.MULTIPLY:
                    program[program[i+3]] = program[program[i+1]] * program[program[i+2]]; break;

                case Operation.STORE:
                    program[program[i+1]] = input; break;

                case Operation.RETURN:
                    output.accept(program[program[i+1]]); break;

                case Operation.HALT:
                default:
                    break loop;
            }
        }

        return program;
    }



}
