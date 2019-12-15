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

            int operation = program[globalPointer.get()] % 100;

            switch (operation) {
                case Operation.ADD:
                    add();
                    break;

                case Operation.MULTIPLY:
                    mult();
                    break;

                case Operation.STORE:
                    store(input);
                    break;

                case Operation.RETURN:
                    output(output);
                    break;

                case Operation.JMP_IF_TRUE:
                    jumpIfTrue();
                    break;

                case Operation.JMP_IF_FALSE:
                    jumpIfFalse();
                    break;

                case Operation.LESS_THAN:
                    lessThan();
                    break;

                case Operation.EQ:
                    equals();
                    break;

                case Operation.HALT:
                    break loop;

                default:
                    break loop;
            }
        }

        return program;
    }

    private void equals() {
        program[program[globalPointer.get() + 3]] = (firstArg() == secondArg()) ? 1 : 0;
        globalPointer.addAndGet(4);
    }

    private void lessThan() {
        program[program[globalPointer.get() + 3]] = (firstArg() < secondArg()) ? 1 : 0;
        globalPointer.addAndGet(4);
    }

    private void jumpIfTrue() {
        test(firstArg() != 0);
    }

    private void jumpIfFalse() {
        test(firstArg() == 0);
    }

    private void output(Consumer<Integer> output) {
        output.accept(firstArg());
        globalPointer.addAndGet(2);
    }

    private void store(Supplier<Integer> input) {
        program[program[globalPointer.get() + 1]] = input.get();
        globalPointer.addAndGet(2);
    }

    private void mult() {
        program[program[globalPointer.get() + 3]] = firstArg() * secondArg();
        globalPointer.addAndGet(4);
    }

    private void add() {
        program[program[globalPointer.get() + 3]] = firstArg() + secondArg();
        globalPointer.addAndGet(4);
    }

    static int[] runIntcode(int[] program) {
        return new IntCode(program).run(() -> 0, i -> {});
    }

    static int[] runIntcode(int[] program, Supplier<Integer> input, Consumer<Integer> output) {
        return new IntCode(program).run(input, output);
    }

    private int firstArg() {
        boolean mode = (program[globalPointer.get()] / 100) % 10 == 0;
        return arg(globalPointer.get() + 1, mode);
    }

    private int secondArg() {
        boolean mode = (program[globalPointer.get()] / 1000) == 0;
        return arg(globalPointer.get() + 2, mode);
    }

    private int arg(int index, boolean positionMode) {
        return positionMode ? program[program[index]] : program[index];
    }

    private void test(boolean test) {
        if (test) {
            globalPointer.set(secondArg());
        } else {
            globalPointer.addAndGet(3);
        }
    }

    static Supplier<Integer> input(int... inputs) {

        return new Supplier<>() {

            int index = 0;

            @Override
            public Integer get() {
                return inputs[index++];
            }
        };
    }
}
