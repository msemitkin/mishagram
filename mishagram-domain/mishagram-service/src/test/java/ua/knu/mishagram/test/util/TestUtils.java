package ua.knu.mishagram.test.util;


import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;

public class TestUtils {

    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T is(T expected) {
        return argThat(actual -> Objects.equals(toJson(actual), toJson(expected)));
    }

    public static void assertJsonModelsEquals(Object expected, Object actual) {
        Assertions.assertEquals(toJson(expected), toJson(actual));
    }
}
