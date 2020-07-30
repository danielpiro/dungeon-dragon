package BusinessLayer;

public class Health {
    private int pool;
    private int amount;

    public Health(int pool) { //default ctor
        this.pool = pool;
        this.amount = pool;
    }

    public int getPool() {
        return pool;
    }

    public void setPool(int pool) {
        this.pool = pool;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void LevelUp(Player p) { //when player level up its update its health
        setPool(10 * p.getLevel() + this.pool);
        setAmount(pool);
    }

    public String toString() {
        return "Health: " + amount + "/" + pool;
    }
}

