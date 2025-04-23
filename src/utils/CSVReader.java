package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import models.Album;
import models.Rating;
import models.User;

public class CSVReader {

    public static List<Album> readAlbums(String filename) {
        List<Album> albums = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // пропустить заголовок
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                albums.add(new Album(parts[0], parts[1], parts[2], parts[3]));
            }
        } catch (Exception e) {
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
                users.add(new User(parts[0], parts[1]));
            }
        } catch (Exception e) {
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
                ratings.add(new Rating(userId, albumId, rating, listened));
            }
        } catch (Exception e) {
        }
        return ratings;
    }
}
