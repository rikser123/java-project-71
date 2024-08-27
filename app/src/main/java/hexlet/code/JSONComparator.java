package hexlet.code;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;


public class JSONComparator {
    private static ObjectMapper configureMapper() {
        var objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        return objectMapper;
    }
    private static Map<String,Object> getFileDataAsMap(String data, ObjectMapper mapper) throws Exception {
       return mapper.readValue(data, new TypeReference<Map<String,Object>>(){});
    }

    private static String getFormattedOutput(Map<String, Object> data, ObjectMapper mapper) throws Exception {
        var printer = new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter("  ", "\n"));
        var json = mapper.writer(printer).writeValueAsString(data);
        return json.replace("\"", "").replace(" :", ":");
    }

    private static Set<String> getUniqKeys(Map<String,Object> firstData, Map<String,Object> secondData) {
        var firstKeys = firstData.keySet();
        var secondKeys = secondData.keySet();

        var keysSet = new TreeSet<String>();
        keysSet.addAll(firstKeys);
        keysSet.addAll(secondKeys);

        return keysSet;
    }

    private static Map<String, Object> getTwoFilesCompareResult(Map<String, Object> firstFileData, Map<String, Object> secondFileData) {
        var keysSet = getUniqKeys(firstFileData, secondFileData);
        var resultMap = new LinkedHashMap<String, Object>();

        for (var key: keysSet) {
            var isInFirstData = firstFileData.containsKey(key);
            var inInSecondData = secondFileData.containsKey(key);

            if (!inInSecondData) {
                resultMap.put("- " + key, firstFileData.get(key));
            }
            if (!isInFirstData) {
                resultMap.put("+ " + key, secondFileData.get(key));
            }

            if (isInFirstData && inInSecondData) {
                var firstValue = firstFileData.get(key);
                var secondValue = secondFileData.get(key);
                var isEqual = String.valueOf(firstValue).compareTo(String.valueOf(secondValue));
                if (isEqual == 0) {
                    resultMap.put("  " + key, firstValue);
                } else {
                    resultMap.put("- " + key, firstValue);
                    resultMap.put("+ " + key, secondValue);
                }
            }
        }

        return resultMap;
    }

    public static String compare(String firstFileContent, String secondFileContent) throws Exception {
        var mapper = configureMapper();
        var firstFileData = getFileDataAsMap(firstFileContent, mapper);
        var secondFileData = getFileDataAsMap(secondFileContent, mapper);
        var resultMap = getTwoFilesCompareResult(firstFileData, secondFileData);

        var json = getFormattedOutput(resultMap, mapper);

        return json;

    }
}
