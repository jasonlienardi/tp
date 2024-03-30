package seedu.fitnus.parser;

import seedu.fitnus.Drink;
import seedu.fitnus.Exercise;
import seedu.fitnus.ExerciseIntensity;
import seedu.fitnus.Meal;

import seedu.fitnus.exception.IncompleteDeleteException;
import seedu.fitnus.exception.IncompleteDrinkException;
import seedu.fitnus.exception.IncompleteEditException;
import seedu.fitnus.exception.IncompleteExerciseException;
import seedu.fitnus.exception.IncompleteInfoException;
import seedu.fitnus.exception.IncompleteMealException;
import seedu.fitnus.exception.InvalidCommandException;
import seedu.fitnus.exception.InvalidListIndexException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.UnregisteredDrinkException;
import seedu.fitnus.exception.UnregisteredExerciseException;
import seedu.fitnus.exception.UnregisteredMealException;

import seedu.fitnus.user.User;
import seedu.fitnus.validator.IntegerValidation;

public class Parser {
    public static final int MIN_INTEGER_VALUE = -2147483648;
    public static final int MAX_INTEGER_VALUE = 2147483647;
    public static String mealDescription;
    public static int mealSize;
    public static String drinkDescription;
    public static int drinkSize;
    public static int editMealIndex;
    public static int editMealSize;
    public static int editDrinkIndex;
    public static int editDrinkSize;
    public static int editWaterSize;
    public static String mealStorageDescription;
    public static int mealStorageSize;
    public static String mealStorageDate;

    public static String drinkStorageDescription;
    public static int drinkStorageSize;
    public static String drinkStorageDate;

    public static String exerciseDescription;
    public static int exerciseDuration;

    public static ExerciseIntensity exerciseIntensity;
    private User user;

    public Parser(User user) {
        this.user = user;
    }

    public void handleCommand(String command) {
        try {
            if (command.equals("help")) {
                handleHelp();
            } else if (command.startsWith("ate")) {
                user.handleMeal(command);
            } else if (command.startsWith("drink")) {
                user.handleDrink(command);
            } else if (command.startsWith("exercise")) {
                user.handleExercise(command);
            } else if (command.startsWith("infoMeal")) {
                Meal.handleInfoMeal(command);
            } else if (command.startsWith("infoDrink")) {
                Drink.handleInfoDrink(command);
            } else if (command.startsWith("infoExercise")) {
                Exercise.handleInfoExercise(command);
            } else if (command.equals("calories")) {
                user.handleViewCalories();
            } else if (command.equals("caloriesBurnt")) {
                user.handleCaloriesBurnt();
            } else if (command.equals("carbs")) {
                user.handleViewCarbohydrates();
            } else if (command.equals("protein")) {
                user.handleViewProteins();
            } else if (command.equals("sugar")) {
                user.handleViewSugar();
            } else if (command.equals("fat")) {
                user.handleViewFat();
            } else if (command.equals("viewWater")) {
                user.handleViewWaterIntake();
            } else if (command.equals("fiber")) {
                user.handleViewFiber();
            } else if (command.equals("listMeals")) {
                user.handleListMeals();
            } else if (command.equals("listDrinks")) {
                user.handleListDrinks();
            } else if (command.equals("listExercises")) {
                user.handleListExercises();
            } else if (command.equals("listEverything")) {
                user.handleListEverything();
            } else if (command.startsWith("editMeal")) {
                User.handleEditMealServingSize(command);
            } else if (command.startsWith("editDrink")) {
                User.handleEditDrinkServingSize(command);
            } else if (command.startsWith("editWater")) {
                User.handleEditWaterIntake(command);
            } else if (command.startsWith("deleteMeal")) {
                user.handleDeleteMeal(command);
            } else if (command.startsWith("deleteDrink")) {
                user.handleDeleteDrink(command);
            } else if (command.startsWith("deleteExercise")) {
                user.handleDeleteExercise(command);
            } else if (command.equals("clear")) {
                user.handleClear();
            } else {
                throw new InvalidCommandException();
            }
        } catch (InvalidCommandException e) {
            System.out.println("Invalid command, type [help] to view all commands.");
        } catch (IncompleteDrinkException e) {
            System.out.println("Incomplete command, the format must be [drink d/DRINK s/SERVING_SIZE].");
        } catch (IncompleteMealException e) {
            System.out.println("Incomplete command, the format must be [ate m/MEAL s/SERVING_SIZE].");
        } catch (IncompleteExerciseException e) {
            System.out.println("Incomplete command, the format must be [exercise e/EXERCISE d/DURATION i/INTENSITY].\n"
                    + " > DURATION should be in minutes and INTENSITY can only be HIGH/MEDIUM/LOW.");
        } catch (UnregisteredDrinkException e) {
            System.out.println("Sorry that drink is not registered in the database.Please check the spelling and " +
                    "try again");
        } catch (UnregisteredMealException e) {
            System.out.println("Sorry that meal is not registered in the database. Please check the spelling and " +
                    "try again");
        } catch (InvalidListIndexException e) {
            System.out.println("Sorry the index you provided is invalid, check [listMeals/listDrinks/listExercises] " +
                    "to view valid indexes.");
        } catch (UnregisteredExerciseException e) {
            System.out.println("Sorry that exercise is not registered in the database. Please check the spelling and" +
                    " try again");
        } catch (NumberFormatException e) {
            System.out.println("An integer value is expected, try again please :)\n" +
                    "  > Friendly reminder that integer values cannot exceed the range of " + MIN_INTEGER_VALUE + " to "
                    + MAX_INTEGER_VALUE + ".\n    Although, we highly doubt you will ever exceed this range");
        } catch (IncompleteDeleteException e) {
            System.out.println("Please specify an index that you would like to delete. The format should be " +
                    "[deleteMeal/deleteDrink/deleteExercise INDEX]");
        } catch (IncompleteEditException e) {
            System.out.println("Please specify an index that you would like to edit AND/OR the new serving size. "+
                    "Type [help] to view the commands format.");
        } catch (IncompleteInfoException e) {
            System.out.println("Please specify a meal/drink/exercise that you would like to view the info of. " +
                    "Type [help] to view the commands format.");
        } catch (NegativeValueException e) {
            System.out.println("Your serving size/exercise duration must be at least 0!");
        }

    }

