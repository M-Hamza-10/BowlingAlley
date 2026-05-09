package com.BowlORama;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

    public class Scorecardgraphics {

        private Stage stage;

        private ArrayList<Label> topLabels = new ArrayList<>();
        private ArrayList<Label> bottomLabels = new ArrayList<>();

        private Label totalScore;
        private Label player;

        private Drawable frameBackground;
        private Drawable boardBackground;
        private Label.LabelStyle labelStyle;
        private Label.LabelStyle labelStyle2;
        private Label.LabelStyle labelStyle3;
        private int pos;

        private ImageButton menuButton;
        private Texture menubtntexture;

        private Main game;
        private GameScreen gameScreen;
        private String Playername;

        public Scorecardgraphics(int i , Main game , GameScreen gameScreen , String name){
            pos = i;
            this.game = game;
            this.gameScreen = gameScreen;
            Playername = name;
        }
        public void createScoreboard() {
            createStage();
            createDrawables();
            createLabelStyle();
            createLabelStyle2();
            createLabelStyle3();

            Table board = createBoard();
            Table playerboard = createPlayerBoard();
            Table menuboard = createmenuboard();
            addFrames(board);
            addPlayerHeader(playerboard);
            addbutton(menuboard);

            stage.addActor(board );
            stage.addActor(playerboard);
            stage.addActor(menuboard);
        }

        private void createStage() {
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);
        }

        private void createDrawables() {
            // Transparent black background for frames
            Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            //pix.setColor(0, 0, 0, 0.6f);
            pix.fill();

            Texture tex = new Texture(pix);
            pix.dispose();

            frameBackground = new TextureRegionDrawable(new TextureRegion(tex));

            // Main board image
            Texture boardTex = new Texture("scorecardUI3.png");
            boardBackground = new TextureRegionDrawable(boardTex);

            menubtntexture = new Texture("menubutton.png");
            createmenubutton();
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
            labelStyle.fontColor = Color.LIME;
        }
            private void createLabelStyle2(){
            FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(
                    Gdx.files.internal("OrbitronFont/Science_Gothic/static/ScienceGothic-Regular.ttf")
                );

            FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

            parameter.size = 24;
            parameter.borderColor.set(Color.WHITE);

            BitmapFont font = generator.generateFont(parameter);

            generator.dispose();

            labelStyle2 = new Label.LabelStyle();
            labelStyle2.font = font;
            labelStyle2.fontColor = Color.LIGHT_GRAY;
        }
        private void createLabelStyle3(){
            FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(
                    Gdx.files.internal("OrbitronFont/Science_Gothic/static/ScienceGothic-Regular.ttf")
                );

            FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

            parameter.size = 24;
            parameter.borderColor.set(Color.WHITE);

            BitmapFont font = generator.generateFont(parameter);

            generator.dispose();

            labelStyle3 = new Label.LabelStyle();
            labelStyle3.font = font;
            labelStyle3.fontColor = Color.LIME;
        }

        private Table createBoard() {
            Table board = new Table();

            
            board.top().padTop(5);
            
            board.setBackground(boardBackground);
            board.center();

            board.setSize(850f, 200f);
            if(pos == 1)
                board.setPosition(0, Gdx.graphics.getHeight() - board.getHeight());
            else if(pos == 2)
                board.setPosition(Gdx.graphics.getWidth() - board.getWidth(), Gdx.graphics.getHeight() - board.getHeight());
            return board;
        }

        private Table createPlayerBoard() {
            Table board = new Table();
            if(pos == 2){
            board.top().padTop(10);
            board.center();
            board.setSize(300f, 200f);
            board.setPosition(Gdx.graphics.getWidth() - board.getWidth(), Gdx.graphics.getHeight() - (1.5f * board.getHeight()) );
            }
            if(pos == 1){
            board.top().padTop(10);
            board.center();
            board.setSize(300f, 200f);
            board.setPosition(10f, Gdx.graphics.getHeight() - (1.5f * board.getHeight()) );
            }

            return board;
        }

        private Table createmenuboard(){
            Table board = new Table();
            //board.top().padBottom(10f);
            board.center();
            board.setSize(140f, 55f);
            board.setPosition(10f,0f);

            return board;
        }

        private void addPlayerHeader(Table board) {
            player = new Label("PLAYER: " + Playername, labelStyle);
            if(pos == 1)
                player.getStyle().fontColor = Color.LIGHT_GRAY;
            else
                player.getStyle().fontColor = Color.LIME;
            board.row();
            board.add(player).colspan(10).padTop(10);
            board.row();
            
        }
        

        private void addFrames(Table board) {
            Table framesRow = new Table();

            for (int i = 0; i < 10; i++) {
                framesRow.add(createFrame(i)).pad(3);
            }

            framesRow.add(createTotalFrame()).pad(2);

            board.add(framesRow);
        }

        private void addbutton(Table board){
            board.add(menuButton).width(200).height(100);
        }


        private Table createFrame(int index) {

            Label top;
            Label bottom;
            if(pos == 1){
            top = createCenteredLabel2("");
            bottom = createCenteredLabel2("");
            }
            else{
            top = createCenteredLabel3("");
            bottom = createCenteredLabel3(""); 
            }

            topLabels.add(top);
            bottomLabels.add(bottom);

            Table frame = new Table();

            int width = (index == 9) ? 80 : 60;

            frame.add(top).width(width).height(25).colspan(10).padBottom(30);
            frame.row();
            frame.row();
            frame.add(bottom).width(width).height(35).colspan(10).padBottom(10);

            frame.setBackground(frameBackground);

            return frame;
        }
        public void setPlayerLabel(String text){
            player.setText(text);
        }
        private Table createTotalFrame() {
            totalScore = createCenteredLabel("");

            Table frame = new Table();

            frame.add(totalScore).width(80).height(80);
            frame.setBackground(frameBackground);

            return frame;
        }
        private Label createCenteredLabel(String text) {
            Label label = new Label(text, labelStyle);
            label.setAlignment(Align.center);
            return label;
        }
        private Label createCenteredLabel2(String text) {
            Label label = new Label(text, labelStyle2);
            label.setAlignment(Align.center);
            return label;
        }
        private Label createCenteredLabel3(String text) {
            Label label = new Label(text, labelStyle3);
            label.setAlignment(Align.center);
            return label;
        }


        public void setScoreUI(int top, String score1, int bottom, String score2) {
            if(score1 == null || score1.equals("-1"))
                score1 = "";
            if(score2 == null || score2.equals("-1"))
                score2 = "";

            topLabels.get(top).setText(score1);
            bottomLabels.get(bottom).setText(score2);

        }

        public void setpreviousUI(int[] scorecard){

            int totalscore = 0;
            for(int i = 0 ; i < 10 ; i++){
                if(scorecard[i] != -1){
                    totalscore += scorecard[i];
                    bottomLabels.get(i).setText(totalscore);
                }
            }
        }

        public void setTotalScore(String score) {
            totalScore.setText(score);
        }

    public void createmenubutton(){

        menuButton = new ImageButton(new TextureRegionDrawable(menubtntexture));
        menuButton.setSize(140f, 55f);

        // REQUIRED for scaling visuals
        menuButton.setTransform(true);

        // Scale from center
        menuButton.setOrigin(menuButton.getWidth() / 2f, menuButton.getHeight() / 2f);

        menuButton.addListener(new ClickListener() {

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

            menuButton.clearActions();

            menuButton.addAction(
                Actions.scaleTo(1.12f, 1.12f, 0.15f , Interpolation.swingOut)
            );

        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

            menuButton.clearActions();

            menuButton.addAction(
                Actions.scaleTo(1f, 1f, 0.15f)
            );
        }

        @Override
        public void clicked(InputEvent event , float x , float y){
            gameScreen.paused = true;
            game.setScreen(new PauseMenu(game, gameScreen , "PAUSED" , false));
        }
        });

    }


        public void render(float delta) {
            Gdx.input.setInputProcessor(stage);
            stage.act(delta);
            stage.draw();
        }

        public void dispose() {
            stage.dispose();
        }

        public Stage getStage() {
            return stage;
        }
    }