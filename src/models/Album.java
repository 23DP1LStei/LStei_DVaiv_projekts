package models;

public class Album {
    private final String id;
    private String title;
    private String artist;
    private String country; // год

    public Album(String id, String title, String artist, String country) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCountry() {
        return country;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Вывод
    @Override
    public String toString() {
        return title + " by " + artist + " (" + country + ")";
    }
}
