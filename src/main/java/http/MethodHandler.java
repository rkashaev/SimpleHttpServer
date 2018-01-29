package http;

import java.util.List;

@FunctionalInterface
interface MethodHandler {
    byte[] apply(HttpRequest request, List<String> headers);

}
