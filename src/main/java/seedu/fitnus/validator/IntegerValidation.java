package seedu.fitnus.validator;

import seedu.fitnus.exception.ExceedTypeLongException;
import seedu.fitnus.exception.NegativeValueException;
import seedu.fitnus.exception.NonPositiveValueException;

/**
 * Validates whether an integer value complies with the condition stated.
 */
public class IntegerValidation {
//    public static final long MIN_LONG_VALUE = -9223372036854775808;
//    public static final long MAX_LONG_VALUE = 9223372036854775807;
    /**
     * Validates whether the integer value is a positive integer.
     *
     * @param value integer value to check
     * @throws NonPositiveValueException if the value is less than or equals to zero
     */
    public static void checkIntegerGreaterThanZero (int value) throws NonPositiveValueException {
        if (value <= 0) {
            throw new NonPositiveValueException();
        }
    }

    public static void checkIntegerGreaterOrEqualThanZero (int value) throws NegativeValueException {
        if (value < 0) {
            throw new NegativeValueException();
        }
    }

    public static void checkNoOverflowForSum(long value) throws ExceedTypeLongException {
        if (value < 0) {
            throw new ExceedTypeLongException();
        }
    }

}
