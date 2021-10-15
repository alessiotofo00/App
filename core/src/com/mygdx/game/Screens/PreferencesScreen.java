package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PreferencesScreen implements Screen {

    private MyGdxGame game;

    private Stage stage;
    private Skin skin;

    private FitViewport viewport;

    private Table table;
    private Label titleLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    private SpriteBatch batch;

    public PreferencesScreen(final MyGdxGame game){

        this.game = game;

        skin = new Skin(Gdx.files.internal("skin-commodore/uiskin.json"));
        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);

        table = new Table();
        table.top(); //set the table on the top of the stage
        table.setFillParent(true); //now the table's size is the same of the stage's size

        final CheckBox musicCheckBox = new CheckBox(null, skin);
        musicCheckBox.setChecked(true);
        musicCheckBox.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };
        });
        final CheckBox soundsCheckBox = new CheckBox(null, skin);
        soundsCheckBox.setChecked(true);
        soundsCheckBox.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            };
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(MyGdxGame.MENU);
            }
        });

        titleLabel = new Label( "Options", skin );
        musicOnOffLabel = new Label( "Music", skin );
        soundOnOffLabel = new Label( "Sounds", skin );

        table.add(titleLabel).padTop(100).padBottom(100);
        table.row();
        table.add(musicOnOffLabel).padBottom(30);
        table.add(musicCheckBox).padBottom(30);
        table.row();
        table.add(soundOnOffLabel).padBottom(50);
        table.add(soundsCheckBox).padBottom(50);
        table.row();
        table.add(backButton).padBottom(30);

        stage.addActor(table);

        batch = new SpriteBatch();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.end();

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
        stage.dispose();
    }
}
