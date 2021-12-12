package ua.com.alevel.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Factory {

    public static <CLASS> Method getSet(CLASS object, Field field, boolean get) throws NoSuchMethodException {
        String prefix = "get";
        if (!get) {
            prefix = "set";
        }
        String methodName = prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Class[] arg = new Class[0];
        for (Method method :
                object.getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                arg = method.getParameterTypes();
            }
        }
        Method returnMethod = object.getClass().getDeclaredMethod(methodName, arg);
        returnMethod.setAccessible(true);
        return returnMethod;
    }
}
