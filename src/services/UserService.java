package services;

import java.util.List;
import java.util.UUID;
import models.User;
import utils.CSVReader;
import utils.CSVWriter;

public class UserService {
    private final String USER_FILE = "src\\databases\\users.csv";

    public User registerUser(String username) {
        List<User> users = CSVReader.readUsers(USER_FILE);
    
        // Проверка существует ли пользователь
        for (User user : users) {
            if (user.getUsername().trim().equalsIgnoreCase(username.trim())) {
                return user; 
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
