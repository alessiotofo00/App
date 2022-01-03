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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.mygdx.game.MyGdxGame.levelFile;
import static com.mygdx.game.Scenes.Hud.addScore;
import static com.mygdx.game.Screens.PlayScreen.level;

public class GameOverScreen implements Screen {
    public static int played=0;
    protected Label saveRercordLabel;
    private Label endScoreLabel;
    public TextField nameToSave;
    private TextButton saveGame;
    public static int getPlayed() {
        return played;
    }

    public static void setPlayed(int played) {
        GameOverScreen.played = played;
    }

    private final MyGdxGame game;

    private final Skin skin;

    private final Stage stage;
    private final FitViewport viewport;

    private final Table table;

    private Label gameOverLabel;
    private TextButton playAgainButton;
    private TextButton exitButton;

    public GameOverScreen(final MyGdxGame game){

        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        gameOverLabel = new Label("GAME OVER", skin);
        playAgainButton = new TextButton("Play Again", skin);
        endScoreLabel=new Label(String.format("Final score: %d", Hud.score), skin);
        saveRercordLabel=new Label("Your name: ",skin);
        nameToSave=new TextField("name ",skin);
        saveGame=new TextButton("Save your record!",skin);
        exitButton = new TextButton("Exit to Menu", skin);

        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.hardMode){
                    try {
                        levelFile = new File(String.valueOf(Gdx.files.internal("levelHolder.txt")));
                        if (levelFile.exists()){
                            System.out.println("file esistente");
                            levelFile.delete();
                            levelFile.createNewFile();
                            FileWriter fw = new FileWriter(levelFile);
                            fw.write("1");
                            fw.close();
                        }
                        else if(levelFile.createNewFile()) {
                            System.out.println("file creato");
                            FileWriter fw = new FileWriter(levelFile);
                            fw.write("1");
                            fw.close();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                        throw (new RuntimeException());
                    }
                    game.playScreen = new PlayScreen(game);
                    game.changeScreen(MyGdxGame.APPLICATION);
                }
                else {
                    setPlayed(getPlayed() + 1);
                    addScore(-10);
                    game.playScreen = new PlayScreen(game);
                    game.changeScreen(MyGdxGame.APPLICATION);
                }
            }
        });
//saveGame.addListener(new ClickListener()) { }
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playScreen = new PlayScreen(game);
                game.changeScreen(MyGdxGame.MENU);
            }
        });

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(endScoreLabel).padTop(70);
        table.row();
        table.add(saveRercordLabel).padTop(50);
        table.row();
        table.add(nameToSave);
        table.row();
        table.add(saveGame);
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
