import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class HealerClass extends PlayerClass {
    private static double HP_Healers=800;
    private Scanner sc;
    public HealerClass(int position, ArrayList<HealerClass> known_players) {
        super(HP_Healers,position,known_players);
        sc=new Scanner(System.in);
    }
    @Override

    public PlayerClass do_action(GameClass g){
        //do action
        Random rand=new Random();
        ArrayList<HealerClass> known_players= (ArrayList<HealerClass>) this.getKnown_players();
        if(known_players.size()==0) {
            System.out.println("Healers have chosen someone to heal");
            return null;
        }
        boolean counter=false;
        for(HealerClass i:known_players){
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
                    System.out.print("Choose a player to heal: ");
                    int x = sc.nextInt();
                    if(!(x>0 && x<=g.getNumber_of_players()))
                        System.out.println("Please choose a player from 1 to "+g.getNumber_of_players());
                    else if(!g.getPlayers().get(x-1).isAlive())
                        System.out.println("You cannot choose an already killed player");
                    else {
                        v1=g.getPlayers().get(x - 1);
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
            PlayerClass v1=new CommonerClass(1,new ArrayList<CommonerClass>());
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

    public void take_action(PlayerClass v1){
//        System.out.println("Healer's choice: "+(v1.getPosition()+1));
        v1.setHP(v1.getHP()+500);
        return;
    }
}
