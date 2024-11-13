package fr.ninauve.renaud.kata.diamond;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DiamondTest {

    static Stream<Arguments> should_print_diamond() {
        return Stream.of(
                Arguments.of('A', "A"),
                Arguments.of(
                        'B',
                        """
                                .A.
                                B.B
                                .A."""),
                Arguments.of(
                        'C',
                        """
                                ..A..
                                .B.B.
                                C...C
                                .B.B.
                                ..A..""")
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_print_diamond(char widest, String expected) {
        String actual = printDiamondWithWidestChar(widest);
        String expectedWithSpaces = expected.replace('.', ' ');
        assertThat(actual).isEqualTo(expectedWithSpaces);
    }

    public String printDiamondWithWidestChar(char widest) {
        return String.join(System.lineSeparator(), printDiamondWithWidestCharLines(widest));
    }

    private List<String> printDiamondWithWidestCharLines(char widest) {
        List<String> diamondA = List.of("A");
        List<String> diamondB = List.of(
                " A ",
                widestLine('B', diamondA),
                " A ");
        List<String> diamondC = List.of(
                "  A  ",
                " B B ",
                widestLine('C', diamondB),
                " B B ",
                "  A  ");

        return switch (widest) {
            case 'A' -> diamondA;
            case 'B' -> diamondB;
            case 'C' -> diamondC;

            default -> List.of();
        };
    }

    private String widestLine(char widest, List<String> previous) {
        return new StringBuilder()
                .append(widest)
                .repeat(" ", previous.getFirst().length())
                .append(widest)
                .toString();
    }
}
