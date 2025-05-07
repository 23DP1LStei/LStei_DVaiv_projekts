package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import models.Album;
import models.Rating;
import utils.CSVReader;
import utils.CSVWriter;

public class RatingService {
    private final List<Rating> ratings;
    private final String RATINGS_FILE = "src\\databases\\ratings.csv";
    private final AlbumService albumService;
    
    // Сортировка
    public enum SortType {
        DATE_ADDED_ASC,
        DATE_ADDED_DESC,
        RATING_ASC,
        RATING_DESC
    }
    
    public enum DecadeFilter {
        ALL,
        SIXTIES,  
        SEVENTIES, 
        EIGHTIES,  
        NINETIES,  
        TWO_THOUSANDS 
    }

    public RatingService() {
        this.ratings = CSVReader.readRatings(RATINGS_FILE);
        this.albumService = new AlbumService();
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

    public boolean deleteAlbum(String userId, String albumId) {
        Rating rating = findRating(userId, albumId);
        if (rating != null) {
            ratings.remove(rating);
            CSVWriter.updateRatings(RATINGS_FILE, ratings);
            return true;
        }
        return false;
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

    // Топ альбомов локальными пользователями
    public List<Map.Entry<String, Double>> getTopRatedAlbums(int limit) {
        return getTopRatedAlbums(limit, DecadeFilter.ALL);
    }
    
    public List<Map.Entry<String, Double>> getTopRatedAlbums(int limit, DecadeFilter decade) {
        Map<String, List<Integer>> albumRatings = new HashMap<>();
        
        for (Rating rating : ratings) {
            if (rating.getRating() > 0) { 
                albumRatings.computeIfAbsent(rating.getAlbumId(), k -> new ArrayList<>())
                           .add(rating.getRating());
            }
        }
        
        Map<String, Double> albumAverages = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : albumRatings.entrySet()) {
            List<Integer> albumScores = entry.getValue();
            double average = albumScores.stream().mapToInt(Integer::intValue).average().orElse(0);
            
            String albumId = entry.getKey();
            Album album = albumService.getAlbumById(albumId);
            
            if (decade != null && decade != DecadeFilter.ALL && album != null) {               
                 try {
                    int year = Integer.parseInt(album.getCountry());
                    
                    boolean matchesDecade = switch (decade) {
                        case SIXTIES -> year >= 1960 && year < 1970;
                        case SEVENTIES -> year >= 1970 && year < 1980;
                        case EIGHTIES -> year >= 1980 && year < 1990;
                        case NINETIES -> year >= 1990 && year < 2000;
                        case TWO_THOUSANDS -> year >= 2000;
                        default -> true;
                    };
                    
                    if (matchesDecade) {
                        albumAverages.put(albumId, average);
                    }
                } catch (NumberFormatException e) {
                }
            } else {
                albumAverages.put(albumId, average);
            }
        }
        
        // sorting
        return albumAverages.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Rating findRating(String userId, String albumId) {
        for (Rating rating : ratings) {
            if (rating.getUserId().equals(userId) && rating.getAlbumId().equals(albumId)) {
                return rating;
            }
        }
        return null;
    }
    
    public long getRatingCount(String albumId) {
        return ratings.stream()
                .filter(r -> r.getAlbumId().equals(albumId) && r.getRating() > 0)
                .count();
    }
}