package hexlet.code.formatter;

import hexlet.code.Beautifier;
import hexlet.code.DifferItem;

import java.util.LinkedHashMap;
import java.util.Map;


public class StylishFormatter implements Formatter {
    public String format(Map<String, DifferItem> data) throws Exception {
        var mapper = new Beautifier();
        var result = new LinkedHashMap<String, Object>();
        var entries = data.entrySet();

        for (var entry : entries) {
            var key = entry.getKey();
            var differItem = entry.getValue();
            var values = differItem.getValues();

            if (differItem.getState().equals("added")) {
                result.put("+ " + key, values.get(0));
            }

            if (differItem.getState().equals("removed")) {
                result.put("- " + key, values.get(0));
            }

            if (differItem.getState().equals("unchanged")) {
                result.put("  " + key, values.get(0));
            }

            if (differItem.getState().equals("changed")) {
                result.put("- " + key, values.get(0));
                result.put("+ " + key, values.get(1));
            }
        }

        var json = mapper.getFormattedOutput(result);

        return json;
    }
}