    public static void handleHelp() {
        System.out.println("here's all the valid commands i recognise: ");
        System.out.println("- Add a meal eaten: ate m/MEAL s/SERVING_SIZE");
        System.out.println("- Add a drink: drink d/DRINK s/VOLUME(ML)");
        System.out.println("- Track and exercise: exercise e/EXERCISE d/DURATION(MINUTES) " +
                "i/INTENSITY(HIGH, MEDIUM, LOW)");
        System.out.println("- Find the information about a certain meal: infoMeal MEAL");
        System.out.println("- Find the information about a certain drink: infoDrink DRINK");
        System.out.println("- Find the information about a certain exercise: infoExercise EXERCISE");
        System.out.println("- View daily calories consumed: calories");
        System.out.println("- View daily carbohydrates consumed: carbs");
        System.out.println("- View daily proteins consumed: protein");
        System.out.println("- View daily fat consumed: fat");
        System.out.println("- View daily sugar consumed: sugar");
        System.out.println("- View daily fiber consumed: fiber");
        System.out.println("- View daily water consumption: viewWater");
        System.out.println("- View daily calories burnt: caloriesBurnt");
        System.out.println("- List meal intake: listMeals");
        System.out.println("- List drink intake: listDrinks");
        System.out.println("- List exercises done: listExercises");
        System.out.println("- List entire food intake for the day: listEverything");
        System.out.println("- Edit an existing meal after inserted: editMeal INDEX s/NEW_SERVING_SIZE");
        System.out.println("- Edit an existing drink after inserted: editDrink INDEX s/NEW_SERVING_SIZE");
        System.out.println("- Edit total water intake after inserted: editWater s/TOTAL_WATER_INTAKE");
        System.out.println("- Delete certain meal entry: deleteMeal INDEX");
        System.out.println("- Delete certain drink entry: deleteDrink INDEX");
        System.out.println("- Clear all entries: clear");
        System.out.println("- Exit the app: exit ");
    }

    public static void parseMeal(String command) throws IncompleteMealException, UnregisteredMealException,
            NegativeValueException {
        if (!command.contains("m/") || !command.contains("s/")) {
            throw new IncompleteMealException();
        }
        int descriptionIndex = command.indexOf("m/") + 2;
        int sizeIndex = command.indexOf("s/") + 2;
        if (sizeIndex >= command.length()) {
            throw new IncompleteMealException();
        }
        mealDescription = command.substring(descriptionIndex, sizeIndex - 2).trim().toLowerCase();
        if (mealDescription.isEmpty()) {
            throw new IncompleteMealException();
        }
        if (!Meal.getNutrientDetails().containsKey(mealDescription)) {
            throw new UnregisteredMealException();
        }
        mealSize = Integer.parseInt(command.substring(sizeIndex).trim());
        IntegerValidation.checkIntegerGreaterThanZero(mealSize);
    }

    public static void parseDrink(String command) throws IncompleteDrinkException, UnregisteredDrinkException,
            NegativeValueException {
        if (!command.contains("d/") || !command.contains("s/")) {
            throw new IncompleteDrinkException();
        }
        int descriptionIndex = command.indexOf("d/") + 2;
        int sizeIndex = command.indexOf("s/") + 2;
        if (sizeIndex >= command.length()) {
            throw new IncompleteDrinkException();
        }
        drinkDescription = command.substring(descriptionIndex, sizeIndex - 2).trim().toLowerCase();
        if (drinkDescription.isEmpty()) {
            throw new IncompleteDrinkException();
        }
        if (!Drink.getNutrientDetails().containsKey(drinkDescription) && !drinkDescription.equals("water")) {
            throw new UnregisteredDrinkException();
        }

        drinkSize = Integer.parseInt(command.substring(sizeIndex).trim());
        IntegerValidation.checkIntegerGreaterThanZero(drinkSize);
    }

