package uk.co.probablyfine.exercises.adventofcode24;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.probablyfine.exercises.adventofcode24.Day17Test.Computer.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day17Test {

    public static String sampleInput() {
        return """
                Register A: 729
                Register B: 0
                Register C: 0

                Program: 0,1,5,4,3,0
                """;
    }

    public record Computer(
            int pointer, List<Integer> output, List<Integer> program, int A, int B, int C) {

        public static Computer setup(List<Integer> program, int A, int B, int C) {
            return new Computer(0, new ArrayList<>(), program, A, B, C);
        }

        public Computer tick() {
            var insn = program.get(pointer);

            if (insn == 0) {
                var denominator =
                        switch (program.get(pointer + 1)) {
                            case 4 -> A;
                            case 5 -> B;
                            case 6 -> C;
                            default -> program.get(pointer + 1);
                        };

                var newA = A / Math.pow(2, denominator);
                return new Computer(pointer + 2, output, program, (int) newA, B, C);
            }
            return this;
        }
    }

    public static Computer parse(String input) {

        var values = Arrays.stream(input.split("\\D+")).skip(1).map(Integer::parseInt).toList();

        return setup(
                values.subList(3, values.size() - 1), values.get(0), values.get(1), values.get(2));
    }

    @Test
    void shouldParse() {
        var computer = parse(sampleInput());

        assertThat(computer.pointer(), is(0));
        assertThat(computer.program(), is(Arrays.asList(0, 1, 5, 4, 3)));
        assertThat(computer.A(), is(729));
        assertThat(computer.B(), is(0));
        assertThat(computer.C(), is(0));
        assertThat(computer.output(), is(emptyList()));
    }

    @Test
    void supportDivision() {
        var computer = Computer.setup(Arrays.asList(0, 1), 16, 0, 0).tick();

        assertThat(computer.A(), is(16 / 2));
    }

    @Test
    void supportDivisionWithComboOperator() {
        var computer = Computer.setup(Arrays.asList(0, 5), 16, 3, 0).tick();

        assertThat(computer.A(), is(16 / 8));
    }
}
