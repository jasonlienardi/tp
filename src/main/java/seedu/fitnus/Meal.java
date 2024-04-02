package seedu.fitnus;

import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.parser.Parser;

import java.util.HashMap;

public class Meal {
    public static HashMap<String, int[]> nutrientDetails = new HashMap<>();
    private String name;
    private int servingSize;
    private String dateAdded;
    private int calories;
    private int carbs;
    private int protein;
    private int fat;
    private int fiber;
    private int sugar;

    /**
     * Constructor for a Meal, requires the name and serving size of the meal,
     * as well as date that it is added into the list.
     *
     * @param name name of meal to be added into the list
     * @param servingSize serving size of the meal to be added into the list
     * @param currentDate date of which the meal is added into list
     */
    public Meal(String name, int servingSize, String currentDate) {
        assert name != null : "Name must not be null";
        this.name = name;
        assert servingSize > 0 : "Serving size must be greater than 0";
        this.servingSize = servingSize;
        setNutrientValues(name); // Assign nutrient values based on the name
        this.dateAdded = currentDate;
    }

    static {
        nutrientDetails.put("chicken rice", new int[]{400, 50, 30, 20, 10, 5});
        nutrientDetails.put("fried rice", new int[]{500, 60, 25, 15, 15, 5});
        nutrientDetails.put("pizza", new int[]{600, 80, 50, 40, 30, 20});
        nutrientDetails.put("kaya toast", new int[]{459, 44, 8, 27, 10, 1});
        nutrientDetails.put("laksa", new int[]{377, 71, 18, 2, 4, 10});
    }

    private void setNutrientValues(String name) {
        int[] nutrients = nutrientDetails.get(name);
        calories = nutrients[0] * servingSize;
        carbs = nutrients[1] * servingSize;
        protein = nutrients[2] * servingSize;
        fat = nutrients[3] * servingSize;
        fiber = nutrients[4] * servingSize;
        sugar = nutrients[5] * servingSize;
    }

    /**
     * Returns a string stating the name of the meal.
     *
     * @return string stating the name of the meal
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an integer value of the amount of calories in the meal.
     *
     * @return an integer value of the amount of calories in the meal
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Returns an integer value of the amount of carbohydrates in the meal.
     *
     * @return an integer value of the amount of carbohydrates in the meal
     */
    public int getCarbs() {
        return carbs;
    }

    /**
     * Returns an integer value of the amount of protein in the meal.
     *
     * @return an integer value of the amount of protein in the meal
     */
    public int getProtein() {
        return protein;
    }

    /**
     * Returns an integer value of the amount of fat in the meal.
     *
     * @return an integer value of the amount of fat in the meal
     */
    public int getFat() {
        return fat;
    }

    /**
     * Returns an integer value of the amount of fiber in the meal.
     *
     * @return an integer value of the amount of fiber in the meal
     */
    public int getFiber() {
        return fiber;
    }

    /**
     * Returns an integer value of the amount of sugar in the meal.
     *
     * @return an integer value of the amount of sugar in the meal
     */
    public int getSugar() {
        return sugar;
    }

    /**
     * Returns an integer value of the serving size of the meal.
     *
     * @return an integer value of the serving size of the meal
     */
    public int getServingSize() {
        return servingSize;
    }

    /**
     * Handles when user would like to find out the information about a certain meal.
     * Prints out all nutritional content of the meal.
     *
     * @param command string inputted by user, containing meal to be viewed
     * @throws UnregisteredMealException if meal specified is not a pre-defined meal
     * @throws IncompleteInfoException if the user did not comply with the required format
     */
    public static void handleInfoMeal(String command) throws UnregisteredMealException, IncompleteInfoException {
        String name = Parser.parseInfoMeal(command);
        int[] nutrients = nutrientDetails.get(name);
        System.out.println("Meal: " + name + " (per serving)");
        System.out.println("Calories: " + nutrients[0]);
        System.out.println("Carbs: " + nutrients[1]);
        System.out.println("Protein: " + nutrients[2]);
        System.out.println("Fat: " + nutrients[3]);
        System.out.println("Fiber: " + nutrients[4]);
        System.out.println("Sugar: " + nutrients[5]);
    }

    /**
     * Prints out all pre-defined meals in one line,
     * only called when the user first enters the program.
     */
    public static void printAvailableMeals() {
        System.out.print("Available meals: ");
        for (String meal : nutrientDetails.keySet()) {
            System.out.print(meal);
            System.out.print(", ");
        }
        System.out.print("etc.");
        System.out.println();
    }

    /**
     * Prints out all pre-defined meals in a list.
     */
    public static void listAvailableMeals() {
        System.out.println("Available meals: ");
        for (String meal : nutrientDetails.keySet()) {
            System.out.println("- " + meal);
        }
        System.out.println();
        System.out.println("You may also input a meal that isn't here.");
    }

    public static HashMap<String, int[]> getNutrientDetails() {
        return nutrientDetails;
    }

    /**
     * Returns the date of which the meal was added into mealList.
     *
     * @return date of which the meal was added into mealList
     */
    public String getDate() {
        return dateAdded;
    }
}
