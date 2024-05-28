package com.mygdx.game.runner.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.runner.ParallaxLayer;
import com.badlogic.gdx.utils.TimeUtils;

public class RunnerScreen extends GameScreen {

    boolean isPressed = false;
    long jumpTime = 0;
    Texture Player;
    SpriteBatch batch;
    Camera camera;

    Game game;

    ParallaxLayer[] layers;


    public RunnerScreen(Game game){
        super(game);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Viewport size the same as the background texture
        camera = new OrthographicCamera(1920, 1080);
        Gdx.graphics.setWindowedMode(720*2, 480*2);

        // Art assets from
        // https://opengameart.org/content/parallax-2d-backgrounds
        layers = new ParallaxLayer[11];
        layers[0] = new ParallaxLayer(new Texture("parrallaxEffect/11.png"), 0.1f, true, false);
        layers[1] = new ParallaxLayer(new Texture("parrallaxEffect/10.png"), 0.2f, true, false);
        layers[2] = new ParallaxLayer(new Texture("parrallaxEffect/09.png"), 0.3f, true, false);
        layers[3] = new ParallaxLayer(new Texture("parrallaxEffect/08.png"), 0.5f, true, false);
        layers[4] = new ParallaxLayer(new Texture("parrallaxEffect/07.png"), 1.2f, true, false);
        layers[5] = new ParallaxLayer(new Texture("parrallaxEffect/06.png"), 1.3f, true, false);
        layers[6] = new ParallaxLayer(new Texture("parrallaxEffect/05.png"), 1.7f, true, false);
        layers[7] = new ParallaxLayer(new Texture("parrallaxEffect/04.png"), 2.8f, true, false);
        layers[8] = new ParallaxLayer(new Texture("parrallaxEffect/03.png"), 3.5f, true, false);
        layers[9] = new ParallaxLayer(new Texture("parrallaxEffect/02.png"), 4.0f, true, false);
        layers[10] = new ParallaxLayer(new Texture("parrallaxEffect/01.png"), 20.0f, true, false);

        // Could be part of the constructor but this is a bit more flexible (can create the parallax layers before
        // initialising the camera if needed)
        for (ParallaxLayer layer : layers) {
            layer.setCamera(camera);
        }
        Player = new Texture(Gdx.files.internal("data/Background.png"));
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int speed = 50;
        //if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed * Gdx.graphics.getDeltaTime();

        //Camera Default Movement
        camera.position.x += speed * Gdx.graphics.getDeltaTime();
        //if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y -= speed * Gdx.graphics.getDeltaTime();
        //if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y += speed * Gdx.graphics.getDeltaTime();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (ParallaxLayer layer : layers) {
            layer.render(batch);
        }
        batch.end();

        System.out.println("time:" + jumpTime);

        if (!isPressed) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                isPressed = true;
                camera.position.y -=10;
                jumpTime = TimeUtils.nanoTime();
            }
        }
        else {
                if ((TimeUtils.nanoTime() - jumpTime) > 1000000000) {
                    System.out.println("Time:" + jumpTime);
                    camera.position.y +=10;
                    jumpTime = 0;
                    isPressed = false;
                }
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
    }
}
