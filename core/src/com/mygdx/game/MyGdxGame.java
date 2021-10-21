package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.*;

public class MyGdxGame extends Game {

	public static final int V_WIDTH = 2048;
	public static final int V_HEIGHT = 1024;
	//pixel per meter
	public static final float PPM = 100;

	public SpriteBatch batch;

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private PauseScreen pauseScreen;
	//variabili per lo switch case dei vari screen
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int PAUSE = 3;
	//salvo lo schermo precedente a quello attuale e lo schermo attuale
	//(serve al PreferencesScreen per distinguere se è stato chiamato dal MenuScreen o dal PauseScreen)
	public static int previousScreen;
	public static int currentScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}

	//metodo per switchare gli schermi
	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				currentScreen = MENU;
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				currentScreen = PREFERENCES;
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				currentScreen = APPLICATION;
				if(playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case PAUSE:
				currentScreen = PAUSE;
				if(pauseScreen == null) pauseScreen = new PauseScreen(this);
				this.setScreen(pauseScreen);
				break;
		}
	}
}
