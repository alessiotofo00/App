package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Window;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.B2WorldCreator;

public class  PlayScreen implements Screen {

    private final MyGdxGame game;
    private TextureAtlas atlas;

    private final Skin skin;

    private final OrthographicCamera gamecam;
    private final Viewport gamePort;

    private final Hud hud;
    //map declarations
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    //player declaration
    private Player player;
    //boolean per lo stato di gioco(vedi metodo render)
    static boolean paused;

    //variabili per la creazione del mondo di gioco
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final B2WorldCreator creator;

    //sprite di prova per i cuori
    private Sprite hearts;
//anche qua PlayScreen (MultipleScreen game){}
    public PlayScreen(final MyGdxGame game){

        atlas = new TextureAtlas("RedKnight.pack");
        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,
                MyGdxGame.V_HEIGHT / MyGdxGame.PPM,
                gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mappa16x16.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
        //gamecam.position necessario per non centrare in posizione 0.0 (il centro della mappa, visto come assi cartesiani)
        //divido quindi per 2 h e l
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true); //il -10 indica la gravità nel mondo di gioco
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        player = new Player(world, this);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {
        //batch=new SpriteBatch();
        //sfondo
        // TODO Auto-generated method stub
    }

    //method useful to catch the keyboard inputs
    public void handleInput(float dt){
        //tasto per la pausa
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = true;
            game.changeScreen(MyGdxGame.PAUSE);
        }
        //tasti per il movimento
        if(Gdx.input.isKeyJustPressed(Input.Keys.W))
            player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);

        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    //we will call this method in our render method
    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);
        //aggiorno il player
        player.update(dt);
        //gamecam che segue il player
        gamecam.position.x = player.b2body.getPosition().x;
        //aggiorno la gamecam
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        //da cancellare, messo in multi
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(paused){
            Gdx.app.log("paused", "yes");
        }
        else{
            update(delta);
        }

        renderer.render();
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        //hud.stage.draw();
        game.batch.begin();
            //hearts.draw(game.batch);
            if(paused){
                Sprite sprite = new Sprite(new Texture(Gdx.files.internal("pause2.png")));
                sprite.draw(game.batch);
            }
            player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        gamePort.update(width, height);
    }

    public TiledMap getMap(){
        return map;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
    // public void crete(){
    //MenuScreen=new MenuScreen(this);
    //backtomenu();
    //public void backToMenu()
    //{
        //setScreen(menuScreen);
    //}
}
