package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.detectors.CharacterSetDetector;
import kr.revelope.study.refactoring.files.CSVFile;
import kr.revelope.study.refactoring.files.CSVReader;
import kr.revelope.study.refactoring.model.Column;
import kr.revelope.study.refactoring.parser.ProgramArgumentParser;
import kr.revelope.study.refactoring.parser.types.ArgumentType;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class DirtyCodeMain {
    public static void main(String[] args) throws Exception {
        ProgramArgumentParser argumentParser = new ProgramArgumentParser(args);

        // null 체크
        final String fileName = argumentParser.getArgumentValue(ArgumentType.FILE_NAME);
        final List<Column> columnNames = argumentParser.getArgumentValues(ArgumentType.COLUMN_NAME);
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
