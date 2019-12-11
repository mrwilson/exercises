package uk.co.probablyfine.exercises.adventofcode19;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

class IntCode {

    private final int[] program;
    private final AtomicInteger globalPointer;

    public interface Operation {
        int ADD = 1;
        int MULTIPLY = 2;
        int STORE = 3;
        int RETURN = 4;
        int JMP_IF_TRUE = 5;
        int JMP_IF_FALSE = 6;
        int LESS_THAN = 7;
        int EQ = 8;
        int HALT = 99;
    }

    private IntCode(int[] program) {
        this.program = program;
        this.globalPointer = new AtomicInteger(0);
    }

    private int[] run(Supplier<Integer> input, Consumer<Integer> output) {

        loop:
        while (globalPointer.get() < program.length) {

            int pointer = globalPointer.get();

            boolean firstArgMode = ((program[pointer] / 100) % 10) == 0;
            boolean secondArgMode = (program[pointer] / 1000) == 0;

            int operation = program[pointer] % 100;

            switch (operation) {
                case Operation.ADD:
                    add();
                    break;

                case Operation.MULTIPLY:
                    mult(pointer, firstArgMode, secondArgMode);
                    break;

                case Operation.STORE:
                    program[program[pointer + 1]] = input.get();
                    globalPointer.addAndGet(2);
                    break;

                case Operation.RETURN:
                    output.accept(arg(pointer + 1, firstArgMode));
                    globalPointer.addAndGet(2);
                    break;

                case Operation.JMP_IF_TRUE:
                    if(arg(pointer + 1, firstArgMode) == 0) {
                        globalPointer.set(arg(pointer + 2, firstArgMode));
                    } else {
                        globalPointer.addAndGet(2);
                    }
                    break;

                case Operation.JMP_IF_FALSE:
                    if(arg(pointer + 1, firstArgMode) != 0) {
                        globalPointer.set(arg(pointer + 2, firstArgMode));
                    } else {
                        globalPointer.addAndGet(2);
                    }
                    break;

                case Operation.LESS_THAN:
                    if (arg(pointer + 1, firstArgMode) < arg(pointer + 2, secondArgMode)) {
                        program[program[pointer+3]] = 1;
                    } else {
                        program[program[pointer+3]] = 0;
                    }
                    globalPointer.addAndGet(4);
                    break;

                case Operation.EQ:
                    if (arg(pointer + 1, firstArgMode) == arg(pointer + 2, secondArgMode)) {
                        program[program[pointer+3]] = 1;
                    } else {
                        program[program[pointer+3]] = 0;
                    }
                    globalPointer.addAndGet(4);
                    break;

                case Operation.HALT:
                default:
                    break loop;
            }
        }

        return program;

    }

    private void mult(int pointer, boolean firstArgMode, boolean secondArgMode) {
        program[program[pointer + 3]] =
                arg(pointer + 1, firstArgMode)
                        * arg(pointer + 2, secondArgMode);

        globalPointer.addAndGet(4);
    }

    private void add() {
        int pointer = globalPointer.get();

        boolean firstArgMode = (program[pointer] / 100) % 10 == 0;
        boolean secondArgMode = (program[pointer] / 1000) == 0;

        program[program[pointer + 3]] =
                arg(pointer + 1, firstArgMode)
                        + arg(pointer + 2, secondArgMode);

        globalPointer.addAndGet(4);
    }

    static int[] runIntcode(int[] program) {
        return new IntCode(program).run(() -> 0, i -> {});
    }

    static int[] runIntcode(int[] program, Supplier<Integer> input, Consumer<Integer> output) {
        return new IntCode(program).run(input, output);

    }

    private int arg(int index, boolean positionMode) {
        return positionMode ? program[program[index]] : program[index];
    }
}
