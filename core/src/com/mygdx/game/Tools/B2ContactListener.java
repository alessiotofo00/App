package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTileObject;

public class B2ContactListener  implements ContactListener {

    @Override
    public void beginContact(Contact contact) {//comincia il contatto tra due corpi

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //Per messaggio a console per il running, con get.Body posso ottenere le fixture che si sono colpite, salvate in fixA e fixB,
        //con getType stampo il tipo se dinamico o statico in base agli oggetti che si sono colpiti
        if(fixA.getUserData() == "body" || fixB.getUserData() == "body"){
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).bodyHit();
                //devo sapere se quello che ho colpito è un interactive,quale è e attivare di conseguenza il bodyhit corretto
                //controllo anche che sia una collisione corretta not null
            }
        }
        //lo switch per ora ha un solo case ma sarà utile anche per gli altri tipi di contatti(es. enemy che va contro un oggetto)
        switch(cDef){
            case MyGdxGame.PLAYER_BIT | MyGdxGame.ENEMY_BIT:
                Gdx.app.log("Contact", "Enemy");
                //se si scontrano Player e Enemy chiamo il metodo hitPlayer della classe Enemy
                //il metodo sarà specializzatp per ogni nemico
                if(fixA.getFilterData().categoryBits == MyGdxGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).hitPlayer();
                else
                    ((Enemy) fixB.getUserData()).hitPlayer();
                break;


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
