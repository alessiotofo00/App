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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.HealthBar;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.B2ContactListener;
import com.mygdx.game.Tools.B2WorldCreator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.mygdx.game.Scenes.Hud.addScore;

public class  PlayScreen implements Screen {

    private final MyGdxGame game;
    private final TextureAtlas knightAtlas;
    private final TextureAtlas skeletonAtlas;
    private final TextureAtlas healthBarAtlas;

    private final OrthographicCamera gamecam;
    private final Viewport gamePort;

    private final Hud hud;
    private TiledMap map = null;
    private final OrthogonalTiledMapRenderer renderer;
    //player declaration
    private final Player player;
    //boolean per lo stato di gioco(vedi metodo render)
    static boolean paused;

    public boolean canJump;
    public boolean canDash = true;

    private final HealthBar healthBar;

    //variabili per la creazione del mondo di gioco
    public World world;
    private final Box2DDebugRenderer b2dr;
    private final B2WorldCreator creator;

    public static int level=1;
    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        PlayScreen.level = level;
    }

    public PlayScreen(final MyGdxGame game){

        knightAtlas = new TextureAtlas("RedKnight.pack");
        skeletonAtlas = new TextureAtlas("skeletonWalk.pack");
        healthBarAtlas = new TextureAtlas("HealthBar.pack");
        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM,
                MyGdxGame.V_HEIGHT / MyGdxGame.PPM,
                gamecam);
        hud = new Hud(game.batch, this);

        try {
            BufferedReader buf = new BufferedReader(new FileReader(String.valueOf(Gdx.files.internal("levelHolder.txt"))));
            level = Integer.parseInt(buf.readLine());
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //map declarations
        TmxMapLoader mapLoader = new TmxMapLoader();
        switch (level){
            case 1:
                map = mapLoader.load("Level1.tmx");
                break;
            case 2:
                map = mapLoader.load("Level2.tmx");
                setLevel(2);
                break;
            case 3:
                map = mapLoader.load("Level3.tmx");
                setLevel(3);
                break;
            case 4:
                map = mapLoader.load("Level4.tmx");
                setLevel(4);
                break;
            case 5:
                map = mapLoader.load("Level5.tmx");
                setLevel(5);
                break;
            case 6:
                map = mapLoader.load("Level6.tmx");
                setLevel(6);
                break;
        }
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
        //gamecam.position necessario per non centrare in posizione 0.0 (il centro della mappa, visto come assi cartesiani)
        //divido quindi per 2 h e l
        gamecam.position.set(gamePort.getWorldWidth() / 2 - (224 / MyGdxGame.PPM), gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -13), true); //il -10 indica la gravità nel mondo di gioco
        b2dr = new Box2DDebugRenderer();
        //b2dr.setDrawBodies(false);
        creator = new B2WorldCreator(this);
        player = new Player(this);
        healthBar = new HealthBar(world, this);
        //listener
        world.setContactListener(new B2ContactListener());
    }



    public TextureAtlas getKnightAtlas(){
        return knightAtlas;
    }

    public TextureAtlas getSkeletonAtlas() { return skeletonAtlas;}

    public TextureAtlas getHealthBarAtlas(){
        return healthBarAtlas;
    }

    public Player getPlayer() { return player;}

    public MyGdxGame getGame() { return game;}


    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    //method useful to catch the keyboard inputs
    public void handleInput(){
        //tasto provvisorio per provare lo shop
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            game.changeScreen(MyGdxGame.SHOP);
        }
        //tasto per la pausa
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = true;
            game.changeScreen(MyGdxGame.PAUSE);
        }
        //tasti per il movimento
        if(player.currentState != Player.State.GAMEOVER) {
            //if (player.b2body.getLinearVelocity().y == 0 || canJump) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                player.b2body.applyLinearImpulse(new Vector2(0, 4), player.b2body.getWorldCenter(), true);
                canJump = false;
            }
            //}
            if (canDash){
                if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) /*&& Gdx.input.isKeyPressed(Input.Keys.D)*/ && player.runningRight) {
                    player.b2body.applyLinearImpulse(new Vector2(1.5f, 1.1f), player.b2body.getWorldCenter(), true);
                }
                if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.A) && !player.runningRight){
                    player.b2body.applyLinearImpulse(new Vector2(-1.5f, 1.1f), player.b2body.getWorldCenter(), true);
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2) {
                if (player.currentState == Player.State.HIT) {
                    player.b2body.applyLinearImpulse(new Vector2(0.05f, 0), player.b2body.getWorldCenter(), true);
                } else player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2) {
                if (player.currentState == Player.State.HIT) {
                    player.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), player.b2body.getWorldCenter(), true);
                } else player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            }
        }
    }

    //we will call this method in our render method
    public void update(float dt) {
        handleInput();

        healthBar.update(dt);

        world.step(1/60f, 6, 2);
        //aggiorno il player
        player.update(dt);
        //update scheletri
        for(Enemy enemy : creator.getSkeletons())
            enemy.update(dt);
        //update bullets
        for(Enemy enemy : creator.getBullets()) {
            enemy.update(dt);
        }
        //gamecam che segue il player
        //gamecam.position.x = player.b2body.getPosition().x;
        //aggiorno la gamecam
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //controllo che il Player abbia ricevuto un numero >= di hit rispetto alla sua vita -> metto GAMEOVER come stato corrente
        if(Player.hits >= 4){
            player.currentState = Player.State.GAMEOVER;
        }
        if(paused){
            Gdx.app.log("paused", "yes");
        }
        else{
            update(delta);
        }

        renderer.render();
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if(paused){
            Sprite sprite = new Sprite(new Texture(Gdx.files.internal("pause2.png")));
            sprite.draw(game.batch);
        }
        player.draw(game.batch);
        healthBar.draw(game.batch);
        for(Enemy enemy : creator.getSkeletons())
            enemy.draw(game.batch);
        for(Enemy enemy : creator.getBullets())
            enemy.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.changeScreen(MyGdxGame.GAMEOVER);
        }
    }

    public boolean gameOver(){
        return player.currentState == Player.State.GAMEOVER && player.getStateTimer() > 1.5;
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
}
