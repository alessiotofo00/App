package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    protected PlayScreen screen;
    protected World world;
    public Body b2body;
    public Vector2 velocityX, velocityY;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocityX = new Vector2(0.8f,0);
        velocityY = new Vector2(0, 1.5f);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);

    public abstract void hitPlayer();
    public abstract void hitByPlayer();

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocityX.x = -velocityX.x;
        if(y)
            velocityY.y = -velocityY.y;
    }

}
