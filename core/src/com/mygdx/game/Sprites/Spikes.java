package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Spikes extends InteractiveTileObject {

    PlayScreen screen;

    public Spikes(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        super.fixture.setRestitution(0);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.SPIKES_BIT);
    }

    @Override
    public void bodyHit() {
        Player.setHits(4);
    }
}
