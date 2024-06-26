package gr.aueb.cf.plantshopapp.service.exception;

/**
 * Exception thrown when a user already exists with the same username or email.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
