public abstract class Villager {
    protected double HP;
    protected final int position;
    protected boolean alive;
    public Villager(double HP, int position) {
        this.HP = HP;
        this.alive=true;
        this.position=position;
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
    public boolean isAlive(){
        return alive;
    }
    public void setAlive(boolean a){
        alive=a;
    }
    public abstract Villager do_action(Game g);
    public abstract void print_known_players();
    public abstract void delete_known_player(Villager v1);
}
