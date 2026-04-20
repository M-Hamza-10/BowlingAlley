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

    int pincolumn = 0;

      public Pins(SceneManager sceneManager , PerspectiveCamera camera , Map map , SceneAsset pin){
        this.map = map;
        this.camera  = camera;
        this.sceneManager = sceneManager;
        this.pin = pin;
      }

      public void create(int i){
        
        pinscene = new Scene(pin.scene);

        float y = map.getpathheight() + 0.5f;
        float startZ = map.getpathdepth()/2 - 1f;

        pinscene = new Scene(pin.scene);

        float x = 0f;
        float z = 0f;

        if(i >= 0 && i < 4){   // row 4
            if(i == 0) x = -1.5f;
            if(i == 1) x = -0.5f;
            if(i == 2) x = 0.5f;
            if(i == 3) x = 1.5f;

            z = startZ;
        }
        else if(i >= 4 && i <= 6){   // row 3
            if(i == 4) x = -1f;
            if(i == 5) x = 0f;
            if(i == 6) x = 1f;

            z = startZ - 1f;
        }
        else if(i >= 7 && i <= 8){   // row 2
            x = (i == 7) ? -0.5f : 0.5f;
            z = startZ - 2f;
        }
        else if(i == 9){
            x = 0f;
            z = startZ - 3f;
        }
        
        // pinscene.modelInstance.transform.setToTranslation(0f,map.getpathheight()+0.5f,map.getpathdepth()/2 - 2.5f);
        pinscene.modelInstance.transform.setToTranslation(x,y,z);

        sceneManager.addScene(pinscene);
      }

      public void dispose(){
        pin.dispose();
      }

}
