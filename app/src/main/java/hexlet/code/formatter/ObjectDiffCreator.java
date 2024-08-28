package hexlet.code.formatter;

import hexlet.code.DifferItem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

class ObjectDiffCreator {
    public Map<String, Object> createDiff(
            Map<String, DifferItem> data, Function<Object, Object> formatValue
    ) throws Exception {
        var result = new LinkedHashMap<String, Object>();
        var entries = data.entrySet();

        for (var entry : entries) {
            var key = entry.getKey();
            var differItem = entry.getValue();
            var values = differItem.getValues();
            var formattedValue = formatValue.apply(values.get(0));

            if (differItem.getState().equals("added")) {
                result.put("+ " + key, formattedValue);
            }

            if (differItem.getState().equals("removed")) {
                result.put("- " + key, formattedValue);
            }

            if (differItem.getState().equals("unchanged")) {
                result.put("  " + key, formattedValue);
            }

            if (differItem.getState().equals("changed")) {
                result.put("- " + key, formattedValue);
                result.put("+ " + key, formatValue.apply(values.get(1)));
            }
        }

        return result;
    }
}
