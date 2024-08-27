package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    private Path getFilePah(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    @Override
    public Integer call() throws Exception {
       var firstFilePath = getFilePah(filepath1);
       var secondFilePath = getFilePah(filepath2);

       var firstFileContent = Files.readString(firstFilePath);
       var secondFileContent = Files.readString(secondFilePath);

       try {
           var result = JSONComparator.compare(firstFileContent, secondFileContent);
           System.out.println(result);
       } catch (Exception e) {
           System.out.println("Не удалось выполнить сравнение");
           System.out.println(e);
       }

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}