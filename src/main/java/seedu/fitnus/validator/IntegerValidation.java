package seedu.fitnus.validator;

import seedu.fitnus.exception.NegativeValueException;

/**
 * Validates whether an integer value complies with the condition stated.
 */
public class IntegerValidation {

    /**
     * Validates whether the integer value is a positive integer.
     *
     * @param value integer value to check
     * @throws NegativeValueException if the value is less than or equals to zero
     */
    public static void checkIntegerGreaterThanZero (int value) throws NegativeValueException {
        if (value <= 0) {
            throw new NegativeValueException();
        }
    }

    public static void checkIntegerGreaterOrEqualThanZero (int value) throws NegativeValueException {
        if (value < 0) {
            throw new NegativeValueException();
        }
    }
}
