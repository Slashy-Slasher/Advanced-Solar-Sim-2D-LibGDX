package solarSim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class Engine
{
    public Vector2 gravity(SolarObject p1, SolarObject p2, boolean projection)
    {
        double G = .1f;
        double numerator = G * p1.getMass() * p2.getMass();
        float rSquared = p2.getPosition().cpy().sub(p1.getPosition()).len();
        float gForce = (float)numerator/rSquared;
        Vector2 direction = p2.getPosition().cpy().sub(p1.getPosition()).nor();
        float forceX = gForce * direction.x;
        float forceY = gForce * direction.y;
        //System.out.println(p1.getName() + " force's = (" + forceX + ", " + forceY + ") is projection: " + projection);
        return new Vector2(forceX, forceY);
    }
    //I'm looking for why
    public void universalGravity(ArrayList<SolarObject> xplanetList, boolean isProjection)
    {
        Vector2 totalForce;
        for (int i = 0; i < xplanetList.size(); i++)
        {
            if(!xplanetList.get(i).getStable())
            {
                totalForce = new Vector2();
                for(int j = 0; j < xplanetList.size(); j++)
                {
                    if (i != j)
                    {
                        totalForce.add(gravity(xplanetList.get(i), xplanetList.get(j), isProjection));
                    }
                }
                xplanetList.get(i).setVelocity(xplanetList.get(i).getVelocity().add(totalForce.cpy()).cpy());
            }
        }
    }
    public void panCamera(ArrayList<SolarObject> planetList, int x, int y)
    {
        for(int i = 0; i < planetList.size(); i++)
        {
            planetList.get(i).setPosition(planetList.get(i).getPosition());
        }
    }

    public void initializeSolarSystem(ArrayList<SolarObject> planetList)
    {
        planetList.add(new SolarObject(100, new Vector2(600,450), new Vector2(0, 0), 30, new Color(.7f, .3f, 0, 1), "sun" ,true));
        for (int i = 0; i < Math.random()*5;i++)
        {
            planetList.add(new SolarObject(15*Math.random(), new Vector2((float)(600*Math.random()+650),(float)(450)), new Vector2(0, (float)(-2-5*Math.random())), (float)(3*Math.random()+3), new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1), "Planet: "+i ,false));
        }

    }
    public void fpsToConsole()
    {
        System.out.println("Current FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    public void positionRecorder(ArrayList<SolarObject> planetList)
    {
        for(int i = 0; i < planetList.size(); i++)
        {
            planetList.get(i).addPreviousPositions(planetList.get(i).getPosition());
            if(planetList.get(i).getPreviousPositions().size() > 1000)
            {
                planetList.get(i).removeFirstPreviousPosition();
            }
        }
    }
    //Takes the current PlanetList
    //Uses the TempPlanetList Array to project the future positions of each planet and save them to the future position
    //of each planet
    public void projectFuturePosition(ArrayList<SolarObject> planetList, int ticks)
    {
        ArrayList<SolarObject> tempPlanetList = deepCopySolarList(planetList);
        for(int i = 0; i < ticks; i++)
        {
            universalGravity(tempPlanetList, true);
            applyForce(tempPlanetList);
            for(int x = 0; x < tempPlanetList.size(); x++)
            {
                if(!tempPlanetList.get(x).getStable())
                {
                    //System.out.println("Current " + tempPlanetList.get(x).getName() + " " + planetList.get(x).getPosition());
                    //System.out.println("Projected " + tempPlanetList.get(x).getName() + " " + tempPlanetList.get(x).getPosition());
                }
                planetList.get(x).addProjectedPositions(tempPlanetList.get(x).getPosition().cpy());
            }
        }
    }

    public ArrayList<SolarObject> deepCopySolarList(ArrayList<SolarObject> x)
    {
        ArrayList<SolarObject> copiedList = new ArrayList<>();

        for (SolarObject originalObject : x) {
            copiedList.add(originalObject.deepCopy());
        }

        return copiedList;
    }
    public ArrayList<Vector2> deepCopyVectorList(ArrayList<Vector2> x)
    {
        ArrayList<Vector2> copiedList = new ArrayList<>();

        for (Vector2 originalObject : x) {
            copiedList.add(originalObject.cpy());
        }
        return copiedList;
    }


    public void applyForce(ArrayList<SolarObject> planetList)
    {
        for (int i = 0; i < planetList.size(); i++)
        {
            planetList.get(i).setPosition(planetList.get(i).getPosition().add(planetList.get(i).getVelocity()));
        }
    }
    public void refreshProjection(ArrayList<SolarObject> planetList)
    {
        for(int i = 0; i < planetList.size(); i++)
        {
            planetList.get(i).clearProjectedPosition();
        }
    }










}
