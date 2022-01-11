package ua.com.alevel.util;

import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

public final class ResourcesUtil {

    private static final String fileSeparator = FileSystems.getDefault().getSeparator();

    private ResourcesUtil() {
    }

    public static String getLastId(Class<?> thisClass, String table) {
        try {
            String path = System.getProperty("user.dir") + fileSeparator + "logs" + fileSeparator + "table.properties";
            List<String> lines = new BufferedReader(new FileReader(path)).lines().toList();
            Map<String, String> properties = lines.stream().map(line -> line.split("=", 2))
                    .collect(Collectors.toMap(arg -> arg[0], arg -> arg[1]));
            String returnValue = properties.get(table);
            if (returnValue == null) {
                returnValue = "0";
            }
            return returnValue;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static boolean setLastId(Class<?> thisClass, String table, String id) {
        try {
            String path = System.getProperty("user.dir") + fileSeparator + "logs" + fileSeparator + "table.properties";
            List<String> lines = new BufferedReader(new FileReader(path)).lines().toList();
            Map<String, String> properties = lines.stream().map(line -> line.split("="))
                    .collect(Collectors.toMap(arg -> arg[0], arg -> arg[1]));
            Set<String> tables = properties.keySet();
            if (tables.contains(table)) {
                properties.replace(table, id);
            } else {
                properties.put(table, id);
            }
            StringBuilder returnString = new StringBuilder();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                returnString.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append(System.lineSeparator());
            }
            boolean returnValue = writeEntries(thisClass, "table.properties", returnString.toString());
            return returnValue;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String getPath(Class<?> table) {
        try {
            List<String> lines = returnEntries(table, "app.properties");
            List<String[]> entries = lines.stream().map(line -> line.split(" = ", 2)).toList();
            Map<String, String> paths = new HashMap<>();
            entries.forEach(entry -> paths.put(entry[0], entry[1]));
            if (paths.isEmpty()) {
                throw new Exception();
            } else if (paths.get(table.getSimpleName()) == null) {
                throw new Exception();
            } else if (paths.get(table.getSimpleName()).isEmpty() || paths.get(table.getSimpleName()).isBlank()) {
                throw new Exception();
            }
            return System.getProperty("user.dir") + fileSeparator + paths.get(table.getSimpleName());
        } catch (Exception exception) {
            exception.getCause();
            String defaultPath = System.getProperty("user.dir") + fileSeparator + "junkyard";
            return defaultPath;
        }
    }

    private static List<String> returnEntries(Class<?> thisClass, String fileName) {
        String[] parts = String.valueOf(thisClass.getProtectionDomain().getCodeSource().getLocation()).split(".*\\.");
        String postfix = parts[parts.length - 1];
        switch (postfix) {
            case "jar" -> {
                return returnEntriesFromJar(thisClass, fileName);
            }
            default -> {
                return returnEntriesFromFile(fileName);
            }
        }
    }

    private static List<String> returnEntriesFromFile(String fileName) {
        try {
            URL systemResource = ClassLoader.getSystemResource(fileName);
            String path = systemResource.getPath();
            return new BufferedReader(new FileReader(path)).lines().toList();
        } catch (Exception exception) {
            return null;
        }
    }

    private static List<String> returnEntriesFromJar(Class<?> thisClass, String fileName) {
        String path = thisClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        try (JarFile jarFile = new JarFile(new File(path))) {
            ZipEntry entry = jarFile.getEntry(fileName);
            InputStream inputStream = jarFile.getInputStream(entry);
            Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream)).lines();
            return stream.toList();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static boolean writeEntries(Class<?> thisClass, String fileName, String inputValue) {
        String[] parts = String.valueOf(thisClass.getProtectionDomain().getCodeSource().getLocation()).split(".*\\.");
        String postfix = parts[parts.length - 1];
        switch (postfix) {
//            case "jar" -> {
//                return writeEntriesToJar(thisClass, fileName, inputValue);
//            }
            default -> {
                return writeEntriesToFile(fileName, inputValue);
            }
        }
    }

    private static boolean writeEntriesToJar(Class<?> thisClass, String fileName, String inputValue) {
        String path = thisClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        try (JarFile jarFile = new JarFile(new File(path))) {
            ZipEntry entry = jarFile.getEntry(fileName);
            Path entryPath = Path.of(path + fileSeparator + fileName);
            File temp = File.createTempFile(thisClass.getSimpleName(), null);
            temp.deleteOnExit();
            Path updatePath = Path.of(temp.getPath());
            FileWriter rewrite = new FileWriter(temp);
            rewrite.write(inputValue);
            rewrite.flush();
            Files.copy(updatePath, entryPath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private static boolean writeEntriesToFile(String fileName, String inputValue) {
        try {
            String path = System.getProperty("user.dir") + fileSeparator + "logs" + fileSeparator + "table.properties";
//            URL idHolderFile = ClassLoader.getSystemResource(fileName);
//            String path = idHolderFile.getPath();
            FileWriter rewrite = new FileWriter(path);
            rewrite.write(inputValue);
            rewrite.flush();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}
