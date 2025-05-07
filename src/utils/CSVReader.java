package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import models.Album;
import models.Rating;
import models.User;

public class CSVReader {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static List<Album> readAlbums(String filename) {
        List<Album> albums = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                // Split the CSV line carefully to handle quotes
                String[] parts = parseCSVLine(line);
                
                if (parts.length >= 4) {
                    String id = parts[0].replace("\"", "");
                    String title = parts[1].replace("\"", "");
                    String artist = parts[2].replace("\"", "");
                    String year = parts[4].replace("\"", ""); // Year is in the 5th column (index 4)
                    
                    albums.add(new Album(id, title, artist, year));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading albums: " + e.getMessage());
        }
        return albums;
    }

    public static List<User> readUsers(String filename) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0].trim(), parts[1].trim()));
            }
        } catch (Exception e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    public static List<Rating> readRatings(String filename) {
        List<Rating> ratings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String userId = parts[0];
                String albumId = parts[1];
                int rating = Integer.parseInt(parts[2]);
                boolean listened = Boolean.parseBoolean(parts[3]);
                
                LocalDateTime dateAdded;
                if (parts.length > 4) {
                    try {
                        dateAdded = LocalDateTime.parse(parts[4], DATE_FORMATTER);
                    } catch (Exception e) {
                        dateAdded = LocalDateTime.now();
                    }
                } else {
                    dateAdded = LocalDateTime.now();
                }
                
                ratings.add(new Rating(userId, albumId, rating, listened, dateAdded));
            }
        } catch (Exception e) {
            System.err.println("Error reading ratings: " + e.getMessage());
        }
        return ratings;
    }
    
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());
        
        return result.toArray(String[]::new);
    }
}