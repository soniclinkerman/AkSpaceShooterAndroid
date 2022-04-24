package com.ak.spaceshooter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {


    private Camera camera;
    private Viewport viewport;

    SpriteBatch batch;
    TextureAtlas textureAtlas;

    TextureRegion[] backgrounds;

    TextureRegion playerShipTextureRegion,enemyShipTextureRegion,
            playerLaserTextureRegion, enemyLaserTextureRegion;

    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollSpeed;

    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;


    Ship playerShip;
    Ship enemyShip;
    LinkedList<Laser> playerLaserList;
    LinkedList<Laser> enemyLaserList;

    GameScreen(){
        camera = new OrthographicCamera(); //2D CAMERA
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
        textureAtlas = new TextureAtlas("Game Assets.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Space-Background-Tiled");
        backgrounds[1] = textureAtlas.findRegion("layer1");
        backgrounds[2] = textureAtlas.findRegion("layer2");
        backgrounds[3] = textureAtlas.findRegion("layer3");

        backgroundMaxScrollSpeed =(float)(WORLD_HEIGHT) / 4;

        playerShipTextureRegion= textureAtlas.findRegion("ship_01");
        enemyShipTextureRegion= textureAtlas.findRegion("ship_22");

        playerLaserTextureRegion = textureAtlas.findRegion("tile_01");
        enemyLaserTextureRegion = textureAtlas.findRegion("tile_02");

        playerShip = new PlayerShip(
                WORLD_WIDTH/2,WORLD_HEIGHT/4,
                10,10,
                5,3,
                0.4f,4,
                45,.5f,playerShipTextureRegion,playerLaserTextureRegion
        );
        enemyShip = new EnemyShip(
                WORLD_WIDTH/2,WORLD_HEIGHT* 3 /4,
                10,10,
                2, 1,
                0.3f,5,
                50,.8f,enemyShipTextureRegion,enemyLaserTextureRegion
        );



        playerLaserList = new LinkedList<>();
        enemyLaserList= new LinkedList<>();



        batch = new SpriteBatch();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();

        playerShip.update(delta);
        enemyShip.update(delta);
        renderBackground(delta);


        enemyShip.draw(batch);
        playerShip.draw(batch);


        //PLAYER LASERS
        if(playerShip.canFireLaser())
        {
            Laser[] lasers = playerShip.fireLasers();
            for(Laser laser: lasers)
            {
                playerLaserList.add(laser);
            }
        }

        //ENEMY LASERS
        if(enemyShip.canFireLaser())
        {
            Laser[] lasers = enemyShip.fireLasers();
            for(Laser laser: lasers)
            {
                enemyLaserList.add(laser);
            }
        }

//        DRAW PLAYER LASERS
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext())
        {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition+=laser.movementSpeed*delta;
            if(laser.yPosition > WORLD_HEIGHT)
            {
                iterator.remove();
            }
        }

        //        DRAW ENEMY LASERS
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext())
        {
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.yPosition-=laser.movementSpeed*delta;
            if(laser.yPosition + laser.height < 0)
            {
                iterator.remove();
            }
        }







        batch.end();

    }
    void  renderBackground(float time)
    {
        backgroundOffsets[0] += time*backgroundMaxScrollSpeed / 8;
//        backgroundOffsets[1] += time*backgroundMaxScrollSpeed / 4;
//        backgroundOffsets[2] += time*backgroundMaxScrollSpeed / 2;
//        backgroundOffsets[3] += time*backgroundMaxScrollSpeed;

        for(int i = 0; i < backgroundOffsets.length; i++)
        {
            if(backgroundOffsets[i] > WORLD_HEIGHT){
                backgroundOffsets[i]=0;
            }
            batch.draw(backgrounds[i],0,-backgroundOffsets[i],WORLD_WIDTH,WORLD_HEIGHT);
            batch.draw(backgrounds[i],0,-backgroundOffsets[i]+WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        }
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
