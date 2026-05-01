package com.BowlORama;

import java.util.ArrayList;

abstract public class Player {

    String name;
    BowlingScore score;
    Scorecardgraphics scoreDisplay;
    protected int turn;
    protected int subturn;
    

    public Player(String name ){
        this.name = name;
    }

    abstract public void getscore();
    abstract public void setscore(int score);
    abstract public void displayscore();
    abstract public int getturn();
    abstract public int getsubturn();
    abstract public void setturn();
    abstract public void setsubturn();
    abstract void initscoreboard();
    abstract public void render(float dt);
    abstract public void update();
    
}

class Player1 extends Player{



    public Player1(String name , BowlingScore score , Scorecardgraphics scoreDisplay){
        super(name);
        this.score = score;
        this.scoreDisplay = scoreDisplay;
        initscoreboard();
    }

    @Override
    void initscoreboard(){
        scoreDisplay.createScoreboard();
    }
    @Override
    public void setscore(int score2){
        score.setscore(score2);
        displayscore();
    }

    @Override
    public void getscore(){
        int tempscore = -1;
        for(int i = turn-3 ; i < 10 ; i++){
            if(i < 0)
                i = 0;

            tempscore = score.getFrameScore(i);
            if(tempscore == -1)
                return;
        }
        
        
    }

    @Override 
    public void displayscore(){
        scoreDisplay.setScoreUI(turn, score.getsubturnscore(turn) , turn, String.valueOf(score.getFrameScore(turn)));
        
        scoreDisplay.setTotalScore(String.valueOf(score.getTotalScore()));
    }

    @Override
    public int getturn(){
        return turn;
    }

    @Override
    public int getsubturn(){
        return subturn;
    }

    @Override
    public void setturn(){
        turn = score.getturn();
    }

    @Override
    public void setsubturn(){
        subturn = score.getsubturn();
    }

    public void update(){
        setturn();
        setsubturn();
    }

    public void render(float dt){
        scoreDisplay.render(dt);
    }
}

class Computer extends Player{


    public Computer(BowlingScore score , Scorecardgraphics scoreDisplay){
        super("COMPUTER");
        this.score = score;
        this.scoreDisplay = scoreDisplay;
        initscoreboard();
    }

    @Override
    void initscoreboard(){
        scoreDisplay.createScoreboard();
    }
 
    @Override
    public void setscore(int score2){
        score.setscore(score2);
        displayscore();
    }

    @Override
    public void getscore(){
        int tempscore = -1;
        for(int i = turn-3 ; i < 10 ; i++){
            if(i < 0)
                i = 0;

            tempscore = score.getFrameScore(i);
            if(tempscore == -1)
                return;
        }
        
        
    }

    @Override 
    public void displayscore(){
        scoreDisplay.setScoreUI(turn, score.getsubturnscore(turn) ,turn, String.valueOf(score.getFrameScore(turn)));
        scoreDisplay.setTotalScore(String.valueOf(score.getTotalScore()));
    }

    @Override
    public int getturn(){
        return turn;
    }

    @Override
    public int getsubturn(){
        return subturn;
    }

    @Override
    public void setturn(){
       turn = score.getturn();
    }

    @Override
    public void setsubturn(){
        subturn = score.getsubturn();
    }

    public void update(){
        setturn();
        setsubturn();
    }

    public void render(float dt){
        scoreDisplay.render(dt);
    }
}