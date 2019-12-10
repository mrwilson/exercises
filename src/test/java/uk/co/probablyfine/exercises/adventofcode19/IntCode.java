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

        int pointer = 0;

        loop:
        while (pointer < program.length) {

            boolean firstArgMode = ((program[pointer] / 100) % 10) == 0;
            boolean secondArgMode = (program[pointer] / 1000) == 0;

            int operation = program[pointer] % 100;

            switch (operation) {
                case Operation.ADD:
                    program[program[pointer + 3]] =
                            arg(program, pointer + 1, firstArgMode) + arg(program, pointer + 2, secondArgMode);

                    pointer += 4;
                    break;

                case Operation.MULTIPLY:
                    program[program[pointer + 3]] =
                            arg(program, pointer + 1, firstArgMode) * arg(program, pointer + 2, secondArgMode);

                    pointer += 4;
                    break;

                case Operation.STORE:
                    program[program[pointer + 1]] = input;
                    pointer += 2;
                    break;

                case Operation.RETURN:
                    output.accept(arg(program, pointer + 1, firstArgMode));
                    pointer += 2;
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
