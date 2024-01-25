package solarSim.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class RendHelp
{
    Engine engine = new Engine();
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
    public void drawProjections(SolarObject x, ShapeRenderer shapeRenderer)
    {
        ArrayList<Vector2> projections = engine.deepCopyVectorList(x.getProjectedPositions());
        shapeRenderer.begin(ShapeType.Line);
        //shapeRenderer.setColor(new Color(1f, 1f, 1f, 1));
        shapeRenderer.setColor(x.getColor());
        //System.out.println("Future Positions Drawn: " + projections.size());
        for(int i = 1; i < projections.size(); i++)
        {
            if(i % 6 == 1)
            {
                    shapeRenderer.line(projections.get(i-1), projections.get(i));
            }
        }
        shapeRenderer.end();
    }
    public void drawSelectedFeatures(SolarObject x, ShapeRenderer shapeRenderer)
    {
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(new Color(80f, 80f, 80f, 1));
        shapeRenderer.circle(x.getPosition().x, x.getPosition().y , x.getRadius()+900);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(x.getPosition(), x.getPosition().add(new Vector2(x.getVelocity().x, 0)));
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(x.getPosition(), x.getPosition().add(new Vector2(0, x.getVelocity().y)));
        shapeRenderer.end();
        //Create an arrayList, Fill it with all the positions in the path -> (Planet's position -> Planet's Velocity)
        //Break the line up using modulo like I did for the future positions
        //Draw the x and y lines, this should give the cool vectoring effect when a planet is selected
        //For planets to have the the same effect when selected, I need to draw a circle of vector2s then cull them in a similar fasion then draw them as arcLines (Future effect, post Beta)
    }

    public void drawPreviousPositions(SolarObject x, ShapeRenderer shapeRenderer)
    {
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(x.getColor());
        for(int i = 1; i < x.getPreviousPositions().size(); i++)
        {
            shapeRenderer.line(x.getPreviousPositions().get(i-1), x.getPreviousPositions().get(i));
        }
        shapeRenderer.end();
    }
    public void drawSolarSystem(ArrayList<SolarObject> x, ShapeRenderer shapeRenderer, boolean debug)
    {
        for(int i = 0; i < x.size(); i++)
        {
            drawPreviousPositions(x.get(i), shapeRenderer);
            drawProjections(x.get(i), shapeRenderer);
            drawPlanet(x.get(i), shapeRenderer, debug);
            if(x.get(i).isSelected)
            {
                drawSelectedFeatures(x.get(i), shapeRenderer);
            }
        }
    }





}
