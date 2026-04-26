package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

import java.io.InputStream;
import com.badlogic.gdx.Game;

import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import java.util.ArrayList;

public class GameScreen implements Screen{

    private PerspectiveCamera camera;
    private Environment environment;
    private SceneManager sceneManager;

    private EventHandler eventHandler;
    private SceneAsset pinasset;
    

    //classes initializtion
    Map map;
    Ball ball;
    ArrayList<Pins> pin;
    ArrayList<Vector3> temppins;
    CollisionPhysics collisondetector;

    public GameScreen(){
        //NUll pointer Exception occurs bcz it first initalizes the map then gamescreen so screenmanager uninitialized
        // map = new Map(camera, sceneManager);
        //Do not add heavy objects here like camera
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        eventHandler.handleball(delta);
        collisondetector.update(delta);
        sceneManager.update(delta);
        sceneManager.render();

        //System.out.println("Screen MAnager render........................................");
        //renders all objects like camera environment automatically and no need to change it
    }


    @Override
    public void show() {

        sceneManager = new SceneManager();
        camera = new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        map = new Map(camera, sceneManager);
        pinasset = new GLBLoader().load(Gdx.files.internal("pin.glb"));
        
        
        // called once when screen starts  like create
        
        camera.position.set(0f, (map.getpathheight() + 2), -(map.getpathdepth()/2 +1f));
        camera.lookAt(0,0,0);
        camera.near = 0.1f;
        camera.far = 100f;
        camera.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));
       
        
        sceneManager.setCamera(camera);
        sceneManager.environment = environment;
        DirectionalLightEx light = new DirectionalLightEx();
        light.direction.set(0.5f, -1f, 0.5f).nor(); //1,-3,1
        light.color.set(Color.WHITE);
        light.intensity = 3f;
        sceneManager.environment.add(light);

        // Add ambient/skybox light
        sceneManager.setAmbientLight(0.3f); //Only this line can suffice also but proper light is good

        map.create();

        ball = new Ball(camera, sceneManager, map);
        ball.create();

        pin = new ArrayList<>();
        temppins = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
        pin.add(new Pins(sceneManager, camera, map , pinasset));
        pin.get(i).create(i);
        Vector3 pos = new Vector3();
        pin.get(i).pinscene.modelInstance.transform.getTranslation(pos);
        temppins.add(pos);
        }

        collisondetector = new CollisionPhysics(map, pin, ball);
        eventHandler = new EventHandler(ball, collisondetector , camera , map , temppins , pin);
        

        //System.out.println("Show ");
    }


    @Override
    public void resize(int width, int height) {
        // window resized
    }

    @Override
    public void pause() {
        // app paused (mobile)
    }

    @Override
    public void resume() {
        // app resumed
    }

    @Override
    public void hide() {
        // when switching to another screen
    }

    @Override
    public void dispose(){
        sceneManager.dispose();//Does not dispose objects automatically manually dispose models in each class 
        map.dispose();
    }


}
