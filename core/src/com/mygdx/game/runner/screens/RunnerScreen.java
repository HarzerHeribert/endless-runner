package com.mygdx.game.runner.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.runner.ParallaxLayer;
import com.badlogic.gdx.utils.TimeUtils;

public class RunnerScreen extends GameScreen {

    boolean isFalling = false;
    boolean isJumping = false;
    private long jumpStartTime = 0;
    private long fallStartTime = 0;
    private float startY;
    private float targetY;
    private float jumpDuration = 1.0f; // Dauer der Sprungbewegung in Sekunden
    private float fallDuration = 1.0f; // Dauer der Fallbewegung in Sekunden
    private float elapsedTime = 0;

    boolean isPressed = false;
    long jumpTime = 0;

    float time = 0;
    Texture Player;
    SpriteBatch batch;
    Camera camera;
    private static final float FRAME_TIME_RUN = 1/8f;
    private Animation<TextureRegion> run;

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
        Gdx.graphics.setWindowedMode(1920, 1080);

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

        TextureAtlas runRegions = new TextureAtlas(Gdx.files.internal("adventurer/run/run.atlas"));
        run = new Animation<>(FRAME_TIME_RUN, runRegions.findRegions("run"));
        run.setFrameDuration(FRAME_TIME_RUN);

        layers[10] = new ParallaxLayer(new Texture("parrallaxEffect/01.png"), 20.0f, true, false);

        // Could be part of the constructor but this is a bit more flexible (can create the parallax layers before
        // initialising the camera if needed)
        for (ParallaxLayer layer : layers) {
            layer.setCamera(camera);
        }
        Player = new Texture(Gdx.files.internal("data/Background.png"));
    }

    @Override
    public void render(float delta) {
        time += delta;

        if (time > 0.1) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            TextureRegion currentFrame = run.getKeyFrame(time, true);

            int speed = 25;
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

            batch.draw(currentFrame, camera.position.x -650, -200, currentFrame.getRegionWidth() * 4, currentFrame.getRegionHeight() * 4);

            batch.end();

            if (!isJumping && !isFalling) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    isJumping = true;
                    startY = camera.position.y;
                    targetY = startY - 10;
                    jumpStartTime = TimeUtils.nanoTime();
                    elapsedTime = 0;
                }
            } else if (isJumping) {
                elapsedTime += Gdx.graphics.getDeltaTime();
                float progress = Math.min(elapsedTime / jumpDuration, 1) * 2; //Sprunggeschwindigkeit hier anpassen
                camera.position.y = startY + (targetY - startY) * progress;
                if (progress >= 1) {
                    isJumping = false;
                    isFalling = true;
                    startY = targetY;
                    targetY = startY + 10;
                    elapsedTime = 0;
                    fallStartTime = TimeUtils.nanoTime();
                }
            } else if (isFalling) {
                elapsedTime += Gdx.graphics.getDeltaTime();
                float progress = Math.min(elapsedTime / fallDuration, 1) * 2; //Fallgeschwindigkeit hier anpassen
                camera.position.y = startY + (targetY - startY) * progress;
                if (progress >= 1) {
                    isFalling = false;
                    camera.position.y = targetY;
                }
            }
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
    }
}
