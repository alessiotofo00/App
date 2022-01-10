package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;

import javax.swing.text.LabelView;
import java.io.*;

import static com.mygdx.game.MyGdxGame.levelFile;
import static com.mygdx.game.Scenes.Hud.addScore;
import static com.mygdx.game.Scenes.Hud.score;
import static com.mygdx.game.Screens.PlayScreen.level;

public class GameOverScreen implements Screen {

    private File recordFile;

    private final MyGdxGame game;

    private final Skin skin;

    private final Stage stage;
    private final FitViewport viewport;

    private final Table table;

    private Label gameOverLabel;
    private TextButton playAgainButton;
    private TextButton exitButton;
    protected Label recordScoreLabel;
    private Label endScoreLabel;
    private Label newRecLabel;

    public static int played=0;
    private Integer recordScore;
    boolean newRec=false;

    public GameOverScreen(final MyGdxGame game) {

        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        gameOverLabel = new Label("GAME OVER", skin);
        playAgainButton = new TextButton("Play Again", skin);
        endScoreLabel = new Label(String.format("Final score: %d", Hud.score), skin);

        exitButton = new TextButton("Exit to Menu", skin);

//prima volta
     /*   try {
            recordFile = new File(String.valueOf(Gdx.files.internal("Records.txt")));
            if (recordFile.exists()) {
                System.out.println("file record esistente");
                //non faccio nulla

            } else if (recordFile.createNewFile()) {
                System.out.println("file records creato");
                FileWriter fw = new FileWriter(recordFile);
                PrintWriter pw = new PrintWriter(fw);
                pw.printf("%d", score);
                fw.close();
                System.out.println("Scrittura record corretta");
            }
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio record");
            e.printStackTrace();

            throw (new RuntimeException());
        }


        //voglio sapere il vecchio record
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
        recordScore=0;
        //ho un nuovo record
        if ( score >= recordScore) {
            newRec = true;
            {
                try {
                    recordFile = new File(String.valueOf(Gdx.files.internal("Records.txt")));
                    if (recordFile.exists()) {
                        System.out.println("file record esistente");
                       // recordFile.delete();
                       // recordFile.createNewFile();
                        FileWriter fw = new FileWriter(recordFile);
                        PrintWriter pw = new PrintWriter(fw);
                        int s = Hud.score.intValue();
                        pw.printf("%d", s);
                        recordScore= score;
                        fw.close();
                    } else if (recordFile.createNewFile()) {
                        System.out.println("file record creato");
                        FileWriter fw = new FileWriter(recordFile);
                        PrintWriter pw = new PrintWriter(fw);
                        int s = Hud.score.intValue();
                        pw.printf("%d", s);
                        recordScore= Hud.score;
                        System.out.println("aggiornamento nuovo record corretto");
                        fw.close();
                    }
                } catch (IOException e) {
                    System.out.println("Scrittura nuovo record sbagliata");
                    e.printStackTrace();
                    throw (new RuntimeException());
                }
            }
        }

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.hardMode) {
                    try {
                        levelFile = new File(String.valueOf(Gdx.files.internal("levelHolder.txt")));
                        if (levelFile.exists()) {
                            System.out.println("file esistente");
                            levelFile.delete();
                            levelFile.createNewFile();
                            FileWriter fw = new FileWriter(levelFile);
                            fw.write("1");
                            fw.close();
                        } else if (levelFile.createNewFile()) {
                            System.out.println("file creato");
                            FileWriter fw = new FileWriter(levelFile);
                            fw.write("1");
                            fw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Errore nel playagain");
                        throw (new RuntimeException());
                    }
                    game.playScreen = new PlayScreen(game);
                    game.changeScreen(MyGdxGame.APPLICATION);
                } else {
                    setPlayed(getPlayed() + 1);
                    addScore(-10);
                    game.playScreen = new PlayScreen(game);
                    game.changeScreen(MyGdxGame.APPLICATION);
                }
            }
        });

            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.playScreen = new PlayScreen(game);
                    game.changeScreen(MyGdxGame.MENU);
                }
            });
            recordScoreLabel = new Label(String.format("RECORD: " + recordScore), skin);
            newRecLabel = new Label(String.format("NUOVO RECORD: " + score), skin);

            table.add(gameOverLabel).expandX();
            table.row();
            table.add(endScoreLabel).padTop(70);
            table.row();
            if (!newRec) {
                table.add(recordScoreLabel).padTop(50);
                table.row();
            }
            if (newRec) {
                table.add(newRecLabel).expandX().padTop(30);
                table.row();
                newRec = false;
            }

            table.add(playAgainButton).padTop(60);
            table.row();
            table.add(exitButton).padTop(60);

            stage.addActor(table);
        }

    public static int getPlayed() {
        return played;
    }

    public static void setPlayed(int played) {
        GameOverScreen.played = played;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
