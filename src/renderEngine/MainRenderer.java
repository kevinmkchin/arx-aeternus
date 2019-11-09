package renderEngine;

import assets.entities.Camera;
import assets.entities.Entity;
import assets.entities.Light;
import assets.models.TexturedModel;
import assets.shaders.StaticShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.renderer.SGL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainRenderer {

    /**
     * THE MAIN RENDERER
     *
     *
     * */

    private static final float FOV = 80;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private ModelRenderer modelRenderer;


    /**
     * STORES THE MAP OF A TEXTURED MODEL AND ALL ENTITIES THAT USE THAT TEXTURED MODEL
     * Key: TexturedModel
     * Value: List of all entities that use this TexturedModel
     *
     * Contains all entities to be rendered in a given frame.
     *
     * */
    private Map<TexturedModel, List<Entity>> entityMap = new HashMap<>();

    public MainRenderer(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        modelRenderer = new ModelRenderer(shader, projectionMatrix);
    }

    public void render(Light light, Camera camera){
        prepare();
        shader.start();
        shader.loadFog(0.5f, 0.5f, 0.5f, 0.04f,3);
        shader.loadLight(light);
        shader.loadViewMatrix(camera);

        modelRenderer.render(entityMap);

        shader.stop();
        entityMap.clear();
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, SGL.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); //disables bilinear filtering
        GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entityMap.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entityMap.put(entityModel, newBatch);
        }
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / Display.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }

    public void cleanUp(){
        shader.cleanUp();
    }

}
