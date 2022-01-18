package kr.revelope.study.refactoring.files;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class CSVFile {
    private final CSVReader csvReader;
    private final String[] header;
    private final List<String[]> body;

    public CSVFile(CSVReader csvReader) throws Exception {
        if (csvReader == null) {
            throw new IllegalArgumentException("csvReader must not be null");
        }

        this.csvReader = csvReader;
        this.header = this.makeCSVHeader();
        this.body = this.makeCSVBody();
    }

    private String[] makeCSVHeader() throws Exception {
        String[] csvHeader = this.csvReader.readNext();
        if (csvHeader == null || csvHeader.length == 0) {
            throw new IllegalArgumentException("First line must be columns. Column can not found.");
        }

        return csvHeader;
    }

    private List<String[]> makeCSVBody() throws Exception {
        if (this.header == null || this.header.length == 0) {
            throw new IllegalArgumentException("First line must be columns. Column can not found.");
        }

        return this.csvReader.readAll().stream()
                .filter(data -> data.length == this.header.length)
                .collect(Collectors.toList());
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

    public boolean isPresentColumnName(String columnName) {
        return Arrays.stream(this.header)
                .anyMatch(column -> StringUtils.equals(column, columnName));
    }
}
