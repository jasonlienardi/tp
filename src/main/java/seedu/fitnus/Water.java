package seedu.fitnus;

public class Water {
    private static Water instance = null;
    private static int waterIntake = 0;
    private static String dateAdded;

    /**
     * Constructs a Water object with the specified amount of water intake and date added.
     *
     * @param amount The amount of water intake.
     * @param dateAdded The date when the water intake was added.
     */
    public Water(int amount, String dateAdded) {
        assert amount > 0 : "Water volume must be greater than 0.";
        waterIntake = amount;
        Water.dateAdded = dateAdded;
    }

    /**
     * Checks the instance of the Water object, create a new Water object if it doesn't exist,
     * else add water intake to the existing object.
     *
     *
     * @param amount The amount of water intake.
     * @param dateAdded The date when the water intake was added.
     */
    public static void checkInstance(int amount, String dateAdded) {
        if (instance == null) {
            instance = new Water(amount, dateAdded);
        } else {
            addWaterIntake(amount);
        }
    }

    public static int getWater() {
        return waterIntake;
    }

    public static void addWaterIntake(int amount) {
        waterIntake += amount;
    }

    public static void editWaterIntake(int amount) {
        waterIntake = amount;
    }

    public static String getDate() {
        return dateAdded;
    }
}
