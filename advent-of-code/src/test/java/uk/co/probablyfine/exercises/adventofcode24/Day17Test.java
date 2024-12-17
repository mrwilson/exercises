package uk.co.probablyfine.exercises.adventofcode24;

import static java.lang.Math.pow;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
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

        public boolean halted() {
            return pointer >= program.size();
        }

        public Computer update(int pointer, int A, int B, int C) {
            return new Computer(pointer, output, program, A, B, C);
        }

        public Computer tick() {

            return switch (program.get(pointer)) {
                case 0 -> update(pointer + 2, (int) (A / pow(2, combo())), B, C);
                case 1 -> update(pointer + 2, A, B ^ program.get(pointer + 1), C);
                case 2 -> update(pointer + 2, A, combo() % 8, C);
                case 3 -> update(A == 0 ? pointer + 2 : program.get(pointer + 1), A, B, C);
                case 4 -> update(pointer + 2, A, B ^ C, C);
                case 5 -> {
                    output.add(combo() % 8);
                    yield update(pointer + 2, A, B, C);
                }
                case 6 -> update(pointer + 2, A, (int) (A / pow(2, combo())), C);
                case 7 -> update(pointer + 2, A, B, (int) (A / pow(2, combo())));
                default -> this;
            };
        }

        private int combo() {
            return switch (program.get(pointer + 1)) {
                case 4 -> A;
                case 5 -> B;
                case 6 -> C;
                default -> program.get(pointer + 1);
            };
        }
    }

    public static Computer parse(String input) {

        var values = Arrays.stream(input.split("\\D+")).skip(1).map(Integer::parseInt).toList();

        return setup(values.subList(3, values.size()), values.get(0), values.get(1), values.get(2));
    }

    @Test
    void shouldParse() {
        var computer = parse(sampleInput());

        assertThat(computer.pointer(), is(0));
        assertThat(computer.program(), is(asList(0, 1, 5, 4, 3, 0)));
        assertThat(computer.A(), is(729));
        assertThat(computer.B(), is(0));
        assertThat(computer.C(), is(0));
        assertThat(computer.output(), is(emptyList()));
    }

    @Test
    void supportDivision() {
        assertThat(setup(asList(0, 1), 16, 0, 0).tick().A(), is(16 / 2));
        assertThat(setup(asList(0, 5), 16, 3, 0).tick().A(), is(16 / 8));
    }

    @Test
    void supportBxl() {
        assertThat(setup(asList(1, 8), 0, 3, 0).tick().B(), is(11));
    }

    @Test
    void supportBst() {
        assertThat(setup(asList(2, 2), 0, 2, 0).tick().B(), is(2));
        assertThat(setup(asList(2, 4), 197, 0, 0).tick().B(), is(5));
    }

    @Test
    void supportJnz() {
        var computer = setup(asList(3, 6, 0, 0, 0, 0, 2, 4), 197, 0, 0).tick();
        assertThat(computer.pointer(), is(6));
        assertThat(computer.tick().B(), is(5));
    }

    @Test
    void supportBxc() {
        assertThat(setup(asList(4, -1), 0, 3, 8).tick().B(), is(11));
    }

    @Test
    void supportOut() {
        assertThat(setup(asList(5, 3), 0, 0, 0).tick().output(), is(singletonList(3)));
        assertThat(setup(asList(5, 6), 0, 0, 10).tick().output(), is(singletonList(2)));
    }

    @Test
    void supportBdv() {
        assertThat(setup(asList(6, 1), 16, 0, 0).tick().B(), is(16 / 2));
        assertThat(setup(asList(6, 5), 16, 3, 0).tick().B(), is(16 / 8));
    }

    @Test
    void supportCdv() {
        assertThat(setup(asList(7, 1), 16, 0, 0).tick().C(), is(16 / 2));
        assertThat(setup(asList(7, 5), 16, 3, 0).tick().C(), is(16 / 8));
    }

    @Test
    void examples() {
        assertThat(run(setup(asList(2, 6), 0, 0, 9)).B(), is(1));
        assertThat(run(setup(asList(5, 0, 5, 1, 5, 4), 10, 0, 0)).output(), is(asList(0, 1, 2)));
        assertThat(
                run(setup(asList(0, 1, 5, 4, 3, 0), 2024, 0, 0)).output(),
                is(asList(4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0)));
        assertThat(run(setup(asList(1, 7), 0, 29, 0)).B(), is(26));
        assertThat(run(setup(asList(4, 0), 0, 2024, 43690)).B(), is(44354));
    }

    @Test
    void sample() {
        assertThat(run(parse(sampleInput())).output(), is(asList(4, 6, 3, 5, 6, 3, 5, 2, 1, 0)));
    }

    public static Computer run(Computer computer) {
        while (!computer.halted()) {
            computer = computer.tick();
        }
        return computer;
    }
}
