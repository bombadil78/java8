package excercises.chapter03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigViewer {

    public static void main(String[] args) {
        ConfigViewer cfgViewer = new ConfigViewer(args);
    }

    public ConfigViewer(String[] args) {
        printPropertiesFromFile();
        printSystemProperties();
        printCommandLineArgs(args);
        printEnvironmentVariables();
    }

    private void printPropertiesFromFile() {
        Properties props = new Properties();
        try (Stream<String> s = Files.lines(Paths.get("src/main/resources/application.properties"))) {
            List<String> lines = s.collect(Collectors.toList());
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length != 2) throw new IllegalArgumentException("Illegal properties file format: " + line);
                props.setProperty(parts[0], parts[1]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
    }

    private void printSystemProperties() {
        Properties props = System.getProperties();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
    }

    private void printCommandLineArgs(String[] args) {
        Arrays.stream(args).forEach(System.out::println);
    }

    private void printEnvironmentVariables() {
        System.getenv().entrySet().forEach((e) -> System.out.println(String.format("%s=%s", e.getKey(), e.getValue())));
    }
}
