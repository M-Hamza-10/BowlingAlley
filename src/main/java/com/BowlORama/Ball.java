package com.BowlORama;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
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

    public Ball(PerspectiveCamera camera, SceneManager sceneManager, Map map){
        this.sceneManager = sceneManager;
        this.map = map;
        this.camera = camera;
    }

    public void create(){

        ball = new GLBLoader().load(Gdx.files.internal("Ball.glb"));
        ballScene = new Scene(ball.scene);

        ballScene.modelInstance.transform.setToTranslation(0,map.getpathheight()+0.5f,-40f);
        ballScene.modelInstance.transform.scale(0.6f, 0.6f, 0.6f);
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

    public void dispose(){
        ball.dispose();
    }
}
