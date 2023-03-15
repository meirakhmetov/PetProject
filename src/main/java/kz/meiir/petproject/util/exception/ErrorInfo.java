package kz.meiir.petproject.util.exception;

/**
 * @author Meiir Akhmetov on 14.03.2023
 */
public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String[] details;

    public ErrorInfo(String url, ErrorType type, String... details) {
        this.url = url;
        this.type = type;
        this.details = details;
    }
}
