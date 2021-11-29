package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.InteractiveTileObject;

public class B2ContactListener  implements ContactListener {

    private MyGdxGame game;
    @Override
    public void beginContact(Contact contact) { //comincia il contatto tra due corpi
        System.out.println("Contact");
        Fixture fixA= contact.getFixtureA();
        Fixture fixB= contact.getFixtureB();
        System.out.println(fixA.getBody().getType()+" ha colpito "+ fixB.getBody().getType());
        //Per messaggio a console per il running, con get.Body posso ottenere le fixture che si sono colpite, salvate in fixA e fixB,
        //con getType stampo il tipo se dinamico o statico in base agli oggetti che si sono colpiti
        if(fixA.getUserData()=="body"||fixB.getUserData()=="body"){
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).bodyHit();
                //devo sapere se quello che ho colpito è un interactive,quale è e attivare di conseguenza il bodyhit corretto
                //controllo anche che sia una collisione corretta not null
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { //cosa succede quando due oggetti collidono

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) { //cosa succede dopo la collisione

    }
}
