package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class EventHandler {
    
    Ball ball;
    CollisionPhysics collisionDetector;
    PerspectiveCamera camera;
    Vector3 pos;

    boolean spidercamview = false;

    private boolean thrown = false;
    private boolean blocked = false;

    public EventHandler(Ball ball , CollisionPhysics collsiondetector , PerspectiveCamera camera){
        this.ball = ball;
        this.collisionDetector = collsiondetector;
        this.camera = camera;
        pos = new Vector3();
    }

    public void handleball(float delta){
        
        handlemousePhysics(5f);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
           collisionDetector.shootBall(new Vector3(0f,0f, 80f));
           thrown = true;
            collisionDetector.getBallBody().getWorldTransform().getTranslation(pos);  
        }
        handlecamera(delta);
        // ball.update(delta);   // continuous movement here
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                ///Rigid body reference is required to change position gettranslation only gives position values
                btRigidBody body = collisionDetector.getBallBody();

                Matrix4 transform = body.getWorldTransform();

                transform.getTranslation(pos);

                pos.x -= 1f;

                transform.setTranslation(pos);
                body.setWorldTransform(transform);
            }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

            btRigidBody body = collisionDetector.getBallBody();

            Matrix4 transform = body.getWorldTransform();
            transform.getTranslation(pos);

            pos.x += 1f;

            transform.setTranslation(pos);
            body.setWorldTransform(transform);
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

            if (camera.position.z > (70f / 2f) - 5f)
                thrown = false;
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
}
