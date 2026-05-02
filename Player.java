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
