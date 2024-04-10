package seedu.fitnus.user;

import seedu.fitnus.Date;
import seedu.fitnus.Drink;
import seedu.fitnus.exercise.Exercise;
import seedu.fitnus.Meal;
import seedu.fitnus.exercise.ExerciseList;
import seedu.fitnus.parser.Parser;
import seedu.fitnus.Water;

import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.exception.InvalidDateException;

import java.util.ArrayList;

/**
 * Handles all methods related to the user's meals, drinks and exercise.
 */
public class User {
    public static final int RECOMMEND_WATER_INTAKE = 2600;
    public static final int RECOMMEND_CALORIE_INTAKE = 2200;
    // list for today
    public static ArrayList<Meal> mealList;
    public static ArrayList<Drink> drinkList;
    public static ArrayList<Water> waterList;
    // list for all dates except today
    public static ArrayList<Meal> mealListAll;
    public static ArrayList<Drink> drinkListAll;
    public static ArrayList<Water> waterListAll;
    public static ExerciseList myExerciseList;

    public User() {
        mealList = new ArrayList<>();
        drinkList = new ArrayList<>();
        waterList = new ArrayList<>();
        mealListAll = new ArrayList<>();
        drinkListAll = new ArrayList<>();
        waterListAll = new ArrayList<>();

        myExerciseList = new ExerciseList();
    }

    public void handleAddNewMealNutrient(String command) throws NegativeValueException{
        Parser.parseNewMeal(command);
        String description = Parser.mealNutrientDescription;
        int calories = Parser.mealNutrientCalories;
        int carbs = Parser.mealNutrientCarbs;
        int protein = Parser.mealNutrientProtein;
        int fat = Parser.mealNutrientFat;
        int fiber = Parser.mealNutrientFiber;
        int sugar = Parser.mealNutrientSugar;
        Meal.nutrientDetails.put(description, new int[]{calories, carbs, protein, fat, fiber, sugar});

        System.out.println("Added " + description + " to available meals");
    }

    public void handleAddNewDrinkNutrient(String command) throws NegativeValueException{
        Parser.parseNewDrink(command);
        String description = Parser.drinkNutrientDescription;
        int calories = Parser.drinkNutrientCalories;
        int carbs = Parser.drinkNutrientCarbs;
        int sugar = Parser.drinkNutrientSugar;
        int protein = Parser.drinkNutrientProtein;
        int fat = Parser.drinkNutrientFat;
        Drink.nutrientDetails.put(description, new int[]{calories, carbs, sugar, protein, fat});

        System.out.println("Added " + description + " to available drinks");
    }

