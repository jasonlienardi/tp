package seedu.fitnus.user;

import seedu.fitnus.Date;
import seedu.fitnus.Drink;
import seedu.fitnus.Exercise;
import seedu.fitnus.ExerciseIntensity;
import seedu.fitnus.Meal;
import seedu.fitnus.parser.Parser;
import seedu.fitnus.Water;
import seedu.fitnus.storage.Storage;

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
import seedu.fitnus.exception.InvalidDateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Handles all methods related to the user's meals, drinks and exercise.
 */
public class User {
    public static final int RECOMMEND_WATER_INTAKE = 2600;
    public static final int RECOMMEND_CALORIE_INTAKE = 2200;
    // list for today
    protected static ArrayList<Meal> mealList;
    protected static ArrayList<Drink> drinkList;
    protected static ArrayList<Water> waterList;
    protected static ArrayList<Exercise> exerciseList;
    // list for all dates except today
    protected static ArrayList<Meal> mealListAll;
    protected static ArrayList<Drink> drinkListAll;
    protected static ArrayList<Water> waterListAll;
    protected static ArrayList<Exercise> exerciseListAll;

    public User(Storage mealStorage, Storage drinkStorage, Storage exerciseStorage,
                Storage mealNutrientStorage, Storage drinkNutrientStorage, Storage exerciseCaloriesStorage) {
        try {
            mealList = new ArrayList<>();
            drinkList = new ArrayList<>();
            exerciseList = new ArrayList<>();
            waterList = new ArrayList<>();
            mealListAll = new ArrayList<>();
            drinkListAll = new ArrayList<>();
            exerciseListAll = new ArrayList<>();
            waterListAll = new ArrayList<>();
            loadMealNutrient(mealNutrientStorage);
            loadDrinkNutrient(drinkNutrientStorage);
            loadExerciseCalories(exerciseCaloriesStorage);
            loadMeal(mealStorage);
            loadDrink(drinkStorage);
            loadExercise(exerciseStorage);
        } catch (NegativeValueException e) {
            System.out.println("Nutrient details must be greater than 0");
        }
    }

    /**
     * Loads any previously stored mealList from the user and
     * adds the saved meals into the ArrayList mealListAll.
     *
     * @param mealStorage contains filePath and folderPath of where the saved meals are stored.
     */
    public void loadMeal(Storage mealStorage) {
        try {
            ArrayList<String> mealStorageList = mealStorage.readFile();
            if (!mealStorageList.isEmpty()) {
                for (String s : mealStorageList) {
                    Parser.parseMealStorage(s);
                    String mealDescription = Parser.mealStorageDescription;
                    int mealSize = Parser.mealStorageSize;
                    String currentDate = Parser.mealStorageDate;
                    mealListAll.add(new Meal(mealDescription, mealSize, currentDate));
                }
            }
            Date currentDate = new Date();
            String todayDate = currentDate.getDate();
            for (Meal m : mealListAll) {
                if (m.getDate().equals(todayDate)) {
                    mealList.add(m);
                }
            }
            mealListAll.removeAll(mealList);
        } catch (FileNotFoundException e) {
            mealStorage.createFile();
        }
    }

    /**
     * Loads any previously stored drinkList from the user and
     * adds the saved drinks into the ArrayList drinkListAll.
     *
     * @param drinkStorage contains filePath and folderPath of where the saved drinks are stored.
     */
    public void loadDrink(Storage drinkStorage) {
        try {
            ArrayList<String> drinkStorageList = drinkStorage.readFile();
            if (!drinkStorageList.isEmpty()) {
                for (String s : drinkStorageList) {
                    Parser.parseDrinkStorage(s);
                    String drinkDescription = Parser.drinkStorageDescription;
                    String drinkDate = Parser.drinkStorageDate;
                    int drinkSize = Parser.drinkStorageSize;
                    if (drinkDescription.equals("water")) {
                        waterListAll.add(new Water (drinkSize, drinkDate));
                    } else {
                        drinkListAll.add(new Drink(drinkDescription, drinkSize, drinkDate));
                    }
                }
            }
            Date currentDate = new Date();
            String todayDate = currentDate.getDate();
            for (Drink d : drinkListAll) {
                if (d.getDate().equals(todayDate)) {
                    drinkList.add(d);
                }
            }
            drinkListAll.removeAll(drinkList);
            for (Water w : waterListAll) {
                if (w.getDate().equals(todayDate)) {
                    waterList.add(w);
                }
            }
            waterListAll.removeAll(waterList);
        } catch (FileNotFoundException e) {
            drinkStorage.createFile();
        }
    }

