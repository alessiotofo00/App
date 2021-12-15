package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public abstract class CircleInteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Ellipse ellipse;
    protected Body body;
    protected Fixture fixture;

    public CircleInteractiveTileObject(PlayScreen screen, Ellipse ellipse){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.ellipse = ellipse;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(ellipse.x / MyGdxGame.PPM, ellipse.y / MyGdxGame.PPM);

        body = world.createBody(bdef);

        shape.setRadius(ellipse.x + ellipse.width / 2);
        fdef.shape = shape;
        fdef.restitution = 0.4f;
        fixture = body.createFixture(fdef);
    }

    public abstract void bodyHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(4); //numero di graphic
        return layer.getCell((int)(body.getPosition().x * MyGdxGame.PPM/16), (int)(body.getPosition().y * MyGdxGame.PPM/16));
    }
}
