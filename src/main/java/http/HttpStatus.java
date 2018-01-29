package http;

public enum HttpStatus {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),
    INTERNAL_ERROR(500, "Internal Server Error"),;

    public final int code;
    public final String desc;

    HttpStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return code + " " + desc;
    }
}
