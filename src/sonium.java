import java.util.List;
import java.util.Scanner;
import models.Album;
import models.Rating;
import models.User;
import services.AlbumService;
import services.RatingService;
import services.UserService;

public class sonium {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
    
            System.out.println("""

              ██████  ▒█████   ███▄    █  ██▓ █    ██  ███▄ ▄███▓
            ▒██    ▒ ▒██▒  ██▒ ██ ▀█   █ ▓██▒ ██  ▓██▒▓██▒▀█▀ ██▒
            ░ ▓██▄   ▒██░  ██▒▓██  ▀█ ██▒▒██▒▓██  ▒██░▓██    ▓██░
              ▒   ██▒▒██   ██░▓██▒  ▐▌██▒░██░▓▓█  ░██░▒██    ▒██ 
            ▒██████▒▒░ ████▓▒░▒██░   ▓██░░██░▒▒█████▓ ▒██▒   ░██▒
            ▒ ▒▓▒ ▒ ░░ ▒░▒░▒░ ░ ▒░   ▒ ▒ ░▓  ░▒▓▒ ▒ ▒ ░ ▒░   ░  ░
            ░ ░▒  ░ ░  ░ ▒ ▒░ ░ ░░   ░ ▒░ ▒ ░░░▒░ ░ ░ ░  ░      ░
            ░  ░  ░  ░ ░ ░ ▒     ░   ░ ░  ▒ ░ ░░░ ░ ░ ░      ░   
                  ░      ░ ░           ░  ░     ░            ░   
                                                                 
            
            """);
    
            AlbumService albumService = new AlbumService();
            RatingService ratingService = new RatingService();
            UserService userService = new UserService();

            System.out.println("Welcome to Sonium!");
            System.out.print("Enter your name: ");
            String username = scanner.nextLine();

            // Регистрация 
            User currentUser = userService.registerUser(username);
            
            System.out.println("Hi, " + currentUser.getUsername() + "!");

            while (true) {
                // Главное меню
                System.out.println("Menu");
                System.out.println("1. Find an album");
                System.out.println("2. My albums");
                System.out.println("3. Quit");

                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.print("Enter album's name: ");
                        String query = scanner.nextLine();
                        List<Album> foundAlbums = albumService.searchAlbums(query);

                        if (foundAlbums.isEmpty()) {
                            System.out.println("Nothing found.");
                            break;
                        }

                        // Результат поиска
                        for (int i = 0; i < foundAlbums.size(); i++) {
                            System.out.println((i + 1) + ". " + foundAlbums.get(i));
                        }

                        System.out.print("Choose album by number: ");
                        int albumIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        Album selectedAlbum = foundAlbums.get(albumIndex);

                        System.out.println("You chose: " + selectedAlbum);
                        System.out.println("1. Mark as listened");
                        System.out.println("2. Rate an album");
                        System.out.println("3. View average rating");

                        String action = scanner.nextLine();

                        switch (action) {
                            case "1" -> {
                                ratingService.markAsListened(currentUser.getId(), selectedAlbum.getId());
                                System.out.println("Marked as listened!");
                        }
                            case "2" -> {
                                System.out.print("Enter rating (1-5): ");
                                int score = Integer.parseInt(scanner.nextLine());
                                ratingService.rateAlbum(currentUser.getId(), selectedAlbum.getId(), score);
                                System.out.println("Thanks for your rating!");
                        }
                            case "3" -> {
                                double avgRating = ratingService.getAlbumAverageRating(selectedAlbum.getId());
                                System.out.println("Average album's rating: " + avgRating);
                        }
                            default -> System.out.println("Incorrect choise.");
                        }
                    }

                    case "2" -> {
                        List<Rating> listened = ratingService.getUserListenedAlbums(currentUser.getId());
                        if (listened.isEmpty()) {
                            System.out.println("You haven't listened any albums yet.");
                        } else {
                            System.out.println("Your logged albums:");
                            for (Rating r : listened) {
                                Album a = albumService.getAlbumById(r.getAlbumId());
                                String info = a.getTitle() + " (" + a.getArtist() + ")";
                                if (r.getRating() > 0) {
                                    info += " - Rating: " + r.getRating();
                                }
                                System.out.println(". " + info);
                            }
                        }
                    }

                    case "3" -> {
                        System.out.println("Bye-bye!!!");
                        return;
                    }

                    default -> System.out.println("Incorrect. Try again.");
                }
            }
        } catch (NumberFormatException e) {

        }
    }
}
