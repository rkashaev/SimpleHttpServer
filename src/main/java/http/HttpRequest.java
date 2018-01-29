package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LogManager.getLogger(HttpRequest.class);
    private Map<String, String> headers = new HashMap<>();

    private HttpMethod method;
    private String uri;
    private String version;

    public HttpRequest(BufferedReader br) {
        try {

            String line = br.readLine();

            parseRequestString(line);

            do {

                line = br.readLine();
                parseHeader(line);

            } while (!line.equals(""));

//            headers.forEach((k,v) -> log.debug(toHeader(k, v)));

        } catch (IOException e) {
            log.error("", e);
        }

    }

    private void parseRequestString(String line) {
        log.debug("Requested: {}", line);

        String[] fragments = line.split("\\s+");

        method = HttpMethod.valueOf(fragments[0]);
        uri = fragments[1];
        version = fragments[2];
    }

    private void parseHeader(String line) {
        if (line == null || line.isEmpty()) return;

        String[] parts = line.split("\\s*:\\s*");
        headers.put(parts[0], parts.length > 0 ? parts[1] : "");
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }
}
