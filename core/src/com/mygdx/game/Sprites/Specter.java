package com.mygdx.game.Sprites;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Specter extends Enemy{
    public Specter(PlayScreen screen, float x, float y) {
        super(screen, x, y);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 882 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MyGdxGame.PPM);
//definisco BIT del nemico
        fdef.filter.categoryBits=MyGdxGame.ENEMY_BIT;
        //definisco le cose che possono collidere col nemico
        fdef.filter.maskBits=MyGdxGame.GROUND_BIT |MyGdxGame.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

      //  b2body.createFixture(fdef).setUserData("body");
    }
}
