package com.library.demo.utils;

public class PersistenceHelper {
    public static <T> T findCause(Exception exception, Class<T> targetType) {
        while (exception  != null) {
            if (targetType.isInstance(exception)){
                return targetType.cast(exception);
            }

            Throwable cause = exception.getCause();
            if (!(cause instanceof Exception)) {
                return null;
            }

            exception = (Exception) cause;
        }

        return null;
    }
}
