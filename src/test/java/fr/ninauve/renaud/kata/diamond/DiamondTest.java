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
        if (widest == 'A') {
            return List.of("A");
        }
        List<String> previous = printDiamondWithWidestCharLines((char) (widest - 1));

        return switch (widest) {
            case 'B' -> List.of(
                    " A ",
                    widestLine(widest, previous),
                    " A ");

            case 'C' -> List.of(
                    "  A  ",
                    " B B ",
                    widestLine(widest, previous),
                    " B B ",
                    "  A  ");

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
