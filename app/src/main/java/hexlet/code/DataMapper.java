package hexlet.code;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

class DataMapper {
    private ObjectMapper mapper;
    private String fileExtension = "json";

    public DataMapper(String fileExtension) {
        this.fileExtension = fileExtension;

        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper = objectMapper;
    }


    public Map<String, Object> getFileDataAsMap(String data) throws Exception {
        var currentMapper = mapper;
        if (this.fileExtension.equals("yml")) {
            currentMapper = new ObjectMapper(new YAMLFactory());
        }
        return currentMapper.readValue(data, new TypeReference<Map<String, Object>>() { });
    }

    public String getFormattedOutput(Map<String, Object> data) throws Exception {
        var printer = new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter("  ", "\n"));
        var json = mapper.writer(printer).writeValueAsString(data);
        return json.replace("\"", "").replace(" :", ":").replace(",", "").trim();
    }
}
