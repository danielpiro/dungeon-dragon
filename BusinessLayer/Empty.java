package BusinessLayer;

public class Empty extends Tile {
    public Empty(int x, int y) {
        super('.', x, y);
    }

    public boolean accept(Visitor v) {
        return v.visit(this);
    }

    public boolean visit(Player a) {
        return true;
    }

    public boolean visit(Wall a) {
        return true;
    }

    public boolean visit(Enemies a) {
        return true;
    }

    public boolean visit(Empty a) {
        return true;
    }
}
