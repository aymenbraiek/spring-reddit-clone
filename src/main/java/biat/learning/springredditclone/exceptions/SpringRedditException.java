package biat.learning.springredditclone.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String message) {
        super(message);
    }

    public SpringRedditException(String exmessage, Exception exception) {
        super(exmessage, exception);
    }
}
