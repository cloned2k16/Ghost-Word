package es.ledg;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseObj {
    public class LogWrapper {
        Logger logger;

        LogWrapper(String className) {
            logger = Logger.getLogger(className);
        }

        public void dbg(String fmt, Object... args) {
            logger.log(Level.INFO, String.format(fmt, args));
        }

        public void err(String fmt, Object... args) {
            logger.log(Level.SEVERE, String.format(fmt, args));
        }

    }

    static Logger slog = Logger.getAnonymousLogger();

    public static void sout(String fmt, Object... args) {
        slog.log(Level.WARNING, String.format(fmt, args));
    }

    public LogWrapper log = new LogWrapper(getClassName());

    /**
     *
     * @return the automatic retrieved class name of the caller
     */
    public String getClassName() {
        try {
            return Thread.currentThread().getStackTrace()[2].getClassName();
        } catch (Exception e) {
            return "cant.get.class.name";
        }
    }
}
