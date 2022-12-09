package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectTest {

    public Project proj;
    public Date date_start;

    @BeforeEach
    public void beforeEach() {

        this.proj = new Project("test_project");
        this.date_start = new Date();
    }

    @Test
    public void testProjectCreation(){
        Assertions.assertEquals("test_project", this.proj.getTitle());
        Assertions.assertEquals("", this.proj.getNotes());
        Assertions.assertNull(this.proj.getColor());
        Assertions.assertNotNull(this.proj.getTimeCreated());
        Assertions.assertNotNull(this.proj.getTimeStart());
        Assertions.assertEquals(this.date_start, this.proj.getTimeCreated());
        Assertions.assertEquals(this.date_start, this.proj.getTimeStart());
    }

    @Test
    public void testPause(){

        int nPreviousSecondsOverall = this.proj.getSecondsOverall();
        int nPreviousSecondsToday = this.proj.getSecondsToday();

        try {
            this.proj.start();
        } catch (ProjectException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            this.proj.pause();
        } catch (ProjectException e){
            // Shouldn't reach this
        }

        assert(!proj.isRunning());

        Assertions.assertTrue(proj.getSecondsOverall() > nPreviousSecondsOverall);
        Assertions.assertTrue(proj.getSecondsToday() > nPreviousSecondsToday);

        assertThrows(ProjectException.class, () -> this.proj.pause());

    }

    @Test
    public void testProjectDefaultCreation(){
        Project proj2 = new Project();
        Date d = new Date();

        Assertions.assertEquals("project", proj2.getTitle());
        Assertions.assertEquals("", proj2.getNotes());
        Assertions.assertNull(proj2.getColor());
        Assertions.assertNotNull(proj2.getTimeCreated());
        Assertions.assertNotNull(proj2.getTimeStart());
        Assertions.assertEquals(d, proj2.getTimeCreated());
        Assertions.assertEquals(d, proj2.getTimeStart());
    }

    @Test
    public void testGetColor(){
        this.proj.setColor(null);
        Assertions.assertNull(this.proj.getColor());

        Color new_col = new Color(240,250,187);
        this.proj.setColor(new_col);
        Assertions.assertEquals(new_col.getRGB(), this.proj.getColor().getRGB());
        Assertions.assertEquals(new_col.getClass(), this.proj.getColor().getClass());
    }
    @Test
    public void testSetTitle() {
        String str = "change_title";
        this.proj.setTitle(str);

        Assertions.assertEquals(str, this.proj.getTitle());
        Assertions.assertEquals(str.getClass(), this.proj.getTitle().getClass());
    }

    @Test
    public void testSetTimeCreated(){
        Date changed_date = new Date(1212121212121L); // Fri May 30 06:20:12 CEST 2008
        this.proj.setTimeCreated(changed_date);
        Assertions.assertEquals(changed_date, this.proj.getTimeCreated());
        Assertions.assertEquals(changed_date.getClass(), this.proj.getTimeCreated().getClass());
    }

    @ParameterizedTest
    @MethodSource("provideDates")
    public void setTimeStartTest(Date date) {
        this.proj.setTimeStart(date);

        Assertions.assertEquals(date, this.proj.getTimeStart());
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

        Assertions.assertEquals(quotaToday, this.proj.getQuotaToday());

    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setQuotaOverallTest(int quotaOverall) {
        this.proj.setQuotaOverall(quotaOverall);

        Assertions.assertEquals(quotaOverall, this.proj.getQuotaOverall());

    }

    @ParameterizedTest
    @MethodSource("provideNotes")
    public void setNotesTest(String notes) {
        this.proj.setNotes(notes);

        Assertions.assertEquals(notes, this.proj.getNotes());
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

        Assertions.assertTrue(this.proj.toString().equalsIgnoreCase(test));

        this.proj.setRunning(true);

        test = String.format("Project [title=%s, running=%s, secondsOverall=%d, secondsToday=%d, checked=%s]",
                this.proj.getTitle(),
                (this.proj.isRunning()) ? "yes" : "no",
                this.proj.getSecondsOverall(), this.proj.getSecondsToday(),
                (this.proj.isChecked()) ? "yes" : "no");

        Assertions.assertTrue(this.proj.toString().equalsIgnoreCase(test));

        this.proj.setChecked(true);

        test = String.format("Project [title=%s, running=%s, secondsOverall=%d, secondsToday=%d, checked=%s]",
                this.proj.getTitle(),
                (this.proj.isRunning()) ? "yes" : "no",
                this.proj.getSecondsOverall(), this.proj.getSecondsToday(),
                (this.proj.isChecked()) ? "yes" : "no");

        Assertions.assertTrue(this.proj.toString().equalsIgnoreCase(test));
    }

    @Test
    public void setRunningTest() {
        this.proj.setRunning(true);

        Assertions.assertTrue(this.proj.isRunning());

        this.proj.setRunning(false);

        Assertions.assertFalse(this.proj.isRunning());
    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setSecondsOverallTest(int sec) {
        this.proj.setSecondsOverall(sec);

        if (sec < 0) {
            sec = 0;
        }

        Assertions.assertEquals(sec, this.proj.getSecondsOverall());
    }

    @Test
    public void adjustSecondsTodayTest(){
        this.proj.setRunning(true);

        int sec_today = 22;
        this.proj.setSecondsToday(4);
        int prev_overall = this.proj.getSecondsOverall();

        this.proj.adjustSecondsToday(sec_today);

        Assertions.assertTrue(proj.getSecondsOverall() > prev_overall);
        Assertions.assertEquals(sec_today, this.proj.getSecondsToday());
    }

    @Test
    public void adjustSecondsTodayTestNegative(){
        this.proj.setRunning(true);

        int sec_today = -22;
        this.proj.setSecondsToday(4);
        int prev_overall = this.proj.getSecondsOverall();

        this.proj.adjustSecondsToday(sec_today);

        Assertions.assertTrue(proj.getSecondsOverall() <= prev_overall);
        Assertions.assertEquals(0, this.proj.getSecondsToday());
    }

    @Test
    public void resetToday() {
        this.proj.resetToday();

        int sec = this.proj.getSecondsToday();
        int quota = this.proj.getQuotaToday();

        Assertions.assertEquals(0, sec);
        Assertions.assertEquals(0, quota);
    }

    @Test
    public void testStart(){
        assertThrows(ProjectException.class, () -> {
            proj.setRunning(true);
            proj.start();
        });
    }

    @Test
    public void testToggleRunning(){
        proj.setRunning(true);
        proj.toggle();
        Assertions.assertFalse(proj.isRunning());
    }

    @Test
    public void testToggleNotRunning(){
        proj.setRunning(false);
        proj.toggle();
        Assertions.assertTrue(proj.isRunning());
    }

    @Test
    public void testGetSecondsToday(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date d = cal.getTime();
        proj.setTimeStart(d);

        proj.setRunning(true);
        int result = proj.getSecondsToday();

        proj.setRunning(false);
        int result2 = proj.getSecondsToday();

        Assertions.assertEquals(result, result);
        Assertions.assertEquals(result2, result2);
        Assertions.assertNotEquals(result, result2);
        Assertions.assertTrue(result>=result2);
    }

    @Test
    public void testGetSecondsOverall(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2022);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date d = cal.getTime();
        proj.setTimeStart(d);

        proj.setRunning(true);
        int result = proj.getSecondsOverall();

        proj.setRunning(false);
        int result2 = proj.getSecondsOverall();

        Assertions.assertEquals(result2, result2);
        Assertions.assertNotEquals(result, result2);
        Assertions.assertTrue(result>=result2);
    }

    // Todo : Doesn't change anything
    /*@Test
    public void testSetSecondsOverall(){
        int val = 89;
        Assertions.assertTrue(val>=0);

        proj.setRunning(false);
        proj.setSecondsOverall(val);

        int result = proj.getSecondsOverall();
        Assertions.assertTrue(result == (int)result);
        Assertions.assertNotEquals(0, result);
    }*/

    @Test
    public void testSetSecondsOverall(){
        proj.setRunning(true);

        int val_neg = -1;
        Assertions.assertTrue(val_neg<0);

        int val_pos = 1;
        Assertions.assertTrue(val_pos>=0);

        proj.setSecondsOverall(val_neg);

        int result_neg = proj.getSecondsOverall();
        Assertions.assertTrue(result_neg == (int)result_neg);
        Assertions.assertEquals(0,result_neg);

        proj.setSecondsOverall(val_pos);

        int result_pos = proj.getSecondsOverall();
        Assertions.assertTrue(result_pos == (int)result_pos);
        Assertions.assertEquals(val_pos,result_pos);
    }

    @ParameterizedTest
    @MethodSource("provideAdjustValue")
    public void setSecondsTodayTest (int secondsToday) {
        this.proj.setSecondsToday(secondsToday);
        int sec = this.proj.getSecondsToday(); // begin at 0

        if (secondsToday < 0) {
            secondsToday = 0;
        }

        Assertions.assertEquals(secondsToday, sec);
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
