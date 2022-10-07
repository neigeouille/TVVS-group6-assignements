package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.JTimeSchedApp;
import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

public class ProjectTableModelTest {

    private static ArrayList<Project> arrProj;
    private static ProjectTableModel tableModel;

    @BeforeAll
    public static void beforeAll() {
        JTimeSchedApp.main(null); // Necessary to initialize the logger.
    }

    @BeforeEach
    public void beforeEach() {
        // Given
        arrProj = new ArrayList<>();

        for (int i = 0; i < 10 ; i++) {
            arrProj.add(new Project());
        }

        tableModel = new ProjectTableModel(arrProj);
    }

    @ParameterizedTest
    @MethodSource("provideSetValue")
    public void testSetValueAt(Object value, int row, int column) {

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
        }
    }

    private static Stream<Arguments> provideSetValue() {
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

    // This one is important because it tests cases that the one above doesn't, for example when we are trying to get a
    // value that wasn't set.
    @ParameterizedTest
    @MethodSource("provideGetValue")
    public void testGetValueAtOutOfBounds(int row, int column) {
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> {
            tableModel.getValueAt(row, column);
        });
    }

    private static Stream<Arguments> provideGetValue() {
        return Stream.of(
                Arguments.of(-1, 1),
                Arguments.of(11, 1),
                Arguments.of(1, 11),
                Arguments.of(1, -1)
        );
    }
}
