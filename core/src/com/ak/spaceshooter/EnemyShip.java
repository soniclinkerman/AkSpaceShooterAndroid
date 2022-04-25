package com.ak.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip extends Ship {

    Vector2 directionVector;
    float timeSinceLastDirectionChange=0;
    float directionChangeFrequency = 0.75f;
    public EnemyShip(float xCenter, float yCenter,
                      float width, float height,
                      float movementSpeed, int health,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTextureRegion,
                      TextureRegion laserTextureRegion) {
        super(xCenter, yCenter, width, height, movementSpeed, health, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, laserTextureRegion);

        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    void randomizeDirectionVector()
    {
        double bearing = AKSpaceShooter.random.nextDouble()*6.283185;
        directionVector.x = (float) Math.sin(bearing);
        directionVector.y =(float) Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange+=deltaTime;
        if(timeSinceLastDirectionChange > directionChangeFrequency)
        {
            randomizeDirectionVector();
            timeSinceLastDirectionChange-=directionChangeFrequency;
        }

    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser =new Laser[2];
        laser[0] = new Laser(boundingBox.x+ boundingBox.width*0.18f,boundingBox.y+laserHeight / 2,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion);
        laser[1] = new Laser(boundingBox.x+ boundingBox.width*0.82f,boundingBox.y+laserHeight / 2,
                laserWidth,laserHeight,
                laserMovementSpeed,laserTextureRegion
        );

        timeSinceLastShot=0;
        return  laser;
    }
}
