package com.mygdx.game.runner.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.runner.ParallaxLayer;

public class MainMenu extends GameScreen {
    SpriteBatch batch;
    Camera camera;
    float time = 0;

    TextureAtlas atlas;
    ParallaxLayer[] parallaxLayers;

    public MainMenu (Game game) {
        super(game);
        this.resize(720, 480);
    }

    @Override
    public void show () {
        batch = new SpriteBatch();

        // Viewport size the same as the background texture
        camera = new OrthographicCamera(1920, 1080);
        Gdx.graphics.setWindowedMode(720*2, 480*2);

        // Art assets from
        // https://opengameart.org/content/parallax-2d-backgrounds
        parallaxLayers = new ParallaxLayer[11];
        parallaxLayers[0] = new ParallaxLayer(new Texture("parrallaxEffect/11.png"), 0f, true, false);
        parallaxLayers[1] = new ParallaxLayer(new Texture("parrallaxEffect/10.png"), 0f, true, false);
        parallaxLayers[2] = new ParallaxLayer(new Texture("parrallaxEffect/09.png"), 0f, true, false);
        parallaxLayers[3] = new ParallaxLayer(new Texture("parrallaxEffect/08.png"), 0f, true, false);
        parallaxLayers[4] = new ParallaxLayer(new Texture("parrallaxEffect/07.png"), 0f, true, false);
        parallaxLayers[5] = new ParallaxLayer(new Texture("parrallaxEffect/06.png"), 0f, true, false);
        parallaxLayers[6] = new ParallaxLayer(new Texture("parrallaxEffect/05.png"), 0f, true, false);
        parallaxLayers[7] = new ParallaxLayer(new Texture("parrallaxEffect/04.png"), 0f, true, false);
        parallaxLayers[8] = new ParallaxLayer(new Texture("parrallaxEffect/03.png"), 0f, true, false);
        parallaxLayers[9] = new ParallaxLayer(new Texture("parrallaxEffect/02.png"), 0f, true, false);
        parallaxLayers[10] = new ParallaxLayer(new Texture("parrallaxEffect/01.png"),0f, true, false);

        // Could be part of the constructor but this is a bit more flexible (can create the parallax layers before
        // initialising the camera if needed)
        for (ParallaxLayer layer : parallaxLayers) {
            layer.setCamera(camera);
        }
    }

    @Override
    public void render (float delta) {
        time += delta;

        if (time > 0.1) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);;
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (ParallaxLayer layer : parallaxLayers) {
                layer.render(batch);
            }
            batch.end();

            time += delta;

            if (Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.justTouched()) {
                game.setScreen(new RunnerScreen(game));
            }
        }
    }


    @Override
    public void hide () {
        Gdx.app.debug("Endless Runner", "Dispose Menu");
        batch.dispose();
    }

}