package seedu.fitnus.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Class that handles any references to the current date and time.
 */
public class Date {
    protected static String currentDate;

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
}
