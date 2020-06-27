package renderEngine;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.ImageIOImageData;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.ByteBuffer;

public class DisplayManager {

    private static final int WIDTH = 1600; //resolution x
    private static final int HEIGHT = 900; //resolution y
    private static final int FPS_CAP = 100; //framerate cap
    private static boolean vSyncOn = false; //v-sync on?

    public static void createDisplay(){

        ContextAttribs attribs = new ContextAttribs(3,2) //opengl 3.2
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat().withSamples(0), attribs); //TODO MULTISAMPLING ANTIALIASING
            Display.setTitle("ARX AETERNUS");


            Mouse.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
