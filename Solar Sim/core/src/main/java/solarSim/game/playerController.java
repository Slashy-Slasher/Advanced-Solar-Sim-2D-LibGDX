package solarSim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class playerController
{
    public void inputHandler(ArrayList<SolarObject> planetList)
    {
        Engine engine = new Engine();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))    //Turn Left
        {
            planetList.get(0).degree -= 3;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))    //Turn Right
        {
            planetList.get(0).degree += 3;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))       //Forward Thrust
        {
            planetList.get(0).setVelocity(planetList.get(0).getVelocity().add(engine.thrustVec(planetList.get(0).degree, 1f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.K))        //Jump
        {
            // new position = position + Angle * Quadrant * Jump Distance
            float degree = engine.degreeCorrection(planetList.get(0).degree);
            planetList.get(0).setPosition(planetList.get(0).getPosition().add(engine.simpleVectorMult(engine.findQuadrant(degree), engine.findAngle(degree), 500)));
            //planetList.get(0).setVelocity(planetList.get(0).getVelocity().add(engine.thrustVec(planetList.get(0).degree, 1f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            planetList.get(0).setVelocity(planetList.get(0).getVelocity().add(engine.thrustVec(planetList.get(0).degree, -.85f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            planetList.get(0).setVelocity(new Vector2(0,0));
        }
    }
}
