package seedu.fitnus.drink;

import seedu.fitnus.date.Date;

import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NonPositiveValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrinkListTest {
    DrinkList testerDrinkList;
    String todayDate;
    ArrayList<Drink> testDrinkList;
    ArrayList<Drink> testDrinkListAll;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        testerDrinkList = new DrinkList();

        testDrinkList = testerDrinkList.drinkList;
        testDrinkListAll = testerDrinkList.drinkListAll;

        Date currentDate = new Date();
        todayDate = currentDate.getDate();

        testDrinkList.add(new Drink("kopi", 100, todayDate));

        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void handleDrink_validInputs_correctlyAddDrink() throws IncompleteDrinkException,
            UnregisteredDrinkException, NonPositiveValueException {
        String command = "drink d/kopi s/500";
        testerDrinkList.handleDrink(command);

        assertEquals("kopi", testDrinkList.get(1).getName());
        assertEquals(500, testDrinkList.get(1).getDrinkVolumeSize());
    }

//    @Test
//    public void handleViewWaterIntake_correctWaterCalculation_viewWaterAccurate() {
//        testDrinkList.add(new Drink ("water", 500, todayDate));
//
//        testerDrinkList.handleViewWaterIntake();
//        String expectedOutput = "Total water intake today: 600 ml";
//        String actualOutput = outputStream.toString().trim();
//        assertEquals(actualOutput, expectedOutput);
//    }

    @Test
    public void handleListDrinks_emptyList_printListAccurate() {
        testDrinkList.clear();
        testerDrinkList.handleListDrinks();

        String expectedOutput = "here's what you have drank today" + System.lineSeparator()  +
                "  >> nothing so far :o";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    public void handleListDrinks_validList_printListAccurate() {
        testerDrinkList.handleListDrinks();

        String expectedOutput = "here's what you have drank today" + System.lineSeparator() +
                "1. kopi (volume: 100ml) | date: " + todayDate + System.lineSeparator() + System.lineSeparator() +
                "Total water intake today: 0 ml";
        String actualOutput = outputStream.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void handleEditDrinkServingSize_validCommand_editDrinkSuccessful() throws InvalidListIndexException,
            IncompleteEditException, NonPositiveValueException {
        String command = "editDrink 1 s/100000000";
        testerDrinkList.handleEditDrinkServingSize(command);

        int drinkIndex = 0;
        assertEquals("kopi", testDrinkList.get(drinkIndex).getName());
        assertEquals(100000000, testDrinkList.get(drinkIndex).getDrinkVolumeSize());
    }

    @Test
    public void handleDeleteDrink_invalidDrinkIndex_exceptionThrown() throws InvalidListIndexException {
        Exception exception = assertThrows(InvalidListIndexException.class, () -> {
            String command = "deleteDrink 5";
            testerDrinkList.handleDeleteDrink(command);
        });
    }

    @Test
    public void handleDeleteDrink_validCommand_deleteDrinkSuccessful() throws InvalidListIndexException,
            IncompleteDeleteException {
        String command = "deleteDrink 1";
        testerDrinkList.handleDeleteDrink(command);
        assertEquals(0, testDrinkList.size());
    }

}
