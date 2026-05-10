
package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MainMenu extends ScreenAdapter {

    private Stage stage;
    private SpriteBatch batch;

    // Images
    private Texture background;
    private Texture logo;

    private Texture playTex;
    private Texture scoreTex;
    private Texture settingsTex;
    private Texture quitTex;
    private Main game;

    private Music bgMusic;

    private Sound btnhitSound;
    private Sound btnclickSound;

    // Glow animation
    private float glowTime = 0f;

    public MainMenu(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        loadAudio();

        //Used for handling Mouse Inputs and events
        Gdx.input.setInputProcessor(stage);

        // Load textures
        background = new Texture("background.png");
        logo = new Texture("logo.png");

        playTex = new Texture("btn_play.png");
        scoreTex = new Texture("btn_scores.png");
        settingsTex = new Texture("btn_settings.png");
        quitTex = new Texture("btn_quit.png");

        // Create buttons
        ImageButton playBtn = createButton(playTex, 0);
        ImageButton scoreBtn = createButton(scoreTex, -90);
        ImageButton settingsBtn = createButton(settingsTex, -180);
        ImageButton quitBtn = createButton(quitTex, -270);

        // Button positions
        playBtn.setPosition(490, 360);
        scoreBtn.setPosition(490, 280);
        settingsBtn.setPosition(490, 200);
        quitBtn.setPosition(490, 120);

        // Click actions
        playBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.play();
                btnclickSound.play();
                bgMusic.stop();
            }
        });


        scoreBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Scores");
                btnclickSound.play();
            }
        });

        settingsBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Settings");
                btnclickSound.play();
            }
        });

        quitBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                btnclickSound.play();
            }
        });

        bgMusic.setLooping(true);
        bgMusic.setVolume(0.1f);
        bgMusic.play();

        stage.addActor(playBtn);
        stage.addActor(scoreBtn);
        stage.addActor(settingsBtn);
        stage.addActor(quitBtn);
    }

    public void loadAudio(){
        bgMusic = Gdx.audio.newMusic(
        Gdx.files.internal("sound/bgMenumusic.mp3")
    );

    btnhitSound = Gdx.audio.newSound(
        Gdx.files.internal("sound/select.wav")
    );

    btnclickSound = Gdx.audio.newSound(
        Gdx.files.internal("sound/menu-click.wav")
    );
    }
private ImageButton createButton(Texture tex, float delay) {

    ImageButton button = new ImageButton(
        new TextureRegionDrawable(tex)
    );

    button.setSize(300, 65);

    // REQUIRED for scaling visuals
    button.setTransform(true);

    // Scale from center
    button.setOrigin(button.getWidth() / 2f, button.getHeight() / 2f);

    button.addListener(new ClickListener() {

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

            button.clearActions();

            button.addAction(
                Actions.scaleTo(1.12f, 1.12f, 0.15f , Interpolation.swingOut)
            );
            btnhitSound.play();
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

            button.clearActions();

            button.addAction(
                Actions.scaleTo(1f, 1f, 0.15f)
            );
        }
    });
    return button;
}

    @Override
    public void render(float delta) {

        glowTime += delta;

        // Dark scene
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.04f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Background
        batch.draw(background, 0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());

        // Animated neon logo glow
        float glow = 0.8f + MathUtils.sin(glowTime * 3f) * 0.2f;

        batch.setColor(glow, glow, glow, 1);

        batch.draw(logo, 330, 500, 620, 200);

        batch.setColor(Color.WHITE);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

        batch.dispose();
        stage.dispose();

        background.dispose();
        logo.dispose();

        playTex.dispose();
        scoreTex.dispose();
        settingsTex.dispose();
        quitTex.dispose();
        bgMusic.dispose();
        btnhitSound.dispose();
        btnclickSound.dispose();
    }
}