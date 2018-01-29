package http;

import java.util.List;

@FunctionalInterface
interface MethodHandler {
    String VERSION = "HTTP/1.1";

    byte[] apply(HttpRequest request, List<String> headers);

    static void fillHeaders(List<String> headers, HttpStatus status) {
        headers.add(VERSION + " " + status.toString());
        headers.add("Server: Simple Http Server");
        headers.add("Connection: close");
    }

}
