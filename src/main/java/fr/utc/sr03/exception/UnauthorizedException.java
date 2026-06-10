package fr.utc.sr03.exception;

/**
 * Levee quand l'authentification echoue (mauvais email / mot de passe).
 * Traduite en HTTP 401 par ApiExceptionHandler.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
