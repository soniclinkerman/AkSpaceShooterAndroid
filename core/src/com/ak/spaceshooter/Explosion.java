package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
    Animation<TextureRegion> explosionAnimation;
    float explosionTimer;
    Rectangle boundingBox;

    Explosion(Texture texture, Rectangle boundingBox, float totalAnimationTime){
        this.boundingBox = boundingBox;

//        TextureRegion[][] textureRegion2D = TextureRegion.split(texture,64, 64);

        TextureRegion[][] textureRegion2D = TextureRegion.split(texture,texture.getWidth() / 5, texture.getHeight()/1);

        TextureRegion[] textureRegion1D = new TextureRegion[5];


        int index=0;
        for(int i=0; i < 1; i++)
        {
            for(int j=0; j < 5; j++)
            {
               textureRegion1D[index] = textureRegion2D[i][j];
               index++;
            }

        }




        explosionAnimation = new Animation<TextureRegion>(totalAnimationTime/16, textureRegion1D);

        explosionTimer=0;


    }
    public void update(float deltaTime)
    {
        explosionTimer+=deltaTime;
    }

    public void draw(SpriteBatch batch){
    batch.draw(explosionAnimation.getKeyFrame(explosionTimer),
            boundingBox.x,boundingBox.y,
            boundingBox.width,boundingBox.height);
    }

    public boolean isFinished(){
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }




}
