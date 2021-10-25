package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
//import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;
//import com.mygdx.game.MyGdxGame;

public class Hud extends Sprite implements Disposable{

    public Stage stage;
    private Viewport viewport;
    private Skin skin;
    private static Integer score;
    Label levelLabel;
    static Label  scoreLabel;
    int level = 1;

    public Hud(SpriteBatch sb, PlayScreen screen){

        viewport = new ScreenViewport();
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label(String.format("LEVEL %d", level), skin);
        scoreLabel = new Label(String.format("COINS %d", score), skin);
        table.add(levelLabel).expandX();
        table.row();

        stage.addActor(table);
    }
    public static void addScore(int value){
        score+=value;
        scoreLabel.setText(String.format("%d",score));
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
