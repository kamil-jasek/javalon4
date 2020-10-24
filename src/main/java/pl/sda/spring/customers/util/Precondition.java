package pl.sda.spring.customers.util;

import java.util.Objects;

public final class Precondition {

    public static void requireNonNull(Object... args) {
        for (Object arg : args) {
            Objects.requireNonNull(arg);
        }
    }
}
