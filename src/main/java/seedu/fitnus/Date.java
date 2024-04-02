package seedu.fitnus;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date {
    // reference: https://www.javatpoint.com/java-get-current-date
    private String currentDate;

    public Date() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date);

        this.currentDate = formattedDate;
    }

    public String getDate() {
        return currentDate;
    }

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
