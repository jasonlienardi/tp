package seedu.fitnus.date;

import seedu.fitnus.exception.FutureDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidation extends Date {
    /**
     * Returns true if the date inputted by the user is a valid date.
     * The method verifies the day, month and year are in the correct range.
     *
     * @param date string containing the date to check if valid
     * @return true if the date inputted by the user is a valid date.
     */
    public static String formatDateIfValid(String date) throws FutureDateException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        String formattedDate = date;

        java.util.Date javaDate = dateFormat.parse(date);
        checkDateHasPassed(javaDate);
        formattedDate = dateFormat.format(javaDate);

        return formattedDate;

//
//
//        String[] arrayOfDates = date.split("-");
//        int day = Integer.parseInt(arrayOfDates[0]);
//        int month = Integer.parseInt(arrayOfDates[1]);
//        int year = Integer.parseInt(arrayOfDates[2]);
//        if (day < 1 || month < 1 || month > 12 || year < 0) {
//            return false;
//        }
//        int[] dayInMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//        if (day > dayInMonth[month - 1]) {
//            return false;
//        }
    }

    public static boolean checkDateHasPassed(java.util.Date inputDate) throws FutureDateException {
        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);

        if (inputDate.after(currentDate)) {
            throw new FutureDateException();
        }
        return true;
    }
}
