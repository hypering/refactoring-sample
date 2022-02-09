package kr.revelope.study.refactoring.files;

import kr.revelope.study.refactoring.argument.model.Column;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
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

    public Map<List<String>, Integer> getGroupingCount(List<Column> columnNames) {
        if (isNotExistColumnNames(columnNames)) {
            throw new IllegalArgumentException(String.format("Can not found target columns %s", columnNames));
        }

        int[] columnIndexes = columnNames.stream()
                .mapToInt(columnName -> ArrayUtils.indexOf(this.header, columnName.getColumnName()))
                .toArray();

        Map<String, Integer> check = new HashMap<>();
        for (Column column : columnNames) {
            check.put(column.getColumnName(), column.getValue());
        }

        return this.body.stream()
                .map(data -> {
                    List<String> values = new ArrayList<>();
                    for (int idx : columnIndexes) {
                        values.add(data[idx]);
                    }

                    return values;
                })
                .filter(columns -> {
                    for (int i = 0; i < columns.size(); i++) {
                        if (columnNames.get(i).getValue() == null) {
                            continue;
                        }

                        // TODO : 일단은 숫자가 아닌 경우 처리안하도록 구성만 해놓은 상태
                        if (!StringUtils.isNumeric(columns.get(i))) {
                            continue;
                        }

                        if (columnNames.get(i).getValue() > Integer.parseInt(columns.get(i))) {
                            return false;
                        }
                    }

                    return true;
                })
                .collect(Collectors.groupingBy(column -> column, Collectors.summingInt(value -> 1)));
    }

    public boolean isNotExistColumnNames(List<Column> columnNames) {
        return columnNames.stream()
                .anyMatch(columnName -> !isExistColumnName(columnName.getColumnName()));
    }

    public boolean isExistColumnName(String columnName) {
        return Arrays.stream(this.header)
                .anyMatch(column -> StringUtils.equals(column, columnName));
    }
}
