import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Main3 {


   private static LearningAppFacade facade = LearningAppFacade.getInstance();


   private static void reloadAndDisplayUsers() {
       System.out.println("Loading User:");
       // TODO:
       ArrayList<User> reloadedUsers = facade.loadUsers();
       for (User user : reloadedUsers) {
           System.out.println(user.toString());
       }
       System.out.println("");
   }


   private static void displayLoginResult(String usernameOrEmail, User user) {
       if (user != null) {
           System.out.println("Login successful for: " + usernameOrEmail);
       } else {
           System.out.println("Login failed. Invalid credentials.");
       }
       System.out.println("");
   }
}