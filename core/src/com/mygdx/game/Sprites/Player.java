package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Weapon.Weapon;

public class Player extends Sprite {

    //stati in cui si può trovare il Player. Utili per distinguere le animazioni da assegnare
    public enum State { FALLING, JUMPING, STANDING, RUNNING, HIT, GAMEOVER}
    public State currentState;
    public State previousState;

    private Weapon CurrentWeapon;
    public World world;
    public Body b2body;

    //run e jump(etc) sono Animation di TextureRegion, in quanto serve piu di una TextureRegion per creare l'animazione
    //del movimento e del salto. Per stand/fall basta una sola TextureRegion (una sola immagine praticamente)
    private final TextureRegion stand;
    private final TextureRegion fall;
    private final Animation<TextureRegion> run;
    private final Animation<TextureRegion> jump;
    private final Animation<TextureRegion> hit;
    private final Animation<TextureRegion> gameover;
    //timer che indica il tempo speso in un certo stato (RUNNING,JUMPING, etc.),
    //rappresentato dalla corrispondente animazione(vedi il suo utilizzo come parametro nello switch del metodo getFrame)
    private float stateTimer;
    //boolean utile a capire il verso in cui corre il Player e, di conseguenza, ad orientare la sua sprite
    private boolean runningRight;
    //int per tenere conto del numero di colpi ricevuti dal Player
    private int hits;

    public Player(PlayScreen screen){

        //seleziono la regione di texture da cui partire per andare a costruire le varie animazioni
        //la stringa inserita come parametro è presente nel file 'RedKnight.pack' e ce n'è una per ogni regione
        super(screen.getKnightAtlas().findRegion("knight death animation"));
        //setto il mondo di gioco del PlayScreen
        this.world = screen.getWorld();

        setHits(0);

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        //array temporaneo per salvare i frame dell'animazione corrente
        Array<TextureRegion> frames = new Array<>();
        //animazione dello stato RUNNING
        for(int i = 14; i < 18; i++){
            //le coordinate x e y le prendo dal file RedKnight.png
            //se lo apri da IntelliJ e usi lo strumento show grid si crea una vera e propria griglia di pixel
            frames.add(new TextureRegion(getTexture(), i * 43, 5, 42, 42));
        }
        run = new Animation<>(0.1f, frames);
        frames.clear();

        //animazione dello stato JUMPING
        for(int i = 12; i < 14; i++){
            frames.add(new TextureRegion(getTexture(), i * 43, 5, 42, 42));
        }
        jump = new Animation<>(0.1f, frames);
        frames.clear();

        //frame dello stato FALLING
        fall = new TextureRegion(getTexture(),12 * 43, 5, 42, 42);
        frames.clear();

        //animazione dello stato HIT
        for(int i = 2; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i * 79, 83, 55, 42));
        }
        hit = new Animation<>(0.1f, frames);
        frames.clear();

        //animazione dello stato GAMEOVER
        for(int i = 1; i < 9; i++){
            frames.add(new TextureRegion(getTexture(), i * 43, 5, 42, 42));
        }
        gameover = new Animation<>(0.1f, frames);
        //chiamo la funzione definePlayer() che definisce tutte le caratteristiche del corpo del Player
        definePlayer();

        stand = new TextureRegion(getTexture(), 7, 7, 42, 42);
        setBounds(0, 0, 42 / MyGdxGame.PPM, 42 / MyGdxGame.PPM);
        setRegion(stand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2,
                b2body.getPosition().y - getHeight() / 2);
        //setto la regione in base alla serie di frame che sono ritornati dal metodo getFrame
        setRegion(getFrame(dt));
    }

    //metodo per la selezione dei frame corrispondenti allo stato corrente e per l'aggiornamento dello stateTimer
    public TextureRegion getFrame(float dt){
        //metto in currentState lo stato che ottengo dal metodo getState
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = jump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = run.getKeyFrame(stateTimer, true);//il parametro looping va messo a true solo se
                                                                  //l'animazione va ripetuta(es. RUNNING)
                break;
            case FALLING:
                region = fall;
                break;
            case HIT:
                region = hit.getKeyFrame(stateTimer, true);
                break;
            case GAMEOVER:
                region = gameover.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = stand;
                break;
        }
        //if utili a settare il verso della texture tramite il boolean runningRight
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //aggiorno lo state timer--> se (currentState == previousState) --> stateTimer += dt;
        //else --> stateTimer = 0;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    //metodo che seleziona lo stato corrente in base alla velocita del Player
    public State getState(){
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
                return State.HIT;
        else if(b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(getHits() == 4)
            return State.GAMEOVER;
        else return State.STANDING;
    }

    public float getStateTimer(){ return stateTimer; }

    public Weapon getCurrentWeapon() {
        return CurrentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        CurrentWeapon = currentWeapon;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void hitDetect(){
        //test di prova per il funzionamento della HealthBar
        if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
            setHits(getHits() + 1);
        }
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 882 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        //prova di come costruire i contorni di un oggetto davanti al personaggio, si potrebbe implementare un arma tipo lancia
        //nel caso non si riuscisse a creare uno swing con la spada: carica->contatto->danno, tipo cavaliere
        EdgeShape weapon = new EdgeShape();
        weapon.set(new Vector2(8/ MyGdxGame.PPM, -2 / MyGdxGame.PPM), new Vector2(20 / MyGdxGame.PPM, 10 / MyGdxGame.PPM));
        fdef.shape = weapon;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("weapon");
    }
}

