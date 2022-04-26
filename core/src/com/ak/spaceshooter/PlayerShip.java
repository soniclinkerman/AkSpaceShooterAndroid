package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {




    int lives;
    public PlayerShip(float xCenter, float yCenter,
                      float width, float height,
                      float movementSpeed, int health,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTextureRegion,
                      TextureRegion laserTextureRegion) {
        super(xCenter, yCenter, width, height, movementSpeed, health, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, laserTextureRegion);
        lives = 5;
    }



    @Override
    public Laser[] fireLasers() {
        Laser[] laser =new Laser[2];
        laser[0] = new Laser(boundingBox.x+ boundingBox.width*0.07f,boundingBox.y+ boundingBox.height*0.45f,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion);
        laser[1] = new Laser(boundingBox.x+ boundingBox.width*0.93f,boundingBox.y+ boundingBox.height*0.45f,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion
        );

        timeSinceLastShot=0;
        return  laser;
    }


}
