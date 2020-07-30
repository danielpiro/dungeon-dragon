package BusinessLayer;

public class Wall extends Tile {
    public Wall(int x, int y) {
        super('#', x, y);
    }

    public boolean accept(Visitor v) {
        return v.visit(this);
    }

    public boolean visit(Player a) {
        return false;
    }

    public boolean visit(Wall a) {
        return false;
    }

    public boolean visit(Enemies a) {
        return false;
    }

    public boolean visit(Empty a) {
        return false;
    }
}
