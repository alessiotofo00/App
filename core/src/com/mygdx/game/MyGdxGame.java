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
	private EndScreen endScreen;
	//variabili per lo switch case dei vari screen
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

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
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}
}
