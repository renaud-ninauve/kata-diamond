package fr.ninauve.renaud.kata.diamond;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
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
        char previousWidest = (char) (widest - 1);
        List<String> previous = printDiamondWithWidestCharLines(previousWidest);
        String widestLine = widestLine(widest, previous);
        int widestIndex = widest - 'A';

        List<String> previousDuplicatedMiddle = duplicateMiddle(previous);
        List<String> previousWithMiddleAndSpace = addBeginingAndTrailingSpace(previousDuplicatedMiddle);
        return insert(previousWithMiddleAndSpace, widestLine, widestIndex);
    }

    private String widestLine(char widest, List<String> previous) {
        return new StringBuilder()
                .append(widest)
                .repeat(" ", previous.getFirst().length())
                .append(widest)
                .toString();
    }

    private List<String> addBeginingAndTrailingSpace(List<String> lines) {
        return lines.stream()
                .map(line -> " " + line + " ")
                .toList();
    }

    private List<String> duplicateMiddle(List<String> lines) {
        int middle = lines.size() / 2;
        final List<String> result = new ArrayList<>(lines.subList(0, middle));
        result.add(lines.get(middle));
        result.addAll(lines.subList(middle, lines.size()));
        return result;
    }

    private List<String> insert(List<String> lines, String newLine, int index) {
        Stream<String> beforeIndex = lines.subList(0, index).stream();
        Stream<String> newLineStream = Stream.of(newLine);
        Stream<String> afterIndex = lines.subList(index, lines.size()).stream();

        Stream<String> untilNewLine = Stream.concat(beforeIndex, newLineStream);
        return Stream.concat(untilNewLine, afterIndex).toList();
    }
}
