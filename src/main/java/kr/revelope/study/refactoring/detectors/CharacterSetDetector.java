package kr.revelope.study.refactoring.detectors;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class CharacterSetDetector {
    private static final CharsetDetector charsetDetector = new CharsetDetector();

    public static Charset getCharacterSet(InputStream inputStream) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            charsetDetector.setText(bufferedInputStream);

            CharsetMatch charsetMatch = charsetDetector.detect();

            return Charset.forName(charsetMatch.getName());
        } catch (IOException ioException) {
            return null;
        }
    }
}
