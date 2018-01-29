package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public interface Utils {
    static BufferedReader toBufferedReader(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    static String toHeader(String key, String value) {
        return key + ": " + value;
    }
}
