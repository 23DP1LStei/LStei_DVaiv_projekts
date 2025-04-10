import java.util.Scanner;
import models.User;
import services.UserService;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("Welcome to Sonium!");
        System.out.print("Enter user name: ");
        String username = scanner.nextLine();

        // registration
        User currentUser = userService.registerUser(username);
        System.out.println("Hi, " + currentUser.getUsername() + "!");


    }
}