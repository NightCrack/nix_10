package ua.com.alevel.uniquename;

import ua.com.alevel.util.FileOps;
import ua.com.alevel.util.StringEdit;

import static ua.com.alevel.util.GenerateName.word;

public class UniqueName {

    public static final String FILE_DIR = "Files/";
    public static final String INPUT_DIR = FILE_DIR + "Input/";
    public static final String INPUT_FILE = "NamesInput.txt";
    public static final int ENTRIES_NUMBER = 1000;

    public void run() {
        FileOps.createDirs(FILE_DIR, INPUT_DIR);
        FileOps.createFiles(INPUT_DIR + INPUT_FILE);
        System.out.println();
        System.out.println("Input location: " + System.getProperty("user.dir") + "/" + INPUT_DIR + INPUT_FILE);
        if (FileOps.isEmpty(INPUT_DIR + INPUT_FILE)) {
            for (int index = 0; index < ENTRIES_NUMBER; index++) {
                FileOps.write(INPUT_DIR + INPUT_FILE, word((int) (Math.random() * 10)), true);
            }
        }
        System.out.println();
        System.out.println("First unique name: " + StringEdit.uniqueName(INPUT_DIR + INPUT_FILE));
    }
}
