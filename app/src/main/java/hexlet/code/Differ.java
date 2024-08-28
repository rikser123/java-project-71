package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Differ {
    private static Path getFilePah(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }
    private static final List<String> allowedExtensions = List.of("json", "yml");

    private static String getFileExtension(Path path) {
        var pathString = path.toString();
        return pathString.substring(pathString.lastIndexOf(".") + 1).toLowerCase();
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        var firstFilePath = getFilePah(filepath1);
        var secondFilePath = getFilePah(filepath2);
        var firstFileExtension = getFileExtension(firstFilePath);
        var secondFileExtension = getFileExtension(secondFilePath);

        if (!firstFileExtension.equals(secondFileExtension) || !allowedExtensions.contains(firstFileExtension)) {
            throw new Exception("Неверное расширение файла!");
        }

        var firstFileContent = Files.readString(firstFilePath);
        var secondFileContent = Files.readString(secondFilePath);

        var result = Parser.compare(firstFileContent, secondFileContent, firstFileExtension);

        return result;
    }
}
