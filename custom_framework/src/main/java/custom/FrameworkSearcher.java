package custom;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public record FrameworkSearcher(Class<?> mainClass) {

    public List<Class<?>> classes() {
        return objectSwitch();
    }

    public <IFC> Set<Class<? extends IFC>> getImplementations(Class<IFC> ifc) {
        Set<Class<? extends IFC>> classes = new HashSet<>();
        classes()
                .stream()
                .filter(entry -> !(entry
                        .isInterface()))
                .forEach(entry -> {
                    if (Arrays.asList(entry
                                    .getInterfaces())
                            .contains(ifc)) {
                        classes.add((Class<? extends IFC>) entry);
                    }
                });
        return classes;
    }

    private List<Class<?>> objectSwitch() {
        String[] parts = String.valueOf(mainClass.getProtectionDomain().getCodeSource().getLocation()).split(".*\\.");
        String postfix = parts[parts.length - 1];
        switch (postfix) {
            case "jar" -> {
                return returnClasses(returnClassesFromJar());
            }
            default -> {
                return returnClasses(returnClassFiles(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath()));
            }
        }
    }

    private String className(Object entry, String rootPackage) {
        if (entry.getClass().getSuperclass().equals(JarEntry.class)) {
            return ((JarEntry) entry)
                    .getRealName()
                    .replace(".class", "")
                    .replace("/", ".");
        } else if (entry.getClass().equals(File.class)) {
            return ((File) entry)
                    .getPath()
                    .split("^.*(?=" + rootPackage + ")")[1]
                    .replace(".class", "")
                    .replace(FileSystems.getDefault().getSeparator(), ".");
        }
        return null;
    }

    private List<Class<?>> returnClasses(List<?> foundClasses) {
        String rootPackage = mainClass.getPackageName();
        List<Class<?>> returnClasses = new ArrayList<>();

        foundClasses.forEach(classFile -> {
                    try {
                        Class<?> object = ClassLoader.getSystemClassLoader().loadClass(className(classFile, rootPackage));
                        returnClasses.add(object);
                    } catch (ClassNotFoundException exception) {
                        throw new RuntimeException(exception);
                    }
                }
        );
        return returnClasses;
    }

    private List<JarEntry> returnClassesFromJar() {
        try (JarFile jarFile = new JarFile(new File(mainClass.getProtectionDomain().getCodeSource().getLocation().getPath()))) {
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            List<JarEntry> reorganizedEntries = new ArrayList<>();
            while (jarEntries.hasMoreElements()) {
                reorganizedEntries.add(jarEntries.nextElement());
            }
            List<JarEntry> entries = reorganizedEntries
                    .stream()
                    .filter(jarEntry -> jarEntry
                            .getName()
                            .matches(".*\\.class"))
                    .filter(jarEntry -> className(jarEntry, mainClass
                            .getPackageName())
                            .matches(mainClass
                                    .getPackageName() + "\\..*"))
                    .collect(Collectors.toList());
            return entries;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private List<File> returnClassFiles(String path) {
        File currentFolder = new File(path);
        List<File> classes = Arrays.stream(Objects
                        .requireNonNull(currentFolder
                                .listFiles(file -> file
                                        .getName()
                                        .matches(".*\\.class"))))
                .collect(Collectors.toList());
        File[] folders = currentFolder.listFiles(File::isDirectory);
        assert folders != null;
        for (File folder : folders) {
            classes.addAll(returnClassFiles(folder.getPath()));
        }
        return classes;
    }
}
