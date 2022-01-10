package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Skeleton extends Enemy {

    private float stateTimer;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Skeleton(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i = 3; i < 13; i++)
            frames.add(new TextureRegion(screen.getSkeletonAtlas().findRegion("Skeleton Walk"),
                    i * 22, 0, 21, 34));
        walkAnimation = new Animation(0.1f, frames);
        setBounds(getX(), getY(), 21 / MyGdxGame.PPM, 34 / MyGdxGame.PPM);
        stateTimer = 0;
        setToDestroy = false;
        destroyed = false;
    }

    @Override
    public void update(float dt) {
        stateTimer += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getSkeletonAtlas().findRegion("Skeleton Walk"), 12, 35, 21, 34));
            stateTimer = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocityX);
            setPosition(b2body.getPosition().x - getWidth() / 2,
                    b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTimer, true));
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
        shape.setAsBox(8 / MyGdxGame.PPM, 14 / MyGdxGame.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.OBJECT_BIT | MyGdxGame.PLAYER_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTimer < 1)
            super.draw(batch);
    }

    @Override
    public void hitPlayer() {
        //chiamo il metodo hitDetect di Player
        screen.getPlayer().hitDetect();
    }

    public void hitByPlayer(){
        Gdx.app.log("Nemico", "colpito");
        setToDestroy = true;
    }
}
