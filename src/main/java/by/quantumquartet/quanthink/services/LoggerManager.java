package by.quantumquartet.quanthink.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

    private static final Logger logger = LogManager.getLogger(LogManager.class);

    public static void logException(Exception exception) {
        logger.error(exception, exception.getCause());
    }
}
