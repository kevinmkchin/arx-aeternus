package renderEngine;

import assets.entities.Entity;
import assets.models.RawModel;
import assets.models.TexturedModel;
import assets.shaders.StaticShader;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.renderer.SGL;
import tools.Maths;

import java.util.List;
import java.util.Map;


public class ModelRenderer {

    private StaticShader shader;

    public ModelRenderer(StaticShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entityMap){
        for(TexturedModel model : entityMap.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = entityMap.get(model);
            for(Entity entity : batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES,
                        model.getRawModel().getVertexCount(),
                        GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel texturedModel){
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, SGL.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); //disables bilinear filtering
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
//
//        //enable alpha blending
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        //disable alpha blending
        //GL11.glDisable(GL11.GL_BLEND);

        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity){
        Matrix4f transformationMatrix = Maths.createTransformMatrix(entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        shader.loadTransformMatrix(transformationMatrix);
    }

}
