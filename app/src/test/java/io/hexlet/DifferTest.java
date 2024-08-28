package io.hexlet;

import java.nio.file.Paths;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hexlet.code.Differ;

public class DifferTest {
    private String expected = "";
    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    @BeforeEach()
    public void beforeEach() {
        expected = "{\n"
                + "  - follow: false\n"
                + "    host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "  + timeout: 20\n"
                + "  + verbose: true\n"
                + "}";
    }

    @Test
    public void compareJson() throws Exception {
        var firstFilePath = getFixturePath("file1.json");
        var secondFilePath = getFixturePath("file2.json");
        var actual = Differ.generate(firstFilePath.toString(), secondFilePath.toString());

        assertEquals(expected, actual);
    }

    @Test
    public void compareYml() throws Exception {
        var firstFilePath = getFixturePath("file1.yml");
        var secondFilePath = getFixturePath("file2.yml");
        var actual = Differ.generate(firstFilePath.toString(), secondFilePath.toString());

        assertEquals(expected, actual);
    }
}
