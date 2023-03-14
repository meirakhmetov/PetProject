package kz.meiir.petproject.util.exception;

/**
 * @author Meiir Akhmetov on 14.03.2023
 */
public class IllegalRequestDataException extends RuntimeException{
    public IllegalRequestDataException(String msg){
        super(msg);
    }
}
