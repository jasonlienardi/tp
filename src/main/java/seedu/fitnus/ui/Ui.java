package seedu.fitnus.ui;

import seedu.fitnus.drink.Drink;
import seedu.fitnus.meal.Meal;
import seedu.fitnus.exercise.Exercise;
import seedu.fitnus.parser.Parser;
import seedu.fitnus.storage.Storage;
import seedu.fitnus.storage.StorageManager;
import seedu.fitnus.user.User;

import java.util.Scanner;

public class Ui {
    public static final String LINE = "_________________________________________________________________";
    static Scanner input = new Scanner(System.in);
    public boolean isExit = false;

    private Storage mealStorage = new Storage("./data", "./data/MealList.txt");
    private Storage drinkStorage = new Storage("./data", "./data/DrinkList.txt");
    private Storage exerciseStorage = new Storage ("./data", "./data/ExerciseList.txt");
    private Storage mealNutrientStorage = new Storage("./db", "./db/Meal_db.csv");
    private Storage drinkNutrientStorage = new Storage("./db", "./db/Drink_db.csv");
    private Storage exerciseCaloriesStorage = new Storage("./db", "./db/Exercise_db.csv");
    private User user = new User();
    private StorageManager storageManager = new StorageManager(mealStorage, drinkStorage, exerciseStorage,
            mealNutrientStorage, drinkNutrientStorage, exerciseCaloriesStorage);
    private Parser parser = new Parser(user);

    /**
     * Prints the welcome message upon the start of the application,
     * including all pre-defined meals, drinks and exercises.
     * */
    public void printWelcomeMessage() {
        System.out.println(LINE);
        System.out.println("Hello! Welcome to FitNUS");
        System.out.println("What would you like to track today?");
        Meal.printAvailableMeals();
        Drink.printAvailableDrinks();
        Exercise.printAvailableExercises();
        System.out.println(LINE);
    }

    /**
     * Prints the goodbye message when the user exits the program.
     * The lists for meal, drink and exercise would be stores before exiting.
     */
    public void handleExit() {
        System.out.println("Bye. Hope to see you again soon!");
        input.close();
        isExit = true;
        storageManager.saveMeal(mealStorage);
        storageManager.saveDrink(drinkStorage);
        storageManager.saveExercise(exerciseStorage);
        storageManager.saveMealNutrients(mealNutrientStorage);
        storageManager.saveDrinkNutrients(drinkNutrientStorage);
        storageManager.saveExerciseCalories(exerciseCaloriesStorage);
    }

    /**
     * Prints the divider line between messages.
     */
    public static void showLine() {
        System.out.println(LINE);
    }

    /**
     * Reads the user's input into command line.
     */
    public void readCommand() {
        String command = input.nextLine();
        showLine();
        if (command.trim().equals("exit")) {
            handleExit();
        } else {
            parser.handleCommand(command);
        }
        showLine();
    }
}
