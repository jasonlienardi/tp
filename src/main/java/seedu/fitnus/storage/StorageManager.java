package seedu.fitnus.storage;

import seedu.fitnus.Date;
import seedu.fitnus.Meal;
import seedu.fitnus.Drink;
import seedu.fitnus.exercise.ExerciseIntensity;
import seedu.fitnus.exercise.Exercise;
import seedu.fitnus.Water;
import seedu.fitnus.user.User;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.parser.Parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class StorageManager {
    public StorageManager(Storage mealStorage, Storage drinkStorage, Storage exerciseStorage,
                          Storage mealNutrientStorage, Storage drinkNutrientStorage, Storage exerciseCaloriesStorage) {
        try {
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
                    User.mealListAll.add(new Meal(mealDescription, mealSize, currentDate));
                }
            }
            Date currentDate = new Date();
            String todayDate = currentDate.getDate();
            for (Meal m : User.mealListAll) {
                if (m.getDate().equals(todayDate)) {
                    User.mealList.add(m);
                }
            }
            User.mealListAll.removeAll(User.mealList);
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
                        User.waterListAll.add(new Water(drinkSize, drinkDate));
                    } else {
                        User.drinkListAll.add(new Drink(drinkDescription, drinkSize, drinkDate));
                    }
                }
            }
            Date currentDate = new Date();
            String todayDate = currentDate.getDate();
            for (Drink d : User.drinkListAll) {
                if (d.getDate().equals(todayDate)) {
                    User.drinkList.add(d);
                }
            }
            User.drinkListAll.removeAll(User.drinkList);
            for (Water w : User.waterListAll) {
                if (w.getDate().equals(todayDate)) {
                    User.waterList.add(w);
                }
            }
            User.waterListAll.removeAll(User.waterList);
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
                    User.myExerciseList.exerciseListAll.add(new Exercise(exerciseDescription,
                            exerciseDuration, exerciseIntensity,
                            currentDate));
                }
            }
            Date currentDate = new Date();
            String todayDate = currentDate.getDate();
            for (Exercise e : User.myExerciseList.exerciseListAll) {
                if (e.getDate().equals(todayDate)) {
                    User.myExerciseList.exerciseList.add(e);
                }
            }
            User.myExerciseList.exerciseListAll.removeAll(User.myExerciseList.exerciseList);
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
    public void loadExerciseCalories(Storage exerciseCaloriesStorage) throws NegativeValueException {
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
        for (Meal meal : User.mealListAll) {
            String mealSavedData = meal.getName() + "," + meal.getServingSize() + "," + meal.getDate();
            mealStorage.appendTextContent(mealSavedData);
        }
        for (Meal meal : User.mealList) {
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
        for (Water water : User.waterListAll) {
            String waterSavedData = "water" + "," + water.getWater() + "," + water.getDate();
            drinkStorage.appendTextContent(waterSavedData);
        }
        for (Water water : User.waterList) {
            String waterSavedData = "water" + "," + water.getWater() + "," + water.getDate();
            drinkStorage.appendTextContent(waterSavedData);
        }
        for (Drink drink : User.drinkListAll) {
            String drinkSavedData = drink.getName() + "," + drink.getDrinkVolumeSize() + "," + drink.getDate();
            drinkStorage.appendTextContent(drinkSavedData);
        }
        for (Drink drink : User.drinkList) {
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
        for (Exercise exercise : User.myExerciseList.exerciseListAll) {
            String exerciseSavedData = exercise.getName() + "," + exercise.getDuration() + ","
                    + exercise.getIntensity() + "," + exercise.getDate();
            exerciseStorage.appendTextContent(exerciseSavedData);
        }
        for (Exercise exercise : User.myExerciseList.exerciseList) {
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

    //@@author jasonlienardi
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

    //@@author jasonlienardi
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

    //@@author jasonlienardi
    public void saveExerciseCalories(Storage exerciseCaloriesStorage) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, int[]> entry : Exercise.exerciseDetails.entrySet()) {
            result.append(entry.getKey()).append(",");
            int[] values = entry.getValue();
            for (int value : values) {
                result.append(value).append(",");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
            result.append("\n");
        }
        exerciseCaloriesStorage.appendTextContent(result.toString());
        try {
            exerciseCaloriesStorage.writeFile(exerciseCaloriesStorage.textContent);
        } catch (IOException e) {
            System.out.println("Failed adding exercise calories: " + e.getMessage());
        }
    }
}
