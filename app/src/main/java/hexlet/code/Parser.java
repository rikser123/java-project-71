package hexlet.code;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.Set;

class Parser {
    private static Set<String> getUniqKeys(Map<String, Object> firstData, Map<String, Object> secondData) {
        var firstKeys = firstData.keySet();
        var secondKeys = secondData.keySet();

        var keysSet = new TreeSet<String>();
        keysSet.addAll(firstKeys);
        keysSet.addAll(secondKeys);

        return keysSet;
    }

    private static Map<String, Object> getTwoFilesCompareResult(
            Map<String, Object> firstFileData, Map<String, Object> secondFileData
    ) {
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

    public static String compare(String firstFileContent, String secondFileContent, String fileExtension) throws Exception {
        var mapper = new DataMapper(fileExtension);
        var firstFileData = mapper.getFileDataAsMap(firstFileContent);
        var secondFileData = mapper.getFileDataAsMap(secondFileContent);
        var resultMap = getTwoFilesCompareResult(firstFileData, secondFileData);

        var json = mapper.getFormattedOutput(resultMap);

        return json;

    }
}
