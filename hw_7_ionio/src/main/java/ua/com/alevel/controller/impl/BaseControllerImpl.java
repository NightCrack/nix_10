package ua.com.alevel.controller.impl;

import custom.annotations.Autowired;
import custom.annotations.Service;
import custom.factory.MethodFactory;
import ua.com.alevel.controller.BaseController;
import ua.com.alevel.entity.*;
import ua.com.alevel.service.AuthorsService;
import ua.com.alevel.service.BookInstancesService;
import ua.com.alevel.service.BooksService;
import ua.com.alevel.service.GenresService;
import ua.com.alevel.util.CSVTranslator;
import ua.com.alevel.util.Factory;
import ua.com.alevel.util.Populate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BaseControllerImpl implements BaseController {

    private static final String CREATE_VALUE = "new";
    private static final String UPDATE_VALUE = "upd";
    private static final String DELETE_VALUE = "del";
    private static final String FIND_ONE_VALUE = "one";
    private static final String FIND_ALL_VALUE = "all";
    private static final String EXIT_VALUE = "ext";
    private static final String POPULATE_VALUE = "pop";
    private static final String MENU_VALUE = "tip";
    private static final String[] DATABASE_OBJECTS = {"Author", "Genre", "Book", "BookInstance"};
    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private AuthorsService<Long> authorsService;
    @Autowired
    private BookInstancesService<Long> bookInstancesService;
    @Autowired
    private BooksService<String> booksService;
    @Autowired
    private GenresService<Long> genresService;

    private boolean isPartOfTable(Field field) {
        String name = editFieldName(field.getName());
        List<Class<? extends BaseEntity>> classes = new ArrayList<>();
        classes.add(Author.class);
        classes.add(Book.class);
        classes.add(BookInstance.class);
        classes.add(Genre.class);
        for (Class aClass : classes) {
            if (aClass.getSimpleName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void printTable(Field field) {
        try {
            String name = field.getDeclaringClass().getPackageName() + '.' + editFieldName(field.getName());
            List entries = getAllEntries((Class<? extends BaseEntity>) Class.forName(name));
            List<String> names = new ArrayList<>();
            for (Object entry : entries) {
                Method currentMethod = MethodFactory.getSet(entry, "Name", true);
                String entryName = String.valueOf(currentMethod.invoke(entry));
                names.add(entryName);
            }
            names.forEach(System.out::println);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private String idToName(String id, Field field) {
        try {
            String name = field.getDeclaringClass().getPackageName() + '.' + editFieldName(field.getName());
            Object entry = findEntry(Class.forName(name), id);
            String returnValue = (String) MethodFactory.getSet(entry.getClass(), "Name", true).invoke(entry);
            return returnValue;
        } catch (Exception exception) {
            return null;
        }
    }

    private String nameToId(String entryName, Field field) {
        try {
            String name = field.getDeclaringClass().getPackageName() + '.' + editFieldName(field.getName());
            Class<?> entryTableClass = Class.forName(name);
            List entries = getAllEntries((Class<? extends BaseEntity>) entryTableClass);
            for (Object entry : entries) {
                String currentName = (String) MethodFactory.getSet(entry, "Name", true).invoke(entry);
                if (currentName.equals(entryName)) {
                    Method currentMethod = MethodFactory.getSet(entry, "Id", true);
                    if (currentMethod == null) {
                        currentMethod = MethodFactory.getSet(entry, "Isbn", true);
                    }
                    String entryId = String.valueOf(currentMethod.invoke(entry));
                    return entryId;
                }
            }
        } catch (Exception exception) {
            System.out.println("fail");
        }
        return null;
    }

    private Object entryInput(Class<? extends BaseEntity> entity) {
        Object object;
        try {
            object = entity.getDeclaredConstructor().newInstance();
            List<Field> fields = Arrays.stream(entity
                            .getDeclaredFields())
                    .filter(field -> !(Iterable.class
                            .isAssignableFrom(field.getType()) ||
                            field.getType().isArray()))
                    .filter(field -> !(Modifier
                            .isTransient(field.getModifiers())))
                    .toList();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (isPartOfTable(field)) {
                    fieldName = editFieldName(field.getName());
                    System.out.println("Existing " + fieldName + "s: ");
                    printTable(field);
                }
                System.out.print("Input " + fieldName + ": ");
                String input = scanner.nextLine();
                if (input.isBlank()) {
                    throw new Exception();
                }
                if (isPartOfTable(field)) {
                    input = nameToId(input, field);
                }
                Method setMethod = Factory.getSet(object, field, false);
                Class<?> fieldClass = field.getType();
                Method fieldClassMethod = null;
                List<Method> fieldClassMethods = Arrays.stream(fieldClass
                                .getDeclaredMethods())
                        .filter(method -> method
                                .getName()
                                .equals("valueOf") && method.getParameterCount() == 1).toList();
                for (Method method : fieldClassMethods) {
                    if (method.getParameters()[0].getType().equals(String.class)) {
                        fieldClassMethod = method;
                        break;
                    } else if (method.getParameters()[0].getType().equals(Object.class)) {
                        fieldClassMethod = method;
                    }
                }
                assert fieldClassMethod != null;
                setMethod.invoke(object, fieldClassMethod.invoke(fieldClass, input));
            }
            return object;
        } catch (Exception exception) {
            System.out.println("Wrong parameter, redo.");
            return null;
        }
    }

    private Object sortedService(String objectName) {
        AtomicReference<Object> sortedClass = new AtomicReference<>();
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .filter(field -> field.getType().isInterface())
                .forEach(field -> {
                    if (field.getName().startsWith(objectName)) {
                        field.setAccessible(true);
                        try {
                            sortedClass.set(field.get(this));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return sortedClass.get();
    }

    private String makeServiceName(Class<?> entity) {
        String objectName = entity
                .getSimpleName()
                .substring(0, 1)
                .toLowerCase(Locale.ROOT) +
                entity
                        .getSimpleName()
                        .substring(1);
        return objectName;
    }

    private void printAllEntries(Class<? extends BaseEntity> entity) {
        System.out.println();
        getAllEntries(entity).forEach(entry ->
        {
            try {
                String output = (String) entry.getClass().getDeclaredMethod("toStringBrief").invoke(entry);
                System.out.println(output);
            } catch (Exception exception) {
                System.out.println("fail");
            }
        });
    }

    private List getAllEntries(Class<? extends BaseEntity> entity) {
        try {
            Object service = sortedService(makeServiceName(entity));
            Method findAllMethod = service.getClass().getMethod("findAll");
            List entries = (List) findAllMethod.invoke(service);
            return entries;
        } catch (Exception exception) {
            return null;
        }
    }

    private String editFieldName(String fieldName) {
        String returnValue = "";
        if (fieldName.endsWith("Id")) {
            returnValue = fieldName.replace("Id", "").substring(0, 1).toUpperCase() +
                    fieldName.replace("Id", "").substring(1);
        } else if (fieldName.endsWith("Isbn")) {
            returnValue = fieldName.replace("Isbn", "").substring(0, 1).toUpperCase() +
                    fieldName.replace("Isbn", "").substring(1);
        }
        return returnValue;
    }

    private boolean checkConditions(Class<? extends BaseEntity> aClass) {
        try {
            boolean isIdPresent = (Arrays.stream(aClass.getDeclaredFields()).anyMatch(field -> field.getName().endsWith("Id")));
            isIdPresent = isIdPresent || Arrays.stream(aClass.getDeclaredFields()).anyMatch(field -> field.getName().endsWith("Isbn"));
            if (isIdPresent) {
                List<String> fields = Arrays.stream(aClass
                                .getDeclaredFields())
                        .filter(field -> field
                                .getName()
                                .endsWith("Id") || field
                                .getName()
                                .endsWith("Isbn"))
                        .map(field -> editFieldName(field.getName()))
                        .toList();
                fields = fields.stream().filter(field -> {
                    try {
                        return getAllEntries((Class<? extends BaseEntity>) Class.forName(aClass.getPackageName() + "." + field)).size() < 1;
                    } catch (Exception e) {
                        return true;
                    }
                }).toList();
                if (fields.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int index = 0; index < fields.size(); index++) {
                        stringBuilder.append(fields.get(index));
                        if (index < fields.size() - 1) {
                            stringBuilder.append(", ");
                        } else {
                            stringBuilder.append('.');
                        }
                    }
                    System.out.println("Can't be created. Create: " + stringBuilder);
                    return false;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    private void insertEntry(Class<? extends BaseEntity> entity) {
        if (checkConditions(entity)) {
            Object object = entryInput(entity);
            try {
                assert object != null;
                String serviceName = makeServiceName(object.getClass());
                Object sortedClass = sortedService(serviceName);
                Method createMethod = sortedClass.getClass().getMethod("create", object.getClass());
                createMethod.invoke(sortedClass, object);
            } catch (Exception exception) {
                System.out.println("Insertion failed. Please, retry");
            }
        }
    }

    private void printEntry(Class<? extends BaseEntity> entity) {
        System.out.println();
        System.out.print("Input " + entity.getSimpleName() + "'s Id: ");
        String idInput = scanner.nextLine();
        Object toPrint = findEntry(entity, idInput);
        if (toPrint != null) {
            System.out.println(toPrint);
        }
    }

    private Object findEntry(Class<?> entity, String idInput) {
        try {
            Object service = sortedService(makeServiceName(entity));
            List<Method> methods = List.of(service.getClass().getDeclaredMethods());
            Method foundedMethod = methods.stream()
                    .filter(method -> method.getName().equals("findById"))
                    .filter(method -> !(method.getParameterTypes()[0]
                            .equals(Object.class))).toList().get(0);
            Class<?> idClass = foundedMethod.getParameterTypes()[0];
            Object id = CSVTranslator.anyObjectSetter(idClass, idInput);
            Object entry = foundedMethod.invoke(service, id);
            return entry;
        } catch (Exception exception) {
            System.out.println();
            System.out.println("No entries by this id.");
            return null;
        }
    }

    private void updateEntry(Class<? extends BaseEntity> entity) {
        System.out.println();
        System.out.print("Input " + entity.getSimpleName() + "'s Id: ");
        String idInput = scanner.nextLine();
        Object oldObject = findEntry(entity, idInput);
        if (oldObject != null) {
            Object newObject = entryInput(entity);
            if (newObject != null) {
                List<Field> fields = List.of(entity.getDeclaredFields());
                fields = fields.stream()
                        .filter(field -> !(Modifier.isTransient(field.getModifiers())))
                        .filter(field -> !(Iterable.class.isAssignableFrom(field.getType())))
                        .toList();
                try {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(newObject);
                        if (fieldValue != null) {
                            field.set(oldObject, fieldValue);
                        }
                    }
                    Object service = sortedService(makeServiceName(entity));
                    List<Method> methods = List.of(service.getClass().getDeclaredMethods());
                    Method foundedMethod = methods.stream()
                            .filter(method -> method
                                    .getName()
                                    .equals("update"))
                            .filter(method -> !(method
                                    .getParameterTypes()[0]
                                    .equals(Object.class))).toList().get(0);
                    foundedMethod.invoke(service, oldObject);
                } catch (Exception exception) {
                    System.out.println("fail");
                }
            }
        }
    }

    private void deleteEntry(Class<? extends BaseEntity> entity) {
        try {
            System.out.println();
            System.out.print("Input " + entity.getSimpleName() + "'s Id: ");
            String idInput = scanner.nextLine();
            Object service = sortedService(makeServiceName(entity));
            List<Method> methods = List.of(service.getClass().getDeclaredMethods());
            Method foundedMethod = methods.stream()
                    .filter(method -> method.getName()
                            .equals("delete"))
                    .filter(method -> !(method
                            .getParameterTypes()[0]
                            .equals(Object.class))).toList().get(0);
            Class<?> idClass = foundedMethod.getParameterTypes()[0];
            Object id = CSVTranslator.anyObjectSetter(idClass, idInput);
            foundedMethod.invoke(service, id);
        } catch (Exception exception) {
            System.out.println("fail");
        }

    }

    @Override
    public void run() {
        messages("presentation");
        messages("menu");
        while (true) {
            messages("input");
            String input = scanner.nextLine();
            crud(input);
        }
    }


    private void messages(String opt) {
        switch (opt) {
            case "presentation" -> {
                System.out.println();
                System.out.println("This DB is made alike to \"Express Tutorial: The Local Library website\"");
                System.out.println("https://developer.mozilla.org/en-US/docs/Learn/Server-side/Express_Nodejs/Tutorial_local_library_website");
            }
            case "menu" -> {
                System.out.println();
                System.out.println("Make your choice:");
                System.out.println("Database objects names: " + Arrays.toString(DATABASE_OBJECTS));
                System.out.println("Date format: YYYY-(M)M-(D)D");
                System.out.println("Create an object - enter 'objectName' + whitespace + '" + CREATE_VALUE + "';");
                System.out.println("Update an object - enter 'objectName' + whitespace + '" + UPDATE_VALUE + "';");
                System.out.println("Delete an object - enter 'objectName' + whitespace + '" + DELETE_VALUE + "';");
                System.out.println("Find an object by ID - enter 'objectName' + whitespace + '" + FIND_ONE_VALUE + "';");
                System.out.println("List all object - enter 'objectName' + whitespace + '" + FIND_ALL_VALUE + "';");
                System.out.println("Populate base with genres, authors, books and bookInstances - enter '" + POPULATE_VALUE + "';");
                System.out.println("Call this menu - enter '" + MENU_VALUE + "';");
                System.out.println("Exit the program - enter '" + EXIT_VALUE + "';");
            }
            case "input" -> {
                System.out.println();
                System.out.print("Your input (tip?): ");
            }
            case "badInput" -> {
                System.out.println();
                System.out.println("Incorrect input.");
            }
        }
    }


    private void crud(String input) {
        if (input.equals(MENU_VALUE)) {
            messages("menu");
            return;
        } else if (input.equals(EXIT_VALUE)) {
            System.exit(0);
        } else if (input.equals(POPULATE_VALUE)) {
            Populate.PopulateBase(authorsService, genresService, booksService, bookInstancesService, 5);
            return;
        }
        try {
            String[] parsedInput = input.split(" ");
            Class<? extends BaseEntity> currentClass = (Class<? extends BaseEntity>) Class.forName(BaseEntity.class.getPackageName() + '.' + parsedInput[0]);
            String currentAction = parsedInput[1];
            switch (currentAction) {
                case CREATE_VALUE -> insertEntry(currentClass);
                case UPDATE_VALUE -> updateEntry(currentClass);
                case DELETE_VALUE -> deleteEntry(currentClass);
                case FIND_ONE_VALUE -> printEntry(currentClass);
                case FIND_ALL_VALUE -> printAllEntries(currentClass);
            }
        } catch (Exception exception) {
            messages("badInput");
        }
    }
}