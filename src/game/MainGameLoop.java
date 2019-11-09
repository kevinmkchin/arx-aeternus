package game;

import assets.entities.Camera;
import assets.entities.Entity;
import assets.entities.Light;
import assets.models.ModelTexture;
import assets.models.RawModel;
import assets.models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.MainRenderer;
import renderEngine.ModelLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();

        ModelLoader loader = new ModelLoader();

        float[] vertices = {
                0,1,0,
                0,0,0,
                1,0,0,
                1,1,0,

                0,1,1,
                0,0,1,
                1,0,1,
                1,1,1,

                1,1,0,
                1,0,0,
                1,0,1,
                1,1,1,

                0,1,0,
                0,0,0,
                0,0,1,
                0,1,1,

                0,1,1,
                0,1,0,
                1,1,0,
                1,1,1,

                0,0,1,
                0,0,0,
                1,0,0,
                1,0,1
        };

        float[] textureCoords = {

                0,0,
                0,0.5f,
                0.5f,0.5f,
                0.5f,0,

                0,0,
                0,0.5f,
                0.5f,0.5f,
                0.5f,0,

                0,0,
                0,0.5f,
                0.5f,0.5f,
                0.5f,0,

                0,0,
                0,0.5f,
                0.5f,0.5f,
                0.5f,0,

                0,0.5f,
                0,1,
                0.5f,1,
                0.5f,0.5f,

                0.5f,0.5f,
                0.5f,1,
                1,1,
                1,0.5f
        };

        float[] normals = {
                0,0,-1,
                0,0,-1,
                0,0,-1,
                0,0,-1,

                0,0,1,
                0,0,1,
                0,0,1,
                0,0,1,

                1,0,0,
                1,0,0,
                1,0,0,
                1,0,0,

                -1,0,0,
                -1,0,0,
                -1,0,0,
                -1,0,0,

                0,1,0,
                0,1,0,
                0,1,0,
                0,1,0,

                0,-1,0,
                0,-1,0,
                0,-1,0,
                0,-1,0
        };

        int[] indices = {
                0,3,1,
                3,2,1,
                4,5,7,
                7,5,6,
                8,11,9,
                11,10,9,
                12,13,15,
                15,13,14,
                16,19,17,
                19,18,17,
                20,21,23,
                23,21,22
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, normals, indices);
        ModelTexture grassTexture = new ModelTexture(loader.loadTexture("acacia"));
        TexturedModel texturedModel = new TexturedModel(model, grassTexture);
        Light light1 = new Light(new Vector3f(1,2,1.3f), new Vector3f(1,1,1), 0.2f, true);

        List<Entity> blocks = new ArrayList<>();

        for(int i=0; i<64; i++){
            for(int j=0; j<64; j++) {
                blocks.add(new Entity(texturedModel, new Vector3f(i,0,j),0,0,0,1));
                if(new Random().nextInt(2) == 1) {
                    blocks.add(new Entity(texturedModel, new Vector3f(i, 1, j), 0, 0, 0, 1));
                }
            }
        }

        Camera camera = new Camera();
        MainRenderer renderer = new MainRenderer();

        //MAIN GAME LOOP (GAME LOGIC / RENDERING / UPDATE DISPLAY)
        while(!Display.isCloseRequested()){
            //game logic update
            camera.update();

            for(Entity block : blocks){
                renderer.processEntity(block);
            }

            //INITIALIZE SCENES HERE BY CHECKING FOR STATE CHANGE
            // for example
            // if (gameState_justSwitched) then
            //     if (gameState_1) then scene = new FirstScene();
            //     if (gameState_2) then scene = new SecondScene();

            renderer.render(light1, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
