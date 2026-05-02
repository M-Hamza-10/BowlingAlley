package com.BowlORama;

public class Player2 extends Player{


    public Player2(BowlingScore score , Scorecardgraphics scoreDisplay){
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

        //   int currentTurn = score.getturn();
        // scoreDisplay.setScoreUI(currentTurn, score.getsubturnscore(currentTurn) ,currentTurn, String.valueOf(score.getFrameScore(currentTurn)));
        // scoreDisplay.setTotalScore(String.valueOf(score.getTotalScore()));

int scoredisplayturn = turn;   

        if(scoredisplayturn > 9)
            scoredisplayturn = 9;
        scoreDisplay.setScoreUI(scoredisplayturn, score.getsubturnscore(scoredisplayturn) , scoredisplayturn, String.valueOf(score.getFrameScore(scoredisplayturn)));
        scoreDisplay.setTotalScore(String.valueOf(score.getTotalScore()));
        scoreDisplay.setpreviousUI(score.getScoreArray());
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
