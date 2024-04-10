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
import seedu.fitnus.exception.IncompleteExerciseException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.fitnus.storage.StorageManager;
//import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    User testUser;
    String todayDate;
    StorageManager testStorageManager;
    ArrayList<Meal> testMealList;
    ArrayList<Drink> testDrinkList;
    ArrayList<Water> testWaterList;
    ArrayList<Exercise> testExerciseList;
    private Storage testMealStorage;
    private Storage testDrinkStorage;
    private Storage testExerciseStorage;
    private Storage mealNutrientStorage = new Storage("./db", "./db/Meal_db.csv");
    private Storage drinkNutrientStorage = new Storage("./db", "./db/Drink_db.csv");
    private Storage exerciseCaloriesStorage = new Storage("./db", "./db/Exercise_db.csv");

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() throws UnregisteredExerciseException {
        testMealStorage = new Storage("./src/test/resources", "src/test/resources/MealList.txt");
        testDrinkStorage = new Storage("./src/test/resources", "src/test/resources/DrinkList.txt");
        testExerciseStorage = new Storage("./src/test/resources", "src/test/resources/ExerciseList.txt");

        testUser = new User();
        testStorageManager = new StorageManager(testMealStorage, testDrinkStorage, testExerciseStorage,
                mealNutrientStorage, drinkNutrientStorage, exerciseCaloriesStorage);

        testMealList = testUser.mealList;
        testDrinkList = testUser.drinkList;
        testExerciseList = testUser.exerciseList;
        testWaterList = testUser.waterList;

        Date currentDate = new Date();
        todayDate = currentDate.getDate();

        testMealList.add(new Meal("kaya toast", 4, todayDate));
        testMealList.add(new Meal("laksa", 10, todayDate));
        testDrinkList.add(new Drink("kopi", 100, todayDate));
        testWaterList.add(new Water( 100, todayDate));
        testExerciseList.add(new Exercise("swimming", 20, ExerciseIntensity.HIGH, "30-01-2024"));

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
    public void handleExercise_validInputs_correctlyAddExercise() throws IncompleteExerciseException,
            UnregisteredExerciseException, NegativeValueException {
        String command = "exercise e/running d/30 i/HIGH";
        testUser.handleExercise(command);

        assertEquals("running", testExerciseList.get(1).getName());
        assertEquals(30, testExerciseList.get(1).getDuration());
        assertEquals(ExerciseIntensity.HIGH, testExerciseList.get(1).getIntensity());
    }

    @Test
    public void handleViewCalories_correctCalorieCalculation_viewCaloriesAccurate() {
        testUser.handleViewCalories();
        String expectedOutput = "Total Calories: 5507";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewCarbohydrates_correctCarbsCalculation_viewCarbsAccurate() {
        testUser.handleViewCarbohydrates();
        String expectedOutput = "Total Carbohydrates: 912 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewProtein_correctProteinCalculation_viewProteinAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewProteins();
        String expectedOutput = "Total Proteins: 215 grams";
        String actualOutput = outputStream.toString().trim();
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewWaterIntake_correctWaterCalculation_viewWaterAccurate() {
        testWaterList.add(new Water (500, todayDate));

        testUser.handleViewWaterIntake();
        String expectedOutput = "Total water intake today: 600 ml";
        String actualOutput = outputStream.toString().trim();
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewFiber_correctFiberCalculation_viewFiberAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFiber();
        String expectedOutput = "Total Fiber: 80 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewFat_correctFatCalculation_viewFatAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFat();
        String expectedOutput = "Total Fat: 129 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewSugar_correctSugarCalculation_viewSugarAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewSugar();
        String expectedOutput = "Total Sugar: 106 grams";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void handleViewCaloriesBurnt_correctCalorieBurntCalculation_viewCaloriesBurntAccurate() {
        testUser.handleCaloriesBurnt();
        String expectedOutput = "Total calories burnt: 240";
        String actualOutput = outputStream.toString().trim();

        assertEquals(actualOutput, expectedOutput);
    }
    
    @Test
    public void handleListMeals_emptyList_printListAccurate() {
        testMealList.clear();
        testUser.handleListMeals();

        String expectedOutput = "here's what you have eaten today" + System.lineSeparator() +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListMeals_validList_printListAccurate() {
        testUser.handleListMeals();

        String expectedOutput = "here's what you have eaten today" + System.lineSeparator() +
                "1. kaya toast (serving size: 4) | date: " + todayDate + System.lineSeparator() +
                "2. laksa (serving size: 10) | date: " + todayDate ;
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListDrinks_emptyList_printListAccurate() {
        testDrinkList.clear();
        testWaterList.clear();
        testUser.handleListDrinks();

        String expectedOutput = "here's what you have drank today" + System.lineSeparator()  +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void handleListDrinks_validList_printListAccurate() {
        testUser.handleListDrinks();

        String expectedOutput = "here's what you have drank today" + System.lineSeparator() +
                "1. kopi (volume: 100ml) | date: " + todayDate + System.lineSeparator() + System.lineSeparator() +
                "Total water intake today: 100 ml";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListExercise_emptyList_printListAccurate() {
        testExerciseList.clear();
        testUser.handleListExercises();

        String expectedOutput = "here's the exercises you've done today" + System.lineSeparator()  +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void handleListExercise_validList_printListAccurate() {
        testUser.handleListExercises();

        String expectedOutput = "here's the exercises you've done today" + System.lineSeparator() +
                "1. swimming | duration: 20 | intensity: HIGH | date: 30-01-2024";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleListEverything_allEmptyLists_printListAccurate() {
        testMealList.clear();
        testDrinkList.clear();
        testExerciseList.clear();
        testUser.handleListEverything();

        String expectedOutput = "here's what you have consumed today" + System.lineSeparator() +
                "  >> nothing so far :o" + System.lineSeparator() + System.lineSeparator() +
                "Total water intake today: 100 ml" + System.lineSeparator() + "       ~~~" + System.lineSeparator() +
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
                "Total water intake today: 100 ml" + System.lineSeparator() + "       ~~~" + System.lineSeparator() +
                "here's the exercises you've done today" + System.lineSeparator() +
                "1. swimming | duration: 20 | intensity: HIGH | date: 30-01-2024";
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

        int drinkIndex = 0;
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
    public void handleDeleteExercise_validCommand_deleteMealSuccessful() throws InvalidListIndexException,
            IncompleteDeleteException {
        String command = "deleteExercise 1";
        testUser.handleDeleteExercise(command);
        assertEquals(0, testExerciseList.size());
    }

    @Test
    public void handleClear_validCommand_clearListsSuccessful() {
        testUser.handleClear();
        assertEquals(0, testMealList.size());
        assertEquals(0, testDrinkList.size());
    }
}
