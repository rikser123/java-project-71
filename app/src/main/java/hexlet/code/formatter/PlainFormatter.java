package hexlet.code.formatter;

import hexlet.code.DifferItem;

import java.util.ArrayList;
import java.util.Map;

public final class PlainFormatter implements Formatter {
    private static String formatValue(Object value) {
        if (value == null) {
            return null;
        }
        var valueType = value.getClass().getSimpleName();

        if (valueType.equals("LinkedHashMap") || valueType.equals("ArrayList")) {
            return "[complex value]";
        }

        if (valueType.equals("String")) {
            return "'" + value + "'";
        }

        return String.valueOf(value);
    }

    public String format(Map<String, DifferItem> data) throws Exception {
        var entries = data.entrySet();
        var result = new ArrayList<String>();

        for (var entry : entries) {
            var key = entry.getKey();
            var differItem = entry.getValue();
            var state = differItem.getState();
            var itemValues = differItem.getValues();
            var formattedValue = formatValue(itemValues.get(0));

            if (state.equals("removed")) {
                result.add("Property '" + key + "' was removed");
            }

            if (state.equals("added")) {
                result.add("Property '" + key + "' was added with value: " + formattedValue);
            }

            if (state.equals("changed")) {
                result.add("Property '" + key + "' was updated. From "
                        +  formattedValue + " to " + formatValue(itemValues.get(1)));
            }
        }

        return String.join("\n", result);
    }
}
