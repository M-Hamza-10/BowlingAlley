package com.BowlORama;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.Game;
import com.BowlORama.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    

    @Override
    public void create() {
        setScreen(new MainMenu(this));
        
    }

    public void play(){
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        getScreen().dispose();
    }
}
