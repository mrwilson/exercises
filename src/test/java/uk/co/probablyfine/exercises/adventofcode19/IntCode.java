package uk.co.probablyfine.exercises.adventofcode19;

import java.util.concurrent.atomic.AtomicInteger;
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

        AtomicInteger globalPointer = new AtomicInteger(0);

        loop:
        while (globalPointer.get() < program.length) {

            int pointer = globalPointer.get();

            boolean firstArgMode = ((program[pointer] / 100) % 10) == 0;
            boolean secondArgMode = (program[pointer] / 1000) == 0;

            int operation = program[pointer] % 100;

            switch (operation) {
                case Operation.ADD:
                    program[program[pointer + 3]] =
                            arg(program, pointer + 1, firstArgMode) + arg(program, pointer + 2, secondArgMode);

                    globalPointer.addAndGet(4);
                    break;

                case Operation.MULTIPLY:
                    program[program[pointer + 3]] =
                            arg(program, pointer + 1, firstArgMode) * arg(program, pointer + 2, secondArgMode);

                    globalPointer.addAndGet(4);
                    break;

                case Operation.STORE:
                    program[program[pointer + 1]] = input;
                    globalPointer.addAndGet(2);
                    break;

                case Operation.RETURN:
                    output.accept(arg(program, pointer + 1, firstArgMode));
                    globalPointer.addAndGet(2);
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
