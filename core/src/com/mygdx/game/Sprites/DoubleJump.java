package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sound;

public class DoubleJump extends RectInteractiveTileObject {

    PlayScreen screen;

    public DoubleJump(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        super.body.setAwake(true);
        super.fixture.setRestitution(1.2f);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.DOUBLE_JUMP_BIT);
    }

    @Override
    public void bodyHit() {
        Gdx.app.log("Hit", "DoubleJump");
        //Sound.playDoubleJumpS();

    }

}
