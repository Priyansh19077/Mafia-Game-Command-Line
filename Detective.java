import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Detective extends Villager{
    private static double HP_Detectives=800;
    private Scanner sc;
    private static ArrayList<Detective> known_players=new ArrayList<Detective>();
    public Detective(int position) {
        super(HP_Detectives,position);
        sc=new Scanner(System.in);
    }
    @Override
    public Villager do_action(Game g){
        Random rand=new Random();
        if(known_players.size()==0) {
            System.out.println("Detectives have chosen someone to test");
            return null;
        }
        boolean counter=false;
        for(Detective i:known_players){
            if(i.equals(g.getUser()) && i.isAlive()) {
                counter=true;
                break;
            }
        }
        if(counter) { //ask ther user to enter the player number.... Handle the errors
            boolean counter1=true;
            Villager v1=new Commoner(1);
            while(counter1) {
                try {
                    System.out.print("Choose a player to test: ");
                    int x = sc.nextInt();
                    if(!(x>0 && x<=g.getNumber_of_players()))
                        System.out.println("Please choose a player from 1 to "+g.getNumber_of_players());
                    else if(x==g.getUser().getPosition()+1)
                        System.out.println("You cannot choose yourself");
                    else if(this.getClass()==g.getPlayers().get(x-1).getClass())
                        System.out.println("You cannot choose a Detective");
                    else if(!g.getPlayers().get(x-1).isAlive())
                        System.out.println("You cannot choose an already killed player");
                    else {
                        v1=g.getPlayers().get(x - 1);
                        counter1=false;
                        if(v1.getClass()==(new Mafia(1)).getClass())
                            System.out.println((v1.getPosition()+1)+" is a Mafia");
                        else
                            System.out.println((v1.getPosition()+1)+" is not a Mafia");
                        return v1;
                    }
                } catch (InputMismatchException e){
                    System.out.println("Chosen player must be an Integer");
                    sc.nextLine();
                    counter1=true;
                }
            }
            return v1;
        }
        else {  //select randomly
            boolean counter2=true;
            Villager v1=new Commoner(1);
            while(counter2) {
                int x = rand.nextInt(g.getNumber_of_players());
                v1=g.getPlayers().get(x);
                if(!v1.isAlive() || this.getClass()==v1.getClass())
                    continue;
                else
                    break;
            }
            System.out.println("Detectives have chosen someone to test");
            return v1;
        }
    }

    @Override
    public void delete_known_player(Villager v1) {
        known_players.remove(v1);
    }
    @Override
    public void print_known_players() {
        for(int i=0;i<known_players.size();i++)
            if(this.getPosition()!=known_players.get(i).getPosition())
                System.out.print("[Player"+(known_players.get(i).getPosition()+1)+"] ");
        System.out.println();
    }
    public static void add_known_player(Villager v1) {
        Detective.known_players.add((Detective) v1);
    }
    public void take_action(Villager v1){
        System.out.println("Detective's choice: "+(v1.getPosition()+1));
        if(v1.getClass()==(new Mafia(1)).getClass()){
            v1.setAlive(false);
            v1.delete_known_player(v1);
            System.out.println((v1.getPosition()+1)+" was a Mafia and has been killed!");
        }
    }
}