    public static String parseInfoMeal(String command) throws UnregisteredMealException, IncompleteInfoException {
        int mealIndex = 9;
        if (command.length() < mealIndex + 1) {
            throw new IncompleteInfoException();
        }
        String infoMealDescription = command.substring(mealIndex).trim();

        if (!Meal.getNutrientDetails().containsKey(infoMealDescription)) {
            throw new UnregisteredMealException();
        }
        return infoMealDescription;
    }

    public static String parseInfoExercise(String command) throws UnregisteredExerciseException, IncompleteInfoException {
        int exerciseIndex = 13;
        if (command.length() < exerciseIndex + 1) {
            throw new IncompleteInfoException();
        }
        String infoExerciseDescription = command.substring(exerciseIndex).trim();
        if (!Exercise.getExerciseDetails().containsKey(infoExerciseDescription)) {
            throw new UnregisteredExerciseException();
        }
        return infoExerciseDescription;
    }

    public static String parseInfoDrink(String command) throws UnregisteredDrinkException, IncompleteInfoException {
        int drinkIndex = 10;
        if (command.length() < drinkIndex + 1) {
            throw new IncompleteInfoException();
        }
        String infoDrinkDescription = command.substring(drinkIndex).trim();
        if (!Drink.getNutrientDetails().containsKey(infoDrinkDescription)) {
            throw new UnregisteredDrinkException();
        }
        return infoDrinkDescription;
    }

    public static void parseEditMeal(String command) throws NegativeValueException, IncompleteEditException {
        int mealSizePosition = command.indexOf("s/");
        if (mealSizePosition <= 9) {
            throw new IncompleteEditException();
        }

        editMealIndex = Integer.parseInt(command.substring(9, mealSizePosition).trim()) - 1;
        editMealSize = Integer.parseInt(command.substring(mealSizePosition + 2).trim());
        IntegerValidation.checkIntegerGreaterThanZero(editMealSize);
    }

    public static void parseEditDrink(String command) throws NegativeValueException, IncompleteEditException {
        int drinkSizePosition = command.indexOf("s/");
        if (drinkSizePosition <= 10) {
            throw new IncompleteEditException();
        }

        editDrinkIndex = Integer.parseInt(command.substring(10, drinkSizePosition).trim()) - 1;
        editDrinkSize = Integer.parseInt(command.substring(drinkSizePosition + 2).trim());
        IntegerValidation.checkIntegerGreaterThanZero(editDrinkSize);
    }

    public static void parseEditWater(String command) throws NegativeValueException, IncompleteEditException {
        int waterSizePosition = command.indexOf("s/") + 2;
        if (waterSizePosition <= 1) { //-1 + 2
            throw new IncompleteEditException();
        }
        editWaterSize = Integer.parseInt(command.substring(waterSizePosition).trim());
        IntegerValidation.checkIntegerGreaterThanZero(editWaterSize);
    }

    public static void parseMealStorage(String data) {
        String delimiter = ",";
        String[] arrayOfMealData = data.split(delimiter);
        mealStorageDescription = arrayOfMealData[0];
        mealStorageSize = Integer.parseInt(arrayOfMealData[1]);
    }

    public static void parseDrinkStorage(String data) {
        String delimiter = ",";
        String[] arrayOfDrinkData = data.split(delimiter);
        drinkStorageDescription = arrayOfDrinkData[0];
        drinkStorageSize = Integer.parseInt(arrayOfDrinkData[1]);
    }

    public static void parseExercise(String command) throws IncompleteExerciseException, UnregisteredExerciseException,
            NegativeValueException {
        if (!command.contains("e/") || !command.contains("d/") || !command.contains("i/")) {
            throw new IncompleteExerciseException();
        }
        int descriptionIndex = command.indexOf("e/") + 2;
        int durationIndex = command.indexOf("d/") + 2;
        int intensityIndex = command.indexOf("i/") + 2;
        if (intensityIndex >= command.length()) {
            throw new IncompleteExerciseException();
        }
        exerciseDescription = command.substring(descriptionIndex, durationIndex - 2).trim().toLowerCase();
        if (exerciseDescription.isEmpty()) {
            throw new IncompleteExerciseException();
        }
        if (!Exercise.getExerciseDetails().containsKey(exerciseDescription)) {
            throw new UnregisteredExerciseException();
        }

        exerciseDuration = Integer.parseInt(command.substring(durationIndex, intensityIndex - 2).trim());
        IntegerValidation.checkIntegerGreaterThanZero(exerciseDuration);

        String intensityString = command.substring(intensityIndex).trim().toUpperCase();
        try {
            exerciseIntensity = ExerciseIntensity.valueOf(intensityString);
        } catch (IllegalArgumentException e) {
            throw new IncompleteExerciseException(); // Invalid intensity
        }
    }
}
