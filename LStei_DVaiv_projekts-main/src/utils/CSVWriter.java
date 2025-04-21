package utils;

import java.io.FileWriter;
import java.io.IOException;
import models.Rating;
import models.User;

public class CSVWriter {

    public static void writeUser(String filename, User user) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.append(user.getId()).append(",").append(user.getUsername()).append("\n");
        } catch (IOException e) {
        }
    }

    public static void writeRating(String filename, Rating rating) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.append(rating.getUserId()).append(",")
                  .append(rating.getAlbumId()).append(",")
                  .append(String.valueOf(rating.getRating())).append(",")
                  .append(String.valueOf(rating.isListened())).append("\n");
        } catch (IOException e) {
        }
    }
}
