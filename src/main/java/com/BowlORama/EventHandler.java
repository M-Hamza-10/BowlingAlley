package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class EventHandler {
    
    Ball ball;

    public EventHandler(Ball ball){
        this.ball = ball;
    }

    public void handleball(float delta){
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            ball.throwBall();
        }

        ball.update(delta);   // continuous movement here
    }
}
