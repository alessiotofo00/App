package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LevelSwitcher extends RectInteractiveTileObject {

    PlayScreen screen;
    MyGdxGame game;

    public LevelSwitcher(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        this.screen = screen;
        game = screen.getGame();
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.LEVEL_SWITCHER_BIT);
    }

    @Override
    public void bodyHit() {
        screen.setLevel(screen.getLevel() + 1);
        try{
            System.out.println("file creato");
           // game.levelFile.delete();
           // game.levelFile.createNewFile();
            FileWriter fw = new FileWriter(game.levelFile);
            PrintWriter pw = new PrintWriter(fw);
            int level = screen.getLevel();
            pw.printf("%d", level);
            fw.close();
        }
        catch (IOException e){
            e.printStackTrace();

        }
        game.playScreen = new PlayScreen(game);
        game.changeScreen(MyGdxGame.APPLICATION);

    }
}
