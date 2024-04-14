package seedu.fitnus.drink;

import seedu.fitnus.date.Date;
import seedu.fitnus.exception.FutureDateException;
import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteEditWaterException;
import seedu.fitnus.exception.InvalidDateException;
import seedu.fitnus.exception.InvalidEditWaterException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.NonPositiveValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.parser.Parser;

import java.text.ParseException;
import java.util.ArrayList;

public class DrinkList {
    public static ArrayList<Drink> drinkListAll;
    public static ArrayList<Water> waterListAll;
    public static ArrayList<Drink> drinkList;
    public static ArrayList<Water> waterList;

    public DrinkList() {
        drinkList = new ArrayList<>();
        waterList = new ArrayList<>();
        drinkListAll = new ArrayList<>();
        waterListAll = new ArrayList<>();
    }

    /**
     * Adds a drink to available drinks
     *
     * @param command string inputted by the user, containing the drink they want to add to available drinks and
     *                its nutrient details
     * @throws NegativeValueException if the nutrient detail is a negative value
     */
    public void handleAddNewDrinkNutrient(String command) throws NegativeValueException {
        Parser.parseNewDrink(command);
        String description = Parser.drinkNutrientDescription;
        int calories = Parser.drinkNutrientCalories;
        int carbs = Parser.drinkNutrientCarbs;
        int sugar = Parser.drinkNutrientSugar;
        int protein = Parser.drinkNutrientProtein;
        int fat = Parser.drinkNutrientFat;
        Drink.nutrientDetails.put(description, new int[]{calories, carbs, sugar, protein, fat});

        System.out.println("Added " + description + " to available drinks.");
    }

    /**
     * Adds a drink to the user's current drinkList, based on what the user has drank and the serving size consumed.
     *
     * @param command string inputted by the user, containing the drink they consumed and its serving size
     * @throws IncompleteDrinkException if the user did not comply with the required format
     * @throws UnregisteredDrinkException if the user has inputted a drink that was not pre-defined
     * @throws NonPositiveValueException if the provided serving size is a negative value
     */
    public void handleDrink(String command) throws IncompleteDrinkException, UnregisteredDrinkException,
            NonPositiveValueException {
        Parser.parseDrink(command);
        String drinkName = Parser.drinkDescription;
        int servingSize = Parser.drinkSize;

        Date currentDate = new Date();

        boolean waterExist = false; //Water intake for today does not exist flag
        if (drinkName.equals("water")) {
            for (Water water: waterList) {
                if (currentDate.getDate().equals(water.getDate())) {
                    water.addWaterIntake(servingSize);
                    waterExist = true;
                }
            }
            if (!waterExist) {
                waterList.add(new Water(servingSize, currentDate.getDate()));
            }
        } else {
            drinkList.add(new Drink(drinkName, servingSize, currentDate.getDate()));
        }
        System.out.println("Added " + servingSize + " ml of " + drinkName);
    }

    /**
     * Prints the user's total water intake of the day.
     */
    public void handleViewWaterIntake() {
        int waterIntake = 0;
        for (Water water: waterList) {
            waterIntake += water.getWater();
        }
        System.out.println("Total water intake today: " + waterIntake + " ml");
    }

    /**
     * Prints all the drinks in the drinkListToPrint,
     * inclusive of the volume consumed and date.
     *
     * @param startIndex starting integer value when printing the list, where startIndex >= 1
     * @param drinkListToPrint arraylist containing the drinks that should be printed
     */
    //@@author edwardhumi
    public void printDrinkList(int startIndex, ArrayList<Drink> drinkListToPrint) {
        for (int i = 0; i < drinkListToPrint.size(); i++) {
            Drink currentDrink = drinkListToPrint.get(i);
            System.out.println((startIndex+i) + ". " + currentDrink.getName() + " (volume: "
                    + currentDrink.getDrinkVolumeSize() + "ml)" + " | date: " + currentDrink.getDate());
        }
    }

    /**
     * Handles when the user is listing the drinks they have consumed today.
     * Method first checks if the list is empty.
     */
    public void handleListDrinks() {
        System.out.println("here's what you have drank today");
        int totalWater = 0;
        for (Water water : waterList) {
            totalWater += water.getWater();
        }
        if (drinkList.isEmpty() && totalWater == 0) {
            System.out.println("  >> nothing so far :o");
        } else if (drinkList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
            handleViewWaterIntake();
        } else {
            printDrinkList(1, drinkList);
            System.out.println();
            handleViewWaterIntake();
        }
    }

