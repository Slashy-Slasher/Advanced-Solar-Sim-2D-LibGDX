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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            planetList.get(0).degree -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            planetList.get(0).degree += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            planetList.get(0).setVelocity(planetList.get(0).getVelocity().add(engine.thrustVec(planetList.get(0).degree, .05f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.K))
        {
            planetList.get(0).setVelocity(planetList.get(0).getVelocity().add(engine.thrustVec(planetList.get(0).degree, 1f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            planetList.get(0).setVelocity(planetList.get(0).getVelocity().add(engine.thrustVec(planetList.get(0).degree, -.005f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            planetList.get(0).setVelocity(new Vector2(0,0));
        }
    }
}
