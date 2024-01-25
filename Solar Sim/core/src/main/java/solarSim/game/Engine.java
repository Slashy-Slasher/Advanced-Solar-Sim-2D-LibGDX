package solarSim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class Engine
{
    public Vector2 gravity(SolarObject p1, SolarObject p2)
    {
        double G = .1f;
        double numerator = G * p1.getMass() * p2.getMass();
        float rSquared = p2.getPosition().cpy().sub(p1.getPosition()).len();
        float gForce = (float)numerator/rSquared;
        Vector2 direction = p2.getPosition().cpy().sub(p1.getPosition()).nor();
        float forceX = gForce * direction.x;
        float forceY = gForce * direction.y;
        return new Vector2(forceX, forceY);
    }
    //I'm looking for why
    public void universalGravity(ArrayList<SolarObject> xplanetList)
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
                        totalForce.add(gravity(xplanetList.get(i), xplanetList.get(j)));
                    }
                }
                xplanetList.get(i).setVelocity(xplanetList.get(i).getVelocity().add(totalForce.cpy()).cpy());
            }
        }
    }

    public float degreeCorrection(float degree)
    {
        degree = degree % 360;
        if(degree < 0)
        {
            degree += 360;
        }
        return degree;
    }

    public Vector2 findQuadrant(float degree)
    {
        degree = Math.abs(degree);
        if(degree >= 0.0 && degree < 90.0)
        {
            return new Vector2(1, 1);
        }
        else if(degree >= 90.0 && degree < 180.0)
        {
            return new Vector2(1, -1);
        }
        else if(degree >= 180.0 && degree < 270.0)
        {
            return new Vector2(-1, -1);
        }
        else
        {
            return new Vector2(-1, 1);
        }
    }

    public Vector2 findAngle (float degree)
    {
        Vector2 quad = findQuadrant(degree);
        float x = 0;
        if(quad.equals(new Vector2(-1,1))  || quad.equals(new Vector2(1,-1)))   //Top Left, Bottom Right
        {
            degree = degree % 90;
            x = 90-degree;
            return new Vector2(x*.01f, (90-x)*.01f);
        }
        else                                                                                //Bottom Left, Top Right
        {
            degree = degree % 90;
            x = 90-degree;
            return new Vector2((90-x)*.01f, x*.01f);
        }
    }

    public void mainEngine(ArrayList<SolarObject> planetList, OrthographicCamera cam)
    {
        universalGravity(planetList);
        applyForce(planetList);
        clickedSolarObject(planetList, cam);
        projectFuturePosition(planetList, 5000);
        positionRecorder(planetList);
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
        planetList.add(new SolarObject(100, new Vector2(600,450), new Vector2(0, 0), 30, new Color(1, 1, 1, 1), "sun" ,true));
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
            universalGravity(tempPlanetList);
            applyForce(tempPlanetList);
            for(int x = 0; x < tempPlanetList.size(); x++)
            {
                planetList.get(x).addProjectedPositions(tempPlanetList.get(x).getPosition().cpy());
            }
        }
    }
    public Vector2 getWorldPositionFromScreen(Vector3 v, OrthographicCamera cam)
    {
        Vector3 vc = cam.unproject(new Vector3(v.x,   v.y, v.z));
        return new Vector2(vc.x, vc.y);
    }
    public void clickedSolarObject(ArrayList<SolarObject> planetList, OrthographicCamera cam)
    {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            for(int i = 0; i < planetList.size(); i++)
            {
                //cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                Vector2 mousePos = new Vector2(getWorldPositionFromScreen(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), cam));
                //System.out.println(planetList.get(i).getName() +" "+ planetList.get(i).getPosition() + " Distance from " + new Vector2(mousePos) + ": " + planetList.get(i).getPosition().dst(mousePos));
                if(planetList.get(i).getRadius() > planetList.get(i).getPosition().dst(mousePos))
                {
                    System.out.println(planetList.get(i).getName() + " selected");
                    for(int x = 0; x < planetList.size(); x++)
                    {
                        planetList.get(x).isSelected = false;
                    }
                    planetList.get(i).isSelected = true;
                }
            }
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            for(int i = 0; i < planetList.size(); i++)
            {
                planetList.get(i).isSelected = false;
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

    public Vector2 simpleVectorMult(Vector2 vec1, Vector2 vec2)
    {
        return new Vector2(vec1.x*vec2.x,vec1.y*vec2.y);
    }
    public Vector2 simpleVectorMult(Vector2 vec1, Vector2 vec2, int num)
    {
        return new Vector2(vec1.x*vec2.x*num,vec1.y*vec2.y*num);
    }

    public Vector2 thrustVec(float degree, float thrust)
    {
        degree = degreeCorrection(degree);
        Vector2 vec1 = simpleVectorMult(findQuadrant(degree), findAngle(degree));
        Vector2 vec2 = simpleVectorMult(vec1, new Vector2(thrust, thrust));
        return new Vector2(vec2);
    }

    public void willCollide(SolarObject So1, SolarObject So2)
    {
        for (int i = 0; i < So1.getProjectedPositions().size(); i++)
        {
            if(So1.getProjectedPositions().get(i).dst(So2.getProjectedPositions().get(i)) < So1.getRadius() + So2.getRadius())
            {
                //System.out.println("Collision Imminent in " + i + " ticks");
                So1.clearProjectedPosition(i);
                //System.out.println("Post-Culled List Size(Supposed to be): " + So1.getProjectedPositions().size());
            }
        }
    }

    public void didCollide(SolarObject So1, SolarObject So2)    //Needs to intake an index for both planets, unless I add the index to the planet's class
    {
        if(So1.getMass() > So2.getMass())
        {
            So1.setMass(So1.getMass() + So2.getMass());
            So1.setVelocity(So1.getVelocity().add(So2.getVelocity()));  //Prone to error, too tired to tell
            //planetList.get("Index of So2").remove
        }
        else
        {
            So2.setMass(So1.getMass() + So2.getMass());
            So2.setVelocity(So1.getVelocity().add(So2.getVelocity()));  //Prone to error, too tired to tell
            //planetList.get("Index of So1").remove
        }
    }

    public ArrayList<SolarObject> addSolarObject(ArrayList<SolarObject> planetList, double massx, Vector2 positionx, Vector2 velocityx, float radiusx, Color colorx, String namex, boolean stablex)
    {
        planetList.add(new SolarObject(massx, positionx, velocityx, radiusx, colorx, namex, stablex));
        return planetList;
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
