package UserInterface;

import BusinessLayer.*;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private Tile[][] tile;
    private List<Units> enemies;
    private Position playerPos;

    //default ctor
    public Level() {
        this.enemies = new ArrayList<>();
    }

    public Tile[][] getTile() {
        return this.tile;
    }

    public void parseLevel(List<String> l, Player newPlayer) { //build the levels according to the List<String>
        tile = new Tile[l.size()][l.get(0).length()];
        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.get(i).length(); j++) {
                switch (l.get(i).charAt(j)) {
                    case '.':
                        tile[i][j] = new Empty(j, i);
                        break;
                    case '#':
                        tile[i][j] = new Wall(j, i);
                        break;
                    case '@':
                        tile[i][j] = newPlayer;
                        tile[i][j].setPos(new Position(j, i));
                        this.playerPos = new Position(j, i);
                        break;
                    case 's':
                        tile[i][j] = new Monster("Lannister Soldier", 80, 8, 3, l.get(i).charAt(j), j, i, 25, 3);
                        break;
                    case 'k':
                        tile[i][j] = new Monster("Lannister Knight", 200, 14, 8, l.get(i).charAt(j), j, i, 50, 4);
                        break;
                    case 'q':
                        tile[i][j] = new Monster("Queen’s Guard", 400, 20, 15, l.get(i).charAt(j), j, i, 100, 5);
                        break;
                    case 'z':
                        tile[i][j] = new Monster("Wright", 600, 30, 15, l.get(i).charAt(j), j, i, 100, 3);
                        break;
                    case 'b':
                        tile[i][j] = new Monster("Bear-Wright", 1000, 75, 30, l.get(i).charAt(j), j, i, 250, 4);
                        break;
                    case 'g':
                        tile[i][j] = new Monster("Giant-Wright", 1500, 100, 40, l.get(i).charAt(j), j, i, 500, 5);
                        break;
                    case 'w':
                        tile[i][j] = new Monster("White Walker", 2000, 150, 50, l.get(i).charAt(j), j, i, 1000, 6);
                        break;
                    case 'M':
                        tile[i][j] = new Boss("The Mountain", 1000, 60, 25, l.get(i).charAt(j), j, i, 500, 6, 4);
                        break;
                    case 'C':
                        tile[i][j] = new Boss("Queen Cersei", 100, 10, 10, l.get(i).charAt(j), j, i, 1000, 1, 5);
                        break;
                    case 'K':
                        tile[i][j] = new Boss("Night's King", 5000, 300, 150, l.get(i).charAt(j), j, i, 5000, 8, 3);
                        break;
                    case 'B':
                        tile[i][j] = new Trap("Bonus Trap", 1, 1, 1, l.get(i).charAt(j), j, i, 250, 1, 5);
                        break;
                    case 'Q':
                        tile[i][j] = new Trap("Queen's Trap", 250, 50, 10, l.get(i).charAt(j), j, i, 100, 3, 7);
                        break;
                    case 'D':
                        tile[i][j] = new Trap("Death Trap", 500, 100, 20, l.get(i).charAt(j), j, i, 250, 1, 10);
                        break;

                }
                if (tile[i][j] instanceof Enemies) {
                    enemies.add((Enemies) tile[i][j]);
                }
            }
        }
    }


    public void setTile(Tile[][] tile) {
        this.tile = tile;
    }

    public List<Units> getEnemies() {
        return enemies;
    }

    /*public void setEnemies(List<Enemies> enemies) {
        this.enemies = enemies;
    }
*/
    public ArrayList<String> toString1() { //toString method that return ArrayList<String> for the 2D Array of tiles
        ArrayList<String> pri = new ArrayList<>();
        for (int i = 0; i < tile.length; i++) {
            pri.add(i, "");
            for (int j = 0; j < tile[i].length; j++) {
                pri.set(i, pri.get(i) + tile[i][j].getTile());
            }
        }
        return pri;
    }

    public Position getPlayerPos() {
        return playerPos;
    }
}
