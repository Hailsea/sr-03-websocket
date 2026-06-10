package fr.utc.sr03.exception;

/**
 * Levee quand un utilisateur tente une action qui ne lui est pas permise
 * (ex: accepter une invitation qui ne lui est pas destinee).
 * Traduite en HTTP 403 par ApiExceptionHandler.
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
