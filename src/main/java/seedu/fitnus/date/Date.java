package seedu.fitnus.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class that handles any references to the current date and time.
 */
public class Date {
    private String currentDate;

    /**
     * Constructor that gets the current system date and formats it in the stated format.
     * This date is then saved as a string.
     */
    public Date() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date);

        this.currentDate = formattedDate;
    }

    /**
     * Returns the current system date.
     *
     * @return current system date
     */
    public String getDate() {
        return currentDate;
    }

    /**
     * Returns true if the date inputted by the user is a valid date.
     * The method verifies the day, month and year are in the correct range.
     *
     * @param date string containing the date to check if valid
     * @return true if the date inputted by the user is a valid date.
     */
    public static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            java.util.Date javaDate = dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        String[] arrayOfDates = date.split("-");
        int day = Integer.parseInt(arrayOfDates[0]);
        int month = Integer.parseInt(arrayOfDates[1]);
        int year = Integer.parseInt(arrayOfDates[2]);
        if (day < 1 || month < 1 || month > 12 || year < 0) {
            return false;
        }
        int[] dayInMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (day > dayInMonth[month - 1]) {
            return false;
        }
        return true;
    }
}
