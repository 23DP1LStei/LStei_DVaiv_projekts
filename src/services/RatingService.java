package services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import models.Rating;
import utils.CSVReader;
import utils.CSVWriter;

public class RatingService {
    private final List<Rating> ratings;
    private final String RATINGS_FILE = "src\\databases\\ratings.csv";
    
    
    // Сортировка
    public enum SortType {
        DATE_ADDED_ASC,
        DATE_ADDED_DESC,
        RATING_ASC,
        RATING_DESC
    }

    public RatingService() {
        this.ratings = CSVReader.readRatings(RATINGS_FILE);
    }

    public void markAsListened(String userId, String albumId) {
        Rating existing = findRating(userId, albumId);
        if (existing != null) {
            existing.setListened(true);
            CSVWriter.updateRatings(RATINGS_FILE, ratings);
        } else {
            Rating rating = new Rating(userId, albumId, 0, true, LocalDateTime.now());
            ratings.add(rating);
            CSVWriter.writeRating(RATINGS_FILE, rating);
        }
    }

    public void rateAlbum(String userId, String albumId, int score) {
        Rating existing = findRating(userId, albumId);
        if (existing != null) {
            existing.setRating(score);
            existing.setListened(true);
            existing.setDateAdded(LocalDateTime.now());
            CSVWriter.updateRatings(RATINGS_FILE, ratings);
        } else {
            Rating rating = new Rating(userId, albumId, score, true, LocalDateTime.now());
            ratings.add(rating);
            CSVWriter.writeRating(RATINGS_FILE, rating);
        }
    }

    public List<Rating> getUserListenedAlbums(String userId, SortType sortType) {
        List<Rating> userRatings = ratings.stream()
                .filter(r -> r.getUserId().equals(userId) && r.isListened())
                .collect(Collectors.toList());
        
        switch (sortType) {
            case DATE_ADDED_ASC -> userRatings.sort(Comparator.comparing(Rating::getDateAdded));
            case DATE_ADDED_DESC -> userRatings.sort(Comparator.comparing(Rating::getDateAdded).reversed());
            case RATING_ASC -> userRatings.sort(Comparator.comparingInt(Rating::getRating));
            case RATING_DESC -> userRatings.sort(Comparator.comparingInt(Rating::getRating).reversed());
        }
        
        return userRatings;
    }

    // Вычесление средней оценки
    public double getAlbumAverageRating(String albumId) {
        List<Rating> albumRatings = ratings.stream()
                .filter(r -> r.getAlbumId().equals(albumId) && r.getRating() > 0)
                .collect(Collectors.toList());

        if (albumRatings.isEmpty()) return 0;

        double total = albumRatings.stream().mapToInt(Rating::getRating).sum();
        return total / albumRatings.size();
    }


    public Rating findRating(String userId, String albumId) {
        for (Rating rating : ratings) {
            if (rating.getUserId().equals(userId) && rating.getAlbumId().equals(albumId)) {
                return rating;
            }
        }
        return null;
    }

}
