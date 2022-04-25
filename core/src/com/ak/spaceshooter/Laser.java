package com.ak.spaceshooter;
import  com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Laser {
    Rectangle boundingBox;


    float movementSpeed;

    TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition, float width, float height, float  movementSpeed, TextureRegion textureRegion) {
        this.boundingBox = new Rectangle(xPosition- width/2,yPosition,width,height);
        this. movementSpeed =  movementSpeed;
        this.textureRegion = textureRegion;
    }

    void draw(Batch batch)
    {
        batch.draw(textureRegion,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);


    }

    public Rectangle getBoundingBox()
    {
        return boundingBox;

    }



}
