package sk.tuke.gamestudio.spidersolitaire.server.service;

public class ScoreException extends RuntimeException {
    public ScoreException(String message) {
        super(message);
    }

    public ScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
