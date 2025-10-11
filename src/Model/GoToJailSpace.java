package Model;

public class GoToJailSpace extends Space {
    public GoToJailSpace(String name) {
        super(name);
    }
    
    @Override
    public Codes onLand(Player player) {
        return Codes.SENT_TO_JAIL;
    }
}