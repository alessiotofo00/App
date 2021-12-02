package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class HealthBar extends Sprite {

    private Player player;
    public World world;
    private TextureRegion healthBar;

    public HealthBar(World world, PlayScreen screen){

        super(screen.getHealthBarAtlas().findRegion("healthBar"));

        this.world = screen.getWorld();
        this.player = screen.getPlayer();

        //le coordinate x e y le prendo dal file HealthBar.png
        //se lo apri da IntelliJ e usi lo strumento show grid si crea una vera e propria griglia di pixel
        healthBar = new TextureRegion(getTexture(), 6, 8, 24, 9);
        setRegion(healthBar);

        setBounds(0, 0, 24 / MyGdxGame.PPM, 9 / MyGdxGame.PPM);

    }

    public void update(float dt){
        setPosition(player.b2body.getPosition().x - getWidth() / 2,
                player.b2body.getPosition().y + getHeight() * 2);

        //switch case per capire quanti colpi ha subito il player e quindi settare l'immagine corretta della healthBar
        switch(player.hits){
            //sono multipli di due perchè ogni contatto tra Player ed Enemy lo conta per due(ci sarebbe da capire il motivo ma per ora funziona cosi)
            case 0:
                healthBar = new TextureRegion(getTexture(), 6, 8, 24, 9);
                setRegion(healthBar);
                break;
            case 2:
                healthBar = new TextureRegion(healthBar.getTexture(), 39, 8, 24, 9);
                setRegion(healthBar);
                break;
            case 4:
                healthBar = new TextureRegion(healthBar.getTexture(), 72, 8, 24, 9);
                setRegion(healthBar);
                break;
            case 6:
                healthBar = new TextureRegion(healthBar.getTexture(), 6, 41, 24, 9);
                setRegion(healthBar);
                break;
            case 8:
                healthBar = new TextureRegion(healthBar.getTexture(), 39, 41, 24, 9);
                setRegion(healthBar);
                break;
        }


    }
}
