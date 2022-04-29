package com.ak.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;


public class GameScreen implements Screen {


    private Camera camera;
    private Viewport viewport;

    SpriteBatch batch;
    TextureAtlas textureAtlas;
    Texture explosionTexture;


    TextureRegion[] backgrounds;

    TextureRegion playerShipTextureRegion,enemyShipTextureRegion,
            playerLaserTextureRegion, enemyLaserTextureRegion;

    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollSpeed;
    private float timeBetweenEnemySpawns =1f;
    private float enemySpawnTimer =0;

    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;
    private final float MOVEMENT_THRESHOLD = 0.5f;


    PlayerShip playerShip;
    LinkedList<EnemyShip> enemyShipsList;
    LinkedList<Laser> playerLaserList;
    LinkedList<Laser> enemyLaserList;
    LinkedList<Explosion> explosionList;

    int score = 0;

    int shipsToDestroy;

    boolean isPlayerDead = false;



    //HUD UI
    BitmapFont font;
    float hudVerticalMargin, hudLextX, hudRightX, hudCenterX, hudRow1Y,hudRow2Y,hudSectionWidth;

    private String level_number;//TODO: this is the level number as a string, passed from the constructor. do something with it
    ResultScreen resultScreen;
    GameScreen(String level_number,ResultScreen resultScreen){
        this.level_number=level_number;
        this.resultScreen = resultScreen;

        switch (level_number)
        {
            case "1":
                shipsToDestroy=5;
                timeBetweenEnemySpawns=3f;
                break;
            case "2":
//                shipsToDestroy= 9;
//                timeBetweenEnemySpawns=2f;
                shipsToDestroy= 99;
                timeBetweenEnemySpawns=.5f;
                break;
            case "3":
                shipsToDestroy=12;
                timeBetweenEnemySpawns=1.5f;
                break;
            case "4":
                shipsToDestroy= 16;
                timeBetweenEnemySpawns=1.25f;
                break;
            case "5":
                shipsToDestroy=15;
                timeBetweenEnemySpawns=1f;
                break;
        }




        camera = new OrthographicCamera(); //2D CAMERA
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
        textureAtlas = new TextureAtlas("GameSet.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Space-Background-Tiled");
        backgrounds[1] = textureAtlas.findRegion("layer1");
        backgrounds[2] = textureAtlas.findRegion("layer2");
        backgrounds[3] = textureAtlas.findRegion("layer3");

        backgroundMaxScrollSpeed =(float)(WORLD_HEIGHT) / 4;

        playerShipTextureRegion= textureAtlas.findRegion("ship_02");
        enemyShipTextureRegion= textureAtlas.findRegion("ship_06");

        playerLaserTextureRegion = textureAtlas.findRegion("tile_01");
        enemyLaserTextureRegion = textureAtlas.findRegion("tile_03");


        explosionTexture = new Texture("Explosion.png");
        System.out.println(explosionTexture);


        playerShip = new PlayerShip(
                (float)WORLD_WIDTH/2,(float)WORLD_HEIGHT/4,
                10,10,
                50,3,
                5f,4,
                45,.5f,playerShipTextureRegion,playerLaserTextureRegion
        );


        enemyShipsList = new LinkedList<>();
        explosionList = new LinkedList<>();







        playerLaserList = new LinkedList<>();
        enemyLaserList= new LinkedList<>();



        batch = new SpriteBatch();
        prepareHUD();

    }

    void  prepareHUD()
    {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PixelEmulator-xq08.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth=3.6f;
        fontParameter.color= new Color(0,0,0,0.8f);
        fontParameter.borderColor= new Color(0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);

        font.getData().setScale(0.05f);

         hudVerticalMargin=font.getCapHeight()/2;
         hudLextX= hudVerticalMargin;
         hudRightX=WORLD_WIDTH*2 / 3 - hudLextX;
         hudCenterX= WORLD_WIDTH/3;
         hudRow1Y= WORLD_HEIGHT - hudVerticalMargin;
         hudRow2Y=hudRow1Y - hudVerticalMargin- font.getCapHeight();
         hudSectionWidth=WORLD_WIDTH / 3;
    }


    void checkLevelNumber()
    {

    }


    @Override
    public void show() {

    }

