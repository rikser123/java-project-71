package hexlet.code.formatter;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DifferItem;
import java.util.function.Function;

import java.util.Map;

public final class JSONFormatter implements Formatter {
    public String getBeautifiedOutput(Map<String, Object> data) throws Exception {
        var mapper = new ObjectMapper();
        var printer = new DefaultPrettyPrinter();
        return mapper.writer(printer).writeValueAsString(data);
    }

    public String format(Map<String, DifferItem> data) throws Exception {
        Function<Object, Object> valueFormatter = value -> value;
        var result = new ObjectDiffCreator().createDiff(data, valueFormatter);

        var text = getBeautifiedOutput(result);

        return text;
    }
}
