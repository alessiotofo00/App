package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.mygdx.game.MyGdxGame.levelFile;
import static com.mygdx.game.Scenes.Hud.addScore;
import static com.mygdx.game.Scenes.Hud.score;
import static com.mygdx.game.Screens.GameOverScreen.getPlayed;
import static com.mygdx.game.Screens.GameOverScreen.setPlayed;

public class LevelDeathScreen implements Screen {

    private final MyGdxGame game;

    private final Skin skin;

    private final Stage stage;
    private final FitViewport viewport;

    private final Table table;

    private Label gameOverLabel;
    private TextButton playAgainButton;
    private TextButton exitButton;

    public LevelDeathScreen(final MyGdxGame game){
        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        gameOverLabel = new Label("GAME OVER", skin);
        playAgainButton = new TextButton("Play Again", skin);
        exitButton = new TextButton("Exit to Menu", skin);

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

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainButton).padTop(60);
        table.row();
        table.add(exitButton).padTop(60);

        stage.addActor(table);

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
