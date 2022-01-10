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
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sound;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.GifDecoder;

import static com.mygdx.game.MyGdxGame.V_WIDTH;

public class ShopScreen implements Screen {

    private MyGdxGame game;

    private Skin skin;

    private Stage stage;
    private FitViewport viewport;

    private Table table;

    private Label shopLabel;
    private Label coinsLabel;

    private TextButton heartsButton;
   // private Label heartsLabel;
    private Label heartsCostLabel;

    private TextButton resumeButton;
    private TextButton exitButton;

    Animation<TextureRegion> animation;
    float elapsed;
    private TextButton buyTime;
    private Label timeForCoin;

    public ShopScreen(final MyGdxGame game){

        this.game = game;

        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        viewport = new FitViewport(V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("provashop.gif").read());

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        shopLabel = new Label("SHOP", skin);
        coinsLabel = new Label(String.format("COINS x%d", Hud.numCoins), skin);

        heartsButton = new TextButton("Buy +1 Heart", skin);
       // heartsLabel = new Label("+1 Heart", skin);
        heartsCostLabel = new Label("x1 COIN", skin);
        buyTime=new TextButton("Buy +15 seconds",skin);
        timeForCoin=new Label("x3 COIN",skin);
        resumeButton = new TextButton("Resume", skin);
        exitButton = new TextButton("Back to Menu", skin);

        heartsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sound.playButtonSound();
                //permetto il funzionamento del bottone solo se i coins sono > 0 e se la vita non è piena (hit subite dal player > 0)
                if(Hud.numCoins > 0 && Player.hits > 0) {
                    Hud.subCoins();
                    Player.hits--;
                }
            }
        });
        //scalo 3 dal denaro per il buytime e aggiungo 15
        buyTime.addListener(new ClickListener(){
            public void clicked (InputEvent event,float x,float y){
                Sound.playButtonSound();
                if(Hud.numCoins>=3){
                    Hud.numCoins-=2;
                        Hud.subCoins();
                    coinsLabel = new Label(String.format("COINS x%d", Hud.numCoins), skin);
                    Hud.addTime();
                }
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sound.playButtonSound();
                game.changeScreen(MyGdxGame.MENU);
            }

        });
        //provvisorio: cliccando GO TO THE BOSS! torno al playscreen
        //una volta implementato il BossScreen switcherà su di lui
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sound.playButtonSound();
                game.changeScreen(MyGdxGame.APPLICATION);
            }
        });

        table.add(shopLabel).expandX().padTop(20);
        //table.add(coinsLabel).expandX().padTop(20);
        table.row();
        table.add(heartsButton).expandX().padTop(120);
       // table.add(heartsLabel).expandX().padTop(120);
        table.add(heartsCostLabel).expandX().padTop(120);
        table.row();
        table.add(buyTime).expandX().padTop(100);
        table.add(timeForCoin).expandX().padTop(100);
        table.row();
        table.add(resumeButton).expandX().padTop(120);
        table.add(exitButton).expandX().padTop(120);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

   /* public void updateCoins(){
        Hud.numCoins--;
        coinsLabel.setText((String.format("COINS x%d", Hud.numCoins)));
    }

    */

    @Override
    public void render(float delta) {
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
