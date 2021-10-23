package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

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
