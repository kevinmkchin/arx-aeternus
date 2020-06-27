package game;

import game.scene.GameScene;
import game.scene.Scene;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.ImageIOImageData;
import renderEngine.AMGUIRenderer;
import renderEngine.DisplayManager;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.ByteBuffer;

public class MainGameLoop {

    public static void main(String[] args) {

        try
        {
            Display.setIcon(new ByteBuffer[] {
                    new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/textures/16.png")), false, false, null),
                    new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/textures/32.png")), false, false, null)
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        DisplayManager.createDisplay();

        Scene activeScene = new GameScene();

        AMGUIRenderer amguiRenderer = new AMGUIRenderer();

        ///MAIN GAME LOOP (GAME LOGIC / RENDERING / UPDATE DISPLAY)
        while(!Display.isCloseRequested()){
            //Mouse.setGrabbed(true);
            activeScene.updateScene();
            activeScene.renderScene();

            amguiRenderer.Render(activeScene);

            ///INITIALIZE SCENES HERE BY CHECKING FOR STATE CHANGE
            /// for example
            /// if (gameState_justSwitched) then
            ///     if (gameState_1) then scene = new FirstScene();
            ///     if (gameState_2) then scene = new SecondScene();

            DisplayManager.updateDisplay();
            
        }

        Mouse.setGrabbed(false);
        Mouse.destroy();

        activeScene.closeScene(); //TODO move this close scene to when scene switches
        DisplayManager.closeDisplay();
    }

}
