package kr.revelope.study.refactoring.detectors;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class CharacterSetDetector {
    private CharacterSetDetector() {
    }

    public static Charset getCharacterSet(InputStream inputStream) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(bufferedInputStream);

            CharsetMatch charsetMatch = charsetDetector.detect();
            if (charsetMatch == null) {
                return null;
            }

            return Charset.forName(charsetMatch.getName());
        }
    }
}
