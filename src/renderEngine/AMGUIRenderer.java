package renderEngine;

import assets.AMGUI;
import assets.models.RawModel;
import assets.shaders.GUIShader;
import game.scene.Scene;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.renderer.SGL;
import tools.Maths;

import java.util.ArrayList;

public class AMGUIRenderer {

    private final RawModel quad;
    private GUIShader shader;

    public AMGUIRenderer()
    {
        float[] positions = {-1,1,-1,-1,1,1,1,-1};
        quad = new ModelLoader().loadToVAO(positions);
        shader = new GUIShader();
    }

    public void Render(Scene scene)
    {
        ArrayList<AMGUI> amguis = scene.GameplayStatics.getGlobalGuis();

        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        //render
        for(AMGUI gui : amguis)
        {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, SGL.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); //disables bilinear filtering
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            Matrix4f transformMatrix = Maths.createTransformMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformation(transformMatrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public void cleanUp()
    {
        shader.cleanUp();
    }

}
