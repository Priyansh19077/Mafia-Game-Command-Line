import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DetectiveClass extends PlayerClass {
    private static double HP_Detectives=800;
    private Scanner sc;
    public DetectiveClass(int position, ArrayList<DetectiveClass> known_players) {
        super(HP_Detectives,position,known_players);
        sc=new Scanner(System.in);
    }
    @Override
    public PlayerClass do_action(GameClass g){
        Random rand=new Random();
        ArrayList<DetectiveClass> known_players= (ArrayList<DetectiveClass>) this.getKnown_players();
        if(known_players.size()==0) {
            System.out.println("Detectives have chosen someone to test");
            return null;
        }
        boolean counter=false;
        for(DetectiveClass i:known_players){
            if(i.equals(g.getUser()) && i.isAlive()) {
                counter=true;
                break;
            }
        }
        if(counter) { //ask the user to enter the player number.... Handle the errors
            boolean counter1=true;
            PlayerClass v1=new CommonerClass(1,new ArrayList<CommonerClass>());
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
                        if(v1.getClass()==(new MafiaClass(1,new ArrayList<MafiaClass>())).getClass())
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
            PlayerClass v1=new DetectiveClass(1,new ArrayList<DetectiveClass>());
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

    public void take_action(PlayerClass v1){
//        System.out.println("Detective's choice: "+(v1.getPosition()+1));
        if(v1.getClass()==(new MafiaClass(1,new ArrayList<MafiaClass>())).getClass()){
            v1.setAlive(false);
            v1.delete_known_player(v1);
            v1.setActive(false);
            System.out.println((v1.getPosition()+1)+" was a Mafia and has been killed!");
        }
    }
}
