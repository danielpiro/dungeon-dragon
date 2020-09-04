package BusinessLayer;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    protected int abilityCoolDown;
    private int remainCoolDown;

    //default ctor
    public Warrior(int abilityCoolDown, String name, int pool, int attack, int defense, int x, int y) {
        super(name, pool, attack, defense, x, y);
        this.abilityCoolDown = abilityCoolDown;
        this.remainCoolDown = 0;
    }

    //update warrior stats upon lvl up
    public String LevelUp() {
        super.LevelUp();
        this.remainCoolDown = 0;
        this.health.setPool(health.getPool() + 5 * level);
        this.attack += 2 * level;
        this.defense += level;
        return name + " reached level " + level + ": +" + (level) * (15) + " Health, +" + 6 * level + " Attack, +" + level * 2 + " Defense";
    }

    public String castAbility(List<Units> enemies, List<Units> damaged) { //warrior special ability
        if (this.remainCoolDown > 0) {
            return "you have to wait more " + this.remainCoolDown + " turns to use special ability";
        } else {
            this.remainCoolDown = this.abilityCoolDown;
            this.health.setAmount(Math.min(this.health.getAmount() + (10 * this.defense), this.health.getPool()));
            List<Integer> enemiesWithinRange = new ArrayList<>();
            int i = 0;
            for (Units e : enemies) {
                if (Range(e) < 3)
                    enemiesWithinRange.add(i);
                i++;
            }
            Random r = new Random();
            if ((enemiesWithinRange.size() > 0)) {
                int selected = r.nextInt(enemiesWithinRange.size());
                enemies.get(enemiesWithinRange.get(selected)).setDefending((int) (Math.random() * enemies.get(enemiesWithinRange.get(selected)).getDefense()));
                enemies.get(enemiesWithinRange.get(selected)).getHealth().setAmount(enemies.get(enemiesWithinRange.get(selected)).getHealth().getAmount() - (this.health.getPool() / 10 - enemies.get(enemiesWithinRange.get(selected)).getDefending()));
                if (enemies.get(enemiesWithinRange.get(selected)).getHealth().getAmount() <= 0)
                    enemies.get(enemiesWithinRange.get(selected)).isDead = true;
                damaged.add(enemies.get(enemiesWithinRange.get(selected)));
            }
            return null;
        }
    }

    public String toString() {
        return super.toString() + "CoolDown: " + remainCoolDown + "/" + abilityCoolDown;
    }

    public boolean accept(Visitor v) {//check warrior action based on tile in front of him
        if (remainCoolDown > 0)
            remainCoolDown--;
        return super.accept(v);
    }
}