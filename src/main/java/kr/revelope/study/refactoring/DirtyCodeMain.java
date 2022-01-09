package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.detectors.CharacterSetDetector;
import kr.revelope.study.refactoring.files.CSVFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.
 * args[0] : resources에 보관된 csv 파일명
 * args[1] : 카운트 할 컬럼명
 * <p>
 * // todo 1. arguments 받는 방식을 바꿔주세요.
 * // --file-path {filePath} 또는 -f {filePath}
 * // --column-name {columnName} 또는 -c {columnName}
 * <p>
 * // todo 2. charset이 ms949인 것도 들어올 수 있게 하기
 *
 * <p>
 * 아래 코드를 리팩토링 해보시오
 * <p>
 * <p>
 * 참고 사이트
 * https://recordsoflife.tistory.com/55
 * https://cornswrold.tistory.com/547
 * <p>
 * https://www.tabnine.com/code/java/methods/com.ibm.icu.text.CharsetDetector/detect
 */
public class DirtyCodeMain {
    private static final int CSV_ARG_LENGTH_LIMIT = 2;

    public static void main(String[] args) {
        if (args == null || args.length < CSV_ARG_LENGTH_LIMIT) {
            throw new IllegalArgumentException("File name and target column name is required.");
        }

        String fileName = args[0];
        String columnName = args[1];

        if (!existsFile(fileName)) {
            throw new IllegalArgumentException(String.format("%s file can not found.", fileName));
        }

        Charset charset = Optional.ofNullable(getFileCharacterSet(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Can not found %s file target Charset", fileName)));

        Map<String, Integer> result;
        /*
         * null이 올 수가 있나..?
         * */
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
