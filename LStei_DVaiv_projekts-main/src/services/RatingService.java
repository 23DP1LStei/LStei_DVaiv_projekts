package services;

import java.util.List;
import java.util.stream.Collectors;
import models.Rating;
import utils.CSVReader;
import utils.CSVWriter;

public class RatingService {
    private final List<Rating> ratings;

    public RatingService() {
        this.ratings = CSVReader.readRatings("LStei_DVaiv_projekts-main\\src\\databases\\ratings.csv");
    }

    public void markAsListened(String userId, String albumId) {
        Rating existing = findRating(userId, albumId);
        if (existing != null) {
            existing.setListened(true);
        } else {
            Rating rating = new Rating(userId, albumId, 0, true);
            ratings.add(rating);
            CSVWriter.writeRating("LStei_DVaiv_projekts-main\\src\\databases\\ratings.csv", rating);
        }
    }

    public void rateAlbum(String userId, String albumId, int score) {
        Rating existing = findRating(userId, albumId);
        if (existing != null) {
            existing.setRating(score);
            existing.setListened(true);
        } else {
            Rating rating = new Rating(userId, albumId, score, true);
            ratings.add(rating);
            CSVWriter.writeRating("LStei_DVaiv_projekts-main\\src\\databases\\ratings.csv", rating);
        }
    }

    public List<Rating> getUserListenedAlbums(String userId) {
        return ratings.stream()
                .filter(r -> r.getUserId().equals(userId) && r.isListened())
                .collect(Collectors.toList());
    }

    public double getAlbumAverageRating(String albumId) {
        List<Rating> albumRatings = ratings.stream()
                .filter(r -> r.getAlbumId().equals(albumId) && r.getRating() > 0)
                .collect(Collectors.toList());

        if (albumRatings.isEmpty()) return 0;

        double total = albumRatings.stream().mapToInt(Rating::getRating).sum();
        return total / albumRatings.size();
    }

    private Rating findRating(String userId, String albumId) {
        for (Rating rating : ratings) {
            if (rating.getUserId().equals(userId) && rating.getAlbumId().equals(albumId)) {
                return rating;
            }
        }
        return null;
    }
    
}
