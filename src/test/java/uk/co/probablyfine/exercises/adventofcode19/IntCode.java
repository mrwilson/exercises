package uk.co.probablyfine.exercises.adventofcode19;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

class IntCode {

    private final int[] program;
    private final AtomicInteger globalPointer;
    private final AtomicInteger relativeBase;

    public interface Operation {
        int ADD = 1;
        int MULTIPLY = 2;
        int STORE = 3;
        int RETURN = 4;
        int JMP_IF_TRUE = 5;
        int JMP_IF_FALSE = 6;
        int LESS_THAN = 7;
        int EQ = 8;
        int SET_BASE = 9;
        int HALT = 99;
    }

    private IntCode(int[] program) {
        this.program = program;
        this.globalPointer = new AtomicInteger(0);
        this.relativeBase = new AtomicInteger(0);
    }

    private int[] run(Supplier<Integer> input, Consumer<Integer> output) {

        loop:
        while (globalPointer.get() < program.length) {

            int operation = read(globalPointer.get()) % 100;

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

                case Operation.SET_BASE:
                    setBase();
                    break;

                case Operation.HALT:
                    break loop;

                default:
                    break loop;
            }
        }

        return program;
    }

    private int read(int index) {
        return program[index];
    }

    private void write(int index, int value) {
        program[index] = value;
    }

    private void setBase() {
        relativeBase.addAndGet(firstArg());
        globalPointer.addAndGet(2);
    }

    private void equals() {
        write(read(globalPointer.get() + 3), (firstArg() == secondArg()) ? 1 : 0);
        globalPointer.addAndGet(4);
    }

    private void lessThan() {
        write(read(globalPointer.get() + 3), (firstArg() < secondArg()) ? 1 : 0);
        globalPointer.addAndGet(4);
    }

    private void jumpIfTrue() {
        jumpIf(firstArg() != 0);
    }

    private void jumpIfFalse() {
        jumpIf(firstArg() == 0);
    }

    private void output(Consumer<Integer> output) {
        output.accept(firstArg());
        globalPointer.addAndGet(2);
    }

    private void store(Supplier<Integer> input) {
        write(read(globalPointer.get() + 1), input.get());
        globalPointer.addAndGet(2);
    }

    private void mult() {
        write(read(globalPointer.get() + 3), firstArg() * secondArg());
        globalPointer.addAndGet(4);
    }

    private void add() {
        write(read(globalPointer.get() + 3), firstArg() + secondArg());
        globalPointer.addAndGet(4);
    }

    static int[] runIntcode(int[] program) {
        return new IntCode(program).run(() -> 0, i -> {});
    }

    static int[] runIntcode(int[] program, Supplier<Integer> input, Consumer<Integer> output) {
        return new IntCode(program).run(input, output);
    }

    private int firstArg() {
        return arg(globalPointer.get() + 1, (read(globalPointer.get()) / 100) % 10);
    }

    private int secondArg() {
        return arg(globalPointer.get() + 2, read(globalPointer.get()) / 1000);
    }

    private int arg(int index, int positionMode) {
        if (positionMode == 0) {
            return read(program[index]);
        } else if (positionMode == 1) {
            return read(index);
        } else if (positionMode == 2) {
            return read(program[index] + relativeBase.get());
        } else {
            throw new RuntimeException("Unrecognisable mode for position: "+positionMode);
        }
    }

    private void jumpIf(boolean test) {
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

    interface Output {
        void consume(int output);
        int retrieve();
    }

    static Output output() {
        return new Output() {

            private final Deque<Integer> allOutputs = new ArrayDeque<>();

            @Override
            public void consume(int output) {
                allOutputs.push(output);
            }

            @Override
            public int retrieve() {
                return allOutputs.pop();
            }
        };
    }
}
