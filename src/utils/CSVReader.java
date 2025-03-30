package utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class CSVReader {


    public static List<User> readUsers(String filename) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

}