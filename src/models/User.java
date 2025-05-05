package models;

public class User {
    private final String id;
    private String username;

    // Создание обьекта user
    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // Сеттеры
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}
