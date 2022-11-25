package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.JTimeSchedApp;
import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

import static de.dominik_geyer.jtimesched.project.ProjectTime.parseSeconds;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjectTableModelTest {

    private static final int NUMBER_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 6;

    private static ArrayList<Project> arrProj;
    private static ProjectTableModel tableModel;

    @BeforeAll //todo : fix me because doesn't handle mutation testing
    public static void beforeAll() {
        JTimeSchedApp.main(null); // Necessary to initialize the logger.
    }

    @BeforeEach
    public void beforeEach() {
        // Given
        arrProj = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_ROWS ; i++) {
            arrProj.add(new Project());
        }

        tableModel = new ProjectTableModel(arrProj);
    }

    @Test
    public void addProjectTest() {
        tableModel.addProject(new Project());

        Assert.assertEquals(tableModel.getRowCount(), NUMBER_OF_ROWS + 1);
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

    @ParameterizedTest
    @MethodSource("provideIsCellEditable")
    public void testIsCellEditable(int row, int column) {

        if (row < 0 ||
            row > NUMBER_OF_ROWS ||
            column < 1 ||
            column > NUMBER_OF_COLUMNS) {

            assert (!tableModel.isCellEditable(row, column));
        } else {
            assert (tableModel.isCellEditable(row, column));
        }

        
    }

    private static Stream<Arguments> provideIsCellEditable() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(-1, 0),
                Arguments.of(0, 1),
                Arguments.of(-1, 5),
                Arguments.of(0, 6),
                Arguments.of(9, 1),
                Arguments.of(10, 0),
                Arguments.of(9, 6),
                Arguments.of(10, 7),
                Arguments.of(1, 2),
                Arguments.of(1, 5),
                Arguments.of(8, 2),
                Arguments.of(8, 5),
                Arguments.of(100, 100)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRemoveProject")
    public void testRemoveProject(int row){

        // Row in range
        int arrBefore = tableModel.getRowCount();
        tableModel.removeProject(row);
        int arrAfter = tableModel.getRowCount();

        Assert.assertEquals(1, arrBefore-arrAfter);
    }
    @ParameterizedTest
    @MethodSource("provideRemoveProjectOUT")
    public void testRemoveProjectOUT(int row){

        // Row out of range
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> {
            tableModel.removeProject(row);
        });
    }

    private static Stream<Arguments> provideRemoveProject(){
        return Stream.of(
                Arguments.of(0),
                Arguments.of(9),
                Arguments.of(1),
                Arguments.of(8)
                //Arguments.of(5)
        );
    }

    private static Stream<Arguments> provideRemoveProjectOUT(){
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(10)
                //Arguments.of(-6),
                //Arguments.of(16)
        );
    }
}
