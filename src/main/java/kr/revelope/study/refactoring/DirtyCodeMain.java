package kr.revelope.study.refactoring;

import kr.revelope.study.refactoring.reader.CSVReader;
import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * csv 파일을 읽어서 특정 컬럼명을 group by 하여 출력하는 프로그램이다.
 * args[0] : resources에 보관된 csv 파일명
 * args[1] : 카운트 할 컬럼명
 * <p>
 * 아래 코드를 리팩토링 해보시오
 * <p>
 * <p>
 * 참고 사이트
 * https://recordsoflife.tistory.com/55
 * https://cornswrold.tistory.com/547
 */
public class DirtyCodeMain {
    private static final int CSV_ARG_LENGTH_LIMIT = 2;

    public static void main(String[] args) {
        if (args == null || args.length < CSV_ARG_LENGTH_LIMIT) {
            throw new IllegalArgumentException("File name and target column name is required.");
        }

        String fileName = args[0];
        String columnName = args[1];

        InputStream inputStream = Optional.ofNullable(DirtyCodeMain.class.getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s file can not found.", fileName)));

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(streamReader);
             CSVReader csvReader = new CSVReader(bufferedReader)) {

            Map<String, Integer> result = getGroupingCountByColumnName(csvReader, columnName);
            for (String column : result.keySet()) {
                System.out.println(column + " : " + result.get(column));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> getGroupingCountByColumnName(CSVReader csvReader, String columnName) throws IOException {
        String[] header = csvReader.readNext();
        if (header == null || header.length == 0) {
            throw new IllegalArgumentException("First line must be columns. Column can not found.");
        }

        int columnIndex = ArrayUtils.indexOf(header, columnName);
        if (columnIndex == -1) {
            throw new IllegalArgumentException(String.format("Can not found target column %s", columnName));
        }

        return csvReader.readAll().stream()
                .filter(data -> data.length == header.length)
                .map(data -> data[columnIndex])
                .collect(Collectors.groupingBy(column -> column, Collectors.summingInt(value -> 1)));
    }
}
