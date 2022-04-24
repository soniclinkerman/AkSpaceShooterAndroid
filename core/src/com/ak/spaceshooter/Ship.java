package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

abstract  class Ship {

    float movementSpeed;
    int health;

    //DIMENSIONS & POSITIONS
    float xPosition;
    float yPosition;

    float width;
    float height;

    //LASER INFO
    float laserWidth,laserHeight;
    float timeBetweenShots;
    float timeSinceLastShot = 0;
    float laserMovementSpeed;


    TextureRegion shipTextureRegion,shieldTextureRegion, laserTextureRegion;

    public Ship(
            float xCenter, float yCenter,
            float width,float height,
            float movementSpeed, int health,
            float laserWidth, float laserHeight,
            float laserMovementSpeed,
            float timeBetweenShots,

            TextureRegion shipTextureRegion,
            TextureRegion laserTextureRegion

    ) {
        this.movementSpeed = movementSpeed;
        this.health = health;

        this.xPosition = xCenter- width/2;
        this.yPosition =yCenter - height /2;

        this.width = width;
        this.height = height;

        this.laserWidth=laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;


        this.shipTextureRegion = shipTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
//        this.shieldTexture = shieldTexture;
    }

    public void update(float deltaTime)
    {
        timeSinceLastShot+=deltaTime;
    }

    public boolean canFireLaser()
    {
        return timeSinceLastShot - timeBetweenShots >=0;
    }

    public abstract Laser[] fireLasers();


    public void draw(Batch batch)
    {
        batch.draw(shipTextureRegion, xPosition,yPosition,width,height);

    }

}
