package custom.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class CustomUtils {

    private CustomUtils() {
    }

    public static boolean isNumeric(Class<?> testClass, String fieldName) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = testClass;
        while (!currentClass.equals(Object.class)) {
            fields.addAll(List.of(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }
        boolean returnValue = false;
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                returnValue = Number.class.isAssignableFrom(field.getType());
            }
        }
        return returnValue;
    }
}
