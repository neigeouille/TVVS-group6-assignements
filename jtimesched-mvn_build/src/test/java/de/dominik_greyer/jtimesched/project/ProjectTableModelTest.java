package de.dominik_greyer.jtimesched.project;

import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.stream.Stream;

public class ProjectTableModelTest {

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
        //Assert.assertThrows();

    }

    private static Stream<Arguments> provideIntPair() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, 0, 0),
                Arguments.of(false, 0, 0),
                Arguments.of("False", 0, 0),
                Arguments.of("", 0, 2),
                Arguments.of("", 0, 1)
        );
    }
}
