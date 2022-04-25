package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import  com.badlogic.gdx.math.Rectangle;


abstract  class Ship {

    float movementSpeed;
    int health;

    //DIMENSIONS & POSITIONS
    Rectangle boundingBox;

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

        this.boundingBox = new Rectangle(xCenter- width/2,yCenter - height /2,width,height);

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
//        boundingBox.set(xPosition,yPosition,width,height);
        timeSinceLastShot+=deltaTime;
    }

    public boolean canFireLaser()
    {
        return timeSinceLastShot - timeBetweenShots >=0;
    }


    public boolean intersects(Rectangle otherRectangle)
    {
        return  boundingBox.overlaps(otherRectangle);


    }

    public void hit(Laser laser)
    {
        if(health > 0){
            health--;
        }

    }


    public abstract Laser[] fireLasers();


    public void draw(Batch batch)
    {
        batch.draw(shipTextureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        if(health>0)
        {

        }

    }

    public void translate(float deltaX, float deltaY)
    {
        boundingBox.setPosition(boundingBox.x+deltaX, boundingBox.y+deltaY);

    }


}
