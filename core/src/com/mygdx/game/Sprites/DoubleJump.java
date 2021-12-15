package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class DoubleJump extends RectInteractiveTileObject {

    PlayScreen screen;

    public DoubleJump(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        super.fixture.setRestitution(1.5f);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.DOUBLE_JUMP_BIT);
    }

    @Override
    public void bodyHit() {
        Gdx.app.log("Hit", "DoubleJump");
        screen.canJump = true;
    }

}
