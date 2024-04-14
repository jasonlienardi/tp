package seedu.fitnus.drink;

import seedu.fitnus.exception.ExceedTypeIntException;

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

    /**
     * Returns the user's water intake of the day.
     *
     * @return total water intake of the day
     */
    public int getWater() {
        return waterIntake;
    }

    /**
     * Add a specified amount of water to the user's waterIntake of the day.
     *
     * @param amount volume of water to add to intake.
     */
    public void addWaterIntake(int amount) throws ExceedTypeIntException {
        waterIntake += amount;
        if (waterIntake <= 0) {
            waterIntake -= amount;
            throw new ExceedTypeIntException();
        }
    }

    /**
     * Updates the total water intake of the day.
     *
     * @param amount volume specified by user to be the new total water intake of the day
     */
    public void editWaterIntake(int amount) {
        waterIntake = amount;

    }

    /**
     * Returns the date of which the water was added into drinkList.
     *
     * @return date of which the water was added into drinkList
     */
    public String getDate() {
        return dateAdded;
    }
}
