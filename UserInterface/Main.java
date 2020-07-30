package UserInterface;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        GameControl game = new GameControl();
        Scanner in = new Scanner(System.in);
        game.setPlayer(in.nextInt());
        try {
            List<String> LevelFiles = Files.list(Paths.get(args[0])).sorted().map(Path::toString).collect(Collectors.toList()); //loads all levels from directory as addresses
            List<Level> levels = new ArrayList<>();
            for (String levelPath : LevelFiles) {
                List<String> levelData = Files.readAllLines(Paths.get(levelPath)); //converts the files to a list of strings
                Level l = new Level();
                l.parseLevel(levelData, game.getPlayer()); //builds the level from the levelData
                levels.add(l); //adds all leveles to the list of levels

            }
            game.setLevels(levels);
            game.GameTick(); //starts the game
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

