package BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends Enemies implements HeroicUnit {
    private final int abilityFrequency;
    private int combatTicks;
    private final int visionRange;

    //default ctor
    public Boss(String name, int pool, int attack, int defense, char tile, int x, int y, int experience, int visionRange, int abilityFrequency) {
        super(name, pool, attack, defense, tile, x, y, experience);
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
    }

    public int move(Player p) { //return the direction the boss moves
        if (Range(p) < visionRange) {
            if (combatTicks == abilityFrequency) {
                ArrayList<Units> player = new ArrayList<>();
                player.add(p);
                castAbility(player, new ArrayList<>());
                return 5; //special ability
            } else {
                combatTicks++;
                int dx = pos.getX() - p.pos.getX();
                int dy = pos.getY() - p.pos.getY();
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
                }
            }                   //4=stay at the same place.
        } else {
            combatTicks = 0;
            Random r = new Random();
            return r.nextInt(5);
        }
    }

    @Override
    public String castAbility(List<Units> enemies, List<Units> damaged) { //boss special ability
        Random r = new Random();
        for (Units u : enemies) {
            u.setDefending(r.nextInt(u.getDefense() + 1));
            if (attack > u.getDefending()) {
                u.setHealth(u.getHealth().getPool(), u.getHealth().getAmount() - (attack - u.getDefending()));
                if (u.getHealth().getAmount() <= 0)
                    u.isDead = true;
            }
        }
        combatTicks = 0;
        return null;
    }
}