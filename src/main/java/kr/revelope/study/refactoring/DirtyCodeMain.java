package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.argument.ProgramArgumentParser;
import kr.revelope.study.refactoring.argument.model.Column;
import kr.revelope.study.refactoring.argument.types.ArgumentType;
import kr.revelope.study.refactoring.detectors.CharacterSetDetector;
import kr.revelope.study.refactoring.files.CSVFile;
import kr.revelope.study.refactoring.files.CSVReader;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * 1. Argument에서 fileName을 얻을 때는 String, columnName을 얻을 때는 Column형태로 얻어지는 형태
 * */
public class DirtyCodeMain {
    public static void main(String[] args) throws Exception {
        ProgramArgumentParser argumentParser = new ProgramArgumentParser(args);

        final String fileName = (String) Optional.ofNullable(argumentParser.getArgumentValue(ArgumentType.FILE_NAME))
                .orElseThrow(() -> new IllegalArgumentException("file can not found"));
        final List<Column> columnNames = (List<Column>) Optional.ofNullable(argumentParser.getArgumentValues(ArgumentType.COLUMN_NAME))
                .orElseThrow(() -> new IllegalArgumentException("columnNames not found"));
        final Charset charset = CharacterSetDetector.getCharacterSet(fileName);

        Map<List<String>, Integer> result;
        try (CSVReader csvReader = new CSVReader(fileName, charset)) {
            CSVFile csvFile = new CSVFile(csvReader);

            result = csvFile.getGroupingCount(columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return;
        }

        for (List<String> column : result.keySet()) {
            System.out.println(column + " : " + result.get(column));
        }
    }
}
