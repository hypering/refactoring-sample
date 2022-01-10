package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.detectors.CharacterSetDetector;
import kr.revelope.study.refactoring.files.CSVFile;
import kr.revelope.study.refactoring.parsers.ProgramArgumentParser;
import kr.revelope.study.refactoring.types.ArgumentType;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * TODO : csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.
 * args[0] : resources에 보관된 csv 파일명
 * args[1] : 카운트 할 컬럼명
 *
 * <p>
 * TODO : arguments 받는 방식을 바꿔주세요.
 * --file-path {filePath} 또는 -f {filePath}
 * --column-name {columnName} 또는 -c {columnName}
 *
 * <p>
 * TODO : charset이 ms949인 것도 들어올 수 있게 하기
 *
 * <p>
 * - 참고자료 -
 *
 * <p>
 * * java stream
 * https://recordsoflife.tistory.com/55
 * https://cornswrold.tistory.com/547
 *
 * <p>
 * * CharsetDectector
 * https://www.tabnine.com/code/java/methods/com.ibm.icu.text.CharsetDetector/detect
 *
 * <p>
 * * commons-cli
 * https://junho85.pe.kr/432
 * https://118k.tistory.com/779
 * https://stackoverflow.com/questions/367706/how-do-i-parse-command-line-arguments-in-java
 */
public class DirtyCodeMain {
    public static void main(String[] args) {
        ProgramArgumentParser argumentParser = new ProgramArgumentParser(args);

        String fileName = argumentParser.getArgumentValue(ArgumentType.FILE_NAME.getOptionName());
        String columnName = argumentParser.getArgumentValue(ArgumentType.COLUMN_NAME.getOptionName());

        if (fileName == null || columnName == null) {
            throw new IllegalArgumentException("File name and target column name is required.");
        }

        if (!existsFile(fileName)) {
            throw new IllegalArgumentException(String.format("%s file can not found.", fileName));
        }

        Charset charset = Optional.ofNullable(getFileCharacterSet(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Can not found %s file target Charset", fileName)));

        Map<String, Integer> result;
        try (InputStream inputStream = DirtyCodeMain.class.getClassLoader().getResourceAsStream(fileName);
             InputStreamReader streamReader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(streamReader)) {
            CSVFile csvFile = new CSVFile(bufferedReader);

            result = getGroupingCountByColumnName(csvFile, columnName);
        } catch (IOException e) {
            e.printStackTrace();

            return;
        }

        for (String column : result.keySet()) {
            System.out.println(column + " : " + result.get(column));
        }
    }

    private static boolean existsFile(String fileName) {
        URL url = DirtyCodeMain.class.getClassLoader().getResource(fileName);
        if (url == null) {
            return false;
        }

        File file = new File(url.getFile());

        return file.exists();
    }

    private static Charset getFileCharacterSet(String fileName) {
        try (InputStream inputStream = DirtyCodeMain.class.getClassLoader().getResourceAsStream(fileName)) {
            return CharacterSetDetector.getCharacterSet(inputStream);
        } catch (IOException ioException) {
            return null;
        }
    }

    private static Map<String, Integer> getGroupingCountByColumnName(CSVFile csvFile, String columnName) throws IOException {
        List<String> columnNameList = Optional.ofNullable(csvFile.getColumnNameList(columnName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Can not found target column %s", columnName)));

        return columnNameList.stream()
                .collect(Collectors.groupingBy(column -> column, Collectors.summingInt(value -> 1)));
    }
}
