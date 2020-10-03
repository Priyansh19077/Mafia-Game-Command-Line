import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<Villager> players;
    private boolean game_has_ended;
    private int number_of_players;
    private int number_of_mafias;
    private int number_of_detectives;
    private int number_of_healers;
    private int number_of_commoners;
    private final int selected_role;
    private final Villager user;
    private boolean no_need_for_voting;
    boolean occupied[];
    private ArrayList<Villager> known_villagers_to_user;
    public Game(int n, int selected_role) {
        this.number_of_players=n;
        occupied=new boolean[this.number_of_players];
        this.game_has_ended=false;
        players=new ArrayList<Villager>(number_of_players);
        for(int i=0;i<n;i++)  //only for initializing
            players.add(new Mafia(1));
        System.out.println(selected_role);
        if(selected_role==5)
            selected_role=assign_random_player();
        this.selected_role=selected_role;
        this.number_of_mafias=n/5;
        this.number_of_detectives=n/5;
        this.number_of_healers=Math.max(1,n/10);
        this.number_of_commoners=n-number_of_detectives-number_of_healers-number_of_mafias;
        add_mafias();
        add_detectives();
        add_healers();
        add_commoners();
        user=assign_user(selected_role);
        print_info();
    }


    private void add_mafias() {
        Random rand=new Random();
//        System.out.print("Mafias: ");
        for(int i=0;i<number_of_mafias;i++) {
            while(true) {
                int x=rand.nextInt(number_of_players);
                if(!occupied[x]) {
                    players.set(x,new Mafia(x));
                    Mafia.add_known_player(players.get(x));
//                    System.out.print((x+1)+" ");
                    occupied[x]=true;
                    break;
                }
            }
        }
//        System.out.println();
    }

    private void add_detectives() {
        Random rand=new Random();
//        System.out.print("Detectives are: ");
        for(int i=0;i<number_of_detectives;i++) {
            while(true) {
                int x=rand.nextInt(number_of_players);
                if(!occupied[x]) {
                    players.set(x,new Detective(x));
                    Detective.add_known_player(players.get(x));
//                    System.out.print((x+1)+" ");
                    occupied[x]=true;
                    break;
                }
            }
        }
//        System.out.println();
    }

    private void add_healers() {
//        System.out.print("Healers are: ");
        Random rand=new Random();
        for(int i=0;i<number_of_healers;i++) {
            while(true) {
                int x=rand.nextInt(number_of_players);
                if(!occupied[x]) {
                    players.set(x,new Healer(x));
                    Healer.add_known_player(players.get(x));
//                    System.out.print((x+1)+" ");
                    occupied[x]=true;
                    break;
                }
            }
        }
//        System.out.println();
    }

    private void add_commoners() {
        Random rand=new Random();
//        System.out.print("Commoners are: ");
        for(int i=0;i<number_of_commoners;i++) {
            while(true) {
                int x=rand.nextInt(number_of_players);
                if(!occupied[x]) {
                    players.set(x,new Commoner(x));
//                    Commoner.add_known_player(players.get(x));
//                    System.out.print((x+1)+" ");
                    occupied[x]=true;
                    break;
                }
            }
        }
//        System.out.println();
    }

    private int assign_random_player(){
        Random random=new Random();
        return random.nextInt(4);
    }
    private void print_info(){
        System.out.println("You are Player"+(user.getPosition()+1));
        if(user.getClass()==(new Commoner(1)).getClass())
            System.out.print("You are a Commoner");
        if(user.getClass()==(new Mafia(1)).getClass())
            System.out.print("You are a Mafia. Other mafias are: ");
        if(user.getClass()==(new Detective(1)).getClass())
            System.out.print("You are a Detective. Other detectives are: ");
        if(user.getClass()==(new Healer(1)).getClass())
            System.out.print("You are a Healer. Other healers are: ");
        user.print_known_players();
    }
    public Villager assign_user(int choice) {
        Random rand=new Random();
        Villager v1=new Mafia(1);
        if(choice==2)
            v1=new Detective(1);
        else if(choice==3)
            v1=new Healer(1);
        else if(choice==4)
            v1=new Commoner(1);
        while(true) {
            int a=rand.nextInt(number_of_players);
//            System.out.println(players.size());
            if(players.get(a).getClass()==v1.getClass()) {
                return players.get(a);
            }
        }
    }
    public boolean isGame_has_ended(){
        return this.game_has_ended;
    }
    public  void play_round(){
        no_need_for_voting=false;
        Mafia m=new Mafia(1);
        Villager v1=m.do_action(this);
        Detective d=new Detective(1);
        m.take_action(v1);
        Villager v2=d.do_action(this);
        Healer h=new Healer(1);
        Villager v3=h.do_action(this);
        if(v1.getClass()==m.getClass())
            no_need_for_voting=true;
        h.take_action(v3);
        d.take_action(v2);
        for(int i=0;i<number_of_players;i++) {
            if (players.get(i).getHP() == 0 && players.get(i).getClass() != (new Mafia(1)).getClass()) {
                System.out.println((players.get(i).getPosition() + 1) + "is killed");
                players.get(i).setAlive(false);
                players.get(i).delete_known_player(players.get(i));
            }
        }
        if(!no_need_for_voting) {
            do_voting();
        }
        determine_game_state();
    }
    public void setNo_need_for_voting(boolean a) {
        no_need_for_voting=a;
    }
    private void determine_game_state(){
        int count_of_players=0;
        for(Villager v:players) {
            if (v.isAlive())
                count_of_players++;
        }
        System.out.println("Number of players remaining: "+count_of_players);
        for(Villager v:players) {
            if(v.isAlive())
                System.out.println(v.getPosition() + "  " + v.getClass() + "  " + v.getHP() + "  "+v.isAlive());
            else {
                v.delete_known_player(v);
            }
        }
    }
    public void do_voting(){

    }
    public Villager getUser(){
        return this.user;
    }
    public ArrayList<Villager> getPlayers(){
        return this.players;
    }
    public int getNumber_of_players(){
        return this.number_of_players;
    }
}
