package BusinessLayer;

import java.util.Random;

public class Trap extends Enemies {
    private final int visibilityTime;
    private final int invisibilityTime;
    private int ticksCount;
    private boolean visible;
    private final char realChar;
    //default ctor
    public Trap(String name, int pool, int attack, int defense, char tile, int x, int y, int experience, int visibilityTime, int invisibilityTime) {
        super(name, pool, attack, defense, tile, x, y, experience);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
        this.realChar = tile;
    }

    public String toString() {
        return super.toString();
    }

    public void turn(Player p) { //attack the player if within range and change its visibility status
        visible = ticksCount < visibilityTime;
        if (visible)
            setTile(realChar);
        else
            setTile('.');
        if (ticksCount == (visibilityTime + invisibilityTime))
            ticksCount = 0;
        else
            ticksCount++;
        if (Range(p) < 2) {
            Random r = new Random();
            p.setAttacking(r.nextInt(this.attack + 1));
            p.setDefending(r.nextInt(p.getDefense() + 1));
            if (p.getAttacking() > p.getDefending()) {
                p.setHealth(p.getHealth().getPool(), p.getHealth().getAmount() - (p.getAttacking() - p.getDefending()));
            }
        }
    }
}
