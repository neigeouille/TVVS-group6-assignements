package de.dominik_greyer.jtimesched.project;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;
import static de.dominik_geyer.jtimesched.project.ProjectTime.formatDate;
import static org.junit.Assert.assertEquals;
public class ProjectTimeTest {

    @Test
    public void testFormatDate() {
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 19);

        Date date_test1 = cal.getTime();

        //When
        String sdf_test1 = formatDate(date_test1);

        //Then
        assertEquals("2000-12-19", sdf_test1);
    }


}
