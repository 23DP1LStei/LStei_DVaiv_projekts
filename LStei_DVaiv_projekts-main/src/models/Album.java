package models;

public class Album {
    private String id;
    private String title;
    private String artist;
    private String genre;

    // Конструктор
    public Album(String id, String title, String artist, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    // Сеттеры (если нужно менять данные)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Для вывода в консоль
    @Override
    public String toString() {
        return title + " by " + artist + " [" + genre + "]";
    }
}