package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Differ {
    private static Path getFilePah(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        var firstFilePath = getFilePah(filepath1);
        var secondFilePath = getFilePah(filepath2);

        var firstFileContent = Files.readString(firstFilePath);
        var secondFileContent = Files.readString(secondFilePath);
        var result = JSONComparator.compare(firstFileContent, secondFileContent);

        return result;
    }
}
