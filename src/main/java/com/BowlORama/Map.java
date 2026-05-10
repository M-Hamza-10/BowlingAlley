package com.BowlORama;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

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
    Model sidewallmodel , ceilmodel;
    Model lightStrip;
    Texture texture, texture2, backboard2texture , wallTexture;
    Texture ceilingTexture;
    Material material, material2;
    Material wallmaterial , ceilingmaterial;
    Material lightMaterial;

    ModelInstance pathinstance, externalpathinstance;
    ModelInstance backBoardinstance, backBoardInstance2, backBoardInstance3;
    ModelInstance sideWall, sideWall2;
    ModelInstance ceiling;
    ModelInstance leftLight;
    ModelInstance rightLight;
    

    ArrayList<Scene> lightScenes = new ArrayList<>();
    Scene pathscene, gutterscene, gutter2scene;
    Scene externalpathscene;
    Scene backBoardScene, backBoardScene2, backBoardScene3;
    Scene sideWallscene, sideWallscene2;
    Scene ceilScene;


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
        initCeiling();
        initLights();

        addScenes();
    }

private void initTextures(){

    texture = new Texture(Gdx.files.internal("pathtexture.png"));
    texture2 = new Texture(Gdx.files.internal("externalpathtexture.png"));
    backboard2texture = new Texture(Gdx.files.internal("pathtexture.png"));
    wallTexture = new Texture(Gdx.files.internal("walltexture.png"));
    ceilingTexture = new Texture(Gdx.files.internal("ceilingTexture.png"));

    material = new Material(PBRTextureAttribute.createBaseColorTexture(texture));
    material2 = new Material(PBRTextureAttribute.createBaseColorTexture(texture2));
    wallmaterial = new Material(PBRTextureAttribute.createBaseColorTexture(wallTexture));
    ceilingmaterial = new Material(PBRTextureAttribute.createBaseColorTexture(ceilingTexture));
    lightMaterial = new Material(ColorAttribute.createEmissive(Color.WHITE));
    
}

private void initModels(){

    path = modelBuilder.createBox(pathwidth, pathheight, pathdepth,
            material,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);

    externalpath = modelBuilder.createBox(pathwidth + 80f, pathheight, pathdepth + 70f,
            wallmaterial,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);

    backBoard = modelBuilder.createBox(pathwidth + 80, pathheight + 30, 1f,
            material,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);

            
    sidewallmodel = modelBuilder.createBox(pathwidth + 80, pathheight + 30, 1f,
            wallmaterial,
            VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates);

            ceilmodel = modelBuilder.createRect(

                -75f, 0f, -75f,
                75f, 0f, -75f,
                75f, 0f,  75f,
                -75f, 0f,  75f,

                0, -1, 0,

                ceilingmaterial,

                VertexAttributes.Usage.Position |
                VertexAttributes.Usage.Normal |
                VertexAttributes.Usage.TextureCoordinates
        );
        lightStrip = modelBuilder.createBox(
                1f,      // thickness
                0.2f,    // height
                80f,     // length

                lightMaterial,

                VertexAttributes.Usage.Position |
                VertexAttributes.Usage.Normal
        );
}

private void initLights(){

        ModelInstance strip = new ModelInstance(lightStrip);
        Scene stripScene = new Scene(strip);

        strip.transform.rotate(Vector3.Z,90);
        stripScene.modelInstance.transform.setToTranslation(
                55f,
                0f,
                0f
        );
        lightScenes.add(stripScene);

        ModelInstance strip2 = new ModelInstance(lightStrip);
        Scene stripScene2 = new Scene(strip2);
        strip2.transform.rotate(Vector3.Z,90);
        stripScene2.modelInstance.transform.setToTranslation(
                -55f,
                -1f,
                0f
        );
        lightScenes.add(stripScene2);

    for(int i = -3; i <= 3; i++){

        ModelInstance strip3 = new ModelInstance(lightStrip);

        Scene stripScene3 = new Scene(strip3);

        stripScene3.modelInstance.transform.setToTranslation(
                i * 12f,
                19f,
                0f
        );

        lightScenes.add(stripScene3);
    }
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

    externalpathscene.modelInstance.transform.setToTranslation((pathwidth/2)+3,pathheight-2f,-((pathdepth/2)-5));
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

    sideWall = new ModelInstance(sidewallmodel);
    sideWall2 = new ModelInstance(sidewallmodel);

    sideWallscene = new Scene(sideWall);
    sideWallscene.modelInstance.transform.setToTranslation((pathdepth/1.6f),0,0);
    sideWallscene.modelInstance.transform.rotate(Vector3.Y,90);

    sideWallscene2 = new Scene(sideWall2);
    sideWallscene2.modelInstance.transform.setToTranslation(-(pathdepth/1.6f),0,0);
    sideWallscene2.modelInstance.transform.rotate(Vector3.Y,90);
}

private void initCeiling(){
    ceiling = new ModelInstance(ceilmodel);
    ceilScene = new Scene(ceiling);
    ceilScene.modelInstance.transform.setToTranslation(0f,20f,0f);
    //ceilScene.modelInstance.transform.rotate(Vector3.X,270);
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
    sceneManager.addScene(ceilScene);
    for(Scene s : lightScenes){
        sceneManager.addScene(s);
    }
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

    // Models
    if(path != null)
        path.dispose();

    if(externalpath != null)
        externalpath.dispose();

    if(backBoard != null)
        backBoard.dispose();

    if(sidewallmodel != null)
        sidewallmodel.dispose();

    if(ceilmodel != null)
        ceilmodel.dispose();

    if(lightStrip != null)
        lightStrip.dispose();


    // Textures
    if(texture != null)
        texture.dispose();

    if(texture2 != null)
        texture2.dispose();

    if(backboard2texture != null)
        backboard2texture.dispose();

    if(wallTexture != null)
        wallTexture.dispose();

    if(ceilingTexture != null)
        ceilingTexture.dispose();


    // GLTF Assets
    if(gutterAsset != null)
        gutterAsset.dispose();
}
}