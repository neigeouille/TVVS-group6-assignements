package de.dominik_greyer.jtimesched.misc;

import de.dominik_geyer.jtimesched.misc.PlainTextFormatter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Stream;

public class PlainTextFormatterTest {

    @ParameterizedTest
    @MethodSource("provideLogRecords")
    public void testFormat(Level level, String log) {
        // Given
        LogRecord record = new LogRecord(level, log);
        PlainTextFormatter testFormatter = new PlainTextFormatter();

        // When
        String szTest = testFormatter.format(record);
        System.out.println(szTest);

        //Then
        if (log != null) {
            assert(szTest.contains(log));
        } else {
            assert (szTest.contains("null"));
        }
        assert(szTest.contains(level.getName()));
    }

    private static Stream<Arguments> provideLogRecords() {
        return Stream.of(
                // Arguments.of(null, null), LogRecord requires Level argument to be NonNull
                // Arguments.of(null, "Test null"),
                Arguments.of(Level.SEVERE, null),
                Arguments.of(Level.WARNING, "Test 1"),
                Arguments.of(Level.ALL, ""),
                Arguments.of(Level.INFO, "Test 2")
        );
    }
}
