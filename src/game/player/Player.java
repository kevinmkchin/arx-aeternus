package game.player;

import assets.entities.Camera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Player {

    private final float PLAYER_WIDTH = 0.6f;
    private final float PLAYER_HEIGHT = 1.7f;

    private float playerSpeed = 0.08f;
    private float xPos;
    private float yPos;
    private float zPos;
    private Camera camera;

    private boolean freeMode = true; //TODO set this back to false for normal fps

    public Player(float xPos, float yPos, float zPos, Camera camera){
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.camera = camera;
        camera.setPosition(new Vector3f(xPos, yPos, zPos));
    }

    public void update(){

        Vector3f camDir = camera.getCamDirection();
        float xUnit = camDir.getX()
                / (float) Math.sqrt((camDir.getX() * camDir.getX()) + (camDir.getZ() * camDir.getZ()));
        float zUnit = camDir.getZ()
                / (float) Math.sqrt((camDir.getX() * camDir.getX()) + (camDir.getZ() * camDir.getZ()));

        if(freeMode){
            if(Mouse.isButtonDown(1)) {
                camera.update();

                if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                    yPos += playerSpeed;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                    yPos -= playerSpeed;
                }
            }
        }else{
            camera.update();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            moveForward(xUnit, zUnit);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            moveBackward(xUnit, zUnit);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            strafeLeft(xUnit, zUnit);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            strafeRight(xUnit, zUnit);
        }

        camera.setPosition(new Vector3f(xPos, yPos, zPos));

    }

    private void moveForward(float xUnit, float zUnit){
        xPos += xUnit * playerSpeed;
        zPos += zUnit * playerSpeed;
    }
    private void moveBackward(float xUnit, float zUnit){
        xPos -= xUnit * playerSpeed;
        zPos -= zUnit * playerSpeed;
    }
    private void strafeLeft(float xUnit, float zUnit){
        if(xUnit * zUnit >= 0){ //i.e. + + or - -
            xPos += zUnit * playerSpeed;
            zPos -= xUnit * playerSpeed;
        }else{
            xPos += zUnit * playerSpeed;
            zPos -= xUnit * playerSpeed;
        }
    }
    private void strafeRight(float xUnit, float zUnit){
        if(xUnit * zUnit >= 0){ //i.e. + + or - -
            xPos -= zUnit * playerSpeed;
            zPos += xUnit * playerSpeed;
        }else{
            xPos -= zUnit * playerSpeed;
            zPos += xUnit * playerSpeed;
        }
    }

    public float getxPos() {
        return xPos;
    }
    public float getyPos() {
        return yPos;
    }
    public float getzPos() {
        return zPos;
    }
}
