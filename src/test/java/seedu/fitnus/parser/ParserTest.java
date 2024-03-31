package seedu.fitnus.parser;

import org.junit.jupiter.api.Test;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredMealException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ParserTest {
    @Test
    public void parseMeal_validInputs_success() throws IncompleteMealException, UnregisteredMealException,
            NegativeValueException {
        String command = "eat m/chicken rice s/1";
        Parser.parseMeal(command);
        assertEquals("chicken rice", Parser.mealDescription);
        assertEquals(1, Parser.mealSize);
    }

    @Test
    public void parseEditMeal_validInputs_success() throws IncompleteEditException, NegativeValueException {
        String command = "editMeal 3 s/120";
        Parser.parseEditMeal(command);
        // Meal list starts from 1, however the array index starts from 0, hence the n - 1
        assertEquals(2, Parser.editMealIndex);
        assertEquals(120, Parser.editMealSize);
    }

    @Test
    public void parseDrink_validInputs_success() throws IncompleteDrinkException, UnregisteredDrinkException,
            NegativeValueException {
        String command = "drink d/sprite s/300";
        Parser.parseDrink(command);
        assertEquals("sprite", Parser.drinkDescription);
        assertEquals(300, Parser.drinkSize);
    }

    @Test
    public void parseEditDrink_validInputs_success() throws IncompleteEditException, NegativeValueException {
        String command = "editDrink 1 s/500";
        Parser.parseEditDrink(command);
        // Drink list starts from 1, however the array index starts from 0, hence the n - 1
        assertEquals(0, Parser.editDrinkIndex);
        assertEquals(500, Parser.editDrinkSize);
    }

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
    public void parseEditDrink_unregisteredMeal_exceptionThrown() throws NegativeValueException {
        String command = "editDrink s/100";
        try {
            Parser.parseEditDrink(command);
        } catch (IncompleteEditException e) {
            assertTrue(true);
        }
    }

}
