package com.BowlORama;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.linearmath.btDefaultMotionState;

import java.util.ArrayList;
import java.util.Vector;

public class CollisionPhysics {
    
    private btDefaultCollisionConfiguration config;
    btCollisionDispatcher dispatcher;
    btDbvtBroadphase broadphase;
    btSequentialImpulseConstraintSolver solver;
    private btDiscreteDynamicsWorld world;

    private btCollisionShape laneShape;
    private btCollisionShape gutterShape;

    private btSphereShape ballShape;
    private btCapsuleShape pinShape;

    private btRigidBody laneBody;
    private btRigidBody leftGutterBody;
    private btRigidBody rightGutterBody;
    
    private btRigidBody leftGutterSidewallBody;
    private btRigidBody rightGutterSidewallBody;

    private btRigidBody ballBody;

    private ArrayList<btRigidBody> pinBodies = new ArrayList<>();

    private Map map;
    private ArrayList<Pins> pin;
    private Ball ball;
    private float ballradius = 0.6f;

    public CollisionPhysics(Map map , ArrayList<Pins> pin , Ball ball) {

        Bullet.init();
        this.map = map;
        this.pin = pin;
        this.ball = ball;

        config = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(config);
        broadphase = new btDbvtBroadphase();
        solver = new btSequentialImpulseConstraintSolver();

        world = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, config);
        world.setGravity(new Vector3(0, -9.81f, 0));

