package cz.fi.muni.pv168;

/**
 * Exception expressing some kind of service failure or database inconsistency
 *
 * Created by Milan on 21.03.2016.
 */
public class ServiceFailureException extends RuntimeException{

    public ServiceFailureException(String message) {
        super(message);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }
}
