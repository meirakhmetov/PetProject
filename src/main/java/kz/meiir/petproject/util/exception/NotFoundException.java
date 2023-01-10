package kz.meiir.petproject.util.exception;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
