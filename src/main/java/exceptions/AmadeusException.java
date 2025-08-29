package exceptions;

/**
 * Represents exceptions specific to the Amadeus application.
 * This exception is thrown when application-specific errors occur.
 */
public class AmadeusException extends Exception {

    /**
     * Constructs a new AmadeusException with the specified detail message.
     *
     * @param message Detail message explaining the cause of the exception.
     */
    public AmadeusException(String message) {
        super(message);
    }
}