    /**
     * Loads any previously stored exerciseList from the user and
     * adds the saved exercises into the ArrayList exerciseListAll.
     *
     * @param exerciseStorage contains filePath and folderPath of where the saved exerciseList was stored.
     */
    public void loadExercise(Storage exerciseStorage) {
        try {
            ArrayList<String> exerciseStorageList = exerciseStorage.readFile();
            if (!exerciseStorageList.isEmpty()) {
                for (String s : exerciseStorageList) {
                    Parser.parseExerciseStorage(s);
                    String exerciseDescription = Parser.exerciseStorageDescription;
                    int exerciseDuration = Parser.exerciseStorageDuration;
                    ExerciseIntensity exerciseIntensity = Parser.exerciseStorageIntensity;
                    String currentDate = Parser.exerciseStorageDate;
                    exerciseListAll.add(new Exercise(exerciseDescription, exerciseDuration, exerciseIntensity,
                            currentDate));
                }
            }
            Date currentDate = new Date();
            String todayDate = currentDate.getDate();
            for (Exercise e : exerciseListAll) {
                if (e.getDate().equals(todayDate)) {
                    exerciseList.add(e);
                }
            }
            exerciseListAll.removeAll(exerciseList);
        } catch (FileNotFoundException e) {
            exerciseStorage.createFile();
        } catch (UnregisteredExerciseException e) {
            System.out.println("Sorry that meal is not registered in the database. Please check the spelling and " +
                    "try again");
        }
    }

    /**
     * Loads the file where pre-defined meals and their nutrient counts are stored.
     *
     * @param mealNutrientStorage contains filePath and folderPath of where the pre-defined meals are stored.
     */
    public void loadMealNutrient(Storage mealNutrientStorage) throws NegativeValueException{
        try {
            ArrayList<String> mealNutrientList = mealNutrientStorage.readFile();
            if (!mealNutrientList.isEmpty()) {
                for (String s : mealNutrientList) {
                    Parser.parseMealNutrient(s);
                    String description = Parser.mealNutrientDescription;
                    int calories = Parser.mealNutrientCalories;
                    int carbs = Parser.mealNutrientCarbs;
                    int protein = Parser.mealNutrientProtein;
                    int fat = Parser.mealNutrientFat;
                    int fiber = Parser.mealNutrientFiber;
                    int sugar = Parser.mealNutrientSugar;
                    Meal.nutrientDetails.put(description, new int[]{calories, carbs, protein, fat, fiber, sugar});
                }
            }
        } catch (FileNotFoundException e) {
            mealNutrientStorage.createFile();
        }
    }

    /**
     * Loads the file where pre-defined drinks and their nutrient counts are stored.
     *
     * @param drinkNutrientStorage contains filePath and folderPath of where the pre-defined drinks are stored.
     */
    public void loadDrinkNutrient(Storage drinkNutrientStorage) throws NegativeValueException{
        try {
            ArrayList<String> drinkNutrientList = drinkNutrientStorage.readFile();
            if (!drinkNutrientList.isEmpty()) {
                for (String s : drinkNutrientList) {
                    Parser.parseDrinkNutrient(s);
                    String description = Parser.drinkNutrientDescription;
                    int calories = Parser.drinkNutrientCalories;
                    int carbs = Parser.drinkNutrientCarbs;
                    int sugar = Parser.drinkNutrientSugar;
                    int protein = Parser.drinkNutrientProtein;
                    int fat = Parser.drinkNutrientFat;
                    Drink.nutrientDetails.put(description, new int[]{calories, carbs, sugar, protein, fat});
                }
            }
        } catch (FileNotFoundException e) {
            drinkNutrientStorage.createFile();
        }
    }

    /**
     * Loads the file where pre-defined exercises and the number of calories burnt per minute for
     * respective intensities (HIGH, MEDIUM, LOW) are stored.
     *
     * @param exerciseCaloriesStorage contains filePath and folderPath of where the pre-defined exercises are stored.
     */
    public void loadExerciseCalories(Storage exerciseCaloriesStorage) {
        try {
            ArrayList<String> exerciseCaloriesList = exerciseCaloriesStorage.readFile();
            if (!exerciseCaloriesList.isEmpty()) {
                for (String s : exerciseCaloriesList) {
                    Parser.parseExerciseCalories(s);
                    String description = Parser.exerciseCaloriesDescription;
                    int caloriesHigh = Parser.exerciseCaloriesHigh;
                    int caloriesMedium = Parser.exerciseCaloriesMedium;
                    int caloriesLow = Parser.exerciseCaloriesLow;
                    Exercise.exerciseDetails.put(description, new int[]{caloriesHigh, caloriesMedium, caloriesLow});
                }
            }
        } catch (FileNotFoundException e) {
            exerciseCaloriesStorage.createFile();
        }
    }

