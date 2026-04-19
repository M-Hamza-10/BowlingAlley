package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.scene.*;

public class Pins {
 
    SceneAsset pin;
    Scene pinscene;
    PerspectiveCamera camera;
    SceneManager sceneManager;
    Map map;

      public Pins(SceneManager sceneManager , PerspectiveCamera camera , Map map , SceneAsset pin){
        this.map = map;
        this.camera  = camera;
        this.sceneManager = sceneManager;
        this.pin = pin;
      }

      public void create(){
        
        pinscene = new Scene(pin.scene);

        pinscene.modelInstance.transform.setToTranslation(0,map.getpathheight()+0.5f,map.getpathdepth()/2-1f);
        //pinscene.modelInstance.transform.scale(0.5f, 0.5f, 0.5f);
        //position2  = new Vector3(0,map.getpathheight()+0.5f,-29);

        sceneManager.addScene(pinscene);
      }

      public void dispose(){
        pin.dispose();
      }

}
