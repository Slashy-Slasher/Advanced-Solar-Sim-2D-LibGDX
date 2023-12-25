package solarSim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    public ArrayList<SolarObject> universalGravity(ArrayList<SolarObject> planetList, boolean isProjection)
    {
        Vector2 totalForce;
        for (int i = 0; i < planetList.size(); i++)
        {
            if(!planetList.get(i).getStable())
            {
                totalForce = new Vector2();
                for(int j = 0; j < planetList.size(); j++)
                {
                    if (i != j)
                    {
                        totalForce.add(gravity(planetList.get(i), planetList.get(j), isProjection));
                    }
                }
                planetList.get(i).setVelocity(planetList.get(i).getVelocity().add(totalForce.cpy()));
            }
        }
        return planetList;
    }

    public void initializeSolarSystem(ArrayList<SolarObject> planetList)
    {
        planetList.add(new SolarObject(100, new Vector2(600,450), new Vector2(0, 0), 30, new Color(.7f, .3f, 0, 1), "sun" ,true));
        for (int i = 0; i < Math.random()*5;i++)
        {
            planetList.add(new SolarObject(15*Math.random(), new Vector2((float)(600*Math.random()+600),(float)(450)), new Vector2(0, (float)(-1-5*Math.random())), (float)(3*Math.random()+3), new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1), "Planet: "+i ,false));
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

    /*
    public void projectionController(ArrayList<SolarObject> xList, ArrayList<SolarObject> xProjectList, int ticks)
    {
        xProjectList = (ArrayList<SolarObject>) xList.clone();
        for(int t = ticks; t < xList.size(); t++)
        {
            projectFuturePosition(xList, xProjectList);
            applyForce(xProjectList);
            System.out.println("Future position of planet: " + xProjectList.get(0).getPosition());
        }
    }
    public void projectFuturePosition(ArrayList<SolarObject> planetList, ArrayList<SolarObject> xProjectList)
    {
        Vector2 totalForce;
        for(int i = 0; i < xProjectList.size(); i++)
        {
            if(!xProjectList.get(i).getStable())
            {
                totalForce = new Vector2();
                for (int j = 0; j < xProjectList.size(); j++)
                {
                    if (i != j)
                    {
                        totalForce.add(gravity(xProjectList.get(i), xProjectList.get(j), false));
                    }
                }
                xProjectList.get(i).setVelocity(xProjectList.get(i).getVelocity().add(totalForce.cpy()));
            }
            planetList.get(i).addProjectedPositions(xProjectList.get(i).getPosition());
        }
    }

     */
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
