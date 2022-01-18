package kr.revelope.study.refactoring.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CSVReader {
    private static final String DEFAULT_SEPARATOR = ",";

    private final BufferedReader bufferedReader;

    public CSVReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String[] readNext() throws IOException {
        return Optional.ofNullable(bufferedReader.readLine())
                .map(line -> line.split(DEFAULT_SEPARATOR))
                .orElse(null);
    }

    public List<String[]> readAll() throws IOException {
        List<String[]> list = new ArrayList<>();

        while (true) {
            String[] elements = this.readNext();
            if (elements == null) {
                break;
            }

            list.add(elements);
        }

        return list;
    }
}
