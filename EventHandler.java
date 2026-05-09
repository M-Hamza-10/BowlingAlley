package com.BowlORama;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.collision.Collision;

public class EventHandler {
    
    Ball ball;
    CollisionPhysics collisionDetector;
    PerspectiveCamera camera;
    Vector3 pos;
    Map map;
    ArrayList<Pins> pin;
    ArrayList<Vector3> pininitposition;

    public boolean  turnended = false;
    private boolean spidercamview = false;
    private boolean thrown = false;
    private boolean blocked = false;
    private boolean timerstart = false;
    private boolean inputblocked = false;
    private float timer = 0f;
    final int max_no_of_turns = 2;
    private int no_of_turns = 0;
    private final float waittimer = 7f;
    

    public EventHandler(Ball ball , CollisionPhysics collsiondetector , PerspectiveCamera camera , Map map , ArrayList<Vector3> pininitposition , ArrayList<Pins> pin){
        this.ball = ball;
        this.collisionDetector = collsiondetector;
        this.camera = camera;
        pos = new Vector3();
        this.map = map;
        this.pininitposition = pininitposition;
        this.pin = pin;
    }

    public void handleball(float delta){
        
        handlemousePhysics(5f);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !inputblocked){
            inputblocked = true;
           collisionDetector.shootBall(new Vector3(0f,0f, 150f));
           thrown = true;
            collisionDetector.getBallBody().getWorldTransform().getTranslation(pos);  
        }
        handlecamera(delta);
        // ball.update(delta);   // continuous movement here

        if(timerstart){
            timer+=delta;
        }
        if(timer > waittimer){
            turnended = true;
            reset(delta);
        }

    }

    private void handlecamera(float delta){
        if (thrown) {
            Vector3 pos2 = new Vector3();
            collisionDetector.getBallBody()
                .getWorldTransform()
                .getTranslation(pos2);

            camera.position.z = pos2.z - 6f;   // behind ball
            //camera.position.x = pos2.x;        // follow left/right
            if(Gdx.input.isKeyJustPressed(Input.Keys.V) && !spidercamview){
                camera.position.y = pos2.y + 5f;   // above ball
                spidercamview = true;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.V) && spidercamview){
                camera.position.y = pos2.y + 2f;
                spidercamview = false;
            }

            camera.lookAt(pos2.x, pos2.y, pos2.z + 5f);
            camera.update();

            if (camera.position.z > (70f / 2f) - 5f){
                thrown = false;
                timerstart = true;
            }
        }
    }

    private void handlemousePhysics(float speed){
        Ray ray = camera.getPickRay(Gdx.input.getX(), Gdx.input.getY());

        Vector3 mousepos = new Vector3();
        collisionDetector.getBallBody().getWorldTransform().getTranslation(mousepos);

        mousepos.x += ray.origin.x/2f;

  
        if(!thrown && (mousepos.x > 7f/2 - 1.5f)){
            mousepos.x = 7f/2 - 1.5f;
        }
        else if(!thrown && (mousepos.x < -7f/2 + 1.5f)){
            mousepos.x  = -7f/2 + 1.5f;

        }
        else if(thrown && (mousepos.x > 7f/2) || (mousepos.x < -7f/2)){
            blocked = true;
        }
        


        if(!blocked){
            // Move the rigid body to that position
        btRigidBody body = collisionDetector.getBallBody();
        Matrix4 transform = body.getWorldTransform();
        transform.setTranslation(mousepos);
        body.setWorldTransform(transform);

        }
    }



    public void reset(float dt){

        //---------------RESET BALL-------------------------
        ball.thrown = false;
        thrown = false;
         btRigidBody ballbody = collisionDetector.getBallBody();
          Vector3 initball = ball.getballcoordinates();
        Matrix4 transform = new Matrix4();
        //Matrix4 transform = ballbody.getWorldTransform();
        //transform.setTranslation(initball);
        transform.set(initball , ball.getballinitroattion());
        ballbody.setWorldTransform(transform);
        ballbody.proceedToTransform(transform);
        ballbody.clearForces();
        ballbody.setAngularVelocity(new Vector3(0,0,0));
        ballbody.setLinearVelocity(new Vector3(0,0,0));
        ballbody.activate();
        //-----------CAMERA-------------------
         Vector3 pos2 = new Vector3();
        collisionDetector.getBallBody().getWorldTransform().getTranslation(pos2);
        timer = 0f;
        timerstart = false;
        camera.position.x = 0f;
        camera.position.y = (pos2.y + 2f);
        camera.position.z = pos2.z - 6f;
        camera.lookAt(0f, 0f, 0f);
        spidercamview = false;
        camera.update();
        inputblocked = false;
        blocked = false;
        no_of_turns++;
        if(no_of_turns >= max_no_of_turns){
        no_of_turns = 0;
         //----------------------RESET PINS------------------
        // resetpins();
        }
    }
public void resetpins() {

    for (int i = 0; i < pininitposition.size(); i++) {

        btRigidBody body = collisionDetector.getPinBodies().get(i);
        //body.setActivationState(Collision.ACTIVE_TAG);
        Matrix4 transform = new Matrix4();

        transform.set(
            pininitposition.get(i),
            pin.get(i).getpinrotation()   // original upright quaternion
        );

        body.setLinearVelocity(Vector3.Zero);
        body.setAngularVelocity(Vector3.Zero);
        body.clearForces();

        body.setWorldTransform(transform);
        body.proceedToTransform(transform);

        body.activate();
    }
}

    public boolean quaternionsEqual(Quaternion q1, Quaternion q2, float epsilon) {

    return Math.abs(q1.x - q2.x) < epsilon &&
           Math.abs(q1.y - q2.y) < epsilon &&
           Math.abs(q1.z - q2.z) < epsilon &&
           Math.abs(q1.w - q2.w) < epsilon;
    }
    public int getpindowns(){

        int pinsdown = 0;

        float tolerance = 0.19f;
        for(int i = 0 ; i < pininitposition.size() ; i++){
            
            Quaternion a = collisionDetector.getpinbodyrotation(i);
            Quaternion b = pin.get(i).getpinrotation();
            if(!quaternionsEqual(a,b,tolerance)){
                pinsdown++;
                Matrix4 transform = new Matrix4().setToTranslation(100f,100f,100f);
                collisionDetector.getPinBodies().get(i).setWorldTransform(transform);
            }
        }
        return pinsdown;
    }

}