    /**
     * Saves the user's meals when the user exits the program.
     * Meals from the current day is saved with the date into the .txt file.
     *
     * @param mealStorage contains filePath and folderPath of where the saved meals are stored.
     */
    public void saveMeal(Storage mealStorage) {
        for (Meal meal : mealListAll) {
            String mealSavedData = meal.getName() + "," + meal.getServingSize() + "," + meal.getDate();
            mealStorage.appendTextContent(mealSavedData);
        }
        for (Meal meal : mealList) {
            String mealSavedData = meal.getName() + "," + meal.getServingSize() + "," + meal.getDate();
            mealStorage.appendTextContent(mealSavedData);
        }
        try {
            mealStorage.writeFile(mealStorage.textContent);
        } catch (IOException e) {
            System.out.println("Failed saving meal: " + e.getMessage());
        }
    }

    /**
     * Saves the user's drinks when the user exits the program.
     * MealDrinkss from the current day is saved with the date into the .txt file.
     *
     * @param drinkStorage contains filePath and folderPath of where the saved drinksList are stored.
     */
    public void saveDrink(Storage drinkStorage) {
        for (Water water : waterListAll) {
            String waterSavedData = "water" + "," + water.getWater() + "," + water.getDate();
            drinkStorage.appendTextContent(waterSavedData);
        }
        for (Water water : waterList) {
            String waterSavedData = "water" + "," + water.getWater() + "," + water.getDate();
            drinkStorage.appendTextContent(waterSavedData);
        }
        for (Drink drink : drinkListAll) {
            String drinkSavedData = drink.getName() + "," + drink.getDrinkVolumeSize() + "," + drink.getDate();
            drinkStorage.appendTextContent(drinkSavedData);
        }
        for (Drink drink : drinkList) {
            String drinkSavedData = drink.getName() + "," + drink.getDrinkVolumeSize() + "," + drink.getDate();
            drinkStorage.appendTextContent(drinkSavedData);
        }
        try {
            drinkStorage.writeFile(drinkStorage.textContent);
        } catch (IOException e) {
            System.out.println("Failed saving drink: " + e.getMessage());
        }
    }

    /**
     * Saves the user's exercises when the user exits the program.
     * Exercises from the current day is saved with the date into the .txt file.
     *
     * @param exerciseStorage contains filePath and folderPath of where the saved exerciseList are stored.
     */
    public void saveExercise(Storage exerciseStorage) {
        for (Exercise exercise : exerciseListAll) {
            String exerciseSavedData = exercise.getName() + "," + exercise.getDuration() + ","
                    + exercise.getIntensity() + "," + exercise.getDate();
            exerciseStorage.appendTextContent(exerciseSavedData);
        }
        for (Exercise exercise : exerciseList) {
            String exerciseSavedData = exercise.getName() + "," + exercise.getDuration() + ","
                    + exercise.getIntensity() + "," + exercise.getDate();
            exerciseStorage.appendTextContent(exerciseSavedData);
        }
        try {
            exerciseStorage.writeFile(exerciseStorage.textContent);
        } catch (IOException e) {
            System.out.println("Failed saving drink: " + e.getMessage());
        }
    }

