package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.JTimeSchedApp;
import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

public class ProjectTableModelTest {

    @BeforeAll
    public static void initialize() {
        JTimeSchedApp.main(null); // Necessary to initialize the logger.
    }
    @ParameterizedTest
    @MethodSource("provideIntPair")
    public void testSetValueAt(Object value, int row, int column) {
        // Given
        ArrayList<Project> arrProj = new ArrayList<>();

        for (int i = 0; i < 10 ; i++) {
            arrProj.add(new Project());
        }

        ProjectTableModel tableModel = new ProjectTableModel(arrProj);

        // When
        tableModel.setValueAt(value, row, column);

        // Then
        switch (column) {
            case ProjectTableModel.COLUMN_CHECK:
            case ProjectTableModel.COLUMN_ACTION_DELETE:
            case ProjectTableModel.COLUMN_ACTION_STARTPAUSE:
                assert((boolean) tableModel.getValueAt(row, column) == (boolean) value);
                break;
            case ProjectTableModel.COLUMN_TITLE:
            case ProjectTableModel.COLUMN_COLOR:
            case ProjectTableModel.COLUMN_CREATED:
                assert( tableModel.getValueAt(row, column) == value);
                break;
            case ProjectTableModel.COLUMN_TIMEOVERALL:
            case ProjectTableModel.COLUMN_TIMETODAY:
                assert((int) tableModel.getValueAt(row, column) == (int) value);
                break;
            default:
                assert( tableModel.getValueAt(row, column) == value);
                Assert.fail(); // It should fail if the column doesn't exist
        }

    }

    private static Stream<Arguments> provideIntPair() {
        return Stream.of(
                Arguments.of(false, 1, 1),       // CHECK
                Arguments.of(true, 2, 1),
                Arguments.of("title", 3, 2),     // Title
                Arguments.of(Color.WHITE, 4, 3), // Color
                Arguments.of(new Date(), 5, 4),           // Date Created
                Arguments.of(5, 0, 5),           // Time Overall
                Arguments.of(5 , 0, 6),          // Time Today

                Arguments.of(false, 0, 7),        // Stop
                Arguments.of(false, 0, 0),          // Delete

                Arguments.of(2, 0, 9)
        );
    }
}
