package models;

public class User {
    private String id;
    private String username;

    // Constructor
    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getter
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // Сеттер
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}
