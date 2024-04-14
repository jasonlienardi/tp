package seedu.fitnus.exercise;

import seedu.fitnus.date.Date;

import seedu.fitnus.exception.ExceedTypeLongException;
import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteExerciseException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NonPositiveValueException;
import seedu.fitnus.exception.UnregisteredExerciseException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseListTest {
    ExerciseList testerExerciseList;
    String todayDate;
    ArrayList<Exercise> testExerciseList;
    ArrayList<Exercise> testExerciseListAll;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() throws UnregisteredExerciseException {
        testerExerciseList = new ExerciseList();

        testExerciseList = testerExerciseList.exerciseList;
        testExerciseListAll = testerExerciseList.exerciseListAll;

        Date currentDate = new Date();
        todayDate = currentDate.getDate();

        testExerciseList.add(new Exercise("swimming", 20, ExerciseIntensity.HIGH, todayDate));

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleExercise_validInputs_correctlyAddExercise() throws IncompleteExerciseException,
            UnregisteredExerciseException, NonPositiveValueException {
        String command = "exercise e/running d/30 i/HIGH";
        testerExerciseList.handleExercise(command);

        assertEquals("running", testExerciseList.get(1).getName());
        assertEquals(30, testExerciseList.get(1).getDuration());
        assertEquals(ExerciseIntensity.HIGH, testExerciseList.get(1).getIntensity());
    }

    @Test
    public void handleViewCaloriesBurnt_correctCalorieBurntCalculation_viewCaloriesBurntAccurate()
            throws ExceedTypeLongException {
        testerExerciseList.handleCaloriesBurnt();
        String expectedOutput = "Total calories burnt: 240 kcal";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleListExercise_emptyList_printListAccurate() {
        testExerciseList.clear();
        testerExerciseList.handleListExercises();

        String expectedOutput = "here's the exercises you've done today" + System.lineSeparator()  +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListExercise_validList_printListAccurate() {
        testerExerciseList.handleListExercises();

        String expectedOutput = "here's the exercises you've done today" + System.lineSeparator() +
                "1. swimming | duration: 20 | intensity: HIGH | date: " + todayDate;
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleDeleteExercise_validCommand_deleteMealSuccessful() throws InvalidListIndexException,
            IncompleteDeleteException {
        String command = "deleteExercise 1";
        testerExerciseList.handleDeleteExercise(command);
        assertEquals(0, testExerciseList.size());
    }
}
