package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class DoubleJump extends RectInteractiveTileObject {

    PlayScreen screen;

    public DoubleJump(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        super.body.setAwake(true);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.DOUBLE_JUMP_BIT);
    }

    @Override
    public void bodyHit() {
        Gdx.app.log("Hit", "DoubleJump");
        screen.getPlayer().b2body.applyLinearImpulse(new Vector2(0, 5f),
                                                    screen.getPlayer().b2body.getWorldCenter(), true);
       // MyGdxGame.manager.get("audio/doublejump.wav", Sound.class);
    }

}
