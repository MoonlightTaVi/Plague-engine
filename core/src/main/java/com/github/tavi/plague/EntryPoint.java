package com.github.tavi.plague;

import com.badlogic.gdx.Game;
import com.github.tavi.plague.sciacallo.Membra;
import com.github.tavi.plague.shared.Plogger;
import com.github.tavi.plague.shared.components.Transform;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class EntryPoint extends Game {
	
	@Override
    public void create() {
        setScreen(new Scene());
        
        Membra components = Membra.get();
        Plogger log = Plogger.get();
        
        log.debug(this, "Started registering components...");
        for (int i = 0; i < 100_000; i++) {
        	components.register(i, Transform.class, new Transform());
        }
        
        log.debug(this, "Finished registering components.\nFirst check...");
        log.debug(this, "0's and 0's components are equal:", (components.get(0, Transform.class) == components.get(0, Transform.class)));
        log.debug(this, "0's and 1's components are equal:", (components.get(0, Transform.class) == components.get(1, Transform.class)));
        log.debug(this, "Getting components...");
        for (int i = 0; i < 100_000; i++) {
        	if (components.get(i, Transform.class) == null) {
        		log.debug(this, "Component missing at ID: ", i);
        	}
        }
        log.debug(this, "Finished!");
    }
}