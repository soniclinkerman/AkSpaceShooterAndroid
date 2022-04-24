package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {


    public PlayerShip(float xCenter, float yCenter,
                      float width, float height,
                      float movementSpeed, int health,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTextureRegion,
                      TextureRegion laserTextureRegion) {
        super(xCenter, yCenter, width, height, movementSpeed, health, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, laserTextureRegion);
    }



    @Override
    public Laser[] fireLasers() {
        Laser[] laser =new Laser[2];
        laser[0] = new Laser(xPosition+width*0.07f,yPosition+height*0.45f,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion);
        laser[1] = new Laser(xPosition+width*0.93f,yPosition+height*0.45f,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion
        );

        timeSinceLastShot=0;
        return  laser;
    }
}
