package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //definizioni generiche degli elementi che servono ad implementare tutti gli oggetti del mondo di gioco
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //i numeri nel for (...'.get(n)'...) sono gli indici degli oggetti nella mappa Tiled

        //ground bodie and fixtures
        for (MapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM
                    , (rect.getHeight() / 2) / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //buildings bodies and fixtures
        for (MapObject obj : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM
                    , (rect.getHeight() / 2) / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //coins bodies and fixtures
        for (MapObject obj : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM
                    , (rect.getHeight() / 2) / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //deadly water bodies and fixtures
        for (MapObject obj : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM,
                    (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM
                    , (rect.getHeight() / 2) / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
}
