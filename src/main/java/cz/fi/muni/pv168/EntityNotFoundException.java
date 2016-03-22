package cz.fi.muni.pv168;

/**
 * Created by Radac on 22.3.2016.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs an instance of <code>EntityNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
