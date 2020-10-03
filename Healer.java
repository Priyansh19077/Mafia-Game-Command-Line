import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Healer extends Villager{
    private static double HP_Healers=800;
    private static ArrayList<Healer> known_players=new ArrayList<Healer>();
    private Scanner sc;
    public Healer(int position) {
        super(HP_Healers,position);
        sc=new Scanner(System.in);
    }
    @Override
    public Villager do_action(Game g){
        //do action
        Random rand=new Random();
        if(known_players.size()==0) {
            System.out.println("Healers have chosen someone to heal");
            return null;
        }
        boolean counter=false;
        for(Healer i:known_players){
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
                    else if(!g.getPlayers().get(x-1).isAlive())
                        System.out.println("You cannot choose an already killed player");
                    else {
                        v1=g.getPlayers().get(x - 1);
                        counter1=false;
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
                if(!v1.isAlive())
                    continue;
                else
                    break;
            }
            System.out.println("Healers have chosen someone to heal");
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
                System.out.print("[Player" + (known_players.get(i).getPosition() + 1) + "] ");
        System.out.println();
    }
    public static void add_known_player(Villager v1) {
        Healer.known_players.add((Healer)v1);
    }
    public void take_action(Villager v1){
        System.out.println("Healer's choice: "+(v1.getPosition()+1));
        v1.setHP(v1.getHP()+500);
        return;
    }
}
