package hexlet.code;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

class Parser {
    private static Set<String> getUniqKeys(Map<String, Object> firstData, Map<String, Object> secondData) {
        var firstKeys = firstData.keySet();
        var secondKeys = secondData.keySet();

        var keysSet = new TreeSet<String>();
        keysSet.addAll(firstKeys);
        keysSet.addAll(secondKeys);

        return keysSet;
    }

    private static Map<String, DifferItem> getTwoFilesCompareResult(
            Map<String, Object> firstFileData, Map<String, Object> secondFileData
    ) {
        var keysSet = getUniqKeys(firstFileData, secondFileData);
        var resultMap = new LinkedHashMap<String, DifferItem>();

        for (var key: keysSet) {
            var isInFirstData = firstFileData.containsKey(key);
            var inInSecondData = secondFileData.containsKey(key);
            var firstValue = String.valueOf(firstFileData.get(key));
            var secondValue = String.valueOf(secondFileData.get(key));

            if (!inInSecondData) {
                resultMap.put(key, new DifferItem("removed", List.of(firstValue)));
            }
            if (!isInFirstData) {
                resultMap.put(key, new DifferItem("added", List.of(secondValue)));
            }

            if (isInFirstData && inInSecondData) {
                var isEqual = String.valueOf(firstValue).equals(String.valueOf(secondValue));
                if (isEqual) {
                    resultMap.put(key, new DifferItem("unchanged", List.of(firstValue)));
                } else {
                    resultMap.put(key, new DifferItem("changed", List.of(firstValue, secondValue)));
                }
            }
        }

        return resultMap;
    }

    private static Map<String, Object> readFileData(String data, String fileExtension) throws Exception {
        var currentMapper = new ObjectMapper();
        if (fileExtension.equals("yml")) {
            currentMapper = new ObjectMapper(new YAMLFactory());
        }
        return currentMapper.readValue(data, new TypeReference<Map<String, Object>>() { });
    }

    public static Map<String, DifferItem> parse(ParserParameters parameters) throws Exception {
        var firstFileData = readFileData(parameters.getFirstFileContent(), parameters.getFileExtension());
        var secondFileData = readFileData(parameters.getSecondFileContent(), parameters.getFileExtension());
        var resultMap = getTwoFilesCompareResult(firstFileData, secondFileData);

        return resultMap;
    }
}
