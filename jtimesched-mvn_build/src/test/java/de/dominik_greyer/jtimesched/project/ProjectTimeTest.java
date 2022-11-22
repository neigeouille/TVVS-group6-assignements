package de.dominik_greyer.jtimesched.project;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static de.dominik_geyer.jtimesched.project.ProjectTime.formatDate;
import static de.dominik_geyer.jtimesched.project.ProjectTime.parseSeconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectTimeTest {

    @Test
    public void testFormatDate() {
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 19);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 0);
        cal2.set(Calendar.MONTH, 12);
        cal2.set(Calendar.DAY_OF_MONTH, 32);

        Date date_test1 = cal.getTime();
        Date date_test2 = cal2.getTime();

        //When

        //Then
        assertEquals("2000-12-19", formatDate(date_test1));
        assertNotEquals("0000-12-32",formatDate(date_test2));
    }

    @Test
    public void testParseSeconds() throws ParseException {

        // Correct parameter
        String time = "3:23:39";
        assertEquals(12219,parseSeconds(time));

        // Incorrect parameters that should throw an exception
        assertThrows(ParseException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                String time2 = "anything";
                 parseSeconds(time2);
            }
        });

    }


}
