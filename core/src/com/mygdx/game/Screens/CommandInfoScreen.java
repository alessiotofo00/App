package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.mygdx.game.MyGdxGame.APPLICATION;
import static com.mygdx.game.MyGdxGame.V_WIDTH;

public class CommandInfoScreen implements Screen {

    private MyGdxGame game;

    private Skin skin;

    private Stage stage;
    private FitViewport viewport;

    private Texture background;

    private Table table;

    private Label titleLabel;

    private Label leftLabel;
    private Label rightLabel;
    private Label jumpLabel;
    private Label dashLabel;
    private Label difficultyLabel;

    private TextButton normalButton;
    private TextButton hardButton;

    public CommandInfoScreen(final MyGdxGame game)  {
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        viewport = new FitViewport(V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        background = new Texture(Gdx.files.internal("dragon-menu.jpg"));

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        titleLabel = new Label("HOW TO PLAY...", skin);
        leftLabel = new Label("LEFT  -  A", skin);
        rightLabel = new Label("RIGHT  -  D", skin);
        jumpLabel = new Label("JUMP  -  W", skin);
        dashLabel = new Label("DASH  -  MOUSE R", skin);
        difficultyLabel = new Label("SELECT DIFFICULTY", skin);

        normalButton = new TextButton("NORMAL", skin);
        hardButton = new TextButton("HARD", skin);

        normalButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
          /*
                try {
                    Desktop.getDesktop().open(new File(String.valueOf(Gdx.files.internal("TestVideo.mp4"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
                game.hardMode = false;
                game.playScreen = new PlayScreen(game);
                game.changeScreen(APPLICATION);
            }
        });
        hardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
          /*
                try {
                    Desktop.getDesktop().open(new File(String.valueOf(Gdx.files.internal("TestVideo.mp4"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
                game.hardMode = true;
                game.playScreen = new PlayScreen(game);
                game.changeScreen(APPLICATION);
            }
        });


        table.add(titleLabel).expandX().padTop(20);
        table.row();
        table.add(leftLabel).padTop(120);
        table.row();
        table.add(rightLabel).padTop(20);
        table.row();
        table.add(jumpLabel).padTop(20);
        table.row();
        table.add(dashLabel).padTop(20);
        table.row();
        table.add(difficultyLabel).padTop(50);
        table.row();
        table.add(normalButton).padTop(20);
        table.row();
        table.add(hardButton).padTop(20);

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
        game.batch.draw(background, 0, 0, V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM);
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
        game.batch.dispose();
        stage.dispose();
    }
}
