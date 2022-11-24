package mnk;

import java.util.HashMap;
import java.util.Map;

public class ArgumentsParser {
    private final Map<String, String> parsedArguments = new HashMap<>();

    ArgumentsParser() {}
    ArgumentsParser(String[] arguments) {
        parseArguments(arguments);
    }

    public void parseArguments(String[] arguments) {
        String currentKey = "";

        for (String argument : arguments) {
            boolean isKey = argument.startsWith("--");

            if (currentKey.isEmpty() && isKey) {
                currentKey = argument.substring(2);
            } else if (!currentKey.isEmpty() && isKey) {
                parsedArguments.put(currentKey, "");
                currentKey = argument.substring(2);
            } else if (!currentKey.isEmpty()) {
                parsedArguments.put(currentKey, argument);
                currentKey = "";
            }
        }

        if (!currentKey.isEmpty()) {
            parsedArguments.put(currentKey, "");
        }
    }

    public String get(String key) {
        return parsedArguments.get(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : value;
    }

    public int getOrDefault(String key, int defaultValue) {
        String value = getOrDefault(key, String.valueOf(defaultValue));
        int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            intValue = defaultValue;
        }

        return intValue;
    }

    public boolean getOrDefault(String key, boolean defaultValue) {
        String value = get(key);
        return value != null || defaultValue;
    }

    public boolean isPresent(String key) {
        return parsedArguments.get(key) != null;
    }
}
