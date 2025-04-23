package services;

import java.util.List;
import java.util.stream.Collectors;
import models.Album;
import utils.CSVReader;

public class AlbumService {
    private final List<Album> albums;

    public AlbumService() {
        this.albums = CSVReader.readAlbums("src\\databases\\albums.csv");
    }

    public List<Album> getAllAlbums() {
        return albums;
    }

    public Album getAlbumById(String id) {
        for (Album album : albums) {
            if (album.getId().equals(id)) return album;
        }
        return null;
    }

    public List<Album> searchAlbums(String query) {
        return albums.stream()
                .filter(album -> album.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    public Album findAlbumById(String id) {
        for (Album album : albums) {
            if (album.getId().equals(id)) {
                return album;
            }
        }
        return null;
    }
}
