import java.util.ArrayList;

public class CommonerClass extends PlayerClass {
    private static double HP_Commoners = 1000;

    public CommonerClass(int position, ArrayList<CommonerClass> known_players) {
        super(HP_Commoners, position, known_players);
    }
    @Override
    public PlayerClass do_action(GameClass g) {
        //do nothing
        return new MafiaClass(1, new ArrayList<MafiaClass>());
    }
}
