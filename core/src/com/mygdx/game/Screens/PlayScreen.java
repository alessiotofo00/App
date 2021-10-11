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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {

    private MyGdxGame game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Hud hud;

    //variabili per la creazione del mondo di gioco
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprite di prova per i cuori
    private Sprite hearts;

    public PlayScreen(MyGdxGame game){

        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,
                MyGdxGame.V_HEIGHT / MyGdxGame.PPM,
                gamecam);
        hud = new Hud(game.batch);

        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        //hearts = new Sprite(new Texture(Gdx.files.internal("hearts.png")));
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    //method useful to catch the keyboard inputs
    public void handleInput(float dt){

    }

    //we will call this method in our render method
    public void update(float dt) {
        handleInput(dt);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gamecam.combined);
        hud.stage.draw();
        game.batch.begin();
        //hearts.draw(game.batch);
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
