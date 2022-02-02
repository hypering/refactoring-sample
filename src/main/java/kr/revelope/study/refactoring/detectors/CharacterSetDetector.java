package kr.revelope.study.refactoring.detectors;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

public class CharacterSetDetector {
    private CharacterSetDetector() {
    }

    public static Charset getCharacterSet(String fileName) throws IOException {
        try (InputStream inputStream = Optional.ofNullable(CharacterSetDetector.class.getClassLoader().getResourceAsStream(fileName))
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s file can not found.", fileName)));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
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
