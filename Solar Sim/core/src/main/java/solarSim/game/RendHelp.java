package solarSim.game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.ArrayList;

public class RendHelp
{
    public void drawPlanet(SolarObject x, ShapeRenderer shapeRenderer, boolean debug)
    {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(x.getColor());
        shapeRenderer.circle(x.getPosition().x, x.getPosition().y , x.getRadius());
        shapeRenderer.end();
        if(debug)
        {
            System.out.println("---------------\n"+x.getName() + "    Radius = " + x.getRadius() + "\n       Position = " + x.getPosition().x);
        }
    }
    public void drawSolarSystem(ArrayList<SolarObject> x, ShapeRenderer shapeRenderer, boolean debug)
    {
        for(int i = 0; i < x.size(); i++)
        {
            drawPlanet(x.get(i), shapeRenderer, debug);
        }
    }



}
