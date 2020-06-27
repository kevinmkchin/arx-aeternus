package game;

import game.scene.GameScene;
import game.scene.Scene;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import renderEngine.AMGUIRenderer;
import renderEngine.DisplayManager;
import renderEngine.ModelLoader;

public class MainGameLoop {

    public static void main(String[] args) {

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
