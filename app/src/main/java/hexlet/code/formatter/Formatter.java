package hexlet.code.formatter;

import java.util.Map;
import hexlet.code.DifferItem;

public interface Formatter {
    String format(Map<String, DifferItem> data) throws Exception;
}
