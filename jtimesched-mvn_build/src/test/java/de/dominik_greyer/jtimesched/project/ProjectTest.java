package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.project.Project;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ProjectTest {

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void adjustSecondsTodayTest (int secondsToday){

        Project proj = new Project("test_project");
        proj.adjustSecondsToday(secondsToday);
        int sec = proj.getSecondsToday(); // begin at 0

        assertEquals(0,sec);
        //assertEquals(1,sec);
        //assertEquals(6,sec);

    }

    private static Stream<Arguments> provideAdjustValue() {
        return Stream.of(
                Arguments.of(0), // true
                Arguments.of(-1), // true
                Arguments.of(1), // false
                Arguments.of(-6), // true
                Arguments.of(6) // false

        );
    }
}
