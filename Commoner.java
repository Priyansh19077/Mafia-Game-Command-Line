import java.util.ArrayList;

public class Commoner extends Villager{
    private static double HP_Commoners=1000;
    private static ArrayList<Commoner> known_players=new ArrayList<Commoner>();
    public Commoner(int position) {
        super(HP_Commoners,position);
    }
    @Override
    public Villager do_action(Game g){
        //do nothing
        return new Mafia(1);
    }

    @Override
    public void delete_known_player(Villager v1) {
        known_players.remove(v1);
    }

    @Override
    public void print_known_players() {
        for(int i=0;i<known_players.size();i++) {
            if(this.getPosition()!=known_players.get(i).getPosition())
                System.out.print("[Player" + (known_players.get(i).getPosition() + 1) + "] ");
        }
        System.out.println();
    }

    public static void add_known_player(Villager v1) {
        Commoner.known_players.add((Commoner) v1);
    }
}
