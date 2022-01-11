package custom.util;


import custom.factory.MethodFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CSVTranslator {

    private static final String lineSeparator = System.lineSeparator();
    private static final char quote = '\"';
    private static final String valueOf = "valueOf";
    private static final String csvEntry = "\".*?(?<!\\\\)\"";

    public static <ENTITY> String objectsToString(List<ENTITY> objects) {
        try {
            List<String[]> fields = new ArrayList<>();
            LinkedHashMap<Class<?>, Field[]> objectStructure = new LinkedHashMap<>();
            Class<?> currentClass = objects.get(0).getClass();
            while (!currentClass.equals(Object.class)) {
                List<Field> filtered = Arrays.stream(currentClass
                                .getDeclaredFields())
                        .filter(field -> !(Modifier
                                .isTransient(field
                                        .getModifiers())))
                        .toList();
                Field[] applicableFields = filtered.toArray(new Field[0]);
                objectStructure.put(currentClass, applicableFields);
                currentClass = currentClass.getSuperclass();
            }
            for (Object object : objects) {
                String[] line = new String[0];
                for (Map.Entry<Class<?>, Field[]> classEntry : objectStructure.entrySet()) {
                    int currentPosition = line.length;
                    line = Arrays.copyOf(line, line.length + classEntry.getValue().length);
                    for (int index = currentPosition, counter = 0; index < line.length; index++, counter++) {
                        Method getMethod = MethodFactory.getSet(object, classEntry.getValue()[counter], true);
                        String value = String.valueOf(getMethod.invoke(object));
                        if (value.equals("null")) {
                            line[index] = "[]";
                        } else {
                            line[index] = value;
                        }
                    }
                }
                String[] buffer = new String[line.length];
                for (int index = 0; index < line.length; index++) {
                    buffer[index] = line[line.length - index - 1];
                }
                fields.add(buffer);
            }
            return buildOutput(fields);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String[] buildHeader(Class<?> objectClass) {
        List<String> fieldNames = new ArrayList<>();
        Class<?> currentClass = objectClass;
        while (!currentClass.equals(Object.class)) {
            fieldNames.addAll(Arrays.stream(currentClass
                            .getDeclaredFields())
                    .filter(field -> !(Modifier
                            .isTransient(field.getModifiers())))
                    .map(Field::getName).toList());
            currentClass = currentClass.getSuperclass();
        }
        String[] returnArray = new String[fieldNames.size()];
        for (int index = 0; index < fieldNames.size(); index++) {
            returnArray[index] = fieldNames.get(fieldNames.size() - 1 - index);
        }
        return returnArray;
    }

    public static String buildOutput(List<String[]> extractedFields) {
        StringBuilder output = new StringBuilder();
        for (String[] strings : extractedFields) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < strings.length; index++) {
                if (index < (strings.length - 1)) {
                    stringBuilder.append(quote).append(strings[index]).append(quote).append(',');
                } else {
                    stringBuilder.append(quote).append(strings[index]).append(quote).append(lineSeparator);
                }
            }
            output.append(stringBuilder);
        }
        return output.toString();
    }

    private static List<String> lineParser(String line) {
        List<String> returnValue = new ArrayList<>();
        Matcher entry = Pattern.compile(csvEntry).matcher(line);
        while (entry.find()) {
            returnValue.add(entry.group().split("\"")[1]);
        }
        return returnValue;
    }

    public static List<String> idList(String path, String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String header = reader.readLine();
            List<String> parsedHeader = lineParser(header);
            int idIndex = parsedHeader.indexOf(id);
            List<String> returnList = new ArrayList<>();
            while (reader.ready()) {
                String currentLine = reader.readLine();
                returnList.add(lineParser(currentLine).get(idIndex));
            }
            return returnList;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static <ENTITY> List<ENTITY> buildNewEntitiesFromInput(List<String> input, ENTITY className) {
        try {
            List objects = new ArrayList<>();
            for (int index = 0; index < input.size(); index++) {
                objects.add(((Class<?>) className).getDeclaredConstructor().newInstance());
            }
            for (int indexA = 0; indexA < objects.size(); indexA++) {
                List<String> buffer = new ArrayList<>();
                List<String> values = new ArrayList<>();
                Matcher check = Pattern.compile(csvEntry).matcher(input.get(indexA));
                while (check.find()) {
                    buffer.add(check.group().substring(1, check.group().length() - 1));
                }
                for (int indexB = 0; indexB < buffer.size(); indexB++) {
                    values.add(buffer.get(buffer.size() - 1 - indexB));
                }
                List<Field> fields = new ArrayList<>();
                Class<?> currentClass = (Class<?>) className;
                while (!currentClass.equals(Object.class)) {
                    List<Field> applicableFields = Arrays.stream(currentClass
                                    .getDeclaredFields())
                            .filter(field -> !(Modifier
                                    .isTransient(field
                                            .getModifiers())))
                            .toList();
                    fields.addAll(applicableFields);
                    currentClass = currentClass.getSuperclass();
                }
                Iterator<String> valuesIterator = values.iterator();
                for (Field field : fields) {
                    String value = valuesIterator.next();
                    if (Iterable.class.isAssignableFrom(field.getType())) {
                        List fieldObject = new ArrayList<>();
                        String genericDeclaration = field.getGenericType().getTypeName();
                        String fieldElementName = genericDeclaration.split("^.*<")[1].split(">.*$")[0];
                        Class<?> fieldElementClass = Class.forName(fieldElementName);
                        value = value.substring(1, value.length() - 1);
                        String[] elements = value.split(", ");
                        for (String element : elements) {
                            Object entry = anyObjectSetter(fieldElementClass, element);
                            if (!(entry.toString().isBlank() || entry.toString().equals("0"))) {
                                fieldObject.add(entry);
                            }
                        }
                        field.setAccessible(true);
                        field.set(objects.get(indexA), fieldObject);
                    } else {
                        Method setMethod = MethodFactory.getSet(objects.get(indexA), field, false);
                        Class<?> fieldClass = field.getType();
                        Object fieldValue = anyObjectSetter(fieldClass, value);
                        setMethod.invoke(objects.get(indexA), fieldValue);
                    }
                }
            }
            return (List<ENTITY>) objects;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static <ENTITY> ENTITY anyObjectSetter(ENTITY fieldClass, String value) throws Exception {
        String className = String.valueOf(fieldClass);
        className = className.replace("class ", "");
        if (Number.class.isAssignableFrom(Class.forName(className)) && value.isBlank()) {
            value = "0";
        }
        Constructor[] classConstructors = (Class.forName(className)).getDeclaredConstructors();
        Object object = null;
        for (Constructor constructor : classConstructors) {
            if (constructor.getParameters().length == 0) {
                object = constructor.newInstance();
                break;
            } else if (constructor.getParameters()[0].getType().equals(String.class)) {
                object = constructor.newInstance("0");
                break;
            } else if (constructor.getParameters().length == 1 &&
                    constructor.getParameters()[0].getType().isPrimitive()) {
                object = constructor.newInstance(0);
            }
        }
        assert object != null;
        Method fieldClassMethod = null;
        List<Method> fieldClassMethods = Arrays.stream(object.getClass()
                        .getDeclaredMethods())
                .filter(method -> method
                        .getName()
                        .equals(valueOf) && method.getParameterCount() == 1).toList();
        for (Method method : fieldClassMethods) {
            if (method.getParameters()[0].getType().equals(String.class)) {
                fieldClassMethod = method;
                break;
            } else if (method.getParameters()[0].getType().equals(Object.class)) {
                fieldClassMethod = method;
            }
        }
        assert fieldClassMethod != null;
        object = fieldClassMethod.invoke(fieldClass, value);
        return (ENTITY) object;
    }
}