    public void saveMealNutrients(Storage mealNutrientStorage) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, int[]> entry : Meal.nutrientDetails.entrySet()) {
            result.append(entry.getKey()).append(",");
            int[] values = entry.getValue();
            for (int value : values) {
                result.append(value).append(",");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
            result.append("\n");
        }
        mealNutrientStorage.appendTextContent(result.toString());
        try {
            mealNutrientStorage.writeFile(mealNutrientStorage.textContent);
        } catch (IOException e) {
            System.out.println("Failed adding meal nutrients: " + e.getMessage());
        }
    }

    public void saveDrinkNutrients(Storage drinkNutrientStorage) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, int[]> entry : Drink.nutrientDetails.entrySet()) {
            result.append(entry.getKey()).append(",");
            int[] values = entry.getValue();
            for (int value : values) {
                result.append(value).append(",");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
            result.append("\n");
        }
        drinkNutrientStorage.appendTextContent(result.toString());
        try {
            drinkNutrientStorage.writeFile(drinkNutrientStorage.textContent);
        } catch (IOException e) {
            System.out.println("Failed adding drink nutrients: " + e.getMessage());
        }
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
        for (Exercise exercise: exerciseList) {
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
     * Prints all the exercises in the exerciseListToPrint,
     * inclusive of the duration, intensity and date.
     *
     * @param exerciseListToPrint arraylist containing the exercises that should be printed
     */
    public void printExerciseList(ArrayList<Exercise> exerciseListToPrint) {
        for (int i = 0; i < exerciseListToPrint.size(); i++) {
            Exercise currentExercise = exerciseListToPrint.get(i);
            System.out.println((i+1) + ". " + currentExercise.getName() + " | duration: " +
                    currentExercise.getDuration() + " | intensity: " + currentExercise.getIntensity()
                    + " | date: " + currentExercise.getDate());
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
     * Handles when the user is listing the exercises they have done today.
     * Method first checks if the list is empty.
     */
    public void handleListExercises() {
        System.out.println("here's the exercises you've done today");
        if (exerciseList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            printExerciseList(exerciseList);
        }
    }

    /**
     * Handles when the user is listing all exercises they have done, inclusive of previously saved exercises.
     * Method first checks if the list is empty.
     */

    public void handleListExercisesAll() {
        System.out.println("here's the exercises you've done so far");
        if (exerciseListAll.isEmpty() && exerciseList.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            ArrayList<Exercise> appendedExerciseList = new ArrayList<>();
            appendedExerciseList.addAll(exerciseListAll);
            appendedExerciseList.addAll(exerciseList);
            printExerciseList(appendedExerciseList);
        }
    }

    /**
     * Handles when the user is listing the exercises they have done on a certain date.
     * Method will first extract all exercises that have this corresponding date,
     * before printing.
     *
     * @param command string inputted by the user, containing the date of which they would like to list exercises of
     * @throws InvalidDateException if the date inputted by user is invalid
     */
    public void handleListExercisesDate(String command) throws InvalidDateException {
        String date = Parser.parseListDate(command);
        ArrayList<Exercise> exercisesListDate = new ArrayList<>();
        for (Exercise e : exerciseListAll) {
            if (e.getDate().equals(date)) {
                exercisesListDate.add(e);
            }
        }
        for (Exercise e : exerciseList) {
            if (e.getDate().equals(date)) {
                exercisesListDate.add(e);
            }
        }
        System.out.println("here's the exercises you've done on " + date);
        if (exercisesListDate.isEmpty()) {
            System.out.println("  >> nothing so far :o");
        } else {
            printExerciseList(exercisesListDate);
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
        handleListExercises();
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
        handleListExercisesAll();
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
        handleListExercisesDate(command);
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
     * Handles when the user would like to delete a previously inputted exercise.
     *
     * @param command string inputted by the user, containing the index of the exercise to delete
     * @throws InvalidListIndexException if the provided index is not a valid index in exerciseList
     * @throws IncompleteDeleteException if the user did not comply with the required command format
     */
    public void handleDeleteExercise(String command) throws InvalidListIndexException, IncompleteDeleteException {
        if (command.length() < 16) {
            throw new IncompleteDeleteException();
        }

        int exerciseIndex = Integer.parseInt(command.substring(15).trim()) - 1;
        if (exerciseIndex >= exerciseList.size() || exerciseIndex < 0) {
            throw new InvalidListIndexException();
        }

        String exerciseName = exerciseList.get(exerciseIndex).getName();
        exerciseList.remove(exerciseIndex);
        System.out.println("Removed " + exerciseName + " from exercises done");
    }

    /**
     * Adds an exercise to the user's current exerciseList, based on what exercise the user has done,
     * its duration and intensity.
     *
     * @param command string inputted by the user, containing the exercise they have done, its duration and
     *                intensity.
     * @throws IncompleteExerciseException if the user did not comply with the required format
     * @throws UnregisteredExerciseException if the user has inputted an exercise that was not pre-defined
     * @throws NegativeValueException if the provided exercise duration is a negative value
     */
    public void handleExercise(String command) throws IncompleteExerciseException, UnregisteredExerciseException,
            NegativeValueException {
        Parser.parseExercise(command);
        String exerciseType = Parser.exerciseDescription;
        int duration = Parser.exerciseDuration;
        ExerciseIntensity intensity = Parser.exerciseIntensity;
        Date currentDate = new Date();
        exerciseList.add(new Exercise(exerciseType, duration, intensity, currentDate.getDate()));
        assert !exerciseList.isEmpty(): "failed to track exercise";

        System.out.println("Tracked " + duration + " minutes of " + exerciseType);
    }

    /**
     * Handle when user would like to clear all entries from today.
     * This includes all meals, drinks and exercise.
     */
    public void handleClear() {
        mealList.clear();
        drinkList.clear();
        waterList.clear();
        exerciseList.clear();

        assert mealList.isEmpty(): "clearing of meal list failed";
        assert drinkList.isEmpty(): "clearing of drink list failed";
        assert exerciseList.isEmpty(): "clearing of exercise list failed";

        System.out.println("All entries have been deleted");
    }

    /**
     * Prints the number of calories the user has burnt today.
     */
    public void handleCaloriesBurnt() {
        int caloriesBurnt = 0;
        for (Exercise exercise: exerciseList) {
            caloriesBurnt += exercise.getCaloriesBurnt();
        }
        System.out.println("Total calories burnt: " + caloriesBurnt);
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
        for (Exercise exercise: exerciseList) {
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
