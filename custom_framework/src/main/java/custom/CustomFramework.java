package custom;

import custom.factory.BeanFactory;

import java.util.List;

public final class CustomFramework {

    private static List<Object> returnObjects;

    public static void run(Class<?> mainClass, boolean startApp, Class<?>... implementationsToReturn) {
        FrameworkContext frameworkContext = new FrameworkContext(mainClass);
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.setFrameworkSearcher(frameworkContext.getFrameworkSearcher());
        frameworkContext.setBeanFactory(beanFactory);
        frameworkContext.initBeanMap();
        frameworkContext.configureBeanMap();
        frameworkContext.configureTables();
        frameworkContext.configureTablePaths();
        frameworkContext.configureTableClasses();
        returnObjects = frameworkContext.getImplementation(implementationsToReturn);
        if (startApp) {
            FrameworkStarter frameworkStarter = new FrameworkStarter(frameworkContext.getControllers());
            frameworkStarter.start();
        }
    }

    public static List<Object> getReturnObjects() {
        return returnObjects;
    }
}
