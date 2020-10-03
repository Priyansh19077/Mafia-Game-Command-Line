import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClass {
    public static void main(String args[]) {
        int number_of_players=6, choice=5;
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to Mafia");
        boolean counter=true;
        while (counter){
            System.out.print("Enter Number of Players: ");
            try {
                number_of_players=sc.nextInt();
                if(number_of_players>=6 && number_of_players<=200000000)
                    counter=false;
                else
                    System.out.println("Number of players must be >= 6 and <=200000000");
            }catch (InputMismatchException e) {
                System.out.println("Number of players can only be an Integer");
                sc.nextLine();
                counter=true;
            }
        }
        counter=true;
        while(counter){
            System.out.println("Choose a Character:");
            System.out.println("1) Mafia");
            System.out.println("2) Detective");
            System.out.println("3) Healer");
            System.out.println("4) Commoner");
            System.out.println("5) Assign Randomly");
            try {
                choice=sc.nextInt();
                if(choice>=1 && choice<=5)
                    counter=false;
                else
                    System.out.println("Please choose a number >=1 and <=5");
            }catch (InputMismatchException e) {
                System.out.println("Your choice can only be an Integer");
                sc.nextLine();
                counter=true;
            }
        }
        Game game=new Game(number_of_players,choice);
        int count=5;
        while(!game.isGame_has_ended()){
            game.play_round();
            count--;
            if(count==0)
                break;
        }
    }
}
