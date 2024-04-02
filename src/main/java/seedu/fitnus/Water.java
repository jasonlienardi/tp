package seedu.fitnus;

public class Water {
    private int waterIntake;
    private String dateAdded;

    /**
     * Constructs a Water object with the specified amount of water intake and date added.
     *
     * @param amount The amount of water intake.
     * @param dateAdded The date when the water intake was added.
     */
    public Water(int amount, String dateAdded) {
        assert amount > 0 : "Water volume must be greater than 0.";
        this.waterIntake = amount;
        this.dateAdded = dateAdded;
    }

    public int getWater() {
        return waterIntake;
    }

    public void addWaterIntake(int amount) {
        waterIntake += amount;
    }

    public void editWaterIntake(int amount) {
        waterIntake = amount;
    }

    public String getDate() {
        return dateAdded;
    }
}
