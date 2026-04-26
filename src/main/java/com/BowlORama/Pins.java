package com.BowlORama;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.scene.*;


public class Pins {
 
    SceneAsset pin;
    Scene pinscene;
    PerspectiveCamera camera;
    SceneManager sceneManager;
    Map map;
    Quaternion startrotaion;
    int pincolumn = 0;

      public Pins(SceneManager sceneManager , PerspectiveCamera camera , Map map , SceneAsset pin){
        this.map = map;
        this.camera  = camera;
        this.sceneManager = sceneManager;
        this.pin = pin;
        startrotaion = new Quaternion();
      }

      public void create(int i){

          pinscene = new Scene(pin.scene);

          float y = map.getpathheight() + 0.5f;
          float startZ = map.getpathdepth()/2f - 1f;

          float x = 0f;
          float z = 0f;

          // Wider horizontal spacing
          float gapX = 1.5f;

          // Forward spacing between rows
          float gapZ = 1.35f;

          if(i >= 0 && i < 4){          // back row (4 pins)

              if(i == 0) x = -1.5f * gapX;   // -2.25
              if(i == 1) x = -0.5f * gapX;   // -0.75
              if(i == 2) x =  0.5f * gapX;   //  0.75
              if(i == 3) x =  1.5f * gapX;   //  2.25

              z = startZ;
          }

          else if(i >= 4 && i <= 6){   // row of 3 pins

              if(i == 4) x = -1f * gapX;     // -1.5
              if(i == 5) x =  0f;            //  0
              if(i == 6) x =  1f * gapX;     //  1.5

              z = startZ - gapZ;
          }

          else if(i >= 7 && i <= 8){   // row of 2 pins

              if(i == 7) x = -0.5f * gapX;   // -0.75
              if(i == 8) x =  0.5f * gapX;   //  0.75

              z = startZ - (2f * gapZ);
          }

          else if(i == 9){             // front pin

            x = 0f;
            z = startZ - (3f * gapZ);
        
        }
        
        // pinscene.modelInstance.transform.setToTranslation(0f,map.getpathheight()+0.5f,map.getpathdepth()/2 - 2.5f);
        pinscene.modelInstance.transform.setToTranslation(x,y,z);
        pinscene.modelInstance.transform.getRotation(startrotaion);

        sceneManager.addScene(pinscene);
      }

      public Vector3 getpinlocation(){
        Vector3 pos = new Vector3();
        pinscene.modelInstance.transform.getTranslation(pos);
        return pos;
      }
      public Quaternion getpinrotation(){
        return startrotaion;
      }

      public void dispose(){
        pin.dispose();
      }

}
