package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Finder {
    private File file;
    private SearchAlgorithm algorithm;
    private final String text;

    public Finder(File file, SearchAlgorithm algorithm) {
        this.file = file;
        this.text = readFile(file);
        this.algorithm = algorithm;
    }

    private String readFile(File file) {
        byte[] allBytes = new byte[0];
        try {
            allBytes = Files.readAllBytes(Path.of(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder str = new StringBuilder();
        for (byte allByte : allBytes) {
            str.append((char) allByte);
        }
        return str.toString();
    }

    public boolean contains(String pattern) {
        return algorithm.contains(text, pattern);
    }
}
