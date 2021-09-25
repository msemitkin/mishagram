package ua.knu.mishagram.user;

public class UserNotFoundException extends RuntimeException {

    private final int id;

    public UserNotFoundException(int id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
