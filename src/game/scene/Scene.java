package game.scene;

import assets.entities.Camera;
import assets.entities.Light;
import assets.models.ModelTexture;
import assets.models.RawModel;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
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

    float[] rectangleVertices = {
            -0.45f,1.7f,-0.45f,
            -0.45f,0,-0.45f,
            0.45f,0,-0.45f,
            0.45f,1.7f,-0.45f,

            -0.45f,1.7f,0.45f,
            -0.45f,0,0.45f,
            0.45f,0,0.45f,
            0.45f,1.7f,0.45f,

            0.45f,1.7f,-0.45f,
            0.45f,0,-0.45f,
            0.45f,0,0.45f,
            0.45f,1.7f,0.45f,

            -0.45f,1.7f,-0.45f,
            -0.45f,0,-0.45f,
            -0.45f,0,0.45f,
            -0.45f,1.7f,0.45f,

            -0.45f,1.7f,0.45f,
            -0.45f,1,-0.45f,
            0.45f,1,-0.45f,
            0.45f,1.7f,0.45f,

            -0.45f,0,0.45f,
            -0.45f,0,-0.45f,
            0.45f,0,-0.45f,
            0.45f,0,0.45f
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

    float[] rectTextureCoords = {

            0,0,
            0,1,
            0.53125f,1,
            0.53125f,0,

            0,0,
            0,1,
            0.53125f,1,
            0.53125f,0,

            0,0,
            0,1,
            0.53125f,1,
            0.53125f,0,

            0,0,
            0,1,
            0.53125f,1,
            0.53125f,0,

            0,0,
            0,0,
            0,0,
            0,0,

            0,0,
            0,0,
            0,0,
            0,0,
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


    /// ----- MOST IMPORTANT CLASS OF THE GAME -----
    public AMGameplayStatics GameplayStatics;
    /// --------------------------------------------

    protected ModelLoader modelLoader;
    //protected Camera camera;
    protected Light light;


    // SCENE CONSTRUCTOR MUST HAVE:
    // initScene
    // setCamera
    // setLighting

    protected void initScene(){
        GameplayStatics = new AMGameplayStatics();
        GameplayStatics.setGlobalMainRenderer(new MainRenderer());
        modelLoader = new ModelLoader();
        GameplayStatics.setGlobalModelLoader(modelLoader);

        loadContent();
    }

    protected abstract void loadContent();

    // UPDATE AND RENDER THE SCENE
    // updateScene needs to
    // 1. renderer.processEntity
    public abstract void updateScene();

    public void renderScene(){
        GameplayStatics.getGlobalMainRenderer().render(light, GameplayStatics.getGlobalCamera());
    }
    public void closeScene(){
        GameplayStatics.getGlobalMainRenderer().cleanUp();
        modelLoader.cleanUp();
    }

    protected void setCamera(){
        try
        {
            GameplayStatics.setGlobalCamera(new Camera());
        }
        catch (Exception e)
        {
            System.out.println(e.toString() + "Tried setting global camera but GameplayStatics is not initialized.");
        }
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

    protected TexturedModel makeEntityModel(String entityTextureName)
    {
        RawModel model = modelLoader.loadToVAO(rectangleVertices, rectTextureCoords, cubeNormals, cubeIndices);
        ModelTexture texture = new ModelTexture(modelLoader.loadTexture(entityTextureName));
        return new TexturedModel(model, texture);
    }

}
