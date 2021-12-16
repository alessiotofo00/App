package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class InfoBox extends RectInteractiveTileObject{

    PlayScreen screen;

    public InfoBox(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);

        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.INFOBOX_BIT);
    }

    @Override
    public void bodyHit() {

    }
}
