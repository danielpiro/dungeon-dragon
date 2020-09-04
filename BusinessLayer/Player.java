package BusinessLayer;


import java.util.List;
import java.util.Random;

public abstract class Player extends Units implements HeroicUnit {
    protected int level;
    protected int experience;


    //default ctor
    public Player(String name, int pool, int attack, int defense, int x, int y) {
        super(name, pool, attack, defense, '@', x, y);
        this.level = 1;
        this.experience = 0;
    }

    public String LevelUp() { //update player base stats upon lvl up
        this.experience = experience - (50 * level);
        this.level = level + 1;
        this.health.LevelUp(this);
        this.attack = attack + 4 * level;
        this.defense = defense + level;
        return name + " reached level " + level + ": +" + (level) * (10) + " Health, +" + 4 * level + " Attack, +" + level + " Defense";
    }

    public boolean visit(Player a) {
        return false;
    }

    public boolean visit(Empty a) {
        return true;
    }

    public boolean visit(Wall a) {
        return false;
    }

    public boolean visit(Enemies a) { //roll random number of bast attack and defense when player meet enemy
        Random r = new Random();
        setAttacking(r.nextInt(a.attack + 1));
        setDefending(r.nextInt(getDefense() + 1));
        if (getAttacking() > getDefending()) {
            setHealth(getHealth().getPool(), getHealth().getAmount() - (getAttacking() - getDefending()));

        }
        if (getHealth().getAmount() <= 0) {
            isDead = true;
        }

        return false;
    }

    public String toString() {
        return super.toString() + "Level: " + level + "      Experience: " + experience + "/" + (level * 50) + "      ";
    }

    public boolean accept(Visitor v) {
        return v.visit(this);
    }

    public String castAbility(List<Units> enemies, List<Units> damaged) {
        return null;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
