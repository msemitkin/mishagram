package ua.knu.mishagram.post.get;

public class ContentNotFoundException extends RuntimeException {

    public ContentNotFoundException(String message) {
        super(message);
    }
}
