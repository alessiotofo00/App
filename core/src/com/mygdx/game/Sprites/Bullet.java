package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Bullet extends Enemy {

    private boolean setToDestroy;
    private boolean destroyed;

    private Texture texture;

    public Bullet(PlayScreen screen, float x, float y){
        super(screen, x, y);
        texture = new Texture(Gdx.files.internal("bullet.png"));
        setSize(16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM);

        setBounds(getX(), getY(), 16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        if(b2body.getPosition().y >= MyGdxGame.V_HEIGHT)
            setToDestroy = true;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocityY);
            setPosition(b2body.getPosition().x - getWidth() / 2,
                    b2body.getPosition().y - getHeight() / 2);
            setRegion(texture);
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7 / MyGdxGame.PPM, 7 / MyGdxGame.PPM);
        fdef.shape = shape;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = MyGdxGame.BULLET_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.OBJECT_BIT | MyGdxGame.PLAYER_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    @Override
    public void hitPlayer() {
        screen.getPlayer().hitDetect();
        setToDestroy = true;
    }

    @Override
    public void hitByPlayer() {

    }


}
