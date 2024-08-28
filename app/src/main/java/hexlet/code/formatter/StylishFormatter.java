package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DifferItem;
import java.util.function.Function;

import java.util.Map;

public final class StylishFormatter implements Formatter {
    private static ObjectMapper configureMapper() {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return objectMapper;
    }

    public String getBeautifiedOutput(Map<String, Object> data, ObjectMapper mapper) throws Exception {
        var printer = new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter("  ", "\n"));
        var json = mapper.writer(printer).writeValueAsString(data);
        return json.replace("\"", "").replace(" :", ":").replace(",\n", "\n").trim();
    }

    public String format(Map<String, DifferItem> data) throws Exception {
        var mapper = configureMapper();
        Function<Object, Object> valueFormatter = value -> String.valueOf(value);
        var result = new ObjectDiffCreator().createDiff(data, valueFormatter);


        var text = getBeautifiedOutput(result, mapper);

        return text;
    }
}
