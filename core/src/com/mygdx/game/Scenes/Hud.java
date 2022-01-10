package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.mygdx.game.Screens.GameOverScreen.played;
import static com.mygdx.game.Screens.PlayScreen.level;


public class Hud extends Sprite implements Disposable{

    public Stage stage;
    private Viewport viewport;
    private Skin skin;
    public static Integer score = 0;
    public static Integer recordScore=0;
    Label levelLabel;
    static Label  scoreLabel;
    static Label coinsLabel;
    static Label playedLabel;

    static Label countdownLabel;
    private static Integer worldTimer;
    private float timeCount;
    public static int numCoins = 0;
int levelPrecedente=1;
    public Hud(SpriteBatch sb, PlayScreen screen){
        worldTimer = 25;
        timeCount = 0;

        viewport = new ScreenViewport();
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label(String.format("LEVEL %d", level), skin);
        scoreLabel = new Label(String.format("SCORE %d", score), skin);
        coinsLabel = new Label(String.format("COINS x%d", numCoins), skin);
        playedLabel = new Label(String.format("DEATHS x%d", played), skin);

        countdownLabel = new Label(String.format("TIME: %02d", worldTimer),skin);

        table.add(levelLabel).expandX();
        table.add(scoreLabel).expandX();
        table.add(coinsLabel).expandX();

        table.add(countdownLabel).expandX();
        table.add(playedLabel).expandX();
        table.row();

        stage.addActor(table);
    }
    /*
if (recordFile.exists()) {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(String.valueOf(Gdx.files.internal("Records.txt"))));
            recordScore = Integer.parseInt(buf.readLine());
            buf.close();
            System.out.println("Lettura record corretta");
        } catch (IOException e) {
            System.out.println("Errore nel reperire il record");
            e.printStackTrace();
        }
    }
    
     */
    public static void addScore(int value){
        score+=value;
        scoreLabel.setText(String.format("SCORE %d",score));
    }

    public static void addCoins(){
        numCoins++;
        coinsLabel.setText(String.format("COINS x%d", numCoins));
    }
    public static void subCoins(){
        numCoins--;
        coinsLabel.setText(String.format("COINS x%d",numCoins));
    }
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText((String.format("TIME: %02d", worldTimer)));
            timeCount = 0;
            if(worldTimer<=0){
                Player.setHits(4);
            }
        }
        if(levelPrecedente<PlayScreen.getLevel()){
           addTime();
            levelPrecedente=PlayScreen.getLevel();
        }
    }
    public static void addTime(){
        worldTimer+=15;
        countdownLabel.setText((String.format("TIME: +15!")));
    }
    @Override
    public void dispose() {
        stage.dispose();
    }

}
