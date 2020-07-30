package UserInterface;

import BusinessLayer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameControl {
    Scanner in = new Scanner(System.in);
    private final Player[] characters = new Player[7];
    private Player player;
    private List<Level> levels;
    private Level currentLevel;
    private int x;
    private int y;

    public GameControl() { //default ctor
        characters[0] = new Warrior(3, "Jon Snow", 300, 30, 4, x, y);
        characters[1] = new Warrior(5, "The Hound", 400, 20, 6, x, y);
        characters[2] = new Mage("Melisandre", 100, 5, 1, x, y, 300, 30, 15, 5, 6);
        characters[3] = new Mage("Thoros of Myr", 250, 25, 4, x, y, 150, 20, 20, 3, 4);
        characters[4] = new Rogue("Arya Stark", 150, 40, 2, x, y, 20);
        characters[5] = new Rogue("Bronn", 250, 35, 3, x, y, 50);
        characters[6] = new Hunter("Ygritte", 220, 30, 2, x, y, 6);
        this.player = null;
        int i = 1;
        for (Player player : characters) {
            System.out.println(i + ". " + player.toString());
            i++;
        }
        System.out.println("Select a character");
    }

    public void setLevels(List<Level> levels) { //sets the levels and initiates current level to the first one
        this.levels = levels;
        setLevel(levels.get(0));
    }

    public void setLevel(Level level) {
        this.currentLevel = level;
    }

    public void GameTick() {
        boolean accepted;
        int levelIndex = 0;
        player.setPos(currentLevel.getPlayerPos());
        while (!player.isDead()) { //each iteration is one game tick
            player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
            System.out.println(player);
            for (String k : currentLevel.toString1()) { //print the level in each turn
                System.out.println(k);
            }
            char c = in.next().charAt(0);//input on player turn
            while(c !='w' && c != 'a'&& c != 'd'&& c != 'q'&& c != 's'&& c != 'e'){
                c = in.next().charAt(0);
            }
            switch (c) {
                case 'w':
                    accepted = player.accept(currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()]); //check if player can move up , if the tile above is an enemy than he attack it
                    if (accepted) {
                        Swap(currentLevel.getTile(), player.getPos().getX(), player.getPos().getY(), player.getPos().getX(), player.getPos().getY() - 1); //move player up
                        player.setPos(currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].getPos());
                    }
                    if ((currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()]) instanceof Enemies && !accepted) { //check if the tile above the player was an enemy
                        for (String s : Attack(player, ((Enemies) (currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()])))) { //output for player attacking enemy
                            System.out.println(s);
                        }
                        if (((Enemies) (currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()])).isDead()) { //checks if the enemy died
                            System.out.println(((Enemies) (currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()])).getName() + " died. " + player.getName() + " gained " + ((Enemies) (currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()])).getExperience() + " experience");
                            ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).setExperience(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() + ((Enemies) (currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()])).getExperience()); //adds exp on kill
                            while (((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() >= ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getLevel() * 50) {
                                System.out.println(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).LevelUp()); //levels up the player if needed
                            }
                            currentLevel.getEnemies().remove(currentLevel.getEnemies().indexOf((currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()]))); //removes dead enemy from the enemy list
                            currentLevel.getTile()[player.getPos().getY() - 1][player.getPos().getX()] = new Empty(player.getPos().getX(), player.getPos().getY() - 1); //switches the enemy to empty

                        }
                    }
                    break;
                case 's': //same case for down
                    accepted = player.accept(currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()]);
                    if (accepted) {
                        Swap(currentLevel.getTile(), player.getPos().getX(), player.getPos().getY(), player.getPos().getX(), player.getPos().getY() + 1);
                        player.setPos(currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].getPos());
                    }
                    if ((currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()]) instanceof Enemies && !accepted) {
                        for (String s : Attack(player, ((Enemies) (currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()])))) {
                            System.out.println(s);
                        }
                        if (((Enemies) (currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()])).isDead()) {
                            System.out.println(((Enemies) (currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()])).getName() + " died. " + player.getName() + " gained " + ((Enemies) (currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()])).getExperience() + " experience");
                            ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).setExperience(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() + ((Enemies) (currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()])).getExperience());
                            while (((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() >= ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getLevel() * 50) {
                                System.out.println(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).LevelUp());
                            }
                            currentLevel.getEnemies().remove(currentLevel.getEnemies().indexOf((currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()])));
                            currentLevel.getTile()[player.getPos().getY() + 1][player.getPos().getX()] = new Empty(player.getPos().getX(), player.getPos().getY() + 1);
                        }
                    }
                    break;
                case 'a': //same case for left
                    accepted = player.accept(currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1]);
                    if (accepted) {
                        Swap(currentLevel.getTile(), player.getPos().getX(), player.getPos().getY(), player.getPos().getX() - 1, player.getPos().getY());
                        player.setPos(currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].getPos());
                    }
                    if ((currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1]) instanceof Enemies && !accepted) {
                        for (String s : Attack(player, ((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1])))) {
                            System.out.println(s);
                        }
                        if (((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1])).isDead()) {
                            System.out.println(((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1])).getName() + " died. " + player.getName() + " gained " + ((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1])).getExperience() + " experience");
                            ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).setExperience(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() + ((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1])).getExperience());
                            while (((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() >= ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getLevel() * 50) {
                                System.out.println(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).LevelUp());
                            }
                            currentLevel.getEnemies().remove(currentLevel.getEnemies().indexOf((currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1])));
                            currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() - 1] = new Empty(player.getPos().getX() - 1, player.getPos().getY());
                        }
                    }
                    break;
                case 'd': //same case for right
                    accepted = player.accept(currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1]);
                    if (accepted) {
                        Swap(currentLevel.getTile(), player.getPos().getX(), player.getPos().getY(), player.getPos().getX() + 1, player.getPos().getY());
                        player.setPos(currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].getPos());
                    }
                    if ((currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1]) instanceof Enemies && !accepted) {
                        for (String s : Attack(player, ((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1])))) {
                            System.out.println(s);
                        }
                        if (((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1])).isDead()) {
                            System.out.println(((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1])).getName() + " died. " + player.getName() + " gained " + ((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1])).getExperience() + " experience");
                            ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).setExperience(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() + ((Enemies) (currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1])).getExperience());
                            while (((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() >= ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getLevel() * 50) {
                                System.out.println(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).LevelUp());
                            }
                            currentLevel.getEnemies().remove(currentLevel.getEnemies().indexOf((currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1])));
                            currentLevel.getTile()[player.getPos().getY()][player.getPos().getX() + 1] = new Empty(player.getPos().getX() + 1, player.getPos().getY());
                        }
                    }
                    break;
                case 'e': //special ability
                    List<Units> damaged = new ArrayList<>();
                    String error = player.castAbility(currentLevel.getEnemies(), damaged); //adds all damaged enemies to the empty arraylist
                    if (!(error == null)) {
                        System.out.println(error);
                    } else {
                        ArrayList<Enemies> died = new ArrayList<>();
                        specialAbility(damaged); //output for special abitily
                        for (Units e : currentLevel.getEnemies()) { //checks all enemies if they died
                            if (e.isDead()) {
                                int x = e.getPos().getX();
                                int y = e.getPos().getY();
                                died.add((Enemies) e); //adds all dead enemies to the died arraylist
                                currentLevel.getTile()[y][x] = new Empty(x, y); //sets new empty instead of dead enemy
                                System.out.println(e.getName() + " died. " + player.getName() + " gained " + ((Enemies) e).getExperience() + " experience");
                                ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).setExperience(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() + ((Enemies) e).getExperience());
                                while (((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getExperience() >= ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).getLevel() * 50) {
                                    System.out.println(((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]).LevelUp());
                                }
                            }
                        }
                        for (Enemies i : died) { //removes all dead enemies from the list of enemies
                            currentLevel.getEnemies().remove(i);
                        }
                    }
                    break;
                case 'q': //does nothing.
                    player.accept(player); //game ticks/cooldown advances
                    break;
                default:
                    continue;
            }

            for (Units enemy : currentLevel.getEnemies()) { //passes on every enemy to make their turn
                if (enemy instanceof Monster) {
                    int move = ((Monster) enemy).move(player); //returns the direction the enemy moves.
                    switch (move) {
                        case 0: //up
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY() - 1][enemy.getPos().getX()]); //checks if the enemy can move up, damages the player if the tile above is player
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX(), enemy.getPos().getY() - 1); //switches enemy with tile above if true (tile above is empty)
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY() - 1][enemy.getPos().getX()] instanceof Player && !accepted) { //checks if tile above was player.
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]); //updates player
                                for (String s : Attack(enemy, player)) { //output for enemy attacks player
                                    System.out.println(s);
                                }
                                if (player.isDead()) { //checks if the player died
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());//output for game over.
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 1: //same as case 0 but for moving down
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY() + 1][enemy.getPos().getX()]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX(), enemy.getPos().getY() + 1);
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY() + 1][enemy.getPos().getX()] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 2: //same as case 0 but for moving left
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() - 1]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX() - 1, enemy.getPos().getY());
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() - 1] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 3: //same as case 0 but for moving right
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() + 1]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX() + 1, enemy.getPos().getY());
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() + 1] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 4: //does nothing
                    }
                }
                if (enemy instanceof Trap) {
                    ((Trap) enemy).turn(player); //switches visibility status and damages player if in range
                    if (enemy.Range(player) < 2) {
                        player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]); //updates player
                        for (String s : Attack(enemy, player)) { //output for trap attacks player
                            System.out.println(s);
                        }
                        if (player.isDead()) { //output for game over
                            currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                            System.out.println(player.getName() + " was killed by " + enemy.getName());
                            System.out.println("You lost.");
                            player.getHealth().setAmount(0);
                            for (String k : currentLevel.toString1()) {
                                System.out.println(k);
                            }
                            System.out.println(player);
                            System.out.println("Game Over.");
                        }
                    }
                }
                if (enemy instanceof Boss) {
                    int move = ((Boss) enemy).move(player); //returns the direction the boss moves, if possible casts special ability
                    switch (move) {
                        case 0: //same as monster case 0
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY() - 1][enemy.getPos().getX()]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX(), enemy.getPos().getY() - 1);
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY() - 1][enemy.getPos().getX()] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 1: //same as monster case 1
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY() + 1][enemy.getPos().getX()]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX(), enemy.getPos().getY() + 1);
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY() + 1][enemy.getPos().getX()] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 2: //same as monster case 2
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() - 1]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX() - 1, enemy.getPos().getY());
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() - 1] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 3: //same as monster case 3
                            accepted = enemy.accept(currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() + 1]);
                            if (accepted) {
                                Swap(currentLevel.getTile(), enemy.getPos().getX(), enemy.getPos().getY(), enemy.getPos().getX() + 1, enemy.getPos().getY());
                            }
                            if (currentLevel.getTile()[enemy.getPos().getY()][enemy.getPos().getX() + 1] instanceof Player && !accepted) {
                                player = ((Player) currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()]);
                                for (String s : Attack(enemy, player)) {
                                    System.out.println(s);
                                }
                                if (player.isDead()) {
                                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                    System.out.println(player.getName() + " was killed by " + enemy.getName());
                                    System.out.println("You lost.");
                                    player.getHealth().setAmount(0);
                                    for (String k : currentLevel.toString1()) {
                                        System.out.println(k);
                                    }
                                    System.out.println(player);
                                    System.out.println("Game Over.");
                                }
                            }
                            break;
                        case 4: //same as monster case 4
                            break;
                        case 5: //special ability - output
                            int t = enemy.getAttack() - player.getDefending();
                            if (t < 0)
                                t = 0;
                            System.out.println(enemy.getName() + " use his special ability");
                            System.out.println(player.getName() + " rolled " + player.getDefending() + " defense points.");
                            System.out.println(enemy.getName() + " hit " + player.getName() + " for " + t + " ability damage.");
                            if (player.isDead()) { //output for game over.
                                currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()].setTile('X');
                                System.out.println(player.getName() + " was killed by " + enemy.getName());
                                System.out.println("You lost.");
                                player.getHealth().setAmount(0);
                                for (String k : currentLevel.toString1()) {
                                    System.out.println(k);
                                }
                                System.out.println(player);
                                System.out.println("Game Over.");
                            }
                    }
                }
            }
            if (currentLevel.getEnemies().isEmpty()) { //checks if all enemies are dead
                if (levelIndex < levels.size() - 1) { //checks if this is not the last level
                    currentLevel = levels.get(levelIndex + 1); //moves to the next level
                    levelIndex++;
                    player.setPos(currentLevel.getPlayerPos()); //sets player position as the initial position of player in the next level
                    currentLevel.getTile()[player.getPos().getY()][player.getPos().getX()] = player; //sets the player in the next level as this player (in order to keep player stats)
                } else { //finished the last level, output for finished game, exits while loop.
                    System.out.println("You won!");
                    break;
                }
            }
        }
    }

    public void Swap(Tile[][] tile, int x, int y, int x2, int y2) { //switches positions of tiles in the arrays and in position field
        Tile temp = tile[y][x];
        tile[y][x] = tile[y2][x2];
        tile[y2][x2] = temp;
        Position temp2 = tile[y][x].getPos();
        tile[y][x].setPos(tile[y2][x2].getPos());
        tile[y2][x2].setPos(temp2);
    }

    public Player getPlayer() {
        return player;
    }

    private void specialAbility(List<Units> damaged) { //output for special ability
        if (player instanceof Warrior) {
            System.out.println(player.getName() + " used Avenger's Shield, healing for " + player.getDefense() * 10 + ".");
            for (Units k : damaged) {
                int t = player.getHealth().getPool() / 10 - k.getDefending();
                if (t < 0)
                    t = 0;
                System.out.println(k.getName() + " rolled " + k.getDefending() + " defense points.");
                System.out.println(player.getName() + " hit " + k.getName() + " for " + t + " ability damage.");
            }
        }
        if (player instanceof Mage) {
            System.out.println(player.getName() + " cast Blizzard.");
            for (Units k : damaged) {
                int t = ((Mage) (player)).getSpellPower() - k.getDefending();
                if (t < 0)
                    t = 0;
                System.out.println(k.getName() + " rolled " + k.getDefending() + " defense points.");
                System.out.println(player.getName() + " hit " + k.getName() + " for " + t + " ability damage.");
            }
        }
        if (player instanceof Rogue) {
            System.out.println(player.getName() + " cast Fan of Knives.");
            for (Units k : damaged) {
                int t = player.getAttack() - k.getDefending();
                if (t < 0)
                    t = 0;
                System.out.println(k.getName() + " rolled " + k.getDefending() + " defense points.");
                System.out.println(player.getName() + " hit " + k.getName() + " for " + t + " ability damage.");
            }
        }
        if (player instanceof Hunter) {
            for (Units k : damaged) {
                System.out.println(player.getName() + " fired an arrow at " + k.getName());
                int t = player.getAttack() - k.getDefending();
                if (t < 0)
                    t = 0;
                System.out.println(k.getName() + " rolled " + k.getDefending() + " defense points.");
                System.out.println(player.getName() + " hit " + k.getName() + " for " + t + " ability damage.");
            }
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<String> Attack(Units attacker, Units defender) { //returns output strings for attacks
        int k = defender.getAttacking() - defender.getDefending();
        if (k < 0)
            k = 0;
        defender.getHealth().setAmount(defender.getHealth().getAmount() + k);
        ArrayList<String> l = new ArrayList<>();
        l.add(attacker.getName() + " engaged in combat with " + defender.getName() + ".");
        l.add(attacker.toString());
        l.add(defender.toString());
        defender.getHealth().setAmount(defender.getHealth().getAmount() - k);
        l.add(attacker.getName() + " rolled " + defender.getAttacking() + " attack points.");
        l.add(defender.getName() + " rolled " + defender.getDefending() + " defense points.");
        l.add(attacker.getName() + " dealt " + k + " damage to " + defender.getName() + ".");
        return l;
    }

    public void setPlayer(int num) { //chooses character.
        while (num <= 0 || num > 7) {
            System.out.println("Please select number between 1 to 7");
            num = in.nextInt();
        }
        this.player = characters[num - 1];
    }
}
