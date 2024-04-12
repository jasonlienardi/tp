package seedu.fitnus.parser;

import org.junit.jupiter.api.Test;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteExerciseException;
import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.InvalidDateException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.NonPositiveValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.exercise.ExerciseIntensity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ParserTest {
    @Test
    public void parseInfoMeal_unregisteredMeal_exceptionThrown() throws IncompleteInfoException {
        String command = "infoMeal blablabla";
        try {
            Parser.parseInfoMeal(command);
        } catch (UnregisteredMealException e) {
            assertTrue(true);
        }
    }

    @Test
    public void parseInfoExercise_unregisteredExercise_exceptionThrown() throws IncompleteInfoException {
        String command = "infoExercise blabla";
        try {
            String infoExercise = Parser.parseInfoExercise(command);
        } catch (UnregisteredExerciseException e) {
            assertTrue(true);
        }
    }

    @Test
    public void parseInfoDrink_unregisteredDrink_exceptionThrown() throws IncompleteInfoException {
        String command = "infoDrink blabla";
        try {
            String infoDrink = Parser.parseInfoDrink(command);
        } catch (UnregisteredDrinkException e) {
            assertTrue(true);
        }
    }

    @Test
    public void parseEditMeal_validInputs_success() throws IncompleteEditException, NonPositiveValueException {
        String command = "editMeal 3 s/120";
        Parser.parseEditMeal(command);
        // Meal list starts from 1, however the array index starts from 0, hence the n - 1
        assertEquals(2, Parser.editMealIndex);
        assertEquals(120, Parser.editMealSize);
    }

    @Test
    public void parseEditDrink_validInputs_success() throws IncompleteEditException, NonPositiveValueException {
        String command = "editDrink 1 s/500";
        Parser.parseEditDrink(command);
        // Drink list starts from 1, however the array index starts from 0, hence the n - 1
        assertEquals(0, Parser.editDrinkIndex);
        assertEquals(500, Parser.editDrinkSize);
    }

    @Test
    public void parseEditDrink_unregisteredMeal_exceptionThrown() throws NonPositiveValueException {
        String command = "editDrink s/100";
        try {
            Parser.parseEditDrink(command);
        } catch (IncompleteEditException e) {
            assertTrue(true);
        }
    }

    @Test
    public void parseEditWater_validInputs_success() throws IncompleteEditException, NonPositiveValueException {
        String command = "editWater s/500";
        Parser.parseEditWater(command);
        assertEquals(500, Parser.editWaterSize);
    }

    @Test
    public void parseMealStorage_validInputs_success() {
        String data = "chicken rice,1,12-02-2024";
        Parser.parseMealStorage(data);
        assertEquals("chicken rice", Parser.mealStorageDescription);
        assertEquals(1, Parser.mealStorageSize);
        assertEquals("12-02-2024", Parser.mealStorageDate);
    }

    @Test
    public void parseDrinkStorage_validInputs_success() {
        String data = "milo,200,12-02-2024";
        Parser.parseDrinkStorage(data);
        assertEquals("milo", Parser.drinkStorageDescription);
        assertEquals(200, Parser.drinkStorageSize);
        assertEquals("12-02-2024", Parser.drinkStorageDate);
    }

    @Test
    public void parseExerciseStorage_validInputs_success() {
        String data = "cycling,100,LOW,29-02-2024";
        Parser.parseExerciseStorage(data);
        assertEquals("cycling", Parser.exerciseStorageDescription);
        assertEquals(100, Parser.exerciseStorageDuration);
        assertEquals(ExerciseIntensity.LOW, Parser.exerciseStorageIntensity);
        assertEquals("29-02-2024", Parser.exerciseStorageDate);
    }

    @Test
    public void parseExercise_validInputs_success() throws IncompleteExerciseException, NonPositiveValueException,
            UnregisteredExerciseException {
        String command = "exercise e/cycling d/100 i/LOW";
        Parser.parseExercise(command);
        assertEquals("cycling", Parser.exerciseDescription);
        assertEquals(100, Parser.exerciseDuration);
        assertEquals(ExerciseIntensity.LOW, Parser.exerciseIntensity);
    }

    @Test
    public void parseMealNutrient_validInputs_success() throws NegativeValueException {
        String data = "fried rice,100,10,9,8,7,6";
        Parser.parseMealNutrient(data);
        assertEquals("fried rice", Parser.mealNutrientDescription);
        assertEquals(100, Parser.mealNutrientCalories);
        assertEquals(10, Parser.mealNutrientCarbs);
        assertEquals(9, Parser.mealNutrientProtein);
        assertEquals(8, Parser.mealNutrientFat);
        assertEquals(7, Parser.mealNutrientFiber);
        assertEquals(6, Parser.mealNutrientSugar);
    }

    @Test
    public void parseDrinkNutrient_validInputs_success() throws NegativeValueException {
        String data = "Guava Juice,143,38,10,9,5";
        Parser.parseDrinkNutrient(data);
        assertEquals("guava juice", Parser.drinkNutrientDescription);
        assertEquals(143, Parser.drinkNutrientCalories);
        assertEquals(38, Parser.drinkNutrientCarbs);
        assertEquals(10, Parser.drinkNutrientSugar);
        assertEquals(9, Parser.drinkNutrientProtein);
        assertEquals(5, Parser.drinkNutrientFat);
    }

    @Test
    public void parseExerciseCalories_validInputs_success() throws NonPositiveValueException {
        String data = "Running,14,10,7";
        Parser.parseExerciseCalories(data);
        assertEquals("running", Parser.exerciseCaloriesDescription);
        assertEquals(14, Parser.exerciseCaloriesHigh);
        assertEquals(10, Parser.exerciseCaloriesMedium);
        assertEquals(7, Parser.exerciseCaloriesLow);
    }

    @Test
    public void parseListDate_validInputs_success() throws InvalidDateException {
        String command = "listMeals d/12-02-2024";
        String date = Parser.parseListDate(command);
        assertEquals("12-02-2024", date);
    }
}
