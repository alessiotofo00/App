package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 1532; //cambia il conteggio dell'ID, id di tiled è 1531, uno parte da 0 e l'altro da 1, non so quale dei due bisogna controllare

    public Coin(PlayScreen screen,Rectangle bounds) {
        super(screen, bounds);
        tileSet=map.getTileSets().getTileSet("Various.tsx");
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.COIN_BIT);
    }

    @Override
    public void bodyHit() {
        Gdx.app.log("Hit", "Coin");
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        //dopo che colpisco il denaro imposto che non esiste più

        setCategoryFilter(MyGdxGame.DESTROYED_BIT);
        //cancello la grafica del denaro
        getCell().setTile(null);
        Hud.addScore(10);
    }

}
