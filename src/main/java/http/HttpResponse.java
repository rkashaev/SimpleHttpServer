package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static http.MethodHandler.fillHeaders;

public class HttpResponse {
    private static final Logger log = LogManager.getLogger(HttpResponse.class);
    public static final byte[] EMPTY_BYTES = new byte[0];

    private static final Map<HttpMethod, MethodHandler> handlers;

    private List<String> headers = new ArrayList<>();
    private byte[] body;

    static {
        Map<HttpMethod, MethodHandler> map = new EnumMap<>(HttpMethod.class);

        map.put(HttpMethod.GET, HttpResponse::processGET);

        handlers = Collections.unmodifiableMap(map);
    }


    private HttpResponse(HttpRequest req) {

        MethodHandler handler = handlers.getOrDefault(req.getMethod(), HttpResponse::emptyHandler);

        body = handler.apply(req, headers);

    }

    /**
     * A fabric method to create new HttpResponse from HttpRequest
     */
    public static HttpResponse from(HttpRequest req) {
        return new HttpResponse(req);
    }

    private static byte[] processGET(HttpRequest req, List<String> headers) {
        byte[] res = EMPTY_BYTES;
        try {
            Path path = Paths.get(".", req.getUri());
            if (!Files.exists(path)) {
                fillHeaders(headers, HttpStatus.NOT_FOUND);
                res = String.format("File %s not found here", path).getBytes();
                return res;
            }
            if (!Files.isDirectory(path)) {
                // working with a file
                res = Files.readAllBytes(path);
            } else {
                // TODO show directory contents in templated html
                res = ("TODO: Entries of a directory: " + path.toAbsolutePath()).getBytes();
            }

            fillHeaders(headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("", e);
        }
        return res;
    }

    private static byte[] emptyHandler(HttpRequest request, List<String> headers) {
        return EMPTY_BYTES;
    }

    public void write(OutputStream os) throws IOException {
        DataOutputStream output = new DataOutputStream(os);

        for (String header : headers) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");

        if (body != null) {
            output.write(body);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

}
