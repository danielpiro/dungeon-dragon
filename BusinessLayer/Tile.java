package BusinessLayer;

public abstract class Tile implements Visited, Visitor {
    private char tile;
    protected Position pos;

    public Tile(char tile, int x, int y) { //default ctor
        this.setTile(tile);
        this.setPos(new Position(x, y));
    }

    public double Range(Tile t2) {
        return getPos().Range(t2.getPos());
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public char getTile() {
        return tile;
    }

    public void setTile(char tile) {
        this.tile = tile;
    }

    public String toString() {
        return "" + tile;
    }
}
