package seedu.fitnus.validator;

import seedu.fitnus.exception.NegativeValueException;

public class IntegerValidation {

    public static void checkIntegerGreaterThanZero (int value) throws NegativeValueException {
        if (value <= 0) {
            throw new NegativeValueException();
        }
    }
}
