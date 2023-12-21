package solarSim.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class Engine
{
    public static Vector2 gravity(SolarObject p1, SolarObject p2)
    {
        double G = .1f;
        double numerator = G * p1.getMass() * p2.getMass();
        //double rSquared = new Vector2(p2.getPosition().x - p1.getPosition().x, p2.getPosition().y - p1.getPosition().y).len();
        float rSquared = p2.getPosition().cpy().sub(p1.getPosition()).len();
        float gForce = (float)numerator/rSquared;
        Vector2 direction = p2.getPosition().cpy().sub(p1.getPosition()).nor();
        //float force = gForce * direction.x;
        float forceX = gForce * direction.x;
        float forceY = gForce * direction.y;
        System.out.println("Forces = " + forceX + " " + forceY);
        return new Vector2(forceX, forceY);
    }
    public static ArrayList<SolarObject> universalGravity(ArrayList<SolarObject> planetList, boolean isProjection)
    {
        Vector2 totalForce;
        for (int i = 0; i < planetList.size(); i++)
        {
            if(!planetList.get(i).getStable())
            {
                totalForce = new Vector2(0,0);
                for(int j = 0; j < planetList.size(); j++)
                {
                    if (i != j)
                    {
                        totalForce.add(gravity(planetList.get(i), planetList.get(j)));
                        System.out.println("Total Force applied to the " +planetList.get(i).getName()+ " " + totalForce);
                    }
                }
                planetList.get(i).setVelocity(planetList.get(i).getVelocity().add(totalForce));
                //planetList.get(i).setVelocity(planetList.get(i).getVelocity().add(totalForce));
                System.out.println(planetList.get(i).getName() + " " + planetList.get(i).getPosition());
                planetList.get(i).setPosition(planetList.get(i).getPosition().add(planetList.get(i).getVelocity()));
            }
        }
        return planetList;
    }
}
