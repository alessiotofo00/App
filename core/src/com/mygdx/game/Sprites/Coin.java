package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sound;


public class Coin extends RectInteractiveTileObject {

        public Coin(PlayScreen screen, Rectangle bounds) {
            super(screen, bounds);
       //     tileSet = map.getTileSets().getTileSet("various");
            fixture.setUserData(this);
            setCategoryFilter(MyGdxGame.COIN_BIT);
        }

        @Override
        public void bodyHit() {
            Gdx.app.log("Hit", "Coin");
            Sound.playCoinSound();
            setCategoryFilter(MyGdxGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addCoins();
        }

    }


