package seedu.fitnus;

import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.parser.Parser;

import java.util.HashMap;

public class Drink  {
    public static HashMap<String, int[]> nutrientDetails = new HashMap<>();
    private String name;
    private int drinkVolume;
    private String dateAdded;
    private int calories;
    private int carbs;
    private int sugar;
    private int protein;
    private int fat;

    /**
     * Constructor for a Drink, requires the name and volume of the drink,
     * as well as date that it is added into the list.
     *
     * @param name name of drink to be added into the list
     * @param volume volume of the drink to be added into the list
     * @param currentDate date of which the drink is added into list
     */
    public Drink(String name, int volume, String currentDate) {
        assert name != null: "Name must not be null";
        this.name = name;
        assert volume > 0: "Drink volume must be greater than 0.";
        this.drinkVolume = volume;
        setNutrientValues(name);
        this.dateAdded = currentDate;
    }

    static {
        nutrientDetails.put("sprite", new int[]{270, 42, 7, 8, 2});
        nutrientDetails.put("iced lemon tea", new int[]{95, 21, 1, 1, 1});
        nutrientDetails.put("milo", new int[]{124, 20, 3, 3, 1});
        nutrientDetails.put("kopi", new int[]{141, 26, 2, 3, 1});
        nutrientDetails.put("soursop juice", new int[]{117, 25, 3, 1, 1});
        nutrientDetails.put("kopi c", new int[]{117, 20, 1, 4, 0});
        nutrientDetails.put("kalamansi juice", new int[]{168, 42, 0, 0, 1});
        nutrientDetails.put("coke", new int[]{153, 32, 1, 2, 1});
        nutrientDetails.put("kopi o", new int[]{67, 15, 1, 0, 0});
        nutrientDetails.put("plum juice", new int[]{57, 13, 1, 0, 1});
        nutrientDetails.put("teh c bing", new int[]{231, 24, 15, 1, 1});
        nutrientDetails.put("guava juice", new int[]{143, 38, 0, 0, 1});
        nutrientDetails.put("tiger beer", new int[]{42, 3, 1, 0, 0});
        nutrientDetails.put("teh tarik", new int[]{124, 21, 3, 3, 0});
        nutrientDetails.put("sugarcane juice", new int[]{192, 52, 0, 0, 1});
        nutrientDetails.put("teh", new int[]{151, 29, 4, 1, 1});
        nutrientDetails.put("bandung", new int[]{153, 32, 1, 2, 1});
    }

    private void setNutrientValues(String name) {
        int[] nutrients = nutrientDetails.get(name);
        calories = nutrients[0] * drinkVolume / 100;
        carbs = nutrients[1] * drinkVolume / 100;
        sugar = nutrients[2] * drinkVolume / 100;
        protein = nutrients[3] * drinkVolume / 100;
        fat = nutrients[4] * drinkVolume / 100;
    }

    /**
     * Handles when user would like to find out the information about a certain drink.
     * Prints out all nutritional content of the drink.
     *
     * @param command string inputted by user, containing drink to be viewed
     * @throws UnregisteredDrinkException if drink specified is not a pre-defined drink
     * @throws IncompleteInfoException if the user did not comply with the required format
     */
    public static void handleInfoDrink(String command) throws UnregisteredDrinkException, IncompleteInfoException {
        String name = Parser.parseInfoDrink(command);
        int[] nutrients = nutrientDetails.get(name);
        System.out.println("Drink: " + name + " (100 ml)");
        System.out.println("Calories: " + nutrients[0]);
        System.out.println("Carbs: " + nutrients[1]);
        // Sugar is part of Carbohydrates
        System.out.println("Sugar: " + nutrients[2]);
        System.out.println("Protein: " + nutrients[3]);
        System.out.println("Fat: " + nutrients[4]);
    }

    /**
     * Prints out all pre-defined drinks in one line,
     * only called when the user first enters the program.
     */
    public static void printAvailableDrinks() {
        System.out.print("Available drinks: ");
        for (String drink : nutrientDetails.keySet()) {
            System.out.print(drink);
            System.out.print(", ");
        }
        System.out.print("etc.");
        System.out.println();
    }

    /**
     * Prints out all pre-defined drinks in a list.
     */
    public static void listAvailableDrinks() {
        System.out.println("Available drinks: ");
        for (String drink : nutrientDetails.keySet()) {
            System.out.println("- " + drink);
        }
        System.out.println();
        System.out.println("You may also input a drink that isn't here.");
    }

    /**
     * Returns a string stating the name of the drink.
     *
     * @return string stating the name of the drink
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an integer value of the volume of the drink.
     *
     * @return an integer value of the volume of the drink
     */
    public int getDrinkVolumeSize() {
        return drinkVolume;
    }

    /**
     * Returns an integer value of the amount of calories in the drink.
     *
     * @return an integer value of the amount of calories in the drink
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Returns an integer value of the amount of carbohydrates in the drink.
     *
     * @return an integer value of the amount of carbohydrates in the drink
     */
    public int getCarbs() {
        return carbs;
    }

    /**
     * Returns an integer value of the amount of sugar in the drink.
     *
     * @return an integer value of the amount of sugar in the drink
     */
    public int getSugar() {
        return sugar;
    }

    /**
     * Returns an integer value of the amount of protein in the drink.
     *
     * @return an integer value of the amount of protein in the drink
     */
    public int getProtein() {
        return protein;
    }

    /**
     * Returns an integer value of the amount of fat in the drink.
     *
     * @return an integer value of the amount of fat in the drink
     */
    public int getFat() {
        return fat;
    }

    public static HashMap<String, int[]> getNutrientDetails() {
        return nutrientDetails;
    }

    /**
     * Returns the date of which the drink was added into drinkList.
     *
     * @return date of which the drink was added into drinkList
     */
    public String getDate() {
        return dateAdded;
    }
}
