package http;

public enum MimeTypes {
    HTML("text/html"),
    HTM("text/htm"),

    ;

    private String type;

    MimeTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Content-type: " + type;
    }
}
