package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sound;

public class Spikes extends RectInteractiveTileObject {

    PlayScreen screen;

    public Spikes(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.SPIKES_BIT);
    }

    @Override
    public void bodyHit() {
        Player.setHits(4);
        Sound.playDeathSound();
    }
}
