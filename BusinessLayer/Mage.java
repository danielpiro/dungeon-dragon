package BusinessLayer;

import java.util.List;
import java.util.Random;

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private final int manaCost;
    private int spellPower;
    private final int hitsCount;
    private final int abilityRange;
    //default ctor
    public Mage(String name, int pool, int attack, int defense, int x, int y, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, pool, attack, defense, x, y);
        this.manaPool = manaPool;
        this.manaCost = manaCost;
        this.hitsCount = hitsCount;
        this.currentMana = manaPool / 4;
        this.spellPower = spellPower;
        this.abilityRange = abilityRange;
    }


    public String LevelUp() { //update mage stats after lvl up
        super.LevelUp();
        this.manaPool += 25 * level;
        this.currentMana = Math.min(this.currentMana + this.manaPool / 4, this.manaPool);
        this.spellPower += 10 * level;
        return name + " reached level " + level + ": +" + (level) * (10) + " Health, +" + 4 * level + " Attack, +" + level + " Defense\n" +
                " +" + 25 * level + " maximum mana, +" + 10 * level + " spell power";
    }

    public String castAbility(List<Units> enemies, List<Units> damaged) { //mage special ability
        if (this.currentMana < this.manaCost)
            return "You don't have enough mana to use this spell";
        else {
            this.currentMana -= this.manaCost;
            int hit = 0;
            int index = 0;
            Random r = new Random();
            while (hit < this.hitsCount && index < enemies.size()) {
                if ((r.nextInt(2) == 1) && (abilityRange > this.Range(enemies.get(index)))) {
                    hit++;
                    enemies.get(index).setDefending(r.nextInt(enemies.get(index).getDefense() + 1));
                    if (spellPower > enemies.get(index).getDefending()) {
                        enemies.get(index).setHealth(enemies.get(index).getHealth().getPool(), enemies.get(index).getHealth().getAmount() - (spellPower - enemies.get(index).getDefending()));
                        if (enemies.get(index).getHealth().getAmount() <= 0)
                            enemies.get(index).isDead = true;
                    }
                    damaged.add(enemies.get(index));
                }
                index++;
            }
            return null;
        }
    }

    public boolean accept(Visitor v) { //check mage action based on tile in front of him
        this.currentMana = Math.min(manaPool, currentMana + level);
        return super.accept(v);
    }

    public String toString() {
        return super.toString() + "Mana: " + currentMana + "/" + manaPool + "      Spell Power: " + spellPower;
    }

    public int getSpellPower() {
        return spellPower;
    }

}
