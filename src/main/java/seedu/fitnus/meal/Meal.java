package seedu.fitnus.meal;

import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.UnregisteredMealException;
import seedu.fitnus.parser.Parser;

import java.util.HashMap;

public class Meal {
    public static HashMap<String, int[]> nutrientDetails = new HashMap<>();
    private String name;
    private int servingSize;
    private String dateAdded;
    private long calories;
    private long carbs;
    private long protein;
    private long fat;
    private long fiber;
    private long sugar;

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
        nutrientDetails.put("ban mian", new int[]{475, 48, 22, 22, 3, 10});
        nutrientDetails.put("tau huay", new int[]{153, 32, 14, 1, 5, 1});
        nutrientDetails.put("nasi goreng", new int[]{346, 45, 13, 12, 10, 2});
        nutrientDetails.put("babi kecap", new int[]{607, 75, 25, 23, 2, 10});
        nutrientDetails.put("soup kambeng", new int[]{203, 6, 28, 7, 2, 5});
        nutrientDetails.put("nasi lemak", new int[]{494, 80, 13, 14, 6, 5});
        nutrientDetails.put("pepper lunch", new int[]{500, 50, 40, 11, 4, 5});
        nutrientDetails.put("char siew rice", new int[]{605, 91, 24, 16, 6, 10});
        nutrientDetails.put("pork satay with satay sauce", new int[]{36, 1, 5, 2, 10, 0});
        nutrientDetails.put("roti prata", new int[]{209, 32, 5, 7, 2, 10});
        nutrientDetails.put("mee goreng", new int[]{500, 61, 18, 20, 4, 5});
        nutrientDetails.put("chendol", new int[]{386, 59, 6, 15, 7, 2});
        nutrientDetails.put("wanton mee", new int[]{555, 97, 15, 14, 13, 10});
        nutrientDetails.put("oyster omlette", new int[]{467, 40, 19, 24, 10, 1});
        nutrientDetails.put("ice kachang", new int[]{257, 58, 6, 1, 10, 2});
        nutrientDetails.put("mala", new int[]{583, 72, 12, 30, 10, 7});
        nutrientDetails.put("hokkien prawn mee", new int[]{522, 69, 18, 19, 4, 10});
        nutrientDetails.put("durian", new int[]{147, 27, 2, 5, 3, 5});
    }

    /**
     * Sets nutrients details for a certain meal based on values in hashmap
     *
     * @param name name of meal to set nutrients
     */
    private void setNutrientValues(String name) {
        int[] nutrients = nutrientDetails.get(name);
        calories = (long) nutrients[0] * servingSize;
        carbs = (long) nutrients[1] * servingSize;
        protein = (long) nutrients[2] * servingSize;
        fat = (long) nutrients[3] * servingSize;
        fiber = (long) nutrients[4] * servingSize;
        sugar = (long) nutrients[5] * servingSize;
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
     * Returns a long value of the amount of calories in the meal.
     *
     * @return a long value of the amount of calories in the meal
     */
    public long getCalories() {
        return calories;
    }

    /**
     * Returns a long value of the amount of carbohydrates in the meal.
     *
     * @return a long value of the amount of carbohydrates in the meal
     */
    public long getCarbs() {
        return carbs;
    }

    /**
     * Returns a long value of the amount of protein in the meal.
     *
     * @return a long value of the amount of protein in the meal
     */
    public long getProtein() {
        return protein;
    }

    /**
     * Returns a long value of the amount of fat in the meal.
     *
     * @return a long value of the amount of fat in the meal
     */
    public long getFat() {
        return fat;
    }

    /**
     * Returns a long value of the amount of fiber in the meal.
     *
     * @return a long value of the amount of fiber in the meal
     */
    public long getFiber() {
        return fiber;
    }

    /**
     * Returns a long value of the amount of sugar in the meal.
     *
     * @return a long value of the amount of sugar in the meal
     */
    public long getSugar() {
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
        int count = 0;
        System.out.print("Available meals: ");
        for (String meal : nutrientDetails.keySet()) {
            if (count < 3) {
                System.out.print(meal);
                System.out.print(", ");
                count ++;
            } else {
                break;
            }
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
