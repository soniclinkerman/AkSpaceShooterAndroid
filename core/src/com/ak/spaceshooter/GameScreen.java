package com.ak.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private final float MOVEMENT_THRESHOLD = 0.5f;


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
                50,3,
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

    void renderLasers(float delta)
    {
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
            laser.boundingBox.y+=laser.movementSpeed*delta;
            if(laser.boundingBox.y > WORLD_HEIGHT)
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
            laser.boundingBox.y-=laser.movementSpeed*delta;
            if(laser.boundingBox.y + laser.boundingBox.height < 0)
            {
                iterator.remove();
            }
        }
    }


    void detectCollisions()
    {
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext())
        {
            Laser laser = iterator.next();
            if(enemyShip.intersects(laser.boundingBox))
            {
                //COntact with enemy ship
                enemyShip.hit(laser);
                iterator.remove();
            }

        }

         iterator = enemyLaserList.listIterator();
        while(iterator.hasNext())
        {
            Laser laser = iterator.next();
            if(playerShip.intersects(laser.boundingBox))
            {
                //Contact with player ship
                playerShip.hit(laser);
                iterator.remove();
            }

        }



    }
    @Override
    public void render(float delta) {
        batch.begin();

        inputDetetcion(delta);

        playerShip.update(delta);
        enemyShip.update(delta);
        renderBackground(delta);


        enemyShip.draw(batch);
        playerShip.draw(batch);


        renderLasers(delta);

        detectCollisions();

        batch.end();

    }

    void inputDetetcion(float deltaTime)
    {
        float leftLimit,rightLimit,upLimit,downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH- playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT/ 2- playerShip.boundingBox.y - playerShip.boundingBox.height;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0)
        {
            float xChange= Math.min(playerShip.movementSpeed * deltaTime,rightLimit);
            playerShip.translate(xChange,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit>0)
        {
            float yChange= Math.min(playerShip.movementSpeed * deltaTime,upLimit);
            playerShip.translate(0,yChange);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit<0)
        {
            float yChange= Math.max(-playerShip.movementSpeed * deltaTime,downLimit);
            playerShip.translate(0,yChange);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0)
        {
            float xChange = Math.max(-playerShip.movementSpeed * deltaTime,leftLimit);
            playerShip.translate(xChange,0);

        }

        if (Gdx.input.isTouched())
        {
            float xPos = Gdx.input.getX();
            float yPos = Gdx.input.getY();

            Vector2 coordinates = new Vector2(xPos,yPos);
            coordinates = viewport.unproject(coordinates);

            Vector2 playerShipCenter = new Vector2(
                    playerShip.boundingBox.x+ playerShip.boundingBox.width / 2,
                    playerShip.boundingBox.y+playerShip.boundingBox.height / 2
                    );


            float distance = coordinates.dst(playerShipCenter);
            if(distance > MOVEMENT_THRESHOLD)
            {
                float xDifference = coordinates.x - playerShipCenter.x;
                float yDifference = coordinates.y - playerShipCenter.y;

                float xMove = xDifference / distance* playerShip.movementSpeed*deltaTime;
                float yMove = yDifference / distance* playerShip.movementSpeed*deltaTime;

                if(xMove > 0)
                {
                    xMove=Math.min(xMove,rightLimit);
                }
                else xMove = Math.max(xMove,leftLimit);


                if(yMove > 0)
                {
                    yMove=Math.min(yMove,upLimit);
                }
                else yMove = Math.max(yMove,downLimit);

                playerShip.translate(xMove,yMove);
            }








        }



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
