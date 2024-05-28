package com.mygdx.game.runner;

import com.badlogic.gdx.Game;
import com.mygdx.game.runner.screens.IntroScreen;

public class ScreenLauncher extends Game{
    @Override
    public void create () {
        setScreen(new IntroScreen(this));
    }
}
