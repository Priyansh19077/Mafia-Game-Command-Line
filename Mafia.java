import java.util.*;

public class Mafia extends Villager implements Comparable<Mafia>{
    private static double HP_Mafias=2500;
    private static ArrayList<Mafia> known_players=new ArrayList<Mafia>();
    private Scanner sc;
    public Mafia(int position) {
        super(HP_Mafias,position);
        sc=new Scanner(System.in);
    }

    @Override
    public int compareTo(Mafia b) {
        if(this.getHP()<b.getHP())
            return -1;
        else if(this.getHP()==b.getHP())
            return 0;
        else
            return 1;
    }
    @Override
    public void delete_known_player(Villager v1) {
        known_players.remove(v1);
        System.out.println(known_players.size());
    }
    @Override
    public Villager do_action(Game g){
        //do action
        Random rand=new Random();
        boolean counter=false;
        for(Mafia i:known_players){
            if(i.equals(g.getUser()) && i.isAlive()) {
                counter=true;
                break;
            }
        }
        if(counter) { //ask ther user to enter the player number.... Handle the errors
            boolean counter1=true;
            while(counter1) {
                try {
                    System.out.print("Select a Target: ");
                    int x = sc.nextInt();
                    if(!(x>0 && x<=g.getNumber_of_players()))
                        System.out.println("Please choose a player from 1 to "+g.getNumber_of_players());
                    else if(x==g.getUser().getPosition()+1)
                        System.out.println("You cannot kill yourself");
                    else if(this.getClass()==g.getPlayers().get(x-1).getClass())
                        System.out.println("You cannot kill a Mafia");
                    else if(!g.getPlayers().get(x-1).isAlive())
                        System.out.println("You cannot kill an already killed player");
                    else {
                        counter1=false;
                        return g.getPlayers().get(x-1);
                    }
                } catch (InputMismatchException e){
                    System.out.println("Chosen player must be an Integer");
                    sc.nextLine();
                    counter1=true;
                }
            }
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
            System.out.println("Mafias have chosen their target");
            return v1;
        }
        return  null;
    }
    @Override
    public void print_known_players() {
        for(int i=0;i<known_players.size();i++)
            if(this.getPosition()!=known_players.get(i).getPosition())
                System.out.print("[Player"+(known_players.get(i).getPosition()+1)+"] ");
        System.out.println();
    }
    public static void add_known_player(Villager v1) {
        Mafia.known_players.add((Mafia)v1);
    }
    public void take_action(Villager v1){
        System.out.println("Mafia's Choice: "+(v1.getPosition()+1));
        double value=v1.getHP();
        double sum=0;
        for(Mafia i:known_players){
            sum+=i.getHP();
        }
        double value_to_subtract=Math.min(value,sum);
        v1.setHP(v1.getHP()-value_to_subtract);
        Collections.sort(known_players);
        double number_of_mafias=known_players.size();
        for(Mafia i:known_players) {
            if (i.getHP()>=value_to_subtract/number_of_mafias)
                i.setHP(i.getHP()-value_to_subtract/number_of_mafias);
            else
            {
                value_to_subtract-=i.getHP();
                i.setHP(0);
                number_of_mafias--;
            }
        }
        if(v1.getHP()==0)
            v1.setAlive(false);
    }
}