        createLane();
        createGutters();
        createBall();
        createPins();
        creategutterwalls();
    }

    private void createLane() {

        laneShape = new btBoxShape(new Vector3(map.getpathwidht()/2 - 0.5f, map.getpathheight()/2,map.getpathdepth()/2 - 0.7f));
        laneBody = createStaticBody(laneShape, new Vector3(0f, 0.5f, 0f));

        world.addRigidBody(laneBody);
    }

    private void createGutters() {


        gutterShape = new btBoxShape(new Vector3(1f, 0.5f, (map.getpathdepth()-2)));

        Vector3 pos = new Vector3();
        map.gutterscene.modelInstance.transform.getTranslation(pos);
        Vector3 pos2 = new Vector3();
        map.gutter2scene.modelInstance.transform.getTranslation(pos2);
        leftGutterBody = createStaticBody(gutterShape, new Vector3(pos2.x,pos2.y-0.9f,pos2.z));
        rightGutterBody = createStaticBody(gutterShape, new Vector3(pos.x,pos.y-0.9f,pos.z));

        leftGutterBody.setFriction(0.9f);
        rightGutterBody.setFriction(0.9f);

        world.addRigidBody(leftGutterBody);
        world.addRigidBody(rightGutterBody);
    }

    private void createBall() {

        ballShape = new btSphereShape(ballradius);
        Vector3 pos = new Vector3();
        ball.ballScene.modelInstance.transform.getTranslation(pos);
        ballBody = createDynamicBody(ballShape, new Vector3(pos.x , pos.y+ballradius , pos.z), 5f);
        

        world.addRigidBody(ballBody);
    }

    private void createPins() {

        pinShape = new btCapsuleShape(0.18f, 0.55f);

            for(int created = 0 ; created < pin.size() ; created++){

                Vector3 pos = new Vector3();
                pin.get(created).pinscene.modelInstance.transform.getTranslation(pos);

                btRigidBody pinBody = createDynamicBody(pinShape, pos, 1f);

                world.addRigidBody(pinBody);
                pinBodies.add(pinBody);

            }

    }
    private void creategutterwalls(){

        leftGutterSidewallBody = createStaticGutterSideBody(gutterShape, new Vector3(map.getpathwidht()/2+1.3f , 1f , 0f));
        world.addRigidBody(leftGutterSidewallBody);
        
        rightGutterSidewallBody = createStaticGutterSideBody(gutterShape, new Vector3(-map.getpathwidht()/2-1.3f , 1f , 0f));
        world.addRigidBody(rightGutterSidewallBody);
        
    }

    private btRigidBody createStaticBody(btCollisionShape shape, Vector3 position) {

        Matrix4 transform = new Matrix4().setToTranslation(position);
        btDefaultMotionState motion = new btDefaultMotionState(transform);

        btRigidBody.btRigidBodyConstructionInfo info =
                new btRigidBody.btRigidBodyConstructionInfo(0f, motion, shape, Vector3.Zero);

        return new btRigidBody(info);
    }

    private btRigidBody createStaticGutterSideBody(btCollisionShape shape, Vector3 position) {

        Matrix4 transform = new Matrix4().setToTranslation(position).rotate(Vector3.Z,90);
        btDefaultMotionState motion = new btDefaultMotionState(transform);

        btRigidBody.btRigidBodyConstructionInfo info =
                new btRigidBody.btRigidBodyConstructionInfo(0f, motion, shape, Vector3.Zero);

        return new btRigidBody(info);
    }

    // private btRigidBody createRotatedStaticBody(btCollisionShape shape, Vector3 position, float angleZ) {

    //     Matrix4 transform = new Matrix4().setToTranslation(position).rotate(Vector3.Z, angleZ);
    //     btDefaultMotionState motion = new btDefaultMotionState(transform);

    //     btRigidBody.btRigidBodyConstructionInfo info =
    //             new btRigidBody.btRigidBodyConstructionInfo(0f, motion, shape, Vector3.Zero);

    //     return new btRigidBody(info);
    // }

    private btRigidBody createDynamicBody(btCollisionShape shape, Vector3 position, float mass) {

        Matrix4 transform = new Matrix4().setToTranslation(position);
        btDefaultMotionState motion = new btDefaultMotionState(transform);

        Vector3 inertia = new Vector3();
        shape.calculateLocalInertia(mass, inertia);

        btRigidBody.btRigidBodyConstructionInfo info = new btRigidBody.btRigidBodyConstructionInfo(mass, motion, shape, inertia);
        

        return new btRigidBody(info);
    }

    public void update(float delta) {
         
        world.stepSimulation(delta, 1, 1f / 60f);
        // Ball sync
        ballBody.getWorldTransform(ball.ballScene.modelInstance.transform);
        Matrix4 temp = new Matrix4();
        Vector3 pos = new Vector3();
        Quaternion rot = new Quaternion();

        ballBody.getWorldTransform(temp);

        temp.getTranslation(pos);
        temp.getRotation(rot);

        ball.ballScene.modelInstance.transform.idt();
        ball.ballScene.modelInstance.transform.set(pos, rot);
        ball.ballScene.modelInstance.transform.scale(0.6f, 0.6f, 0.6f);
        setinitialpins();
       

    }

    private void setinitialpins(){
         // Pins sync
        for (int i = 0; i < pinBodies.size(); i++) {
            pinBodies.get(i).getWorldTransform(
                pin.get(i).pinscene.modelInstance.transform
            );
        }
    }

        

    public btRigidBody getBallBody() {
        return ballBody;
    }

    public ArrayList<btRigidBody> getPinBodies() {
        return pinBodies;
    }

    public btDiscreteDynamicsWorld getWorld() {
        return world;
    }

    public void shootBall(Vector3 force) {

    ballBody.activate();

    ballBody.clearForces();

    ballBody.setAngularVelocity(new Vector3(0,0,0));
    ballBody.setLinearVelocity(new Vector3(0,0,0));

    ballBody.applyCentralImpulse(force);
}
    public Quaternion getpinbodyrotation(int i){

        Matrix4 transform = new Matrix4();
        pinBodies.get(i).getWorldTransform(transform);
        Quaternion rotation = new Quaternion();
        return transform.getRotation(rotation);
    }

    public void dispose() {

        for (btRigidBody pin : pinBodies) {
            pin.dispose();
        }

        pinBodies.clear();

        ballBody.dispose();

        laneBody.dispose();
        leftGutterBody.dispose();
        rightGutterBody.dispose();
        leftGutterSidewallBody.dispose();
        rightGutterSidewallBody.dispose();


        config.dispose();
        dispatcher.dispose();
        broadphase.dispose();
        solver.dispose();
        laneShape.dispose();
        gutterShape.dispose();
        ballShape.dispose();
        pinShape.dispose();

        world.dispose();
    }
}