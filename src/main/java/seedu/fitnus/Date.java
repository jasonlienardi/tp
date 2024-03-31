package seedu.fitnus;

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
}
