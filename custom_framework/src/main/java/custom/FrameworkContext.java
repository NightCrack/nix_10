package custom;

import custom.annotations.Autowired;
import custom.annotations.Entity;
import custom.annotations.Path;
import custom.annotations.SetClass;
import custom.factory.BeanFactory;
import custom.util.CSVTranslator;
import custom.util.CustomUtils;
import custom.util.FileOps;
import custom.util.ResourcesUtil;

import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public final class FrameworkContext {

    private final Map<Class<?>, Object> interfaceImplementations = new HashMap<>();
    private final Map<String, String> tablePaths = new HashMap<>();
    private final FrameworkSearcher frameworkSearcher;
    private Set<Class<?>> serviceInterfaces;
    private BeanFactory beanFactory;
    private Set<Class<?>> controllerInterfaces;

    private final String fileSeparator = FileSystems.getDefault().getSeparator();

    public FrameworkContext(Class<?> mainClass) {
        frameworkSearcher = new FrameworkSearcher(mainClass);
        List<Class<?>> classes = frameworkSearcher.classes();
        initServiceInterfaces(classes);
        initControllers(classes);
    }

    public List<Object> getImplementation(Class<?>... ifc) {
        List<Object> returnObjects = new ArrayList<>();
        for (Class<?> aClass : ifc) {
            returnObjects.add(interfaceImplementations.get(aClass));
        }
        return returnObjects;
    }

    private boolean hasImplementation(Class<?> ifc, List<Class<?>> classes) {
        AtomicBoolean returnValue = new AtomicBoolean(false);
        classes.stream().filter(entry -> !(entry.isInterface())).forEach(entry -> {
            if (Arrays.asList(entry.getInterfaces()).contains(ifc)) {
                returnValue.set(true);
            }
        });
        return returnValue.get();
    }

    public void configureTableClasses() {
        frameworkSearcher.classes()
                .forEach(aClass -> {
                    Field[] fields = aClass.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(SetClass.class)) {
                            field.setAccessible(true);
                            try {
                                Class<?> compareClass = null;
                                for (Class<?> ifc : interfaceImplementations.keySet()) {
                                    if (ifc.getSimpleName().toLowerCase(Locale.ROOT).equals(field.getName().toLowerCase(Locale.ROOT))) {
                                        compareClass = ifc;
                                    }
                                }
                                Class<?> setClass = interfaceImplementations.get(compareClass).getClass();
                                field.set(aClass, setClass);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void configureTablePaths() {
        interfaceImplementations.forEach((ifc, impl) -> {
            Field[] fields = impl.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Path.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(impl, tablePaths.get(ifc.getSimpleName().substring(0, ifc.getSimpleName().length() - 3)));
                    } catch (IllegalAccessException exception) {
                        throw new RuntimeException(exception);
                    }
                }
            }
        });
    }

//    public void configureCounters() {
//        List<Class<?>> classes = frameworkSearcher.classes().stream()
//                .filter(aClass -> Arrays
//                        .stream(aClass
//                                .getDeclaredFields())
//                        .anyMatch(field -> field
//                                .getName().startsWith("counter")))
//                .toList();
//        for (Class<?> aClass : classes) {
//            Arrays.stream(aClass.getDeclaredFields()).filter(field -> field
//                            .getName().startsWith("counter"))
//                    .forEach(field -> {
//                        String tableName = field.getDeclaringClass().getSimpleName() + field.getName();
//                        if (!(ResourcesUtil.getLastId(aClass, tableName) == null ||
//                                ResourcesUtil.getLastId(aClass, tableName).isBlank())) {
//                            ResourcesUtil.setLastId(aClass, tableName, "0");
//                        }
//                    });
//        }
//    }

    public void configureTables() {
        String defaultPath = System.getProperty("user.dir") + fileSeparator + "logs";
        FileOps.createDirs(defaultPath);
        defaultPath = defaultPath + fileSeparator + "table.properties";
        FileOps.createFiles(defaultPath);
        frameworkSearcher.classes()
                .stream()
                .filter(aClass -> aClass.isAnnotationPresent(Entity.class))
                .forEach(entity -> {
                    String path = ResourcesUtil.getPath(entity);
                    FileOps.createDirs(path);
                    path = path + FileSystems.getDefault().getSeparator() + entity.getSimpleName() + ".csv";
                    tablePaths.put(entity.getSimpleName(), path);
                    FileOps.createFiles(path);
                    if (FileOps.isEmpty(path)) {
                        FileOps.write(path, CSVTranslator
                                .buildOutput(Collections
                                        .singletonList(CSVTranslator
                                                .buildHeader(entity))), false);
                        String entityIdField = CSVTranslator.buildHeader(entity)[0];
                        if (CustomUtils.isNumeric(entity, entityIdField)) {
                            ResourcesUtil.setLastId(entity, entity.getSimpleName(), "0");
                        }
                    }
                });
    }

    public Set<Object> getControllers() {
        Set<Object> controllers = new HashSet<>();
        interfaceImplementations.forEach((ifc, impl) -> {
            if (controllerInterfaces.contains(ifc)) {
                controllers.add(impl);
            }
        });
        return controllers;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public FrameworkSearcher getFrameworkSearcher() {
        return frameworkSearcher;
    }

    public void initBeanMap() {
        serviceInterfaces.forEach(ifc -> {
            Object implementation = beanFactory.beanByInterface(ifc);
            if (implementation != null) {
                interfaceImplementations.put(ifc, implementation);
            }
        });
    }

    public void configureBeanMap() {
        interfaceImplementations.forEach((ifc, impl) -> {
            Field[] fields = impl.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(impl, interfaceImplementations.get(field.getType()));
                    } catch (IllegalAccessException exception) {
                        throw new RuntimeException(exception);
                    }
                }
            }
        });
    }

    private void initControllers(List<Class<?>> classes) {
        controllerInterfaces = classes
                .stream()
                .filter(Class::isInterface)
                .filter(this::isController)
                .filter(entry -> hasImplementation(entry, classes))
                .collect(Collectors.toSet());
    }

    private void initServiceInterfaces(List<Class<?>> classes) {
        this.serviceInterfaces = classes
                .stream()
                .filter(Class::isInterface)
                .filter(this::isServiceInterface)
                .filter(entry -> hasImplementation(entry, classes))
                .collect(Collectors.toSet());
    }

    private boolean isController(Class<?> ifc) {
        return ifc.getSimpleName().endsWith("Controller");
    }

    private boolean isDao(Class<?> ifc) {
        return ifc.getSimpleName().endsWith("DAO");
    }

    private boolean isDB(Class<?> ifc) {
        return ifc.getSimpleName().endsWith("DB");
    }

    private boolean isService(Class<?> ifc) {
        return ifc.getSimpleName().endsWith("Service");
    }

    private boolean isServiceInterface(Class<?> ifc) {
        return isController(ifc) ||
                isService(ifc) ||
                isDao(ifc) ||
                isDB(ifc);
    }
}
