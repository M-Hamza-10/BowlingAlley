package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.loaders.glb.GLBLoader;
public class Ball {

    SceneAsset ball;
    Scene ballScene;

    boolean thrown = false;
    boolean movedown = false;
    float speed = 10f;
    PerspectiveCamera camera;
    SceneManager sceneManager;
    Map map;
    private Quaternion ballInitialangle;

    public Ball(PerspectiveCamera camera, SceneManager sceneManager, Map map){
        this.sceneManager = sceneManager;
        this.map = map;
        this.camera = camera;
    }

    public void create(){

        ball = new GLBLoader().load(Gdx.files.internal("Ball.glb"));
        ballScene = new Scene(ball.scene);
        ballInitialangle = new Quaternion();

        ballScene.modelInstance.transform.setToTranslation(0,map.getpathheight()+0.5f,-40f);
        ballScene.modelInstance.transform.scale(0.6f, 0.6f, 0.6f);
        ballScene.modelInstance.transform.getRotation(ballInitialangle);
        sceneManager.addScene(ballScene);
    }

    public void throwBall(){
        thrown = true;
    }

    public void update(float delta){

        // if(thrown){
        // camera.position.add(0,0,speed * delta);
        // camera.update();
        //     if(camera.position.z > (map.getpathdepth()/2f)-1f)
        //     thrown = false;

        // }
        // }

    }

    public Vector3 getballcoordinates(){
        return new Vector3(0,map.getpathheight()+0.5f,-40f);
    }
    public Quaternion getballinitroattion(){
        return ballInitialangle;
    }

    public void dispose(){
        ball.dispose();
    }
}