    /**
     * Handles when the user is listing all drinks they have consumed, inclusive of previously saved drinks.
     * Method first checks if the list is empty.
     */
    //@@author edwardhumi
    public void handleListDrinksAll() {
        System.out.println("here's what you have drank so far");
        int totalWater = 0;
        for (Water water : waterList) {
            totalWater += water.getWater();
        }
        if (drinkListAll.isEmpty() && drinkList.isEmpty() && totalWater == 0) {
            System.out.println("  >> nothing so far :o");
        } else if (drinkListAll.isEmpty() && drinkList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
            handleViewWaterIntake();
        } else {
            printDrinkList(1, drinkListAll);
            printDrinkList(1 + drinkListAll.size(), drinkList);
            System.out.println();
            handleViewWaterIntake();
        }
    }

    /**
     * Handles when the user is listing the drinks they have consumed on a certain date.
     * Method will first extract all drinks that have this corresponding date,
     * before printing.
     *
     * @param command string inputted by the user, containing the date of which they would like to list drinks of
     * @throws InvalidDateException if the date inputted by user is invalid
     */
    //@@author edwardhumi
    public void handleListDrinksDate(String command) throws InvalidDateException, FutureDateException, ParseException {
        String date = Parser.parseListDate(command);
        ArrayList<Drink> drinkListDate = new ArrayList<>();
        for (Drink d : drinkListAll) {
            if (d.getDate().equals(date)) {
                drinkListDate.add(d);
            }
        }
        for (Drink d : drinkList) {
            if (d.getDate().equals(date)) {
                drinkListDate.add(d);
            }
        }
        System.out.println("here's what you have drank on " + date);
        if (drinkListDate.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            printDrinkList(1, drinkListDate);
        }
    }

    /**
     * Handles when the user would like to edit the serving size of a previously inputted drink.
     *
     * @param command string inputted by the user, containing the index of the drink to edit and the new serving size
     * @throws InvalidListIndexException if the provided index is not a valid index in drinkList
     * @throws NonPositiveValueException if the provided serving size is a negative value
     * @throws IncompleteEditException if the user did not comply with the required command format
     */
    //@@author claribelho
    public void handleEditDrinkServingSize(String command) throws InvalidListIndexException,
            NonPositiveValueException, IncompleteEditException {
        Parser.parseEditDrink(command);
        if (Parser.editDrinkIndex >= drinkList.size() || Parser.editDrinkIndex < 0) {
            throw new InvalidListIndexException();
        }
        String drinkName = drinkList.get(Parser.editDrinkIndex).getName();
        String drinkDate = drinkList.get(Parser.editDrinkIndex).getDate();

        Drink updatedDrink = new Drink(drinkName, Parser.editDrinkSize, drinkDate);
        drinkList.set(Parser.editDrinkIndex, updatedDrink);
        System.out.println(drinkName + " has been edited to " + Parser.editDrinkSize + " ml.");
    }


    /**
     * Handles when the user would like to edit the total volume of the water they consumed today.
     *
     * @param command string inputted by the user, containing the new total volume of water
     * @throws NonPositiveValueException if the provided serving size is a negative value
     * @throws IncompleteEditException if the user did not comply with the required command format
     */
    public void handleEditWaterIntake(String command) throws NonPositiveValueException, IncompleteEditWaterException,
            InvalidEditWaterException {
        Parser.parseEditWater(command);
        Date currentDate = new Date();
        for (Water water: waterList) {
            if (water.getDate().equals(currentDate.getDate())) {
                water.editWaterIntake(Parser.editWaterSize);
            }
        }
        if (waterList.isEmpty()) {
            throw new InvalidEditWaterException();
        }
        System.out.println("Total water intake has been edited to " + Parser.editWaterSize + " ml.");
    }

    /**
     * Handles when the user would like to delete a previously inputted drink.
     *
     * @param command string inputted by the user, containing the index of the drink to delete
     * @throws InvalidListIndexException if the provided index is not a valid index in drinkList
     * @throws IncompleteDeleteException if the user did not comply with the required command format
     */
    //@@author claribelho
    public void handleDeleteDrink(String command) throws InvalidListIndexException, IncompleteDeleteException {
        if (command.length() < 13) {
            throw new IncompleteDeleteException();
        }

        int drinkIndex = Integer.parseInt(command.substring(12).trim()) - 1;
        if (drinkIndex >= drinkList.size() || drinkIndex < 0) {
            throw new InvalidListIndexException();
        }

        String drinkName = drinkList.get(drinkIndex).getName();
        drinkList.remove(drinkIndex);
        System.out.println("Removed " + drinkName + " from drinks.");
    }


}
