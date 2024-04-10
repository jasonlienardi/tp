package seedu.fitnus.exercise;

import seedu.fitnus.Date;
import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteExerciseException;
import seedu.fitnus.exception.InvalidDateException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.parser.Parser;

import java.util.ArrayList;

public class ExerciseList {
    public ArrayList<Exercise> exerciseList;
    public ArrayList<Exercise> exerciseListAll;

    public ExerciseList() {
        exerciseList = new ArrayList<>();
        exerciseListAll = new ArrayList<>();
    }

    public void handleAddNewExerciseCalories(String command) throws NegativeValueException {
        Parser.parseNewExercise(command);
        String description = Parser.exerciseCaloriesDescription;
        int high = Parser.exerciseCaloriesHigh;
        int medium = Parser.exerciseCaloriesMedium;
        int low = Parser.exerciseCaloriesLow;
        Exercise.exerciseDetails.put(description, new int[]{high, medium, low});

        System.out.println("Added " + description + " to available exercises");
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
     * Prints the number of calories the user has burnt today.
     */
    public void handleCaloriesBurnt() {
        int caloriesBurnt = 0;
        for (Exercise exercise: exerciseList) {
            caloriesBurnt += exercise.getCaloriesBurnt();
        }
        System.out.println("Total calories burnt: " + caloriesBurnt);
    }
}
