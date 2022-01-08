package kr.revelope.study.refactoring.files;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CSVFile {
    private final CSVReader csvReader;
    private final String[] header;
    private final List<String[]> body;

    public CSVFile(BufferedReader bufferedReader) throws IOException {
        this.csvReader = new CSVReader(bufferedReader);
        this.header = this.makeCSVHeader();
        this.body = this.makeCSVBody();
    }

    /*
     * 생성자에 구현할 지 아니면 이런식으로 빼서 작성할지??
     * */
    private String[] makeCSVHeader() throws IOException {
        String[] csvHeader = this.csvReader.readNext();
        if (csvHeader == null || csvHeader.length == 0) {
            throw new IllegalArgumentException("First line must be columns. Column can not found.");
        }

        return csvHeader;
    }

    private List<String[]> makeCSVBody() throws IOException {
        /*
         * 이미 makeCSVHeader 에서 검사를 하는데 필요할까??
         * */
        if (this.header == null || this.header.length == 0) {
            throw new IllegalArgumentException("First line must be columns. Column can not found.");
        }

        return this.csvReader.readAll().stream()
                .filter(data -> data.length == this.header.length)
                .collect(Collectors.toList());
    }

    public boolean isPresentColumnName(String columnName) {
        return Arrays.stream(this.header)
                .anyMatch(column -> StringUtils.equals(column, columnName));
    }

    public List<String> getColumnNameList(String columnName) {
        if (!isPresentColumnName(columnName)) {
            return null;
        }

        int columnIndex = ArrayUtils.indexOf(this.header, columnName);
        return this.body.stream()
                .map(data -> data[columnIndex])
                .collect(Collectors.toList());
    }

    private final class CSVReader {
        private static final String DEFAULT_SEPARATOR = ",";

        private final BufferedReader bufferedReader;

        private CSVReader(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        private String[] readNext() throws IOException {
            return Optional.ofNullable(bufferedReader.readLine())
                    .map(line -> line.split(DEFAULT_SEPARATOR))
                    .orElse(null);
        }

        private List<String[]> readAll() throws IOException {
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
}
