package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.*;

public class B2WorldCreator {

    private final Array<Skeleton> skeletons = new Array<Skeleton>();

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //definizioni generiche degli elementi che servono ad implementare tutti gli oggetti del mondo di gioco
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //i numeri nel for (...'.get(n)'...) sono gli indici degli oggetti nella mappa Tiled

        //ground bodies and fixtures
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
        //spikes bodies and fixtures
        for (MapObject obj : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            new Spikes(screen, rect);
        }
        //level switcher bodies and fixtures
        for (MapObject obj : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            new LevelSwitcher(screen, rect);
        }
        //doublejumps bodies and fixtures
        for (MapObject obj : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            new DoubleJump(screen, rect);
        }
        //infobox bodies and fixtures
        for (MapObject obj : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            new InfoBox(screen, rect);
        }
        /*//skeletons bodies and fixtures
        for (MapObject obj : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            skeletons.add(new Skeleton(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM));
        }*/
    }

    public Array<Skeleton> getSkeletons() {
        return skeletons;
    }
}
