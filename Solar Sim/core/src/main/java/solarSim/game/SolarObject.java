package solarSim.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class SolarObject
{
    private double mass;
    private Vector2 position;

    private Vector2 velocity;

    private float radius;
    private Color color;

    private String name;

    private boolean stable;

    private ArrayList<Vector2> previousPositions = new ArrayList<Vector2>();
    private ArrayList<Vector2> projectedPositions = new ArrayList<Vector2>();



SolarObject(double massx, Vector2 positionx, Vector2 velocityx, float radiusx, Color colorx, String namex, boolean stablex)
    {
        this.mass = massx;
        this.position = positionx;
        this.velocity = velocityx;
        this.radius = radiusx;
        this.color = colorx;
        this.name = namex;
        this.stable = stablex;
        previousPositions.add(positionx.cpy());
        projectedPositions.add(positionx.cpy());
    }

    public Color getColor() {
        return color.cpy();
    }

    public double getMass() {
        return mass;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }
    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position.cpy();
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity.cpy();
    }

    public float getRadius() {
        return radius;
    }
    public boolean getStable()
    {
        return stable;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Vector2> getPreviousPositions() {
        //return previousPositions;
        return new ArrayList<Vector2>(previousPositions);
    }

    public ArrayList<Vector2> getProjectedPositions() {
        return new ArrayList<Vector2>(projectedPositions);
    }
    public void addProjectedPositions(Vector2 newPosition)
    {
        projectedPositions.add(newPosition.cpy());
    }
    public void clearProjectedPosition()
    {
        projectedPositions.clear();
        projectedPositions = new ArrayList<Vector2>();
        projectedPositions.add(getPosition());
        //System.out.println("Projection Cleared; current Length " + getProjectedPositions().size());
    }
    public void addPreviousPositions(Vector2 newPosition)
    {
        previousPositions.add(newPosition.cpy());
    }
    public void removeFirstPreviousPosition()
    {
        this.previousPositions.remove(0);
    }
    public void clearPreviousPosition()
    {
        previousPositions.clear();
        previousPositions = new ArrayList<Vector2>();
        previousPositions.add(getPosition());

    }

}
