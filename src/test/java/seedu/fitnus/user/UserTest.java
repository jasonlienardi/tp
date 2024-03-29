package seedu.fitnus.user;

import seedu.fitnus.Drink;
import seedu.fitnus.Exercise;
import seedu.fitnus.Meal;

import seedu.fitnus.Water;

import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidServingSizeException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.exception.invalidIndexException;
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
    ArrayList<Meal> testMealList;
    ArrayList<Drink> testDrinkList;
    ArrayList<Exercise> testExerciseList;
    private Storage testMealStorage;
    private Storage testDrinkStorage;


    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        testMealStorage = new Storage("./src/test/resources", "src/test/resources/MealList.txt");
        testDrinkStorage = new Storage("./src/test/resources", "src/test/resources/DrinkList.txt");
        testUser = new User(testMealStorage, testDrinkStorage);

        testMealList = testUser.mealList;
        testDrinkList = testUser.drinkList;

        testMealList.add(new Meal("pizza", 4, "28-03-2024"));
        testMealList.add(new Meal("chicken rice", 10, "28-03-2024"));
        testDrinkList.add(new Drink("sprite", 100, "28-03-2024"));

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleMeal_validInputs_correctlyAddMeal() throws IncompleteMealException, UnregisteredMealException, InvalidServingSizeException {
        String command = "ate m/pizza s/3";
        testUser.handleMeal(command);

        assertFalse(testMealList.isEmpty());

        assertEquals("pizza", testMealList.get(2).getName());
        assertEquals(3, testMealList.get(2).getServingSize());
    }

    @Test
    public void handleDrink_validInputs_correctlyAddDrink() throws IncompleteDrinkException, UnregisteredDrinkException,
            InvalidServingSizeException {
        String command = "drink d/sprite s/500";
        testUser.handleDrink(command);

        assertEquals("sprite", testDrinkList.get(1).getName());
        assertEquals(500, testDrinkList.get(1).getDrinkVolumeSize());
    }

    @Test
    public void handleViewCalories_correctCalorieCalculation_viewCaloriesAccurate() {
        testUser.handleViewCalories();
        String expectedOutput = "Total Calories: 6440";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewCarbohydrates_correctCarbsCalculation_viewCarbsAccurate() {
        testUser.handleViewCarbohydrates();
        String expectedOutput = "Total Carbohydrates: 830";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewProtein_correctProteinCalculation_viewProteinAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewProteins();
        String expectedOutput = "Total Proteins: 400";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewWaterIntake_correctWaterCalculation_viewWaterAccurate() {
        Water.getInstance(500, "28-04-2024");

        testUser.handleViewWaterIntake();
        String expectedOutput = "Total water intake: 500";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewFiber_correctFiberCalculation_viewFiberAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFiber();
        String expectedOutput = "Total Fiber: 220";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewFat_correctFatCalculation_viewFatAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewFat();
        String expectedOutput = "Total Fat: 302";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleViewSugar_correctSugarCalculation_viewSugarAccurate() {
        System.setOut(new PrintStream(outputStream));

        testUser.handleViewSugar();
        String expectedOutput = "Total Sugar: 88";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleListMeals_emptyList_printListAccurate() {
        testMealList.clear();
        testUser.handleListMeals();

        String expectedOutput = "here's what you have eaten today\r\n" +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleListMeals_validList_printListAccurate() {
        testUser.handleListMeals();

        String expectedOutput = "here's what you have eaten today\r\n" +
                "1. pizza (serving size: 4)\r\n" +
                "2. chicken rice (serving size: 10)";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleListDrinks_emptyList_printListAccurate() {
        testDrinkList.clear();
        testUser.handleListDrinks();

        String expectedOutput = "here's what you have drank today\r\n" +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }


    @Test
    public void handleListDrinks_validList_printListAccurate() {
        testUser.handleListDrinks();

        String expectedOutput = "here's what you have drank today\r\n" +
                "1. sprite (volume: 100ml)\r\n\r\n" +
                "Total water intake: 0 ml";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleListEverything_bothEmptyLists_printListAccurate() {
        testMealList.clear();
        testDrinkList.clear();
        testUser.handleListEverything();

        String expectedOutput = "here's what you have consumed today\r\n" +
                "  >> nothing so far :o\r\n\r\n" +
                "Total water intake: 0 ml";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleListEverything_validList_printListAccurate() {
        testUser.handleListEverything();

        String expectedOutput = "here's what you have consumed today\r\n" +
                "1. pizza (serving size: 4)\r\n" +
                "2. chicken rice (serving size: 10)\r\n" +
                "3. sprite (volume: 100ml)\r\n\r\n" +
                "Total water intake: 0 ml";
        String actualOutput = outputStream.toString().trim();
        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void handleEditMealServingSize_invalidMealIndex_exceptionThrown() throws invalidIndexException,
            InvalidServingSizeException {
        Exception exception = assertThrows(invalidIndexException.class, () -> {
            String command = "editMeal 5 s/10";
            testUser.handleEditMealServingSize(command);
        });
    }

    @Test
    public void handleEditMealServingSize_validCommand_editMealSuccessful() throws invalidIndexException,
            InvalidServingSizeException {
        String command = "editMeal 2 s/100000000";
        testUser.handleEditMealServingSize(command);

        int mealIndex = 2 - 1;
        assertEquals("chicken rice", testMealList.get(mealIndex).getName());
        assertEquals(100000000, testMealList.get(mealIndex).getServingSize());
    }

    @Test
    public void handleEditDrinkServingSize_validCommand_editDrinkSuccessful() throws invalidIndexException,
            InvalidServingSizeException {
        String command = "editDrink 1 s/100000000";
        testUser.handleEditDrinkServingSize(command);

        int drinkIndex = 1 - 1;
        assertEquals("sprite", testDrinkList.get(drinkIndex).getName());
        assertEquals(100000000, testDrinkList.get(drinkIndex).getDrinkVolumeSize());
    }

    @Test
    public void handleDeleteMeal_validCommand_deleteMealSuccessful() throws invalidIndexException {
        String command = "deleteMeal 1";
        testUser.handleDeleteMeal(command);
        assertEquals(1, testMealList.size());
        assertEquals("chicken rice", testMealList.get(0).getName());
    }

    @Test
    public void handleDeleteDrink_invalidDrinkIndex_exceptionThrown() throws invalidIndexException {
        Exception exception = assertThrows(invalidIndexException.class, () -> {
            String command = "deleteDrink 5";
            testUser.handleDeleteDrink(command);
        });
    }

    @Test
    public void handleDeleteDrink_validCommand_deleteDrinkSuccessful() throws invalidIndexException {
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

//    @Test
//    public void handleDrink_unknownServingSize_addDrinkFailed() throws IncompleteDrinkException, UnregisteredDrinkException,
//            InvalidServingSizeException {
//
//        Exception exception = assertThrows(IncompleteDrinkException.class, () -> {
//            String command = "drink d/sprite";
//            testUser.handleDrink(command);
//        });
//
//        String error = "Incomplete command, the format must be [drink d/DRINK s/SERVING_SIZE].";
//        String actualMessage = exception.getMessage();
//
//        assertEquals(error, actualMessage);
//    }
//
//    @Test
//    public void handleDrink_invalidServingSize_addDrinkFailed() throws IncompleteDrinkException, UnregisteredDrinkException,
//            InvalidServingSizeException {
//
//        Exception exception = assertThrows(NumberFormatException.class, () -> {
//            String command = "drink d/sprite s/null";
//            testUser.handleDrink(command);
//        });
//
//        String expectedMessage = "An integer value is expected, try again please :)";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
}
