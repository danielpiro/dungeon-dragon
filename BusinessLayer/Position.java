package BusinessLayer;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public double Range(Position p2) { //return the base range
        return Math.sqrt(Math.pow(p2.getX() - getX(), 2) + Math.pow(p2.getY() - getY(), 2));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
