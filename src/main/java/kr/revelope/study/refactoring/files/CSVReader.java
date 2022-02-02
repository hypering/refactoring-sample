package kr.revelope.study.refactoring.files;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CSVReader implements Closeable {
    private static final String DEFAULT_SEPARATOR = ",";

    private final BufferedReader bufferedReader;

    public CSVReader(String fileName, Charset charset) {
        InputStream inputStream = Optional.ofNullable(CSVReader.class.getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s file can not found.", fileName)));
        InputStreamReader streamReader = new InputStreamReader(inputStream, charset);
        this.bufferedReader = new BufferedReader(streamReader);
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

    @Override
    public void close() throws IOException {
        this.bufferedReader.close();
    }
}
