package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class PauseMenu extends ScreenAdapter{
    
    private Stage stage;
    private SpriteBatch batch;;

    private ImageButton restartButton;
    private ImageButton resumeButton;
    private ImageButton mainMenubutton;


    private Texture backGroundTexture;
    private Texture restartTexture;
    private Texture resumeTexture;
    private Texture mainMenuTexture;

    private Main game;
    private GameScreen gameScreen;

    private Label.LabelStyle labelStyle;
    private Label titleLabel;
    private String titleString;
    private boolean isFinished;

    
    private Music bgMusic;
    private Sound btnhitSound;
    private Sound btnclickSound;

    public PauseMenu(Main game , GameScreen gamescreen , String title , boolean isFinished){
        this.game = game;
        this.gameScreen = gamescreen;
        this.titleString = title;
        this.isFinished = isFinished;
    }


    @Override
    public void show(){
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        loadAudio();
        //Used for handling Mouse Inputs and events
        Gdx.input.setInputProcessor(stage);
        initTextures();
        initButtons();
        
        bgMusic.play();
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.1f);
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

    public void initTextures(){
        restartTexture = new Texture("restartbtnTexture.png");
        resumeTexture = new Texture("resumebtnTexture.png");
        mainMenuTexture = new Texture("mainMenubtnTexture.png");
        backGroundTexture = new Texture("pauseMenuTexture.png");
        BitmapFont font = new BitmapFont();

        createLabelStyle();
        titleLabel = new Label(titleString, labelStyle);
        titleLabel.setSize(Gdx.graphics.getWidth(), 100);
        //titleLabel.setFontScale(3f);
        titleLabel.setPosition( 120, Gdx.graphics.getHeight() - 400);
        titleLabel.setAlignment(Align.top);
    }

    private void createLabelStyle() {
            FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(
                    Gdx.files.internal("OrbitronFont/Orbitron/static/Orbitron-Bold.ttf")
                );

            FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

            parameter.size = 36;
            parameter.borderColor.set(Color.WHITE);

            BitmapFont font = generator.generateFont(parameter);

            generator.dispose();

            labelStyle = new Label.LabelStyle();
            labelStyle.font = font;
            labelStyle.fontColor = Color.LIGHT_GRAY;
    }

    public void initButtons(){

        restartButton = createButton(restartTexture);
        resumeButton = createButton(resumeTexture);
        mainMenubutton = createButton(mainMenuTexture);

        restartButton.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 80f);  
        resumeButton.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        mainMenubutton.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 80f);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event , float x , float y){
                btnclickSound.play();
                gameScreen.dispose();
                game.setScreen(new GameScreen(game));
                dispose();
                bgMusic.stop();
            }
        });

            resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnclickSound.play();
                gameScreen.paused = false;
                game.setScreen(gameScreen);
                dispose();
                bgMusic.stop();
                gameScreen.bgMusic.play();
        }
            });

        mainMenubutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event , float x , float y){
                btnclickSound.play();
                gameScreen.dispose();
                game.setScreen(new MainMenu(game));
                dispose();
                bgMusic.stop();
            }
        });

        stage.addActor(restartButton);
        if(!isFinished)
        stage.addActor(resumeButton);
        stage.addActor(mainMenubutton);
        stage.addActor(titleLabel);
    }

    public ImageButton createButton(Texture tex){

    ImageButton button = new ImageButton(new TextureRegionDrawable(tex));
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
        public void exit(InputEvent event , float x , float y , int pointer , Actor fromActor){

            button.clearActions();
            button.addAction(Actions.scaleTo(1f, 1f, 0.15f));
        }
        });

        return button;

    }


    @Override
    public void render(float delta){

                // Dark scene
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.04f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(backGroundTexture , 0f ,0f ,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose(){

        batch.dispose();
        stage.dispose();
        mainMenuTexture.dispose();
        restartTexture.dispose();
        resumeTexture.dispose();
        backGroundTexture.dispose();
        bgMusic.dispose();
        btnhitSound.dispose();
        btnclickSound.dispose();
    }

}
