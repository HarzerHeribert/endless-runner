package com.mygdx.game.runner.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class IntroScreen extends GameScreen {
    TextureRegion intro;
    SpriteBatch batch;
    float time = 0;

    public IntroScreen (Game game) {
        super(game);
    }

    @Override
    public void show () {
        batch = new SpriteBatch();
        intro = new TextureRegion(new Texture(Gdx.files.internal("data/IntroScreen.png")), 0, 0, 300, 300);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 300, 300);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(intro, 0, 0);
        batch.end();

        time += delta;
        if (time > 1) {
            if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
                game.setScreen(new MainMenu(game));
            }
        }
    }

    @Override
    public void hide () {
        Gdx.app.debug("Endless Runner", "Dispose Intro");
        batch.dispose();
        intro.getTexture().dispose();
    }

}