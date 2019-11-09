package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final int WIDTH = 1600; //resolution x
    private static final int HEIGHT = 900; //resolution y
    private static final int FPS_CAP = 60; //framerate cap
    private static boolean vSyncOn = false; //v-sync on?

    public static void createDisplay(){

        ContextAttribs attribs = new ContextAttribs(3,2) //opengl 3.2
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat().withSamples(4), attribs); //TODO MULTISAMPLING ANTIALIASING
            Display.setTitle("Cataclysm 3D Game");

            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glViewport(0,0,WIDTH,HEIGHT); //game takes up entire display

    }

    public static void updateDisplay(){
        Display.sync(FPS_CAP); //FRAME RATE CAP
        Display.setVSyncEnabled(vSyncOn); //V-SYNC

        Display.update();
    }

    public static void closeDisplay(){
        Mouse.destroy();
        Display.destroy();
    }

}
