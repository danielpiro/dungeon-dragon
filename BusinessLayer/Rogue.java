package BusinessLayer;

import java.util.List;
import java.util.Random;

public class Rogue extends Player {
    private int currentEnergy;
    private final int energyCost;
    //default ctor
    public Rogue(String name, int pool, int attack, int defense, int x, int y, int energyCost) {
        super(name, pool, attack, defense, x, y);
        this.currentEnergy = 100;
        this.energyCost = energyCost;
    }

    public String LevelUp() { //update rouge stats upon lvl up
        super.LevelUp();
        this.currentEnergy = 100;
        this.attack += 3 * level;
        return name + " reached level " + level + ": +" + (level) * (10) + " Health, +" + 7 * level + " Attack, +" + level + " Defense";
    }

    public String castAbility(List<Units> enemies, List<Units> damaged) { //rouge special ability
        if (energyCost > currentEnergy) {
            return "You don't have sufficient energy to use this move.";
        } else {
            currentEnergy = currentEnergy - energyCost;
            for (Units e : enemies) {
                if (Range(e) < 2) {
                    Random r = new Random();
                    e.setAttacking(r.nextInt(this.attack + 1));
                    e.setDefending(r.nextInt(e.getDefense() + 1));
                    if (e.getAttacking() > e.getDefending()) {
                        e.setHealth(e.getHealth().getPool(), e.getHealth().getAmount() - (e.getAttacking() - e.getDefending()));
                        if (e.getHealth().getAmount() <= 0) {
                            e.isDead = true;
                        }
                    }
                    damaged.add(e);
                }
            }
            return null;
        }
    }

    public String toString() {
        return super.toString() + "Energy: " + currentEnergy + "/" + energyCost;
    }

    public boolean accept(Visitor v) { //check rouge action based on tile in front of him
        currentEnergy = Math.min(currentEnergy + 10, 100);
        return super.accept(v);
    }
}
