package seedu.fitnus.user;

import seedu.fitnus.Date;
import seedu.fitnus.Drink;
import seedu.fitnus.Exercise;
import seedu.fitnus.ExerciseIntensity;
import seedu.fitnus.Meal;

import seedu.fitnus.Water;

import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    User testUser;
    String todayDate;
    ArrayList<Meal> testMealList;
    ArrayList<Drink> testDrinkList;
    ArrayList<Exercise> testExerciseList;
    private Storage testMealStorage;
    private Storage testDrinkStorage;
    private Storage mealNutrientStorage = new Storage("./db", "./db/Meal_db.csv");
    private Storage drinkNutrientStorage = new Storage("./db", "./db/Drink_db.csv");

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() throws UnregisteredExerciseException {
        testMealStorage = new Storage("./src/test/resources", "src/test/resources/MealList.txt");
        testDrinkStorage = new Storage("./src/test/resources", "src/test/resources/DrinkList.txt");

        testUser = new User(testMealStorage, testDrinkStorage, mealNutrientStorage, drinkNutrientStorage);

        testMealList = testUser.mealList;
        testDrinkList = testUser.drinkList;
        testExerciseList = testUser.exerciseList;
        //Water.editWaterIntake(0);

        Date currentDate = new Date();
        todayDate = currentDate.getDate();

        testMealList.add(new Meal("kaya toast", 4, todayDate));
        testMealList.add(new Meal("laksa", 10, todayDate));
        testDrinkList.add(new Drink("kopi", 100, todayDate));
        testExerciseList.add(new Exercise("swimming", 20, ExerciseIntensity.HIGH));

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleMeal_validInputs_correctlyAddMeal() throws IncompleteMealException, UnregisteredMealException,
            NegativeValueException {
        String command = "eat m/kaya toast s/3";
        testUser.handleMeal(command);

        assertFalse(testMealList.isEmpty());

        assertEquals("kaya toast", testMealList.get(2).getName());
        assertEquals(3, testMealList.get(2).getServingSize());
    }

    @Test
    public void handleDrink_validInputs_correctlyAddDrink() throws IncompleteDrinkException,
            UnregisteredDrinkException, NegativeValueException {
        String command = "drink d/kopi s/500";
        testUser.handleDrink(command);

        assertEquals("kopi", testDrinkList.get(1).getName());
        assertEquals(500, testDrinkList.get(1).getDrinkVolumeSize());
    }

    @Test
    public void handleViewCalories_correctCalorieCalculation_viewCaloriesAccurate() {
        testUser.handleViewCalories();
        String expectedOutput = "Total Calories: 5547";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewCarbohydrates_correctCarbsCalculation_viewCarbsAccurate() {
        testUser.handleViewCarbohydrates();
        String expectedOutput = "Total Carbohydrates: 912";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewProtein_correctProteinCalculation_viewProteinAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewProteins();
        String expectedOutput = "Total Proteins: 215";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewWaterIntake_correctWaterCalculation_viewWaterAccurate() {
        new Water (500, "28-04-2024");

        testUser.handleViewWaterIntake();
        String expectedOutput = "Total water intake: 500";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewFiber_correctFiberCalculation_viewFiberAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFiber();
        String expectedOutput = "Total Fiber: 80";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewFat_correctFatCalculation_viewFatAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFat();
        String expectedOutput = "Total Fat: 129";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewSugar_correctSugarCalculation_viewSugarAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewSugar();
        String expectedOutput = "Total Sugar: 106";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }
    
    @Test
    public void handleListMealsToday_emptyList_printListAccurate() {
        testMealList.clear();
        testUser.handleListMealsToday();

        String expectedOutput = "here's what you have eaten today" + System.lineSeparator() +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListMealsToday_validList_printListAccurate() {
        testUser.handleListMealsToday();

        String expectedOutput = "here's what you have eaten today" + System.lineSeparator() +
                "1. kaya toast (serving size: 4) | date: " + todayDate + System.lineSeparator() +
                "2. laksa (serving size: 10) | date: " + todayDate ;
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListDrinksToday_emptyList_printListAccurate() {
        testDrinkList.clear();
        testUser.handleListDrinksToday();

        String expectedOutput = "here's what you have drank today" + System.lineSeparator()  +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void handleListDrinksToday_validList_printListAccurate() {
        testUser.handleListDrinksToday();

        String expectedOutput = "here's what you have drank today" + System.lineSeparator() +
                "1. kopi (volume: 100ml) | date: " + todayDate + System.lineSeparator() + System.lineSeparator() +
                "Total water intake: 0 ml";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListEverythingToday_allEmptyLists_printListAccurate() {
        testMealList.clear();
        testDrinkList.clear();
        testExerciseList.clear();
        testUser.handleListEverythingToday();

        String expectedOutput = "here's what you have consumed today" + System.lineSeparator() +
                "  >> nothing so far :o" + System.lineSeparator() + System.lineSeparator() +
                "Total water intake: 0 ml" + System.lineSeparator() + "       ~~~" + System.lineSeparator() +
                "here's the exercises you've done today" + System.lineSeparator() +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListEverythingToday_validList_printListAccurate() {
        testUser.handleListEverythingToday();

        String expectedOutput = "here's what you have consumed today" + System.lineSeparator() +
                "1. kaya toast (serving size: 4) | date: " + todayDate + System.lineSeparator() +
                "2. laksa (serving size: 10) | date: " + todayDate + System.lineSeparator() +
                "3. kopi (volume: 100ml) | date: " + todayDate + System.lineSeparator() + System.lineSeparator() +
                "Total water intake: 0 ml" + System.lineSeparator() + "       ~~~" + System.lineSeparator() +
                "here's the exercises you've done today" + System.lineSeparator() +
                "1. swimming | duration: 20 | intensity: HIGH";
        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleEditMealServingSize_invalidMealIndex_exceptionThrown() throws InvalidListIndexException {
        Exception exception = assertThrows(InvalidListIndexException.class, () -> {
            String command = "editMeal 5 s/10";
            testUser.handleEditMealServingSize(command);
        });
    }

    @Test
    public void handleEditMealServingSize_validCommand_editMealSuccessful() throws InvalidListIndexException,
            NegativeValueException, IncompleteEditException {
        String command = "editMeal 2 s/100000000";
        testUser.handleEditMealServingSize(command);

        int mealIndex = 2 - 1;
        assertEquals("laksa", testMealList.get(mealIndex).getName());
        assertEquals(100000000, testMealList.get(mealIndex).getServingSize());
    }

    @Test
    public void handleEditDrinkServingSize_validCommand_editDrinkSuccessful() throws InvalidListIndexException,
            NegativeValueException, IncompleteEditException {
        String command = "editDrink 1 s/100000000";
        testUser.handleEditDrinkServingSize(command);

        int drinkIndex = 1 - 1;
        assertEquals("kopi", testDrinkList.get(drinkIndex).getName());
        assertEquals(100000000, testDrinkList.get(drinkIndex).getDrinkVolumeSize());
    }

    @Test
    public void handleDeleteMeal_noIndexInput_exceptionThrown() throws InvalidListIndexException,
            IncompleteDeleteException {
        Exception exception = assertThrows(IncompleteDeleteException.class, () -> {
            String command = "deleteMeal ";
            testUser.handleDeleteMeal(command);
        });
    }

    @Test
    public void handleDeleteMeal_noIntegerInput_exceptionThrown() throws InvalidListIndexException,
            IncompleteDeleteException {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            String command = "deleteMeal         ";
            testUser.handleDeleteMeal(command);
        });
    }

    @Test
    public void handleDeleteMeal_validCommand_deleteMealSuccessful() throws InvalidListIndexException,
            IncompleteDeleteException {
        String command = "deleteMeal 1";
        testUser.handleDeleteMeal(command);
        assertEquals(1, testMealList.size());
        assertEquals("laksa", testMealList.get(0).getName());
    }

    @Test
    public void handleDeleteDrink_invalidDrinkIndex_exceptionThrown() throws InvalidListIndexException {
        Exception exception = assertThrows(InvalidListIndexException.class, () -> {
            String command = "deleteDrink 5";
            testUser.handleDeleteDrink(command);
        });
    }

    @Test
    public void handleDeleteDrink_validCommand_deleteDrinkSuccessful() throws InvalidListIndexException,
            IncompleteDeleteException {
        String command = "deleteDrink 1";
        testUser.handleDeleteDrink(command);
        assertEquals(0, testDrinkList.size());
    }

    @Test
    public void handleClear_validCommand_clearListsSuccessful() {
        testUser.handleClear();
        assertEquals(0, testMealList.size());
        assertEquals(0, testDrinkList.size());
    }
}
