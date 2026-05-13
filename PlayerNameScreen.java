package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PlayerNameScreen extends ScreenAdapter {

    private Main game;

    private SpriteBatch batch;

    private Texture background;
    private Texture textBoxTexture;

    private BitmapFont font;

    private String player1 = "";
    private String player2 = "";

    private int currentPlayer = 1;
    private float cursorTimer = 0f;

    public static String PLAYER1_NAME = "PLAYER 1";
    public static String PLAYER2_NAME = "PLAYER 2";

    public PlayerNameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();

        background = new Texture("background.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal( "OrbitronFont/Orbitron/static/Orbitron-Bold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 34;
        parameter.color = Color.WHITE;

        font = generator.generateFont(parameter);

        Pixmap pixmap = new Pixmap(500, 70, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();

        pixmap.setColor(Color.WHITE);
        pixmap.drawRectangle(0, 0, 500, 70);

        textBoxTexture = new Texture(pixmap);

        pixmap.dispose();

        generator.dispose();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cursorTimer += delta;

        handleInput();

        batch.begin();

        batch.draw(
            background,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        String currentText =
            currentPlayer == 1 ? player1 : player2;

        String title = currentPlayer == 1 ? "ENTER PLAYER 1 NAME:" : "ENTER PLAYER 2 NAME:";

        font.draw(batch, title, 320, 500);

        // DRAW TEXTBOX
        batch.draw(textBoxTexture, 320, 350);

        String displayText = currentText;

        if((int)(cursorTimer * 2) % 2 == 0)
            displayText += "_";

        font.draw(batch, displayText, 340, 395);

        batch.end();
    }
private void handleInput() {

    // LETTERS
    for(int i = Input.Keys.A; i <= Input.Keys.Z; i++) {

        if(Gdx.input.isKeyJustPressed(i)) {
            char c = (char)('A' + (i - Input.Keys.A));
            if(currentPlayer == 1)
                player1 += c;
            else
                player2 += c;
        }
    }

    // NUMBERS
    for(int i = Input.Keys.NUM_0; i <= Input.Keys.NUM_9; i++) {

        if(Gdx.input.isKeyJustPressed(i)) {

            char c = (char)('0' + (i - Input.Keys.NUM_0));

            if(currentPlayer == 1)
                player1 += c;
            else
                player2 += c;
        }
    }

    // SPACE
    if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

        if(currentPlayer == 1)
            player1 += " ";
        else
            player2 += " ";
    }

    // BACKSPACE
    if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {

        if(currentPlayer == 1 && player1.length() > 0) {

            player1 = player1.substring(0, player1.length() - 1);
        }

        else if(currentPlayer == 2 && player2.length() > 0) {

            player2 =
                player2.substring(0, player2.length() - 1);
        }
    }

    // ENTER
    if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

        if(currentPlayer == 1) {

            if(player1.trim().isEmpty())
                player1 = "PLAYER 1";

            PLAYER1_NAME = player1;

            currentPlayer = 2;
        }

        else {

            if(player2.trim().isEmpty()) player2 = "PLAYER 2";

            PLAYER2_NAME = player2;

            game.play();
        }
    }
}

    @Override
    public void dispose() {

        batch.dispose();
        background.dispose();
        font.dispose();
        textBoxTexture.dispose();
    }
}
