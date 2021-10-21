package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Player extends Sprite {

    public World world;
    public Body b2body;
    private TextureRegion stand;

    public Player(World world, PlayScreen screen){

        super(screen.getAtlas().findRegion("knight death animation"));
        //setto il mondo di gioco del PlayScreen
        this.world = screen.getWorld();

        //chiamo la funzione definePlayer() che definisce tutte le caratteristiche del corpo del Player
        definePlayer();

        stand = new TextureRegion(getTexture(), 7, 7, 32, 35);
        setBounds(0, 0, 32 / MyGdxGame.PPM, 35 / MyGdxGame.PPM);
        setRegion(stand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2,
                b2body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 882 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}

