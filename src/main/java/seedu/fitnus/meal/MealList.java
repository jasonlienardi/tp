package seedu.fitnus.meal;

import seedu.fitnus.date.Date;
import seedu.fitnus.exception.FutureDateException;
import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidDateException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.parser.Parser;

import java.util.ArrayList;

public class MealList {
    public static ArrayList<Meal> mealList;
    // list for all dates except today
    public static ArrayList<Meal> mealListAll;

    public MealList() {
        mealList = new ArrayList<>();
        mealListAll = new ArrayList<>();
    }

    /**
     * Adds a meal to the user's current mealList, based on what the user has eaten and the serving size consumed.
     *
     * @param command string inputted by the user, containing the meal they ate and its serving size
     * @throws IncompleteMealException if the user did not comply with the required format
     * @throws UnregisteredMealException if the user has inputted a meal that was not pre-defined
     * @throws NegativeValueException if the provided serving size is a negative value
     */
    public static void handleMeal(String command) throws IncompleteMealException, UnregisteredMealException,
            NegativeValueException {
        Parser.parseMeal(command);
        String mealName = Parser.mealDescription;
        int servingSize = Parser.mealSize;

        Date currentDate = new Date();

        mealList.add(new Meal(mealName, servingSize, currentDate.getDate()));
        assert !mealList.isEmpty(): "failed to add meal";

        System.out.println("Added " + servingSize + " serving of " + mealName);
    }


    /**
     * Prints all the meals in the mealListToPrint,
     * inclusive of the serving size and date.
     *
     * @param startIndex starting integer value when printing the list, where startIndex >= 1
     * @param mealListToPrint arraylist containing the meals that should be printed
     */
    public static void printMealList(int startIndex, ArrayList<Meal> mealListToPrint) {
        for (int i = 0; i < mealListToPrint.size(); i++) {
            Meal currentMeal = mealListToPrint.get(i);
            System.out.println((startIndex+i) + ". " + currentMeal.getName() + " (serving size: "
                    + currentMeal.getServingSize() + ")" + " | date: " + currentMeal.getDate());
        }
    }

    /**
     * Handles when the user is listing the meals they have eaten today.
     * Method first checks if the list is empty.
     */
    public static void handleListMeals() {
        System.out.println("here's what you have eaten today");
        if (mealList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            printMealList(1, mealList);
        }
    }

    /**
     * Handles when the user is listing all meals they have eaten, inclusive of previously saved meals.
     * Method first checks if the list is empty.
     */
    //@@author edwardhumi
    public static void handleListMealsAll() {
        System.out.println("here's what you have eaten so far");
        if (mealListAll.isEmpty() && mealList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            printMealList(1, mealListAll);
            printMealList(1 + mealListAll.size(), mealList);
        }
    }

    /**
     * Handles when the user is listing the meals they have eaten on a certain date.
     * Method will first extract all meals that have this corresponding date,
     * before printing.
     *
     * @param command string inputted by the user, containing the date of which they would like to list meals of
     * @throws InvalidDateException if the date inputted by user is invalid
     */
    //@@author edwardhumi
    public static void handleListMealsDate(String command) throws InvalidDateException, FutureDateException {
        String date = Parser.parseListDate(command);
        ArrayList<Meal> mealListDate = new ArrayList<>();
        for (Meal m : mealListAll) {
            if (m.getDate().equals(date)) {
                mealListDate.add(m);
            }
        }
        for (Meal m : mealList) {
            if (m.getDate().equals(date)) {
                mealListDate.add(m);
            }
        }
        System.out.println("here's what you have eaten on " + date);
        if (mealListDate.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            printMealList(1, mealListDate);
        }
    }

    /**
     * Handles when the user would like to edit the serving size of a previously inputted meal.
     *
     * @param command string inputted by the user, containing the index of the meal to edit and the new serving size
     * @throws InvalidListIndexException if the provided index is not a valid index in mealList
     * @throws NegativeValueException if the provided serving size is a negative value
     * @throws IncompleteEditException if the user did not comply with the required command format
     */
    public static void handleEditMealServingSize(String command) throws InvalidListIndexException,
            NegativeValueException, IncompleteEditException {
        Parser.parseEditMeal(command); //Parser handles the index, so index can be = 0
        if (Parser.editMealIndex >= mealList.size() || Parser.editMealIndex < 0) {
            throw new InvalidListIndexException();
        }

        String mealName = mealList.get(Parser.editMealIndex).getName();
        String mealDate = mealList.get(Parser.editMealIndex).getDate();

        Meal updatedMeal = new Meal(mealName, Parser.editMealSize, mealDate);
        mealList.set(Parser.editMealIndex, updatedMeal);
        System.out.println(mealName + " has been edited to " + Parser.editMealSize + " serving(s)");
    }

    /**
     * Handles when the user would like to delete a previously inputted meal.
     *
     * @param command string inputted by the user, containing the index of the meal to delete
     * @throws InvalidListIndexException if the provided index is not a valid index in mealList
     * @throws IncompleteDeleteException if the user did not comply with the required command format
     */
    public static void handleDeleteMeal(String command) throws InvalidListIndexException, IncompleteDeleteException {
        if (command.length() < 12) {
            throw new IncompleteDeleteException();
        }
        int mealIndex = Integer.parseInt(command.substring(11).trim()) - 1;

        if (mealIndex >= mealList.size() || mealIndex < 0) {
            throw new InvalidListIndexException();
        }

        String mealName = mealList.get(mealIndex).getName();
        mealList.remove(mealIndex);
        System.out.println("Removed " + mealName + " from meals");
    }

}
