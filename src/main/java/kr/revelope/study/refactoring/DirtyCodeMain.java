package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.detectors.CharacterSetDetector;
import kr.revelope.study.refactoring.files.CSVFile;
import kr.revelope.study.refactoring.files.CSVReader;
import kr.revelope.study.refactoring.parsers.ProgramArgumentParser;
import kr.revelope.study.refactoring.types.ArgumentType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class DirtyCodeMain {
    public static void main(String[] args) throws IOException {
        ProgramArgumentParser argumentParser = new ProgramArgumentParser(args);

        final String fileName = argumentParser.getArgumentValue(ArgumentType.FILE_NAME.getOptionName());
        final String[] columnNames = argumentParser.getArgumentValues(ArgumentType.COLUMN_NAME.getOptionName());
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
