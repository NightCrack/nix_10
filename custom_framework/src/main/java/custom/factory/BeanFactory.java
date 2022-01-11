package custom.factory;

import custom.FrameworkSearcher;
import custom.annotations.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public final class BeanFactory {

    private FrameworkSearcher frameworkSearcher;

    public void setFrameworkSearcher(FrameworkSearcher frameworkSearcher) {
        this.frameworkSearcher = frameworkSearcher;
    }

    public <IFC> IFC beanByInterface(Class<IFC> ifc) {
        try {
            Set<Class<? extends IFC>> implementations = frameworkSearcher.getImplementations(ifc);
            for (Class<? extends IFC> implementation : implementations) {
                if (implementation.isAnnotationPresent(Service.class)) {
                    return implementation.getDeclaredConstructor().newInstance();
                }
            }
            return null;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }
}
