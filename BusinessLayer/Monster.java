package BusinessLayer;

import java.util.Random;

public class Monster extends Enemies {
    private final int visionRange;
    //default ctor
    public Monster(String name, int pool, int attack, int defense, char tile, int x, int y, int experience, int visionRange) {
        super(name, pool, attack, defense, tile, x, y, experience);
        this.visionRange = visionRange;
    }

    public int move(Player p) { //return the direction the boss moves
        if (Range(p) < this.visionRange) {
            int dx = this.pos.getX() - p.pos.getX();
            int dy = this.pos.getY() - p.pos.getY();
            if (Math.abs(dx) <= Math.abs(dy)) {
                if (dy > 0) {
                    return 0; //up
                } else {
                    return 1; //down
                }
            } else {
                if (dx > 0) {
                    return 2; //left
                } else {
                    return 3; //right
                }
            }                   //4=stay at the same place.
        } else {
            Random r = new Random();
            return r.nextInt(5);
        }
    }

    public String toString() {
        return super.toString() + "Vision Range: " + visionRange;
    }

}
