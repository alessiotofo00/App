package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
//import com.mygdx.game.MyGdxGame;

public class MultipleScreens extends game {


    @Override
    public void create() {
        //primo schermo creato all'inizio dello start
        changeScreen((new MenuScreen(this)));
    }
    public void changeScreen(Screen newScreen){
        Screen oldScreen= getScreen();
        setScreen(newScreen);
        //imposta vecchio screen nelle risorse
        if(oldScreen!=null)
            oldScreen.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        //pulisci schermo
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //crea lo schermo corrente
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }


}