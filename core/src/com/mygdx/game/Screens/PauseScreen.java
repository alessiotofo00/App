package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sound;
import com.mygdx.game.Tools.GifDecoder;

import static com.mygdx.game.MyGdxGame.V_WIDTH;
import static com.mygdx.game.MyGdxGame.musicMenu;

public class PauseScreen implements Screen {

    private final MyGdxGame game;

    private final Skin skin;

    private final Stage stage;
    private final FitViewport viewport;

    private final Table table;
    private final TextButton resumeButton;
    private final TextButton exitButton;
    private final TextButton optionsButton;

    private final Label pauseLabel;

    Animation<TextureRegion> animation;
    float elapsed;

    public PauseScreen(final MyGdxGame game){

        this.game = game;

        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        viewport = new FitViewport(V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("darksouls_cave.gif").read());

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
                Sound.playButtonSound();
                Gdx.app.log("ClickedResume", "yes");
                musicMenu.setLooping(true);
                musicMenu.play();
                game.changeScreen(MyGdxGame.APPLICATION);
                PlayScreen.paused = false;
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sound.playButtonSound();
                Gdx.app.log("ClickedOptions", "yes");
                game.changeScreen(MyGdxGame.PREFERENCES);
                MyGdxGame.previousScreen = MyGdxGame.PAUSE;
            }
        });
        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                Sound.playButtonSound();
                Gdx.app.log("ClickedExit", "yes");
                game.playScreen = new PlayScreen(game);
                game.changeScreen(MyGdxGame.MENU);
            }
        });

        table.padTop(20);
        table.add(pauseLabel).padBottom(150);
        table.row();
        table.add(resumeButton).padBottom(40);
        table.row();
        table.add(optionsButton).padBottom(40);
        table.row();
        table.add(exitButton).padBottom(40);


        stage.addActor(table);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        elapsed += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(animation.getKeyFrame(elapsed), 20.0f, 20.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        game.batch.dispose();
        stage.dispose();
    }
}
