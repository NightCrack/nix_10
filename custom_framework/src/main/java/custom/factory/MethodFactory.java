package custom.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class MethodFactory {

    public static <CLASS> Method getSet(CLASS object, Field field, boolean get) {
        return getSet(object, field.getName(), get);
    }

    public static <CLASS> Method getSet(CLASS object, String fieldName, boolean get) {
        try {
            String prefix = "get";
            if (!get) {
                prefix = "set";
            }
            String methodName = prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
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
        } catch (Exception exception) {
            return null;
        }
    }
}
