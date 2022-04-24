package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship {

    public EnemyShip(float xCenter, float yCenter,
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
        laser[0] = new Laser(xPosition+width*0.18f,yPosition+laserHeight / 2,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion);
        laser[1] = new Laser(xPosition+width*0.82f,yPosition+laserHeight / 2,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion
        );

        timeSinceLastShot=0;
        return  laser;
    }
}
