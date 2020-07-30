package BusinessLayer;

public abstract class Units extends Tile {
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;
    protected boolean isDead;
    protected int attacking;
    protected int defending;

    public boolean isDead() {
        return isDead;
    }
    //default ctor
    public Units(String name, int pool, int attack, int defense, char tile, int x, int y) {
        super(tile, x, y);
        this.name = name;
        this.health = new Health(pool);
        this.attack = attack;
        this.defense = defense;
        this.isDead = false;
        this.defending = 0;
        this.attacking = 0;
    }


    public String toString() {
        return name + "      " + health.toString() + "      " + "Attack: " + attack + "      " + "Defense: " + defense + "       ";
    }

    public String getName() {
        return name;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(int pool, int amount) {
        health.setAmount(amount);
        health.setPool(pool);
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttacking() {
        return attacking;
    }

    public void setAttacking(int attacking) {
        this.attacking = attacking;
    }

    public int getDefending() {
        return defending;
    }

    public void setDefending(int defending) {
        this.defending = defending;
    }
}
