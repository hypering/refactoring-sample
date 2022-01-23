package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.detectors.CharacterSetDetector;
import kr.revelope.study.refactoring.files.CSVFile;
import kr.revelope.study.refactoring.files.CSVReader;
import kr.revelope.study.refactoring.parsers.ProgramArgumentParser;
import kr.revelope.study.refactoring.types.ArgumentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DirtyCodeMain {
    public static void main(String[] args) {
        ProgramArgumentParser argumentParser = new ProgramArgumentParser(args);

        final String fileName = argumentParser.getArgumentValue(ArgumentType.FILE_NAME.getOptionName());
        final String[] columnNames = argumentParser.getArgumentValues(ArgumentType.COLUMN_NAME.getOptionName());
        final Charset charset = Optional.ofNullable(getFileCharacterSet(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Can not found %s file target Charset", fileName)));

        Map<String, Integer> result;
        try (InputStream inputStream = Optional.ofNullable(DirtyCodeMain.class.getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s file can not found.", fileName)));
             InputStreamReader streamReader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            CSVReader csvReader = new CSVReader(bufferedReader);
            CSVFile csvFile = new CSVFile(csvReader);

            result = getGroupingCountByColumnName(csvFile, columnNames[0]);
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        for (String column : result.keySet()) {
            System.out.println(column + " : " + result.get(column));
        }
    }

    private static Charset getFileCharacterSet(String fileName) {
        try (InputStream inputStream = Optional.ofNullable(DirtyCodeMain.class.getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s file can not found.", fileName)))) {
            return CharacterSetDetector.getCharacterSet(inputStream);
        } catch (IOException ioException) {
            ioException.printStackTrace();

            return null;
        }
    }

    private static Map<String, Integer> getGroupingCountByColumnName(CSVFile csvFile, String columnName) {
        List<String> columnNameList = Optional.ofNullable(csvFile.getColumnNameList(columnName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Can not found target column %s", columnName)));

        return columnNameList.stream()
                .collect(Collectors.groupingBy(column -> column, Collectors.summingInt(value -> 1)));
    }
}
