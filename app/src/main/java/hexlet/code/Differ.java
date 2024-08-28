package hexlet.code;

import hexlet.code.formatter.Formatter;
import hexlet.code.formatter.JSONFormatter;
import hexlet.code.formatter.PlainFormatter;
import hexlet.code.formatter.StylishFormatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Differ {
    private static Path getFilePah(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }
    private static final List<String> ALLOWED_EXTENSIONS = List.of("json", "yml", "json");

    private static String getFileExtension(Path path) {
        var pathString = path.toString();
        return pathString.substring(pathString.lastIndexOf(".") + 1).toLowerCase();
    }

    private static Formatter createFormatter(String format) {
        switch (format) {
            case "stylish":
                return new StylishFormatter();
            case "plain":
                return new PlainFormatter();
            case "json":
                return new JSONFormatter();
            default:
                return new StylishFormatter();
        }
    }

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        var firstFilePath = getFilePah(filepath1);
        var secondFilePath = getFilePah(filepath2);
        var firstFileExtension = getFileExtension(firstFilePath);
        var secondFileExtension = getFileExtension(secondFilePath);

        if (!firstFileExtension.equals(secondFileExtension) || !ALLOWED_EXTENSIONS.contains(firstFileExtension)) {
            throw new Exception("Неверное расширение файла!");
        }

        var firstFileContent = Files.readString(firstFilePath);
        var secondFileContent = Files.readString(secondFilePath);

        var parserParameters = new ParserParameters(firstFileContent, secondFileContent, firstFileExtension);
        var resultMap = Parser.parse(parserParameters);

        var formatter = createFormatter(format);

        var text = formatter.format(resultMap);

        return text;
    }
}
