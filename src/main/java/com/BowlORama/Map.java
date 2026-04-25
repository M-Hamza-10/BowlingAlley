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
public class Map {

    PerspectiveCamera camera;
    SceneManager sceneManager;

    public Map(PerspectiveCamera camera, SceneManager sceneManager){
        this.camera = camera;
        this.sceneManager = sceneManager;
    }

    private float pathwidth = 7f;
    private float pathheight = 1f;
    private float pathdepth = 90f;

    ModelBuilder modelBuilder;

    Model path, backBoard, externalpath;
    Texture texture, texture2, backboard2texture;
    Material material, material2;

    ModelInstance pathinstance, externalpathinstance;
    ModelInstance backBoardinstance, backBoardInstance2, backBoardInstance3;
    ModelInstance sideWall, sideWall2;

    Scene pathscene, gutterscene, gutter2scene;
    Scene externalpathscene;
    Scene backBoardScene, backBoardScene2, backBoardScene3;
    Scene sideWallscene, sideWallscene2;

    SceneAsset gutterAsset;

    public void create(){

        modelBuilder = new ModelBuilder();

        initTextures();
        initModels();

        initPath();
        initGutters();
        initExternalPath();
        initBackBoards();
        initSideWalls();

        addScenes();
    }

private void initTextures(){

    texture = new Texture(Gdx.files.internal("pathtexture.png"));
    texture2 = new Texture(Gdx.files.internal("externalpathtexture.png"));
    backboard2texture = new Texture(Gdx.files.internal("pathtexture.png"));

    material = new Material(PBRTextureAttribute.createBaseColorTexture(texture));
    material2 = new Material(PBRTextureAttribute.createBaseColorTexture(texture2));
}

private void initModels(){

    path = modelBuilder.createBox(pathwidth, pathheight, pathdepth,
            material,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);

    externalpath = modelBuilder.createBox(pathwidth + 80f, pathheight, pathdepth + 70f,
            material2,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);

    backBoard = modelBuilder.createBox(pathwidth + 80, pathheight + 30, 1f,
            material,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);
}

private void initPath(){

    pathinstance = new ModelInstance(path);
    pathscene = new Scene(pathinstance);

    pathscene.modelInstance.transform.setToTranslation(0,0,0);
}

private void initGutters(){

    gutterAsset = new GLBLoader().load(Gdx.files.internal("Gutter.glb"));

    gutterscene = new Scene(gutterAsset.scene);
    gutter2scene = new Scene(gutterAsset.scene);

    setupGutter(gutterscene, -((pathwidth/2)+0.03f));
    setupGutter(gutter2scene, ((pathwidth/2)+0.03f));
}

private void setupGutter(Scene gutter, float x){

    gutter.modelInstance.transform.setToTranslation(
            x,
            pathheight,
            -((pathdepth/2)-5)
    );

    gutter.modelInstance.transform.rotate(Vector3.Y,90);

    Vector3 pos = new Vector3();
    Quaternion rot = new Quaternion();
    Vector3 scale = new Vector3();

    gutter.modelInstance.transform.getTranslation(pos);
    gutter.modelInstance.transform.getRotation(rot);
    gutter.modelInstance.transform.getScale(scale);

    scale.set(pathdepth - 4f,1f,2f);

    gutter.modelInstance.transform.set(pos,rot,scale);
}

private void initExternalPath(){

    externalpathinstance = new ModelInstance(externalpath);
    externalpathscene = new Scene(externalpathinstance);

    externalpathscene.modelInstance.transform.setToTranslation((pathwidth/2)+3,pathheight-1,-((pathdepth/2)-5));
}

private void initBackBoards(){

    backBoardinstance = new ModelInstance(backBoard);
    backBoardInstance2 = new ModelInstance(backBoard);
    backBoardInstance3 = new ModelInstance(backBoard);

    backBoardInstance2.materials.get(0).set(PBRTextureAttribute.createBaseColorTexture(backboard2texture));

    backBoardScene = new Scene(backBoardinstance);
    backBoardScene.modelInstance.transform.setToTranslation(0,30,pathdepth/2);

    backBoardScene2 = new Scene(backBoardInstance2);
    backBoardScene2.modelInstance.transform.setToTranslation((pathwidth/2)+32,0,pathdepth/2);
    backBoardScene2.modelInstance.transform.scale(pathwidth/10,1,1);

    backBoardScene3 = new Scene(backBoardInstance3);
    backBoardScene3.modelInstance.transform.setToTranslation(-((pathwidth/2)+32),0,pathdepth/2);
    backBoardScene3.modelInstance.transform.scale(pathwidth/10,1,1);
}

private void initSideWalls(){

    sideWall = new ModelInstance(backBoard);
    sideWall2 = new ModelInstance(backBoard);

    sideWallscene = new Scene(sideWall);
    sideWallscene.modelInstance.transform.setToTranslation((pathdepth/1.6f),0,0);
    sideWallscene.modelInstance.transform.rotate(Vector3.Y,90);

    sideWallscene2 = new Scene(sideWall2);
    sideWallscene2.modelInstance.transform.setToTranslation(-(pathdepth/1.6f),0,0);
    sideWallscene2.modelInstance.transform.rotate(Vector3.Y,90);
}

private void addScenes(){

    sceneManager.addScene(pathscene);
    sceneManager.addScene(gutterscene);
    sceneManager.addScene(gutter2scene);
    sceneManager.addScene(externalpathscene);
    sceneManager.addScene(backBoardScene);
    sceneManager.addScene(backBoardScene2);
    sceneManager.addScene(backBoardScene3);
    sceneManager.addScene(sideWallscene);
    sceneManager.addScene(sideWallscene2);
}

public float getpathheight(){
    return pathheight;
}

public float getpathwidht(){
    return pathwidth;
}

public float getpathdepth(){
    return pathdepth;
}

public void dispose(){
    path.dispose();;
    gutterAsset.dispose();
    backBoard.dispose();
}
}