package ua.com.alevel.routing;

import ua.com.alevel.util.CalculateRoute;
import ua.com.alevel.util.FileOps;
import ua.com.alevel.util.GenerateCity;

import java.io.IOException;


public class Routing {

    public static final String FILE_DIR = "Files/";
    public static final String INPUT_DIR = FILE_DIR + "Input/";
    public static final String OUTPUT_DIR = FILE_DIR + "Output/";
    public static final String INPUT_FILE = "CitiesInput.txt";
    public static final String OUTPUT_FILE = "CitiesOutput.txt";
    public static final int ENTRIES_NUMBER = 10;
    public static final int ROUTES_NUMBER = 3;

    private final GenerateCity generator = new GenerateCity();

    public void run() {
        FileOps.createDirs(FILE_DIR, INPUT_DIR, OUTPUT_DIR);
        FileOps.createFiles(INPUT_DIR + INPUT_FILE, OUTPUT_DIR + OUTPUT_FILE);
        if (FileOps.isEmpty(INPUT_DIR + INPUT_FILE)) {
            generator.generate(INPUT_DIR + INPUT_FILE, ENTRIES_NUMBER, ROUTES_NUMBER);
        }
        FileOps.write(OUTPUT_DIR + OUTPUT_FILE, "", false);
        try {
            for (Number cost : CalculateRoute.calculate(INPUT_DIR + INPUT_FILE)) {
                FileOps.write(OUTPUT_DIR + OUTPUT_FILE, cost.toString() + System.lineSeparator(), true);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("Input location: " + System.getProperty("user.dir") + "/" + INPUT_DIR + INPUT_FILE);
        System.out.println("Output location: " + System.getProperty("user.dir") + "/" + OUTPUT_DIR + OUTPUT_FILE);
    }
}
