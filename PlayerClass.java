import java.util.ArrayList;

public abstract class PlayerClass {
    protected double HP;
    private ArrayList<? extends PlayerClass> known_players;
    protected final int position;
    protected boolean alive;
    protected boolean active;
    public PlayerClass(double HP, int position, ArrayList<? extends PlayerClass> known_players) {
        this.HP = HP;
        this.alive=true;
        this.position=position;
        this.known_players=known_players;
        this.active=true;
    }

    public double getHP(){
        return HP;
    }

    public void setHP(double HP){
        this.HP=HP;
    }

    public int getPosition(){
        return position;
    }

    public boolean isAlive(){ return alive; }

    public void setAlive(boolean a){
        alive=a;
    }

    public abstract PlayerClass do_action(GameClass g);

    public void print_known_players(){
        for(int i=0;i<known_players.size();i++)
        {
            if(known_players.get(i)==this)
                continue;
            else
                System.out.print("[Player"+(known_players.get(i).getPosition()+1)+"] ");
        }
        System.out.println();
    }

    public void delete_known_player(PlayerClass v1){
        known_players.remove(v1);
    }

    public ArrayList<? extends PlayerClass> getKnown_players(){
        return known_players;
    }

    public void setActive(boolean a) {
        this.active=a;
    }

    public boolean isActive(){
        return this.active;
    }
}
