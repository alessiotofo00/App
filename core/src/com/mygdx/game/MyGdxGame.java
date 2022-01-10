package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyGdxGame extends Game {

	public static final int V_WIDTH = 960;
	public static final int V_HEIGHT = 540;
	//pixel per meter
	public static final float PPM = 100;

	//valori BIT per capire che tipo di collisione ho avuto
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short DOUBLE_JUMP_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short OBJECT_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ATK_PLAYER_BIT = 64;
	public static final short SPIKES_BIT = 128;
	public static final short LEVEL_SWITCHER_BIT = 256;
	public static final short BULLET_BIT = 512;
	public static final short COIN_BIT = 1024;

	public SpriteBatch batch;

	public MenuScreen menuScreen;
	public PreferencesScreen preferencesScreen;
	public PlayScreen playScreen;
	public PauseScreen pauseScreen;
	public GameOverScreen goScreen;
	public ShopScreen shopScreen;
	public CommandInfoScreen infoScreen;
	public LevelDeathScreen levelDeathScreen;


	//variabili per lo switch case dei vari screen
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int PAUSE = 3;
	public final static int GAMEOVER = 4;
	public final static int SHOP = 5;
	public final static int INFO = 6;
	public final static int LEVELDEATH = 7;
	//salvo lo schermo precedente a quello attuale e lo schermo attuale
	//(serve al PreferencesScreen per distinguere se è stato chiamato dal MenuScreen o dal PauseScreen)
	public static int previousScreen;
	public static int currentScreen;
	//manager per i suoni e la musica
	public static AssetManager manager;
	public static Music musicMenu;
	public static Music soundJump;
	public static Music soundDoubleJump;
	public static File levelFile;
	public static File continueLevelFile;

	public boolean hardMode = false;
	//descrittore del file per la chiamata del load e dell'assegnamento
	private final AssetDescriptor<Music> menuMusicDescriptor = new AssetDescriptor<>("audio/menu-music.mp3", Music.class);
	private final AssetDescriptor<Music> soundJumpDescriptor = new AssetDescriptor<>("audio/jump.wav", Music.class);
	private final AssetDescriptor<Music>soundDoubleJumpDescriptor=new AssetDescriptor<>("audio/doublejump.mp3", Music.class);
	@Override
	public void create () {
		batch = new SpriteBatch();
//carico dall'asset
		manager = new AssetManager();
		manager.load(menuMusicDescriptor);
		manager.load(soundJumpDescriptor);
		manager.load(soundDoubleJumpDescriptor);
		//manager.load("audio/doublejump.wav", Sound.class);

		manager.finishLoading(); //finisco il caricamento

		//assegno il file ad una variabile che può essere chiamata all'interno del programma
		musicMenu=manager.get(menuMusicDescriptor);
		soundJump=manager.get(soundJumpDescriptor);
		soundDoubleJump=manager.get(soundDoubleJumpDescriptor);

		try {
			levelFile = new File(String.valueOf(Gdx.files.internal("levelHolder.txt")));
			if (levelFile.exists()){
				System.out.println("file esistente");
				//	levelFile.delete();
				//	levelFile.createNewFile();
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
		try {
			continueLevelFile = new File(String.valueOf(Gdx.files.internal("ContinueLevelHolder.txt")));
			if (levelFile.exists()){
				System.out.println("file esistente");
			}
			else if(continueLevelFile.createNewFile()) {
				System.out.println("file creato");
				FileWriter fw = new FileWriter(continueLevelFile);
				fw.write("1");
				fw.close();
			}
		}
		catch (IOException e){
			e.printStackTrace();
			throw (new RuntimeException());
		}
		menuScreen = new MenuScreen(this);

		setScreen(menuScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
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
				menuScreen = new MenuScreen(this);
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
			case GAMEOVER:
				currentScreen = GAMEOVER;
				goScreen = new GameOverScreen(this);
				this.setScreen(goScreen);
				break;
			case SHOP:
				currentScreen = SHOP;
				if(shopScreen == null) shopScreen = new ShopScreen(this);
				this.setScreen(shopScreen);
				break;
			case INFO:
				currentScreen = INFO;
				if(infoScreen == null) infoScreen = new CommandInfoScreen(this);
				this.setScreen(infoScreen);
				break;
			case LEVELDEATH:
				currentScreen = LEVELDEATH;
				if(levelDeathScreen == null) levelDeathScreen = new LevelDeathScreen(this);
				this.setScreen(infoScreen);
				break;
		}
	}
}
