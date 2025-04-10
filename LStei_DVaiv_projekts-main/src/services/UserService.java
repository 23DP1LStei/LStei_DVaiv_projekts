package services;

import java.util.List;
import java.util.UUID;
import models.User;
import utils.CSVReader;
import utils.CSVWriter;

public class UserService {
    private final String USER_FILE = "users.csv";

    public User registerUser(String username) {
        List<User> users = CSVReader.readUsers(USER_FILE);
    
        // Проверка по username (без регистра и без пробелов)
        for (User user : users) {
            if (user.getUsername().trim().equalsIgnoreCase(username.trim())) {
                return user; // пользователь уже существует
            }
        }
    
        // Новый пользователь
        String id = UUID.randomUUID().toString();
        User newUser = new User(id, username.trim());
    
        // запись
        CSVWriter.writeUser(USER_FILE, newUser);
        return newUser;
    }
}