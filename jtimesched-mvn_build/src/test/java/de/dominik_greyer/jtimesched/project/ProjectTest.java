package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.mock;

import java.awt.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static de.dominik_geyer.jtimesched.project.ProjectTime.parseSeconds;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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
        assertEquals("test_project", this.proj.getTitle());
        assertEquals("", this.proj.getNotes());
        assertNull(this.proj.getColor());
        assertNotNull(this.proj.getTimeCreated());
        assertNotNull(this.proj.getTimeStart());
        assertEquals(this.date_start, this.proj.getTimeCreated());
        assertEquals(this.date_start, this.proj.getTimeStart());
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

        assert(proj.getSecondsOverall() == nPreviousSecondsOverall + 1);
        assert(proj.getSecondsToday() == nPreviousSecondsToday + 1);

        assertThrows(ProjectException.class, () -> {this.proj.pause();});

    }

    @Test
    public void testProjectDefaultCreation(){
        Project proj2 = new Project();
        Date d = new Date();

        assertEquals("project", proj2.getTitle());
        assertEquals("", proj2.getNotes());
        assertNull(proj2.getColor());
        assertNotNull(proj2.getTimeCreated());
        assertNotNull(proj2.getTimeStart());
        assertEquals(d, proj2.getTimeCreated());
        assertEquals(d, proj2.getTimeStart());
    }

    @Test
    public void testGetColor(){
        this.proj.setColor(null);
        assertNull(this.proj.getColor());

        Color new_col = new Color(240,250,187);
        this.proj.setColor(new_col);
        assertEquals(new_col.getRGB(), this.proj.getColor().getRGB());
        assertEquals(new_col.getClass(), this.proj.getColor().getClass());
    }
    @Test
    public void testSetTitle() {
        String str = "change_title";
        this.proj.setTitle(str);

        assertEquals(str, this.proj.getTitle());
        assertEquals(str.getClass(), this.proj.getTitle().getClass());
    }

    @Test
    public void testSetTimeCreated(){
        Date changed_date = new Date(1212121212121L); // Fri May 30 06:20:12 CEST 2008
        this.proj.setTimeCreated(changed_date);
        assertEquals(changed_date, this.proj.getTimeCreated());
        assertEquals(changed_date.getClass(), this.proj.getTimeCreated().getClass());
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
    public void adjustSecondsTodayTest (int secondsToday){  /// TODO = DATAFLOW

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

    @Test
    public void testStart(){
        assertThrows(ProjectException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                proj.setRunning(true);
                proj.start();
            }
        });
    }

    @Test
    public void testToogleRunning(){
        proj.setRunning(true);
        proj.toggle();
        assertEquals(false, proj.isRunning());
    }

    @Test
    public void testToogleNotRunning(){
        proj.setRunning(false);
        proj.toggle();
        assertEquals(true, proj.isRunning());
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

        assertTrue(result == (int)result);
        assertTrue(result2 == (int)result2);
        assertNotEquals(result, result2);
        assertTrue(result>=result2);
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

        assertTrue(result == (int)result);
        assertTrue(result2 == (int)result2);
        assertNotEquals(result, result2);
        assertTrue(result>=result2);
    }

    // Doesn't change anything
    /*@Test
    public void testSetSecondsOverall(){
        proj.setRunning(false);
        proj.setSecondsOverall(89);
        int result = proj.getSecondsOverall();
        assertTrue(result == (int)result);
        assertNotEquals(0, result);
    }*/
    /*@Test
    public void testSetSecondsOverallNegative(){
        proj.setRunning(false);
        proj.setSecondsOverall(-1);
        int result = proj.getSecondsOverall();
        assertTrue(result == (int)result);
        assertEquals(0,result);
    }*/

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
