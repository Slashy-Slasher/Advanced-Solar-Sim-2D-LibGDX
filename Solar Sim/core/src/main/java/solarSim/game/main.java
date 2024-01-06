package solarSim.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Array;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter
{
    private SpriteBatch batch;
    private Texture image;
    private int tick = 0;

    private int width = 800;
    private int height = 480;
    private ShapeRenderer shapeRenderer;

    private RendHelp rend = new RendHelp();
    private Engine engine = new Engine();
    private ArrayList<SolarObject> planetList = new ArrayList<SolarObject>();
    private Viewport viewport;
    private Camera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        shapeRenderer = new ShapeRenderer();
        //planetList.add(new SolarObject(1, new Vector2(900,450), new Vector2(0, -2f), 3, new Color(.5f, .5f, 0, 1), "mars" ,false));
        //planetList.add(new SolarObject(100, new Vector2(600,450), new Vector2(0, 0), 30, new Color(.7f, .3f, 0, 1), "sun" ,true));
        engine.initializeSolarSystem(planetList);
        camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);
    }

    @Override
    public void render()
    {
        tick++;
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            // your actions
            viewport.update(width, height);
        }
        engine.universalGravity(planetList, false);
        engine.applyForce(planetList);
        engine.projectFuturePosition(planetList, 500);
        engine.positionRecorder(planetList);

        System.out.println(tick + ": Current " +planetList.get(0).getName() +" Position: " + planetList.get(0).getPosition() + " Projected Position next Position for planet: " + planetList.get(0).getProjectedPositions());
        //System.out.println(tick + ": Equal?: " + planetList.get(0).getPosition().equals(planetList.get(0).getProjectedPositions().get(1)));

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        rend.drawSolarSystem(planetList, shapeRenderer, false);
        engine.fpsToConsole();
        engine.refreshProjection(planetList);
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        image.dispose();
    }
}
