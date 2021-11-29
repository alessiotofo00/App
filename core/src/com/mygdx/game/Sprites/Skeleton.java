package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Skeleton extends Enemy {

    private float stateTimer;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    public Skeleton(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for(int i = 3; i < 13; i++)
            frames.add(new TextureRegion(screen.getSkeletonWalkAtlas().findRegion("Skeleton Walk"),
                    i * 22, 0, 21, 34));
        walkAnimation = new Animation(0.1f, frames);
        setBounds(getX(), getY(), 21 / MyGdxGame.PPM, 34 / MyGdxGame.PPM);
        stateTimer = 0;
    }

    @Override
    public void update(float dt) {
        stateTimer += dt;

        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2,
                b2body.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTimer, true));
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
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    @Override
    public void hitBody(Player player) {

    }

    @Override
    public void HitByEnemy(Enemy enemy) {

    }
}
