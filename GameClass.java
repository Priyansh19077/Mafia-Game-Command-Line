import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GameClass {
    private static ArrayList<PlayerClass> players;
    private static ArrayList<CommonerClass> known_players4=new ArrayList<CommonerClass>();
    private static ArrayList<MafiaClass> known_players1=new ArrayList<MafiaClass>();
    private static ArrayList<HealerClass> known_players3=new ArrayList<HealerClass>();
    private static ArrayList<DetectiveClass> known_players2=new ArrayList<DetectiveClass>();
    private boolean game_has_ended;
    private int number_of_players;
    private int number_of_mafias;
    private int number_of_detectives;
    private int number_of_healers;
    private int number_of_commoners;
    private final int selected_role;
    private final PlayerClass user;
    Scanner sc;
    private boolean no_need_for_voting;
    boolean occupied[];
    public GameClass(int n, int selected_role) {
        sc=new Scanner(System.in);
        this.number_of_players=n;
        occupied=new boolean[this.number_of_players];
        this.game_has_ended=false;
        players=new ArrayList<PlayerClass>(number_of_players);
        for(int i=0;i<n;i++)  //only for initializing
            players.add(new MafiaClass(1, new ArrayList<MafiaClass>()));
//        System.out.println("Selected role: "+selected_role);
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

    public PlayerClass getUser(){
        return this.user;
    }

    public ArrayList<PlayerClass> getPlayers(){
        return this.players;
    }

    public int getNumber_of_players(){
        return this.number_of_players;
    }

    private void add_mafias() {
        Random rand=new Random();
//        System.out.print("Mafias: ");
        for(int i=0;i<number_of_mafias;i++) {
            while(true) {
                int x=rand.nextInt(number_of_players);
                if(!occupied[x]) {
                    players.set(x,new MafiaClass(x, known_players1));
                    ArrayList<MafiaClass> h1= (ArrayList<MafiaClass>) players.get(x).getKnown_players();
                    h1.add((MafiaClass) players.get(x));
//                    System.out.print("[Player"+(x+1)+"] ");
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
                    players.set(x,new DetectiveClass(x,known_players2));
                    ArrayList<DetectiveClass> h1= (ArrayList<DetectiveClass>) players.get(x).getKnown_players();
                    h1.add((DetectiveClass) players.get(x));
//                    System.out.print("[Player"+(x+1)+"] ");
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
                    players.set(x,new HealerClass(x,known_players3));
                    ArrayList<HealerClass> h1= (ArrayList<HealerClass>) players.get(x).getKnown_players();
                    h1.add((HealerClass) players.get(x));
//                    System.out.print("[Player"+(x+1)+"] ");
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
                    players.set(x,new CommonerClass(x,known_players4));
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
        if(user.getClass()==(new CommonerClass(1,known_players4)).getClass())
            System.out.print("You are a Commoner");
        if(user.getClass()==(new MafiaClass(1,known_players1)).getClass())
            System.out.print("You are a Mafia. Other mafias are: ");
        if(user.getClass()==(new DetectiveClass(1,known_players2)).getClass())
            System.out.print("You are a Detective. Other detectives are: ");
        if(user.getClass()==(new HealerClass(1,known_players3)).getClass())
            System.out.print("You are a Healer. Other healers are: ");
        user.print_known_players();
    }

    public PlayerClass assign_user(int choice) {
        Random rand=new Random();
        PlayerClass v1=new MafiaClass(1,known_players1);
        if(choice==2)
            v1=new DetectiveClass(1,known_players2);
        else if(choice==3)
            v1=new HealerClass(1,known_players3);
        else if(choice==4)
            v1=new CommonerClass(1,known_players4);
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
        int remaining=0;
        for(int i=0;i<number_of_players;i++) {
            if (players.get(i).isAlive())
                remaining++;
        }
        System.out.print(remaining+" players are remaining: ");
        //Remove the comment only here
        for(int i=0;i<number_of_players;i++) {
            if (players.get(i).isAlive())
                System.out.print("[Player" + (i + 1) + "] ");
        }
        System.out.println(" are alive");
        no_need_for_voting=false;
        MafiaClass m=new MafiaClass(1,known_players1);
        PlayerClass v1=m.do_action(this);
        DetectiveClass d=new DetectiveClass(1,known_players2);
        if(v1!=null) {
            m.take_action(v1);
        } else{
//            System.out.println("Mafias' choice is a NULL as the Mafias are all dead!!! .... Should Never happen");
        }
        PlayerClass v2=d.do_action(this);
        HealerClass h=new HealerClass(1,known_players3);
        PlayerClass v3=h.do_action(this);
        if(v1!=null && v1.getClass()==m.getClass())
            no_need_for_voting=true;
        if(v3!=null) {
            h.take_action(v3);
        }else{
//            System.out.println("Healers's choice is a NULL as the Healers are all dead!!! .... can happen");
        }
        if(v2!=null) {
            d.take_action(v2);
        }else{
//            System.out.println("Detectives' choice is a NULL as the Detectives are all dead!!! .... can happen");
        }
        for(int i=0;i<number_of_players;i++) {
            if (( players.get(i).isActive() && players.get(i).getHP() == 0 && players.get(i).getClass()!=(new MafiaClass(1,known_players1)).getClass())) {
                System.out.println((players.get(i).getPosition() + 1) + " has died");
                players.get(i).setActive(false);
                players.get(i).setAlive(false);
                players.get(i).delete_known_player(players.get(i));
            }
        }
        System.out.println("--End of Actions--");
        if(!no_need_for_voting) {
            determine_game_state();
            if(game_has_ended)
                return;
            do_voting();
        }
        determine_game_state();
    }

    private void determine_game_state(){
        int count_of_players=0;
        for(PlayerClass v:players) {
            if (v.isAlive())
                count_of_players++;
        }
//        System.out.println("Number of players remaining: "+count_of_players);
//        for(PlayerClass v:players) {
//            System.out.println((v.getPosition()+1) + "  " + v.getClass() + "  " + v.getHP() + "  "+v.isAlive()+" "+v.isActive());
//        }
//        MafiaClass m1=new MafiaClass(-1,known_players1);
//        System.out.print("Mafias remaining: ");
//        m1.print_known_players();
//        DetectiveClass d1=new DetectiveClass(-1,known_players2);
//        System.out.print("Detectives remaining: ");
//        d1.print_known_players();
//        HealerClass h1=new HealerClass(-1,known_players3);
//        System.out.print("Healers remaining: ");
//        h1.print_known_players();
        int alive=0;
        int mafias=0;
        for(int i=0;i<number_of_players;i++)
            if(players.get(i).isAlive()) {
                alive++;
                if(players.get(i).getClass()==(new MafiaClass(1,known_players1)).getClass())
                    mafias++;
            }
        if(mafias==0 || alive==2*mafias) {
//            System.out.println(mafias+" "+alive);
            game_has_ended=true;
            System.out.println("Game Over");
            if(alive==2*mafias)
            System.out.println("The Mafias have won!!!");
            else
            System.out.println("The Mafias have lost!!!");
            for(int i=0;i<number_of_players;i++)
                if(players.get(i).getClass()==(new MafiaClass(1,known_players1)).getClass()) {
                    System.out.print("Player" + (i + 1));
                    if(players.get(i)==user)
                        System.out.print("[User]");
                    System.out.print(" ");
                }
            System.out.println("were Mafias");
            for(int i=0;i<number_of_players;i++)
                if(players.get(i).getClass()==(new DetectiveClass(1,known_players2)).getClass()) {
                    System.out.print("Player" + (i + 1));
                    if(players.get(i)==user)
                        System.out.print("[User]");
                    System.out.print(" ");
                }
            System.out.println("were Detectives");
            for(int i=0;i<number_of_players;i++)
                if(players.get(i).getClass()==(new HealerClass(1,known_players3)).getClass()) {
                    System.out.print("Player" + (i + 1));
                    if(players.get(i)==user)
                        System.out.print("[User]");
                    System.out.print(" ");
                }
            System.out.println("were Healers");
            for(int i=0;i<number_of_players;i++)
                if(players.get(i).getClass()==(new CommonerClass(1,known_players4)).getClass()) {
                    System.out.print("Player" + (i + 1));
                    if(players.get(i)==user)
                        System.out.print("[User]");
                    System.out.print(" ");
                }
            System.out.println("were Commnoners");
        }

    }

    public void do_voting(){
        Random rand=new Random();
        int number_of_votes[]=new int[number_of_players];
//        if(user.isAlive())
//          number_of_votes[user.getPosition()]=100000;
//        only to test simulation
        for(int i=0;i<number_of_players;i++)
        {
            PlayerClass v1=players.get(i);
            if(v1.isAlive()){
                if(v1==user){
                    boolean counter1=true;
                    while(counter1) {
                        try {
                            System.out.print("Select a Player to vote for: ");
                            int x = sc.nextInt();
                            if (!(x > 0 && x <= number_of_players))
                                System.out.println("Please choose a player from 1 to " + number_of_players);
                            else if (!getPlayers().get(x - 1).isAlive())
                                System.out.println("You cannot vote for an already killed player");
                            else {
                                counter1 = false;
                                number_of_votes[x-1]++;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Chosen player must be an Integer");
                            sc.nextLine();
                            counter1 = true;
                        }
                    }
                }
                else {
                    boolean counter1=true;
                    while(counter1){
                        int x=rand.nextInt(number_of_players);
                        if(players.get(x).isAlive()){
                            counter1=false;
                            number_of_votes[x]++;
                        }
                    }                    //do random voting
                }
            }
        }
        int max_votes=0;
        int index=0;
        boolean tie=false;
//        System.out.print("Votes are: ");
        for(int i=0;i<number_of_players;i++){
            if(number_of_votes[i]>max_votes){
                max_votes= number_of_votes[i];
                index=i;
                tie=false;
            }
            else if(number_of_votes[i]==max_votes){
                tie=true;
            }
//            System.out.print(number_of_votes[i]+" ");
        }
//        System.out.println();
        if(!tie){
            System.out.println("Player"+(index+1)+" has been voted out!");
            players.get(index).setAlive(false);
            players.get(index).setActive(false);
            players.get(index).delete_known_player(players.get(index));
            return;
        }
        else
        {
            if(user.isAlive())
                System.out.println("It is a tie, voting will be done again!");
            do_voting();
        }
    }

}
