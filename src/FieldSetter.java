import java.lang.reflect.Field;

public class FieldSetter {
    public static void setField(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            System.out.println("Failed to set field '" + fieldName + "': " + e.getMessage());
        }
    }
}