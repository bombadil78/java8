package excercises.chapter03;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import static excercises.chapter03.MyLogger.LogLevel.ERROR;
import static excercises.chapter03.MyLogger.LogLevel.INFO;

public class MyLogger {

    private static final String LOG_LEVEL_PROPERTY = "mylogger.loglevel";
    private static LogLevel currentLogLevel;

    static {
        currentLogLevel = INFO;
        try {
            Scanner scanner = new Scanner(new FileInputStream("src/main/resources/application.properties"));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                String key = StringUtils.trim(parts[0]);
                String value = StringUtils.trim(parts[1]);
                if (key.equals(LOG_LEVEL_PROPERTY)) {
                    currentLogLevel = LogLevel.valueOf(value);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public enum LogLevel {
        INFO(0), WARNING(1), ERROR(2);

        private static Map<String, LogLevel> logLevelsByName = new HashMap<>();
        private int priority;

        static {
            logLevelsByName.put("info", INFO);
            logLevelsByName.put("warning", WARNING);
            logLevelsByName.put("error", ERROR);
        }

        LogLevel(int priority) {
            this.priority = priority;
        }

        public static LogLevel fromString(String representation) {
            return logLevelsByName.get(representation.toLowerCase());
        }
    }

    public static void log(LogLevel level, String message) {
        if (level.priority <= currentLogLevel.priority) {
            System.out.println(message);
        }
    }

    public static void logLambda(LogLevel level, Supplier<Boolean> cond, Supplier<String> logText) {
        if (level.priority <= currentLogLevel.priority) {
            if (cond.get()) {
                System.out.println(logText.get());
            }
        }
    }

    public static final void main(String[] args) {
        MyLogger.log(INFO, "hello world");
        final int i = 9;
        MyLogger.logLambda(INFO, () -> i == 10, () -> "hello world not shown");
        final int j = 10;
        MyLogger.logLambda(INFO, () -> j == 10, () -> "hello world shown");
        MyLogger.logLambda(ERROR, () -> j == 10, () -> "only show if error");
    }
}
