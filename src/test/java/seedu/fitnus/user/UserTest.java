package seedu.fitnus.user;

import seedu.fitnus.Drink;
import seedu.fitnus.Exercise;
import seedu.fitnus.Meal;

import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidServingSizeException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredMealException;

import seedu.fitnus.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

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

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleMeal_validInputs_correctlyAddMeal() throws IncompleteMealException, UnregisteredMealException, InvalidServingSizeException {
        String command = "ate m/pizza s/3";
        testUser.handleMeal(command);

        assertFalse(testMealList.isEmpty());

        assertEquals("pizza", testMealList.get(0).getName());
        assertEquals(3, testMealList.get(0).getServingSize());
    }

    @Test
    public void handleDrink_validInputs_correctlyAddDrink() throws IncompleteDrinkException, UnregisteredDrinkException,
            InvalidServingSizeException {
        String command = "drink d/sprite s/500";
        testUser.handleDrink(command);

        assertEquals("sprite", testDrinkList.get(0).getName());
        assertEquals(500, testDrinkList.get(0).getDrinkVolumeSize());
    }

    @Test
    public void handleViewCalories_correctCalorieCalculation_viewCaloriesAccurate() {
        System.setOut(new PrintStream(outputStream));

        testMealList.add(new Meal("pizza", 4, "28-03-2024"));
        testMealList.add(new Meal("chicken rice", 10, "28-03-2024"));
        testDrinkList.add(new Drink("sprite", 100, "28-03-2024"));

        testUser.handleViewCalories();

        String expectedOutput = "Total Calories: 6440";
        String actualOutput = outputStream.toString().trim();

        assertTrue(actualOutput.contains(expectedOutput));
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
