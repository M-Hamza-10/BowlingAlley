
package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BowlingAnimations {

    private SpriteBatch batch;

    // STRIKE
    private Animation<TextureRegion> strikeAnimation;
    private Texture strikeSheet;
    private boolean showStrike = false;
    private float strikeStateTime = 0f;

    // SPARE
    private Animation<TextureRegion> spareAnimation;
    private Texture spareSheet;
    private boolean showSpare = false;
    private float spareStateTime = 0f;

    // WINNER
    private Animation<TextureRegion> winnerAnimation;
    private Texture winnerSheet;
    private boolean showWinner = false;
    private float winnerStateTime = 0f;

    public BowlingAnimations() {

        batch = new SpriteBatch();

        loadStrikeAnimation();
        loadSpareAnimation();
        loadWinnerAnimation();
    }

    private void loadStrikeAnimation() {

        strikeSheet = new Texture("animations/strike_sheet.png");

        TextureRegion[][] temp = TextureRegion.split(
            strikeSheet,
            strikeSheet.getWidth() / 6,
            strikeSheet.getHeight()
        );

        TextureRegion[] frames = new TextureRegion[6];

        for(int i = 0; i < 6; i++) {
            frames[i] = temp[0][i];
        }

        strikeAnimation = new Animation<>(0.1f, frames);
    }

    private void loadSpareAnimation() {

        spareSheet = new Texture("animations/spare_sheet.png");

        TextureRegion[][] temp = TextureRegion.split(
            spareSheet,
            spareSheet.getWidth() / 6,
            spareSheet.getHeight()
        );

        TextureRegion[] frames = new TextureRegion[6];

        for(int i = 0; i < 6; i++) {
            frames[i] = temp[0][i];
        }

        spareAnimation = new Animation<>(0.1f, frames);
    }

    private void loadWinnerAnimation() {

        winnerSheet = new Texture("animations/winner_sheet.png");

        TextureRegion[][] temp = TextureRegion.split(
            winnerSheet,
            winnerSheet.getWidth() / 6,
            winnerSheet.getHeight()
        );

        TextureRegion[] frames = new TextureRegion[6];

        for(int i = 0; i < 6; i++) {
            frames[i] = temp[0][i];
        }

        winnerAnimation = new Animation<>(0.1f, frames);
    }


    public void playStrike() {

        showStrike = true;
        strikeStateTime = 0f;
    }

    public void playSpare() {

        showSpare = true;
        spareStateTime = 0f;
    }

    public void playWinner() {

        showWinner = true;
        winnerStateTime = 0f;
    }


    public void render(float delta) {

        batch.begin();

        renderStrike(delta);

        renderSpare(delta);

        renderWinner(delta);

        batch.end();
    }


    private void renderStrike(float delta) {

        if(!showStrike)
            return;

        strikeStateTime += delta;

        TextureRegion currentFrame =
            strikeAnimation.getKeyFrame(strikeStateTime);

        batch.draw(
            currentFrame,
            Gdx.graphics.getWidth()/2f - 350,
            Gdx.graphics.getHeight()/2f - 150,
            700,
            300
        );

        if(strikeAnimation.isAnimationFinished(strikeStateTime)) {
            showStrike = false;
        }
    }

    private void renderSpare(float delta) {

        if(!showSpare)
            return;

        spareStateTime += delta;

        TextureRegion currentFrame =
            spareAnimation.getKeyFrame(spareStateTime);

        batch.draw(
            currentFrame,
            Gdx.graphics.getWidth()/2f - 350,
            Gdx.graphics.getHeight()/2f - 150,
            700,
            300
        );

        if(spareAnimation.isAnimationFinished(spareStateTime)) {
            showSpare = false;
        }
    }

    private void renderWinner(float delta) {

        if(!showWinner)
            return;

        winnerStateTime += delta;

        TextureRegion currentFrame =
            winnerAnimation.getKeyFrame(winnerStateTime);

        batch.draw(
            currentFrame,
            Gdx.graphics.getWidth()/2f - 400,
            Gdx.graphics.getHeight()/2f - 180,
            800,
            360
        );

        if(winnerAnimation.isAnimationFinished(winnerStateTime)) {
            showWinner = false;
        }
    }

    public void dispose() {

        batch.dispose();

        strikeSheet.dispose();
        spareSheet.dispose();
        winnerSheet.dispose();
    }
}


