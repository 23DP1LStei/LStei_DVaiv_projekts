package services;

import models.User;
import utils.CSVReader;
import utils.CSVWriter;

import java.util.List;
import java.util.UUID;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = CSVReader.readUsers("users.csv");
    }

    public User registerUser(String username) {
        String id = UUID.randomUUID().toString();
        User newUser = new User(id, username);
        users.add(newUser);
        CSVWriter.writeUser("users.csv", newUser);
        return newUser;
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) return user;
        }
        return null;
    }
}