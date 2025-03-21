package com.github.tavi.plague;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class EntryPoint extends Game {
	
	@Override
    public void create() {
        setScreen(new Scene());
    }
}