package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import models.Rating;
import models.User;

public class CSVWriter {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
                  .append(String.valueOf(rating.isListened())).append(",")
                  .append(rating.getDateAdded().format(DATE_FORMATTER)).append("\n");
        } catch (IOException e) {
        }
    }
    
    public static void updateRatings(String filename, List<Rating> ratings) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.append("userId,albumId,rating,listened,dateAdded\n");
            
            for (Rating rating : ratings) {
                writer.append(rating.getUserId()).append(",")
                      .append(rating.getAlbumId()).append(",")
                      .append(String.valueOf(rating.getRating())).append(",")
                      .append(String.valueOf(rating.isListened())).append(",")
                      .append(rating.getDateAdded().format(DATE_FORMATTER)).append("\n");
            }
        } catch (IOException e) {
        }
    }

    public static void saveAllRatings(String filename, List<Rating> ratings) {
        try {
            try (FileWriter writer = new FileWriter(filename, false)) {
                writer.append("userId,albumId,rating,listened\n");
                
                for (Rating rating : ratings) {
                    writer.append(rating.getUserId()).append(",")
                          .append(rating.getAlbumId()).append(",")
                          .append(String.valueOf(rating.getRating())).append(",")
                          .append(String.valueOf(rating.isListened())).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving ratings: " + e.getMessage());
        }
    }
}