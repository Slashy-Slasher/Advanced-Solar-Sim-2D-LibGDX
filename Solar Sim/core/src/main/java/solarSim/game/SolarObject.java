package solarSim.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class SolarObject
{
    private double mass;
    private Vector2 position;

    private Vector2 velocity;

    private float radius;
    private Color color;

    private String name;

    private boolean stable;

SolarObject(double massx, Vector2 positionx, Vector2 velocityx, float radiusx, Color colorx, String namex, boolean stablex)
    {
        this.mass = massx;
        this.position = positionx;
        this.velocity = velocityx;
        this.radius = radiusx;
        this.color = colorx;
        this.name = namex;
        this.stable = stablex;
    }

    public Color getColor() {
        return color;
    }

    public double getMass() {
        return mass;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
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
}
