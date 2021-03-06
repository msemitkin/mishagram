package ua.knu.mishagram.exceptions;

public class PostNotFoundException extends RuntimeException {

    private final int id;

    public PostNotFoundException(int id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
