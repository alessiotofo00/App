package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.V_WIDTH;

public class PauseScreen implements Screen {

    private final MyGdxGame game;

    private final Stage stageMS;
    private final Skin skin;

    private final FitViewport viewport;

    private final Table table;
    private final TextButton resumeButton;
    private final TextButton exitButton;
    private final TextButton optionsButton;

    private final Label pauseLabel;

    public PauseScreen(final MyGdxGame game){

        this.game = game;

        //w->variabile creata per evitare errore in fase di commit
        int w=V_WIDTH/2;
        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        viewport = new FitViewport(w, MyGdxGame.V_HEIGHT-256, new OrthographicCamera());
        stageMS = new Stage(viewport);

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        resumeButton = new TextButton("Resume", skin);
        exitButton = new TextButton("Exit", skin);
        optionsButton = new TextButton("Options", skin);

        pauseLabel = new Label("PAUSE", skin);

        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedResume", "yes");
                game.changeScreen(MyGdxGame.APPLICATION);
                PlayScreen.paused = false;
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedOptions", "yes");
                game.changeScreen(MyGdxGame.PREFERENCES);
                MyGdxGame.previousScreen = MyGdxGame.PAUSE;
            }
        });
        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedExit", "yes");
                game.changeScreen(MyGdxGame.MENU);
            }
        });

        table.padTop(40);
        table.add(pauseLabel).padBottom(200);
        table.row();
        table.add(resumeButton).padBottom(60);
        table.row();
        table.add(optionsButton).padBottom(60);
        table.row();
        table.add(exitButton).padBottom(60);


        stageMS.addActor(table);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(stageMS);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.end();

        stageMS.act(Gdx.graphics.getDeltaTime());
        stageMS.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        stageMS.dispose();
    }
}
