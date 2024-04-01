package seedu.fitnus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVWriter {
    private static CSVReader csvreader = new CSVReader();

    public static void main(String[] args) {
        writeIntoFile("Chicken Rice", "FOOD");
    }
    public static void writeIntoFile(String foodItem, String fileName) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        File file = null;
        String x = "";
        if (fileName.toLowerCase().contains("food")) {
            file = new File("./tp/db/Output_Food_" + df.format(new Date())+".csv");
        } else {
            file = new File("./tp/db/Output_Drink_" + df.format(new Date())+".csv");
        }
        if (!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("error with creating a file");
            }
        }
        try (FileWriter fw = new FileWriter(file)){
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(csvreader.readMealInfo("./tp/db//Meal_db.csv", foodItem));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("error trying to add something into the file");
        }
    }
}