    void renderExplosions(float deltatime)
    {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while(explosionListIterator.hasNext())
        {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltatime);
            if(explosion.isFinished()){
                explosionListIterator.remove();
            }
            else{
                explosion.draw(batch);
            }
        }
    }

    void renderLasers(float delta)
    {
        //PLAYER LASERS
        if(playerShip.canFireLaser() )
        {
            Laser[] lasers = playerShip.fireLasers();
            for(Laser laser: lasers)
            {
                playerLaserList.add(laser);
            }
        }

        //ENEMY LASERS
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
        while(enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                for (Laser laser : lasers) {
                    enemyLaserList.add(laser);
                }
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


    void detectCollisions() {
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();

            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();
                if (enemyShip.intersects(laser.boundingBox)) {
                    //Contact with enemy ship
                    if (enemyShip.isHit(laser)) {
                        enemyShipListIterator.remove();
                        explosionList.add(
                                new Explosion(explosionTexture, new Rectangle(enemyShip.boundingBox), .7f));
                        score+=100;
                        shipsToDestroy--;
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }

        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersects(laser.boundingBox) && !isPlayerDead) {
                //Contact with player ship
                if (playerShip.isHit(laser)) {
                    playerShip.lives--;
                    if(playerShip.lives < 1)
                    {
                        explosionList.add(
                                new Explosion(explosionTexture, new Rectangle(playerShip.boundingBox), .7f * 2));
                        isPlayerDead=true;
                       playerShip.lives=0;
                    }
                }
            laserListIterator.remove();
        }
    }
}
    @Override
    public void render(float delta) {
        batch.begin();
        renderBackground(delta);

        if(playerShip.lives>0) {
            inputDetetcion(delta);
        }

        playerShip.update(delta);

        if(playerShip.lives>0 && shipsToDestroy > enemyShipsList.size()) {
            spawnEnemyShips(delta);
        }

        ListIterator<EnemyShip> enemyShipListIterator= enemyShipsList.listIterator();

        while(enemyShipListIterator.hasNext())
        {
            EnemyShip enemyShip = enemyShipListIterator.next();
            moveEnemy(enemyShip,delta);
            enemyShip.update(delta);
            enemyShip.draw(batch);
        }

        if(playerShip.lives>0)
        {
            playerShip.draw(batch);
        }










        if(playerShip.lives>0) {

            renderLasers(delta);
        }

        detectCollisions();

        renderExplosions(delta);

        updateAndRenderHUD();

        batch.end();


        if(playerShip.lives<=0 || shipsToDestroy<=0){
            resultScreen.openResultScreen(score,playerShip.lives>0 );
        }

    }

    void updateAndRenderHUD()
    {

        font.draw(batch, "Score", hudLextX,hudRow1Y,hudSectionWidth, Align.left,false);
        font.draw(batch, "Level", hudCenterX,hudRow1Y,hudSectionWidth, Align.center,false);
        font.draw(batch, "Lives", hudRightX,hudRow1Y,hudSectionWidth, Align.right,false);

        font.draw(batch,String.format(Locale.getDefault(), "%06d",score),hudLextX,hudRow2Y,hudSectionWidth, Align.left,false);
        font.draw(batch,String.format(Locale.getDefault(), "%02d",Integer.parseInt(level_number)),hudCenterX,hudRow2Y,hudSectionWidth, Align.center,false);
        font.draw(batch,String.format(Locale.getDefault(), "%02d",playerShip.lives),hudRightX,hudRow2Y,hudSectionWidth, Align.right,false);
    }

    void spawnEnemyShips(float deltaTime) {
        enemySpawnTimer += deltaTime;
            if (enemySpawnTimer > timeBetweenEnemySpawns) {

                enemyShipsList.add(new EnemyShip(AKSpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10 + 5),
                        (WORLD_HEIGHT - 5),
                        10, 10,
                        50, 1,
                        3f, 5,
                        50, .8f, enemyShipTextureRegion, enemyLaserTextureRegion
                ));
                enemySpawnTimer -= timeBetweenEnemySpawns;
            }


    }

    void inputDetetcion(float deltaTime)
    {
        float leftLimit,rightLimit,upLimit,downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH- playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = (float)WORLD_HEIGHT/ 2- playerShip.boundingBox.y - playerShip.boundingBox.height;

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

//        if(Gdx.input.isKeyPressed(Input.Keys.Z) )
//        {
//
//            Intent
//            Intent intent = new Intent(this, AndroidLauncher.class);
//            startActivity(intent);
//
//        }



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

    void moveEnemy(EnemyShip enemyShip,float deltaTime)
    {
        float leftLimit,rightLimit,upLimit,downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit =(float)WORLD_HEIGHT/2 -enemyShip.boundingBox.y;
        rightLimit = WORLD_WIDTH- enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

//        float xDifference = coordinates.x - playerShipCenter.x;
//        float yDifference = coordinates.y - playerShipCenter.y;

        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed*deltaTime;
        float yMove = enemyShip.getDirectionVector().y  * enemyShip.movementSpeed*deltaTime;

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

        enemyShip.translate(xMove,yMove);

    }
    void  renderBackground(float time)
    {
        backgroundOffsets[0] += time*backgroundMaxScrollSpeed / 8;
        backgroundOffsets[1] += time*backgroundMaxScrollSpeed / 4;
        backgroundOffsets[2] += time*backgroundMaxScrollSpeed / 2;
        backgroundOffsets[3] += time*backgroundMaxScrollSpeed;

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
