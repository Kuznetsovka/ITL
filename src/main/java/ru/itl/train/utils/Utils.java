package ru.itl.train.utils;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

/**
 * @author Kuznetsovka 18.07.2023
 */
public class Utils {

    /**
     * Возвращает ServerErrorMessage из дерева исключений
     */
    public static ServerErrorMessage getExceptionErrorMessage(Throwable ex) {
        ServerErrorMessage sem = null;
        while (ex != null) {
            if (PSQLException.class.isAssignableFrom(ex.getClass())) {
                sem = ((PSQLException) ex).getServerErrorMessage();
            }
            ex = ex.getCause();
        }
        return sem;
    }
}
