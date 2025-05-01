package models;

import java.time.LocalDateTime;

public class Rating {
    private final String userId;
    private final String albumId;
    private int rating; // от 1 до 5, 0 если не оценено
    private boolean listened;
    private LocalDateTime dateAdded;

    public Rating(String userId, String albumId, int rating, boolean listened, LocalDateTime dateAdded) {
        this.userId = userId;
        this.albumId = albumId;
        this.rating = rating;
        this.listened = listened;
        this.dateAdded = dateAdded;
    }

    // Геттеры
    public String getUserId() {
        return userId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public int getRating() {
        return rating;
    }

    public boolean isListened() {
        return listened;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    // Сеттеры
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setListened(boolean listened) {
        this.listened = listened;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "User: " + userId + ", Album: " + albumId + ", Rating: " + rating + 
               ", Listened: " + listened + ", Added: " + dateAdded;
    }
}