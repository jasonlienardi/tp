package seedu.fitnus.parser;

import org.junit.jupiter.api.Test;
import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.UnregisteredDrinkException;

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
    public void parseDrink_validInputs_success() throws IncompleteDrinkException, UnregisteredDrinkException,
            NegativeValueException {
        String command = "drink d/sprite s/300";
        Parser.parseDrink(command);
        assertEquals("sprite", Parser.drinkDescription);
        assertEquals(300, Parser.drinkSize);
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
}
