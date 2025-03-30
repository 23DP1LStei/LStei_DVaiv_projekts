package utils;


import java.io.FileWriter;
import java.io.IOException;
import models.User;

public class CSVWriter {

    public static void writeUser(String filename, User user) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.append(user.getId()).append(",").append(user.getUsername()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}