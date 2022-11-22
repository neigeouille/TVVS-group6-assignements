package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.project.Project;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ProjectTest {

    public Project proj;

    @BeforeEach
    public void beforeEach() {
        this.proj = new Project("test_project");
    }

    @ParameterizedTest
    @MethodSource("provideDates")
    public void setTimeStartTest(Date date) {
        this.proj.setTimeStart(date);

        Assert.assertEquals(date, this.proj.getTimeStart());
    }

    private static Stream<Arguments> provideDates() {
        return Stream.of(
                Arguments.of(new Date()),
                Arguments.of(new Date(1L))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setQuotaTodayTest(int quotaToday) {
        this.proj.setQuotaToday(quotaToday);

        Assert.assertEquals(quotaToday,this.proj.getQuotaToday());

    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setQuotaOverallTest(int quotaOverall) {
        this.proj.setQuotaOverall(quotaOverall);

        Assert.assertEquals(quotaOverall,this.proj.getQuotaOverall());

    }

    @ParameterizedTest
    @MethodSource("provideNotes")
    public void setNotesTest(String notes) {
        this.proj.setNotes(notes);

        Assert.assertTrue(notes.equals(this.proj.getNotes()));
    }

    private static Stream<Arguments> provideNotes() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("Hello World"),
                Arguments.of("I am a note with \n strange \t things  ")
        );
    }

    @Test
    public void toStringTest(){

        String test = String.format("Project [title=%s, running=%s, secondsOverall=%d, secondsToday=%d, checked=%s]",
                this.proj.getTitle(),
                (this.proj.isRunning()) ? "yes" : "no",
                this.proj.getSecondsOverall(), this.proj.getSecondsToday(),
                (this.proj.isChecked()) ? "yes" : "no");

        Assert.assertTrue(this.proj.toString().equalsIgnoreCase(test));

        this.proj.setRunning(true);

        test = String.format("Project [title=%s, running=%s, secondsOverall=%d, secondsToday=%d, checked=%s]",
                this.proj.getTitle(),
                (this.proj.isRunning()) ? "yes" : "no",
                this.proj.getSecondsOverall(), this.proj.getSecondsToday(),
                (this.proj.isChecked()) ? "yes" : "no");

        Assert.assertTrue(this.proj.toString().equalsIgnoreCase(test));

        this.proj.setChecked(true);

        test = String.format("Project [title=%s, running=%s, secondsOverall=%d, secondsToday=%d, checked=%s]",
                this.proj.getTitle(),
                (this.proj.isRunning()) ? "yes" : "no",
                this.proj.getSecondsOverall(), this.proj.getSecondsToday(),
                (this.proj.isChecked()) ? "yes" : "no");

        Assert.assertTrue(this.proj.toString().equalsIgnoreCase(test));
    }

    @Test
    public void setRunningTest() {
        this.proj.setRunning(true);

        Assert.assertEquals(this.proj.isRunning(),true);

        this.proj.setRunning(false);

        Assert.assertEquals(this.proj.isRunning(),false);
    }

    @Test
    public void toggleTest() {
        this.proj.setRunning(true);
        this.proj.toggle();
        this.proj.toggle();
    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setSecondsOverallTest(int sec) {
        this.proj.setSecondsOverall(sec);

        if (sec < 0) {
            sec = 0;
        }

        Assert.assertEquals(sec,this.proj.getSecondsOverall());
    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void adjustSecondsTodayTest (int secondsToday){

        this.proj.adjustSecondsToday(secondsToday);
        int sec = this.proj.getSecondsToday(); // begin at 0

        if (secondsToday < 0) {
            secondsToday = 0;
        }

        Assert.assertEquals(secondsToday,sec);
    }

    @Test
    public void resetToday() {
        this.proj.resetToday();

        int sec = this.proj.getSecondsToday();
        int quota = this.proj.getQuotaToday();

        Assert.assertEquals(0, sec);
        Assert.assertEquals(0, quota);
    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setSecondsTodayTest (int secondsToday) {
        this.proj.setSecondsToday(secondsToday);
        int sec = this.proj.getSecondsToday(); // begin at 0

        if (secondsToday < 0) {
            secondsToday = 0;
        }

        Assert.assertEquals(secondsToday,sec);
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