    /**
     * Adds a meal to the user's current mealList, based on what the user has eaten and the serving size consumed.
     *
     * @param command string inputted by the user, containing the meal they ate and its serving size
     * @throws IncompleteMealException if the user did not comply with the required format
     * @throws UnregisteredMealException if the user has inputted a meal that was not pre-defined
     * @throws NegativeValueException if the provided serving size is a negative value
     */
    public void handleMeal(String command) throws IncompleteMealException, UnregisteredMealException,
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
     * Adds a drink to the user's current drinkList, based on what the user has drank and the serving size consumed.
     *
     * @param command string inputted by the user, containing the drink they consumed and its serving size
     * @throws IncompleteDrinkException if the user did not comply with the required format
     * @throws UnregisteredDrinkException if the user has inputted a drink that was not pre-defined
     * @throws NegativeValueException if the provided serving size is a negative value
     */
    public void handleDrink(String command) throws IncompleteDrinkException, UnregisteredDrinkException,
            NegativeValueException {
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
     * Prints the user's total calorie intake of the day.
     * The method sums up the calories from meals and drinks, and subtracts calories burnt from exercise.
     */
    public void handleViewCalories() {
        int caloriesCount = 0;
        for (Meal meal: mealList) {
            caloriesCount += meal.getCalories();
        }
        for (Drink drink: drinkList) {
            caloriesCount += drink.getCalories();
        }
        for (Exercise exercise: myExerciseList.exerciseList) {
            caloriesCount -= exercise.getCaloriesBurnt();
        }
        System.out.println("Total Calories: " + caloriesCount);
    }

    /**
     * Prints the user's total carbohydrate intake of the day.
     * The method sums up the carbohydrates from meals and drinks.
     */
    public void handleViewCarbohydrates() {
        int carbohydratesCount = 0;
        for (Meal meal: mealList) {
            carbohydratesCount += meal.getCarbs();
        }
        for (Drink drink: drinkList) {
            carbohydratesCount += drink.getCarbs();
        }
        System.out.println("Total Carbohydrates: " + carbohydratesCount + " grams");
    }

    /**
     * Prints the user's total protein intake of the day.
     * The method sums up the protein from meals and drinks.
     */
    public void handleViewProteins() {
        int proteinCount = 0;
        for (Meal meal: mealList) {
            proteinCount += meal.getProtein();
        }
        for (Drink drink: drinkList) {
            proteinCount += drink.getProtein();
        }
        System.out.println("Total Proteins: " + proteinCount + " grams");
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
     * Prints the user's total fiber intake of the day.
     * The method sums up the fiber from meals.
     */
    public void handleViewFiber() {
        int fibreCount = 0;
        for (Meal meal: mealList) {
            fibreCount += meal.getFiber();
        }
        System.out.println("Total Fiber: " + fibreCount + " grams");
    }

    /**
     * Prints the user's total fat intake of the day.
     * The method sums up the fat from meals and drinks.
     */
    public void handleViewFat() {
        int fatCount = 0;
        for (Meal meal: mealList) {
            fatCount += meal.getFat();
        }
        for (Drink drink: drinkList) {
            fatCount += drink.getFat();
        }
        System.out.println("Total Fat: " + fatCount + " grams");
    }

    /**
     * Prints the user's total sugar intake of the day.
     * The method sums up the sugar from meals and drinks.
     */
    public void handleViewSugar() {
        int sugarCount = 0;
        for (Meal meal: mealList) {
            sugarCount += meal.getSugar();
        }
        for (Drink drink: drinkList) {
            sugarCount += drink.getSugar();
        }
        System.out.println("Total Sugar: " + sugarCount + " grams");
    }

    /**
     * Prints all the meals in the mealListToPrint,
     * inclusive of the serving size and date.
     *
     * @param startIndex starting integer value when printing the list, where startIndex >= 1
     * @param mealListToPrint arraylist containing the meals that should be printed
     */
    public void printMealList(int startIndex, ArrayList<Meal> mealListToPrint) {
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
    public void handleListMeals() {
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
    public void handleListMealsAll() {
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
    public void handleListMealsDate(String command) throws InvalidDateException {
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
     * Prints all the drinks in the drinkListToPrint,
     * inclusive of the volume consumed and date.
     *
     * @param startIndex starting integer value when printing the list, where startIndex >= 1
     * @param drinkListToPrint arraylist containing the drinks that should be printed
     */
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
    public void handleListDrinksDate(String command) throws InvalidDateException {
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
     * Handles when the user is listing all meals and drinks they have inputted today.
     * Method first checks if the lists is empty.
     */
    public void handleListEverything() {
        System.out.println("here's what you have consumed today");
        if (drinkList.isEmpty() && mealList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
            System.out.println();
            handleViewWaterIntake();
        } else {
            printMealList(1, mealList);
            printDrinkList(mealList.size()+1, drinkList);
            System.out.println();
            handleViewWaterIntake();
        }

        System.out.println("       ~~~");
        myExerciseList.handleListExercises();
    }

    /**
     * Handles when the user is listing all meals and drinks they have inputted,
     * inclusive of previously saved meals and drinks.
     * Method first checks if the lists is empty.
     */
    public void handleListEverythingAll() {
        System.out.println("here's what you have consumed so far");
        if (drinkListAll.isEmpty() && mealListAll.isEmpty() && drinkList.isEmpty() && mealList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
            System.out.println();
            handleViewWaterIntake();
        } else {
            printMealList(1, mealListAll);
            printMealList(mealListAll.size() + 1, mealList);
            printDrinkList(mealListAll.size() + mealList.size() + 1, drinkListAll);
            printDrinkList(mealListAll.size() + mealList.size() + drinkListAll.size() + 1, drinkList);
            System.out.println();
            handleViewWaterIntake();
        }

        System.out.println("       ~~~");
        myExerciseList.handleListExercisesAll();
    }

    /**
     * Handles when the user is listing all meals and drinks they have consumed on a certain date.
     * Method will first extract all meals and drinks that have this corresponding date,
     * before printing.
     *
     * @param command string inputted by the user, containing the date of which they would like to list
     * @throws InvalidDateException if the date inputted by user is invalid
     */
    public void handleListEverythingDate(String command) throws InvalidDateException {
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

        System.out.println("here's what you have consumed on " + date);
        if (mealListDate.isEmpty() && drinkListDate.isEmpty()) {
            System.out.println("  >> nothing so far :o");
            System.out.println();
        } else {
            printMealList(1, mealListDate);
            printDrinkList(1 + mealListDate.size(), drinkListDate);
            System.out.println();
        }

        System.out.println("       ~~~");
        myExerciseList.handleListExercisesDate(command);
    }

    /**
     * Handles when the user would like to edit the serving size of a previously inputted meal.
     *
     * @param command string inputted by the user, containing the index of the meal to edit and the new serving size
     * @throws InvalidListIndexException if the provided index is not a valid index in mealList
     * @throws NegativeValueException if the provided serving size is a negative value
     * @throws IncompleteEditException if the user did not comply with the required command format
     */
    public void handleEditMealServingSize(String command) throws InvalidListIndexException,
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
     * Handles when the user would like to edit the serving size of a previously inputted drink.
     *
     * @param command string inputted by the user, containing the index of the drink to edit and the new serving size
     * @throws InvalidListIndexException if the provided index is not a valid index in drinkList
     * @throws NegativeValueException if the provided serving size is a negative value
     * @throws IncompleteEditException if the user did not comply with the required command format
     */
    public void handleEditDrinkServingSize(String command) throws InvalidListIndexException,
            NegativeValueException, IncompleteEditException {
        Parser.parseEditDrink(command);

        if (Parser.editDrinkIndex >= drinkList.size() || Parser.editDrinkIndex < 0) {
            throw new InvalidListIndexException();
        }
        String drinkName = drinkList.get(Parser.editDrinkIndex).getName();
        String drinkDate = drinkList.get(Parser.editDrinkIndex).getDate();

        Drink updatedDrink = new Drink(drinkName, Parser.editDrinkSize, drinkDate);
        drinkList.set(Parser.editDrinkIndex, updatedDrink);
        System.out.println(drinkName + " has been edited to " + Parser.editDrinkSize + " ml");
    }

    /**
     * Handles when the user would like to edit the total volume of the water they consumed today.
     *
     * @param command string inputted by the user, containing the new total volume of water
     * @throws NegativeValueException if the provided serving size is a negative value
     * @throws IncompleteEditException if the user did not comply with the required command format
     */
    public void handleEditWaterIntake(String command) throws NegativeValueException, IncompleteEditException {
        Parser.parseEditWater(command);
        Date currentDate = new Date();
        for (Water water: waterList) {
            if (water.getDate().equals(currentDate.getDate())) {
                water.editWaterIntake(Parser.editWaterSize);
            }
        }
        System.out.println("Total water intake has been edited to " + Parser.editWaterSize + " ml");
    }

    /**
     * Handles when the user would like to delete a previously inputted meal.
     *
     * @param command string inputted by the user, containing the index of the meal to delete
     * @throws InvalidListIndexException if the provided index is not a valid index in mealList
     * @throws IncompleteDeleteException if the user did not comply with the required command format
     */
    public void handleDeleteMeal(String command) throws InvalidListIndexException, IncompleteDeleteException {
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

    /**
     * Handles when the user would like to delete a previously inputted drink.
     *
     * @param command string inputted by the user, containing the index of the drink to delete
     * @throws InvalidListIndexException if the provided index is not a valid index in drinkList
     * @throws IncompleteDeleteException if the user did not comply with the required command format
     */
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
        System.out.println("Removed " + drinkName + " from drinks");
    }


    /**
     * Handle when user would like to clear all entries from today.
     * This includes all meals, drinks and exercise.
     */
    public void handleClear() {
        mealList.clear();
        drinkList.clear();
        waterList.clear();
        myExerciseList.exerciseList.clear();

        assert mealList.isEmpty(): "clearing of meal list failed";
        assert drinkList.isEmpty(): "clearing of drink list failed";
        assert myExerciseList.exerciseList.isEmpty(): "clearing of exercise list failed";

        System.out.println("All entries have been deleted");
    }

    /**
     * Handles when user would like to see what is recommended to them,
     * only regarding the calorie and water intake.
     */
    public void handleRecommendations() {
        int waterIntake = 0;
        for (Water water: waterList) {
            waterIntake += water.getWater();
        }
        int waterDifference = RECOMMEND_WATER_INTAKE -waterIntake;
        if (waterIntake < RECOMMEND_WATER_INTAKE) {
            System.out.println("We recommend drinking more water. Please drink " +
                    waterDifference + " ml more water to hit the daily water intake goal :)");
        } else {
            System.out.println("Great! You are on track with the water intake!");
        }
        System.out.println("    ~~");
        int caloriesCount = 0;
        for (Meal meal: mealList) {
            caloriesCount += meal.getCalories();
        }
        for (Drink drink: drinkList) {
            caloriesCount += drink.getCalories();
        }
        for (Exercise exercise: myExerciseList.exerciseList) {
            caloriesCount -= exercise.getCaloriesBurnt();
        }
        int caloriesDifference = RECOMMEND_CALORIE_INTAKE - caloriesCount;
        if (caloriesCount < RECOMMEND_CALORIE_INTAKE) {
            System.out.println("We recommend eating more food. Please eat " + caloriesDifference + " more calories");
        } else if (caloriesCount > RECOMMEND_CALORIE_INTAKE && caloriesCount < RECOMMEND_CALORIE_INTAKE + 200) {
            System.out.println("Great! You are on track with the calorie intake!");
        } else {
            System.out.println("You are " + -caloriesDifference
                    + " calories above the recommended calorie amount, consider exercising!");
        }
    }
}
