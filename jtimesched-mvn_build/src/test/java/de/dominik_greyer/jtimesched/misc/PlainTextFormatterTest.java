package de.dominik_greyer.jtimesched.misc;

import de.dominik_geyer.jtimesched.misc.PlainTextFormatter;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.PreconditionViolationException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Stream;

public class PlainTextFormatterTest {

    @ParameterizedTest
    @MethodSource("provideLogRecords")
    public void testFormat(LogRecord record) {
        // Given
        PlainTextFormatter testFormatter = new PlainTextFormatter();

        // When
        String szTest = testFormatter.format(record);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (E) HH:mm:ss");

        //Then
        assert(szTest != null);
        String test = String.format("%s [%s]: %s%n",
                sdf.format(new Date(record.getMillis())),
                record.getLevel(),
                record.getMessage());
        assert(szTest.equals(test));
    }

    private static Stream<Arguments> provideLogRecords() {
        return Stream.of(
                // Arguments.of(null, null), LogRecord requires Level argument to be NonNull
                // Arguments.of(null, "Test null"),
                Arguments.of(new LogRecord(Level.SEVERE, null)),
                Arguments.of(new LogRecord(Level.WARNING, "Test 1")),
                Arguments.of(new LogRecord(Level.ALL, ""))
        );
    }
}
