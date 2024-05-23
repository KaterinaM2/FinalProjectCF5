package gr.aueb.cf.plantshopapp.service.exception;

/**
 * Exception thrown when a user is not found by the provided username.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
