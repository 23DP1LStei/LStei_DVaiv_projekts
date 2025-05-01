import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import models.Album;
import models.Rating;
import models.User;
import services.AlbumService;
import services.RatingService;
import services.RatingService.SortType;
import services.UserService;

public class sonium {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    
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
                System.out.println("\nMenu");
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
                        if (albumIndex < 0 || albumIndex >= foundAlbums.size()) {
                            System.out.println("Invalid choice.");
                            break;
                        }
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
                                if (score < 1 || score > 5) {
                                    System.out.println("Rating must be between 1 and 5.");
                                    break;
                                }
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
                        // Меню сортировки
                        System.out.println("\nSort by:");
                        System.out.println("1. Date added (newest first)");
                        System.out.println("2. Date added (oldest first)");
                        System.out.println("3. Rating (highest first)");
                        System.out.println("4. Rating (lowest first)");
                        
                        System.out.print("Choose sorting option: ");
                        String sortChoice = scanner.nextLine();
                        
                        SortType sortType;
                        switch (sortChoice) {
                            case "1" -> sortType = SortType.DATE_ADDED_DESC;
                            case "2" -> sortType = SortType.DATE_ADDED_ASC;
                            case "3" -> sortType = SortType.RATING_DESC;
                            case "4" -> sortType = SortType.RATING_ASC;
                            default -> {
                                System.out.println("Invalid choice. Showing newest first.");
                                sortType = SortType.DATE_ADDED_DESC;
                            }
                        }
                        
                        List<Rating> listened = ratingService.getUserListenedAlbums(currentUser.getId(), sortType);
                        if (listened.isEmpty()) {
                            System.out.println("You haven't listened any albums yet.");
                        } else {
                            System.out.println("\nYour logged albums:");
                            int counter = 1;
                            for (Rating r : listened) {
                                Album a = albumService.getAlbumById(r.getAlbumId());
                                if (a == null) continue;
                                
                                StringBuilder info = new StringBuilder();
                                info.append(counter++).append(". ");
                                info.append(a.getTitle()).append(" (").append(a.getArtist()).append(")");
                                if (r.getRating() > 0) {
                                    info.append(" - Rating: ").append(r.getRating());
                                }
                                info.append(" - Added: ").append(r.getDateAdded().format(DATE_FORMAT));
                                System.out.println(info.toString());
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
        }
    }
}
