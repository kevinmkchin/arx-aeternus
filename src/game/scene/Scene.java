package game.scene;

import assets.entities.Camera;
import assets.entities.Light;
import assets.models.ModelTexture;
import assets.models.RawModel;
import assets.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.MainRenderer;
import renderEngine.ModelLoader;

public abstract class Scene {

    float[] cubeVertices = {
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

    float[] cubeTextureCoords = {

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

    float[] cubeNormals = {
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

    int[] cubeIndices = {
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


    protected ModelLoader modelLoader;
    protected MainRenderer renderer;

    protected Camera camera;
    protected Light light;


    // SCENE CONSTRUCTOR MUST HAVE:
    // initScene
    // setCamera
    // setLighting

    protected void initScene(){
        modelLoader = new ModelLoader();
        renderer = new MainRenderer();
        loadContent();
    }

    protected abstract void loadContent();

    // UPDATE AND RENDER THE SCENE
    // updateScene needs to
    // 1. renderer.processEntity
    public abstract void updateScene();

    public void renderScene(){
        renderer.render(light, camera);
    }
    public void closeScene(){
        renderer.cleanUp();
        modelLoader.cleanUp();
    }


    protected void setCamera(){
        camera = new Camera();
    }

    protected void setLighting(float x, float y, float z,
                               float r, float g, float b,
                               float ambientLight,
                               boolean isDirectional){
        light = new Light(new Vector3f(x,y,z), new Vector3f(r,g,b), ambientLight, isDirectional);
    }

    protected TexturedModel makeBlockModel(String blockTextureName){
        RawModel model = modelLoader.loadToVAO(cubeVertices, cubeTextureCoords, cubeNormals, cubeIndices);
        ModelTexture texture = new ModelTexture(modelLoader.loadTexture(blockTextureName));
        return new TexturedModel(model, texture);
    }

}
