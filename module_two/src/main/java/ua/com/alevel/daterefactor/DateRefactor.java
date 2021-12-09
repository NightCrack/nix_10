package ua.com.alevel.daterefactor;


import ua.com.alevel.util.DateFormat;
import ua.com.alevel.util.FileOps;
import ua.com.alevel.util.GenerateDate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DateRefactor {

    public static final String FILE_DIR = "Files/";
    public static final String INPUT_DIR = FILE_DIR + "Input/";
    public static final String OUTPUT_DIR = FILE_DIR + "Output/";
    public static final String INPUT_FILE = "DatesInput.txt";
    public static final String OUTPUT_FILE = "DatesOutput.txt";
    public static final int ENTRIES_NUMBER = 1000;

    public void run() {
        FileOps.createDirs(FILE_DIR, INPUT_DIR, OUTPUT_DIR);
        FileOps.createFiles(INPUT_DIR + INPUT_FILE, OUTPUT_DIR + OUTPUT_FILE);
        if (FileOps.isEmpty(INPUT_DIR + INPUT_FILE)) {
            for (int i = 0; i < ENTRIES_NUMBER; i++) {
                FileOps.write(INPUT_DIR + INPUT_FILE, GenerateDate.randomDateString() + System.lineSeparator(), true);
            }
        }
        List<String> toPrint = new ArrayList<>();
        try {
            toPrint = FileOps.read(INPUT_DIR + INPUT_FILE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        FileOps.write(OUTPUT_DIR + OUTPUT_FILE, "", false);
        for (String string : toPrint) {
            for (String date : DateFormat.convertDate(string)) {
                FileOps.write(OUTPUT_DIR + OUTPUT_FILE, date + System.lineSeparator(), true);
            }
        }
        System.out.println();
        System.out.println("Input location: " + System.getProperty("user.dir") + "/" + INPUT_DIR + INPUT_FILE);
        System.out.println("Output location: " + System.getProperty("user.dir") + "/" + OUTPUT_DIR + OUTPUT_FILE);
        System.out.println("RegEx for manual search: (([1-9]([0-9]{0,3})?)/((0[1-9])|(1[0-2]))/((0[1-9])|([1-2][0-9])|(3[0,1])))|" +
                "(((0[1-9])|([1-2][0-9])|(3[0,1]))/((0[1-9])|(1[0-2]))/([1-9]([0-9]{0,3})?))|" +
                "(((0[1-9])|(1[0-2]))-((0[1-9])|([1-2][0-9])|(3[0,1]))-([1-9]([0-9]{0,3})?))");
    }


}
