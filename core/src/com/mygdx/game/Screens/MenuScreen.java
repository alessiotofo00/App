package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.GifDecoder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.mygdx.game.MyGdxGame.*;

public class MenuScreen implements Screen, InputProcessor {
 //aggiunto final per evitare errori e warning
    //final: è un modifier che si mette su una classe per evitare che sia ereditata/modificata. Significato praticamente letterale,
    //si usa per classi "finali",che non si possono estendere(extends) o modificare.
    private final MyGdxGame game;
    //
//private MultipleScreens Game;
//
//palcoscenico su cui vanno in atto gli attori aggiunti(es. table)
    private final Stage stageMS;
    private final Skin skin;

    private final FitViewport viewport;

    private final Table table;
    private final TextButton startButton;
    private final TextButton continueButton;
    private final TextButton quitButton;
    private final TextButton optionsButton;

    private final Label titleLabel;

    Animation<TextureRegion> animation;
    float elapsed;

    public MenuScreen(final MyGdxGame game){
        this.game = game;

        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        viewport = new FitViewport(V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stageMS = new Stage(viewport);

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("darksouls_menu.gif").read());

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        startButton = new TextButton("New Game", skin);
        continueButton = new TextButton("Continue", skin);
        quitButton = new TextButton("Quit Game", skin);
        optionsButton = new TextButton("Options", skin);

        titleLabel = new Label("GAME TITLE", skin);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedNewGame", "yes");
                MyGdxGame.previousScreen = MyGdxGame.MENU;
                PlayScreen.paused = false;
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

                game.changeScreen(MyGdxGame.INFO);
            }
        });
        continueButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedContinue", "yes");
                PlayScreen.paused = false;
                MyGdxGame.previousScreen = MENU;
                game.changeScreen(MyGdxGame.APPLICATION);
            }
        });
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ClickedOptions", "yes");
                game.changeScreen(MyGdxGame.PREFERENCES);
                MyGdxGame.previousScreen = MENU;
            }
        });
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        /*  utile per modificare la dimensione dei bottoni(aggiungere '.size(width, unit' sul table.add)
        int x = Gdx.graphics.getHeight();
        int unit = x / 6;
        float scale = unit / startButton.getHeight();
        float width = startButton.getWidth() * scale;
       */
        table.padTop(20);
        table.add(titleLabel).padBottom(150);
        table.row();
        table.add(startButton).padBottom(40);
        table.row();
        table.add(continueButton).padBottom(40);
        table.row();
        table.add(optionsButton).padBottom(40);
        table.row();
        table.add(quitButton).padBottom(40);


        stageMS.addActor(table);
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

    //riceve eventi input da tastiera e mouse
    @Override
    public void show() {
       Gdx.input.setInputProcessor(stageMS);
    }

    @Override
    public void render(float delta) {

        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(animation.getKeyFrame(elapsed), 20.0f, 20.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stageMS.act(Gdx.graphics.getDeltaTime());
        stageMS.draw();
    }

    @Override
    public void resize(int width, int height) {
        //nel caso si riscontrasse il problema del cambio di dimensioni menu-gioco-menu
        //questo dovrebbe ricreare da zero le dimensioni. Il booleano serve per centrare il menu nello schermo
        // stageMS.getViewport().update(width, height,true);
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
        stageMS.dispose();
    }
}
