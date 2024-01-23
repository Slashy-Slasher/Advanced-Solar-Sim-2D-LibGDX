package solarSim.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Array;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter
{
    private SpriteBatch batch;
    private Texture image;
    private Texture texture;
    private Sprite sprite;
    private int tick = 0;

    private int width = 800;
    private int height = 480;
    private ShapeRenderer shapeRenderer;

    private RendHelp rend = new RendHelp();
    private Engine engine = new Engine();
    private ArrayList<SolarObject> planetList = new ArrayList<SolarObject>();

    private Camera camera;
    private Viewport viewport;
    private ExtendViewport extendView; //main viewport
    private OrthographicCamera extendCam;

    private BitmapFont font;
    private MathUtils MathUtils;

    private playerController playerInput;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        texture = new Texture(Gdx.files.internal("124570.png"));

        sprite = new Sprite(texture, 0, 0, 800, 500);

        shapeRenderer = new ShapeRenderer();

        planetList.add(new SolarObject(15, new Vector2(12000,450), new Vector2(0, -250f), 3, new Color(1f, 1f, 1, 0), "ship" ,false));
        planetList.add(new SolarObject(1510*15, new Vector2(600,450), new Vector2(0, 0), 7000, new Color(1f, 1f, 0, 1), "sun" ,true));

        //planetList.add(new SolarObject(10, new Vector2(1200,450), new Vector2(0, -20f), 3, new Color(1f, 1f, 1, 0), "ship" ,false));
        //planetList.add(new SolarObject(100, new Vector2(0,450), new Vector2(0, 0), 70, new Color(1f, 0f, 0, 1), "planet" ,true));

        /*
        planetList.add(new SolarObject(15, new Vector2(9000600,450), new Vector2(0, -800f), 3, new Color(1f, 1f, 1, 0), "ship" ,false));
        planetList.add(new SolarObject(1*150, new Vector2(9000000,450), new Vector2(0, -800f), 300, new Color(1f, 0, 0, 1), "mars" ,false));
        planetList.add(new SolarObject(151025*15, new Vector2(600,450), new Vector2(0, 0), 7000, new Color(1f, 1f, 0, 1), "sun" ,true));
         */

        //engine.initializeSolarSystem(planetList, 1);
        //camera = new PerspectiveCamera();
        //viewport = new FitViewport(800, 480, camera);

        sprite.setPosition(planetList.get(0).getPosition().x-(512f/2), planetList.get(0).getPosition().y-(512f/2));
        sprite.setOrigin((512f/2), (512f/2));
        sprite.setScale(.05f);
        sprite.setRotation(planetList.get(0).degree);


        extendCam = new OrthographicCamera();
        extendView = new ExtendViewport(900,900, extendCam);

        playerInput = new playerController();


        extendCam.position.set(0, 0,0);

        extendCam.update();

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerInput.inputHandler(planetList);

        extendCam.position.set(planetList.get(0).getPosition().x, planetList.get(0).getPosition().y, 0);
        extendView.apply();
        camZoom(extendCam);
        extendCam.update();

        engine.mainEngine(planetList);

        sprite.setPosition(planetList.get(0).getPosition().x-(512f/2), planetList.get(0).getPosition().y-(512f/2));
        sprite.setRotation(-planetList.get(0).degree);

        batch.setProjectionMatrix(extendCam.combined);
        shapeRenderer.setProjectionMatrix(extendCam.combined);
        engine.willCollide(planetList.get(0), planetList.get(1));

        rend.drawSolarSystem(planetList, shapeRenderer, false); //Draws planets
        batch.begin();

        font.draw(batch, engine.degreeCorrection(planetList.get(0).degree)+ "\n", 200, 200);
        font.draw(batch, engine.findQuadrant(engine.degreeCorrection(planetList.get(0).degree))+ "\n", 200, 175);
        font.draw(batch, engine.simpleVectorMult(engine.findQuadrant(engine.degreeCorrection(planetList.get(0).degree)), engine.findAngle(engine.degreeCorrection(planetList.get(0).degree)))+ "\n", 200, 150);
        sprite.draw(batch);

        batch.end();

        engine.refreshProjection(planetList);
    }
    @Override
    public void resize(int width, int height) //gets called by the resize method in Main
    {
        extendView.update(width, height,true);
    }
    @Override
    public void dispose()
    {
        batch.dispose();
        image.dispose();
    }
    @Override
    public void resume() {
    }

    public void camZoom(OrthographicCamera cam) //if Z or X is pressed, zoom out, else set zoom back to normal
    {
        if(Gdx.input.isKeyPressed(Input.Keys.Z))
            cam.zoom = 10f;
        else if(Gdx.input.isKeyPressed(Input.Keys.X))
            cam.zoom = 100f;
        else if(Gdx.input.isKeyPressed(Input.Keys.C))
            cam.zoom = 1000f;
        else if(Gdx.input.isKeyPressed(Input.Keys.V))
            cam.zoom = 10000f;
        else
            cam.zoom = 3f;
    }

    @Override
    public void pause() {
    }
}
