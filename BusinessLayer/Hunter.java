package BusinessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hunter extends Player {
    private final int range;
    private int arrowsCount;
    private int ticksCount;

    //default ctor
    public Hunter(String name, int pool, int attack, int defense, int x, int y, int range) {
        super(name, pool, attack, defense, x, y);
        this.range = range;
        arrowsCount = 10;
        ticksCount = 0;
    }

    @Override
    public String castAbility(List<Units> enemies, List<Units> damaged) { //hunter special ability
        if (arrowsCount == 0) {
            return "you dont have any arrows left";
        }
        int index = -1;
        for (Units e : enemies
        ) {
            if (Range(e) < range) {
                if (index == -1) {
                    index = enemies.indexOf(e);
                } else {
                    if (Range(e) < Range(enemies.get(index))) {
                        index = enemies.indexOf(e);
                    }
                }
            }
        }
        if (index == -1) {
            return "there are no enemies within range";
        }
        arrowsCount--;
        Random r = new Random();
        enemies.get(index).setDefending(r.nextInt(enemies.get(index).getDefense() + 1));
        if (attack > enemies.get(index).getDefending()) {
            enemies.get(index).setHealth(enemies.get(index).getHealth().getPool(), enemies.get(index).getHealth().getAmount() - (attack - enemies.get(index).getDefending()));
            if (enemies.get(index).getHealth().getAmount() <= 0)
                enemies.get(index).isDead = true;
        }
        damaged.add(enemies.get(index));
        return null;

    }

    @Override
    public String toString() {
        return super.toString() + " Arrows: " + arrowsCount + "     Range: " + range;
    }

    public String LevelUp() { //update hunter stats after lvl up
        super.LevelUp();
        arrowsCount += 10 * level;
        attack += 2 * level;
        defense += level;
        return name + " reached level " + level + ": +" + (level) * (10) + " Health, +" + 6 * level + " Attack, +" + 2 * level + " Defense";
    }

    public boolean accept(Visitor v) { //check hunter action based on tile in front of him
        if (ticksCount == 10) {
            arrowsCount += level;
            ticksCount = 0;
        } else
            ticksCount++;
        return super.accept(v);
    }

}

