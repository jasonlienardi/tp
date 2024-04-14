package seedu.fitnus.exercise;

import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.parser.Parser;

import java.util.HashMap;

public class Exercise {
    public static HashMap<String, int[]> exerciseDetails = new HashMap<>();
    private String name;
    private int duration; // Duration in minutes
    private ExerciseIntensity intensity;
    private long caloriesBurnt;
    private String dateAdded;

    /**
     * Constructor for an Exercise, requires the name, duration and intensity of the exercise,
     * as well as date that it is added into the list.
     *
     * @param name name of exercise to be added into the list
     * @param duration duration of the exercise to be added into the list
     * @param intensity intensity of the exercise to be added into the list, can be HIGH/MEDIUM/LOW
     * @param currentDate date of which the exercise is added into list
     */
    public Exercise(String name, int duration, ExerciseIntensity intensity, String currentDate)
            throws UnregisteredExerciseException {
        assert name != null : "Name must not be null";
        this.name = name;
        assert duration > 0 : "Duration must be greater than 0";
        this.duration = duration;
        assert isValidIntensity(intensity) : "Intensity must be HIGH, MEDIUM, or LOW";
        this.intensity = intensity;
        setCaloriesBurnt(); // Assign exercise details based on the name and intensity
        this.dateAdded = currentDate;
    }

    static {
        exerciseDetails.put("running", new int[]{14, 10, 7});
        exerciseDetails.put("cycling", new int[]{10, 7, 4});
        exerciseDetails.put("swimming", new int[]{12, 8, 5});
    }

    /**
     * Calculate the number of calories burnt from the exercise.
     *
     * @throws UnregisteredExerciseException if specified exercise is not pre-defined.
     */
    private void setCaloriesBurnt() throws UnregisteredExerciseException {
        int[] details = exerciseDetails.get(name);
        if (details == null) {
            throw new UnregisteredExerciseException();
        }
        this.caloriesBurnt = (long) duration * details[intensity.ordinal()];
    }

    /**
     * Returns true if the exercise intensity specified is valid,
     * following the ExerciseIntensity enum.
     *
     * @param intensity intensity of exercise, only can be a value of high/medium/low
     * @return true if the exercise intensity specified is valid
     */
    private boolean isValidIntensity(ExerciseIntensity intensity) {
        return intensity == ExerciseIntensity.HIGH || intensity == ExerciseIntensity.MEDIUM ||
                intensity == ExerciseIntensity.LOW;
    }

    /**
     * Returns a string stating the name of the exercise.
     *
     * @return string stating the name of the exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an integer value of the duration of the exercise.
     *
     * @return an integer value of the duration of the exercise
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the intensity of the exercise.
     *
     * @return the intensity of the exercise
     */
    public ExerciseIntensity getIntensity() {
        return intensity;
    }

    /**
     * Returns the date of which the exercise was added into exerciseList.
     *
     * @return date of which the exercise was added into exerciseList
     */
    public String getDate() {
        return dateAdded;
    }

    /**
     * Returns a long value of the amount of calories burnt from the exercise.
     *
     * @return a long value of the amount of calories burnt from the exercise
     */
    public long getCaloriesBurnt() {
        return caloriesBurnt;
    }

    /**
     * Handles when user would like to find out the information about a certain exercise.
     * Prints out the calories burnt for 1 minute of the workout.
     *
     * @param command string inputted by user, containing exercise to be viewed
     * @throws UnregisteredExerciseException if exercise specified is not a pre-defined exercise
     * @throws IncompleteInfoException if the user did not comply with the required format
     */
    public static void handleInfoExercise(String command) throws UnregisteredExerciseException,
            IncompleteInfoException {
        String name = Parser.parseInfoExercise(command);
        int[] details = exerciseDetails.get(name);
        if (details == null) {
            throw new UnregisteredExerciseException();
        }
        System.out.println("Exercise: " + name);
        System.out.println("~ Calories burnt for a 1 minute workout of ~");
        System.out.println("HIGH intensity: " + details[0]);
        System.out.println("MEDIUM intensity: " + details[1]);
        System.out.println("LOW intensity: " + details[2]);
    }

    /**
     * Prints out all pre-defined exercises in one line,
     * only called when the user first enters the program.
     */
    public static void printAvailableExercises() {
        int count = 0;
        System.out.print("Available exercises: ");
        for (String exercise : exerciseDetails.keySet()) {
            if (count < 3) {
                System.out.print(exercise);
                System.out.print(", ");
                count++;
            } else {
                break;
            }
        }
        System.out.print("etc.");
        System.out.println();
    }

    /**
     * Prints out all pre-defined exercises in a list.
     */
    public static void listAvailableExercises() {
        System.out.println("Available exercises: ");
        for (String exercise : exerciseDetails.keySet()) {
            System.out.println("- " + exercise);
        }
        System.out.println();
        System.out.println("You may also input an exercise that isn't here with newExercise.");
    }

    public static HashMap<String, int[]> getExerciseDetails() {
        return exerciseDetails;
    }
}
