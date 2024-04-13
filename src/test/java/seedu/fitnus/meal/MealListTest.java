package seedu.fitnus.meal;

import seedu.fitnus.date.Date;

import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NonPositiveValueException;
import seedu.fitnus.exception.UnregisteredMealException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealListTest {
    MealList testerMealList;
    String todayDate;
    ArrayList<Meal> testMealList;
    ArrayList<Meal> testMealListAll;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        testerMealList = new MealList();

        testMealList = testerMealList.mealList;
        testMealListAll = testerMealList.mealListAll;

        Date currentDate = new Date();
        todayDate = currentDate.getDate();

        testMealList.add(new Meal("kaya toast", 4, todayDate));
        testMealList.add(new Meal("laksa", 10, todayDate));

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleMeal_validInputs_correctlyAddMeal() throws IncompleteMealException, UnregisteredMealException,
            NonPositiveValueException {
        String command = "eat m/kaya toast s/3";
        testerMealList.handleMeal(command);

        assertFalse(testMealList.isEmpty());

        assertEquals("kaya toast", testMealList.get(2).getName());
        assertEquals(3, testMealList.get(2).getServingSize());
    }

    @Test
    public void handleListMeals_emptyList_printListAccurate() {
        testMealList.clear();
        testerMealList.handleListMeals();

        String expectedOutput = "here's what you have eaten today" + System.lineSeparator() +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListMeals_validList_printListAccurate() {
        testerMealList.handleListMeals();

        String expectedOutput = "here's what you have eaten today" + System.lineSeparator() +
                "1. kaya toast (serving size: 4) | date: " + todayDate + System.lineSeparator() +
                "2. laksa (serving size: 10) | date: " + todayDate ;
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleEditMealServingSize_invalidMealIndex_exceptionThrown() throws InvalidListIndexException {
        Exception exception = assertThrows(InvalidListIndexException.class, () -> {
            String command = "editMeal 5 s/10";
            testerMealList.handleEditMealServingSize(command);
        });
    }

    @Test
    public void handleEditMealServingSize_validCommand_editMealSuccessful() throws InvalidListIndexException,
            IncompleteEditException, NonPositiveValueException {
        String command = "editMeal 2 s/1000000";
        testerMealList.handleEditMealServingSize(command);

        int mealIndex = 2 - 1;
        assertEquals("laksa", testMealList.get(mealIndex).getName());
        assertEquals(1000000, testMealList.get(mealIndex).getServingSize());
    }

    @Test
    public void handleDeleteMeal_noIndexInput_exceptionThrown() throws InvalidListIndexException,
            IncompleteDeleteException {
        Exception exception = assertThrows(IncompleteDeleteException.class, () -> {
            String command = "deleteMeal ";
            testerMealList.handleDeleteMeal(command);
        });
    }

    @Test
    public void handleDeleteMeal_noIntegerInput_exceptionThrown() throws InvalidListIndexException,
            IncompleteDeleteException {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            String command = "deleteMeal         ";
            testerMealList.handleDeleteMeal(command);
        });
    }

    @Test
    public void handleDeleteMeal_validCommand_deleteMealSuccessful() throws InvalidListIndexException,
            IncompleteDeleteException {
        String command = "deleteMeal 1";
        testerMealList.handleDeleteMeal(command);
        assertEquals(1, testMealList.size());
        assertEquals("laksa", testMealList.get(0).getName());
    }
}
