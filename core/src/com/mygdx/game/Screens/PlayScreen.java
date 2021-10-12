package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {

    private MyGdxGame game;

    private Skin skin;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Hud hud;

    //boolean per lo stato di gioco(vedi metodo render)
    boolean paused;

    //variabili per la creazione del mondo di gioco
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprite di prova per i cuori
    private Sprite hearts;

    public PlayScreen(final MyGdxGame game){

        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        gamecam = new OrthographicCamera();
        gamePort = new ScreenViewport();
        hud = new Hud(game.batch);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);


    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    //method useful to catch the keyboard inputs
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = true;
            //game.changeScreen(MyGdxGame.MENU);
        }
    }

    //we will call this method in our render method
    public void update(float dt) {
        handleInput(dt);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(paused){
            if(Gdx.input.isKeyJustPressed(Input.Keys.P))
                paused = false;
        }
        else{
            update(delta);
        }

        game.batch.setProjectionMatrix(gamecam.combined);
        hud.stage.draw();
        game.batch.begin();
            //hearts.draw(game.batch);
            if(paused){
                Sprite sprite = new Sprite(new Texture(Gdx.files.internal("pause.png")));
                sprite.draw(game.batch);
            }
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    public World getWorld(){
        return world;
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
    }
}
