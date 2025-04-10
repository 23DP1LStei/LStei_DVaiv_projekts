package models;

public class Rating {
    private String userId;
    private String albumId;
    private int rating; // от 1 до 5, 0 если не оценено
    private boolean listened;

    // Конструктор
    public Rating(String userId, String albumId, int rating, boolean listened) {
        this.userId = userId;
        this.albumId = albumId;
        this.rating = rating;
        this.listened = listened;
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

    // Сеттеры
    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setListened(boolean listened) {
        this.listened = listened;
    }

    @Override
    public String toString() {
        return "User: " + userId + ", Album: " + albumId + ", Rating: " + rating + ", Listened: " + listened;
    }
}