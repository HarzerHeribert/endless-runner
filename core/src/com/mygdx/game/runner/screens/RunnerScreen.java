package com.mygdx.game.runner.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RunnerScreen extends GameScreen {

    SpriteBatch batch;


    public RunnerScreen (Game game) {
        super(game);
    }

    @Override
    public void show () {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 720*2, 480*2);
    }

    @Override
    public void render (float delta) {
    }

    @Override
    public void hide () {
        Gdx.app.debug("Endlesss Runner", "Dispose Runner Screen");
        batch.dispose();
    }
}
