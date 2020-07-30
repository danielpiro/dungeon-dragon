package BusinessLayer;

import java.util.Random;

public abstract class Enemies extends Units {
    protected int experience;
    //default ctor
    public Enemies(String name, int pool, int attack, int defense, char tile, int x, int y, int experience) {
        super(name, pool, attack, defense, tile, x, y);
        this.experience = experience;
    }

    public boolean accept(Visitor v) {
        return v.visit(this);
    }

    public boolean visit(Empty a) {
        return true;
    }

    public boolean visit(Wall a) {
        return false;
    }

    public boolean visit(Player a) { //player attack enemy
        Random r = new Random();
        setAttacking((int) (Math.random() * a.attack));
        setDefending((int) (Math.random() * getDefense()));
        if (attacking > defending) {
            setHealth(getHealth().getPool(), getHealth().getAmount() - (attacking - defending));
        }
        if (getHealth().getAmount() <= 0) {
            isDead = true;
        }

        return false;
    }

    public boolean visit(Enemies a) {
        return false;
    }

    public String toString() {
        return super.toString() + "Experience Value: " + experience + "      ";
    }

    public int getExperience() {
        return experience;
    }

}
