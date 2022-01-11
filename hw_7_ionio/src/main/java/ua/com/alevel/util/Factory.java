package ua.com.alevel.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Factory {

    public static <CLASS> Method getSet(CLASS object, Field field, boolean get) throws NoSuchMethodException {
        String prefix = "get";
        if (!get) {
            prefix = "set";
        }
        String methodName = prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Class<?>[] arg;
        Method returnMethod = null;
        Class<?> currentClass = object.getClass();
        while (!currentClass.equals(Object.class)) {
            for (Method method : currentClass.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    arg = method.getParameterTypes();
                    returnMethod = currentClass.getDeclaredMethod(methodName, arg);
                    break;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        assert returnMethod != null;
        returnMethod.setAccessible(true);
        return returnMethod;
    }
}
