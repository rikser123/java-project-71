package io.hexlet;

import java.nio.file.Paths;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

public class DifferTest {
    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    @Test
    public void compareJson() throws Exception {
        var expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";
        var firstFilePath = getFixturePath("file1.json");
        var secondFilePath = getFixturePath("file2.json");
        var actual = Differ.generate(firstFilePath.toString(), secondFilePath.toString());

        assertEquals(expected, actual);
    }
}
