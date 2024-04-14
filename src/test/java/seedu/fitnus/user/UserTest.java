package seedu.fitnus.user;

import seedu.fitnus.date.Date;
import seedu.fitnus.drink.Drink;
import seedu.fitnus.exception.ExceedTypeLongException;
import seedu.fitnus.exercise.Exercise;
import seedu.fitnus.exercise.ExerciseIntensity;
import seedu.fitnus.meal.Meal;

import seedu.fitnus.exception.UnregisteredExerciseException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    User testUser;
    String todayDate;
    ArrayList<Meal> testMealList;
    ArrayList<Drink> testDrinkList;
    ArrayList<Exercise> testExerciseList;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() throws UnregisteredExerciseException {
        testUser = new User();

        testMealList = testUser.myMealList.mealList;
        testDrinkList = testUser.myDrinkList.drinkList;
        testExerciseList = testUser.myExerciseList.exerciseList;

        Date currentDate = new Date();
        todayDate = currentDate.getDate();

        testMealList.add(new Meal("kaya toast", 4, todayDate));
        testMealList.add(new Meal("laksa", 10, todayDate));
        testDrinkList.add(new Drink("kopi", 100, todayDate));
        testExerciseList.add(new Exercise("swimming", 20, ExerciseIntensity.HIGH, todayDate));

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleViewCalories_correctCalorieCalculation_viewCaloriesAccurate() throws ExceedTypeLongException {
        testUser.handleViewCalories();
        String expectedOutput = "Total Calories: 5507 kcal";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewCarbohydrates_correctCarbsCalculation_viewCarbsAccurate() throws ExceedTypeLongException {
        testUser.handleViewCarbohydrates();
        String expectedOutput = "Total Carbohydrates: 912 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewProtein_correctProteinCalculation_viewProteinAccurate() throws ExceedTypeLongException {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewProteins();
        String expectedOutput = "Total Proteins: 215 grams";
        String actualOutput = outputStream.toString().trim();
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewFiber_correctFiberCalculation_viewFiberAccurate() throws ExceedTypeLongException {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFiber();
        String expectedOutput = "Total Fiber: 80 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewFat_correctFatCalculation_viewFatAccurate() throws ExceedTypeLongException {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFat();
        String expectedOutput = "Total Fat: 129 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewSugar_correctSugarCalculation_viewSugarAccurate() throws ExceedTypeLongException {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewSugar();
        String expectedOutput = "Total Sugar: 106 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }


    @Test
    public void handleListEverything_allEmptyLists_printListAccurate() {
        testMealList.clear();
        testDrinkList.clear();
        testExerciseList.clear();
        testUser.handleListEverything();

        String expectedOutput = "here's what you have consumed today" + System.lineSeparator() +
                "  >> nothing so far :o" + System.lineSeparator() + System.lineSeparator() +
                "Total water intake today: 0 ml" + System.lineSeparator() + "       ~~~" + System.lineSeparator() +
                "here's the exercises you've done today" + System.lineSeparator() +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListEverything_validList_printListAccurate() {
        testUser.handleListEverything();

        String expectedOutput = "here's what you have consumed today" + System.lineSeparator() +
                "1. kaya toast (serving size: 4) | date: " + todayDate + System.lineSeparator() +
                "2. laksa (serving size: 10) | date: " + todayDate + System.lineSeparator() +
                "3. kopi (volume: 100ml) | date: " + todayDate + System.lineSeparator() + System.lineSeparator() +
                "Total water intake today: 0 ml" + System.lineSeparator() + "       ~~~" + System.lineSeparator() +
                "here's the exercises you've done today" + System.lineSeparator() +
                "1. swimming | duration: 20 | intensity: HIGH | date: " + todayDate;
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleClear_validCommand_clearListsSuccessful() {
        testUser.handleClear();
        assertEquals(0, testMealList.size());
        assertEquals(0, testDrinkList.size());
        assertEquals(0, testExerciseList.size());

    }
}
