package ua.com.alevel.daterefactor;


import ua.com.alevel.util.DateFormat;
import ua.com.alevel.util.DateGenerator;
import ua.com.alevel.util.FileOps;

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
        FileOps.write(INPUT_DIR + INPUT_FILE, "", false);
        for (int i = 0; i < ENTRIES_NUMBER; i++) {
            FileOps.write(INPUT_DIR + INPUT_FILE, DateGenerator.randomDateString() + System.lineSeparator(), true);
        }
        List<String> toPrint = new ArrayList<>();
        try {
            toPrint = FileOps.read(INPUT_DIR + INPUT_FILE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        FileOps.write(OUTPUT_DIR + OUTPUT_FILE, "", false);
        for (String string : toPrint) {
            if (DateFormat.convertDate(string) != null) {
                FileOps.write(OUTPUT_DIR + OUTPUT_FILE, DateFormat.convertDate(string) + System.lineSeparator(), true);
            }
        }
        System.out.println();
        System.out.println("Input location: " + System.getProperty("user.dir") + "/" + INPUT_DIR + INPUT_FILE);
        System.out.println("Output location: " + System.getProperty("user.dir") + "/" + OUTPUT_DIR + OUTPUT_FILE);
    }


}
