package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

public class HighScoreScreen extends ScreenAdapter {

    private Main game;

    private SpriteBatch batch;

    private Texture background;

    private BitmapFont titleFont;
    private BitmapFont scoreFont;

    public HighScoreScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();

        background = new Texture("pauseMenuTexture.png");

        createFonts();
    }

    private void createFonts() {

        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                Gdx.files.internal(
                    "OrbitronFont/Orbitron/static/Orbitron-Bold.ttf"
                )
            );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 42;
        parameter.color = Color.BLACK;

        titleFont = generator.generateFont(parameter);

        parameter.size = 28;

        scoreFont = generator.generateFont(parameter);

        generator.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(
            background,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        titleFont.draw(batch,
            "HIGH SCORES",
            350,
            650
        );

        ArrayList<Highscoremanager.ScoreEntry> scores =
            Highscoremanager.getTop10Scores();

        int y = 560;

        for(int i = 0; i < scores.size(); i++) {

            Highscoremanager.ScoreEntry s = scores.get(i);

            scoreFont.draw(
                batch,
                (i+1) + ". " + s.name + "       -   " + s.score,
                400,
                y
            );

            y -= 45;
        }

        scoreFont.draw(
            batch,
            "Press ESC to return",
            420,
            80
        );

        batch.end();

        if(Gdx.input.isKeyJustPressed(
            com.badlogic.gdx.Input.Keys.ESCAPE
        )) {
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void dispose() {

        batch.dispose();
        background.dispose();
        titleFont.dispose();
        scoreFont.dispose();
    }
}