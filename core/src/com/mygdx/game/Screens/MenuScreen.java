package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen, InputProcessor {

    private MyGdxGame game;

    private Stage stage;
    private Skin skin;

    private OrthographicCamera gamecam;

    private Table table;
    private TextButton startButton;
    private TextButton quitButton;
    private TextButton optionsButton;

    private Label titleLabel;

    private Texture volumeTexture;
    private TextureRegion volumeTexRegion;
    private TextureRegionDrawable volumeTexRegDrawable;
    private ImageButton volumeButton;

    private SpriteBatch batch;
    private Sprite sprite;

    public MenuScreen(final MyGdxGame game){
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        gamecam = new OrthographicCamera();
        stage = new Stage(new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,
                        MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam));

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        startButton = new TextButton("New Game", skin);
        quitButton = new TextButton("Quit Game", skin);
        optionsButton = new TextButton("Options", skin);

        titleLabel = new Label("GAME TITLE", skin);

        volumeTexture = new Texture(Gdx.files.internal("volume-up2.png"));
        volumeTexRegion = new TextureRegion(volumeTexture);
        volumeTexRegDrawable = new TextureRegionDrawable(volumeTexRegion);
        volumeButton = new ImageButton(volumeTexRegDrawable);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedNewGame", "yes");
                game.changeScreen(MyGdxGame.APPLICATION);
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedOptions", "yes");
                game.changeScreen(MyGdxGame.PREFERENCES);
            }
        });
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        volumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedVolume", "yes");
            }
        });

        /*  utile per modificare la dimensione dei bottoni(aggiungere '.size(width, unit' sul table.add)
        int x = Gdx.graphics.getHeight();
        int unit = x / 6;
        float scale = unit / startButton.getHeight();
        float width = startButton.getWidth() * scale;
       */
        table.padTop(20);
        table.add(titleLabel).padBottom(100);
        table.row();
        table.add(startButton).padBottom(30);
        table.row();
        table.add(optionsButton).padBottom(30);
        table.row();
        table.add(quitButton).padBottom(30);


        stage.addActor(table);

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("dragon-menu.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        InputMultiplexer im = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();

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
