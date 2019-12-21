package game.player;

import assets.entities.Camera;
import assets.entities.collisions.AABB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Player {

    private final float PLAYER_WIDTH = 1f;
    private final float PLAYER_HEIGHT = 1.7f;

    /*
    * Player origin will be at xPos, yPos, zPos.
    * The Player origin is at the 'feet' of the Player.
    * xPos is the center x of the player.
    * zPos is the center z of the player.
    * yPos is the BASE of the player. It is the bottom of the player.*/
    private float playerSpeed = 0.08f;
    private float xPos;
    private float yPos;
    private float zPos;
    private Camera camera;

    private AABB collisionBox;

    private boolean freeMode = true; //TODO set this back to false for normal fps

    public Player(float xPos, float yPos, float zPos, Camera camera){
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.camera = camera;
        collisionBox = new AABB(xPos, yPos, zPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        cameraUpdatePosition(xPos, yPos, zPos);
    }

    public void update(){
        float oldX = xPos;
        float oldY = yPos;
        float oldZ = zPos;

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

        /// UPDATE THE AABB AND CAMERA
        collisionBox.updateBB(xPos - oldX, yPos - oldY, zPos - oldZ);
        cameraUpdatePosition(xPos, yPos, zPos);

        /* //DEBUGGING POSITIONS
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            System.out.println("Player Position: " + xPos + " " + yPos + " " + zPos);
            System.out.println("AABB Min Corner: " + collisionBox.getMinCorner().getX() + " "
                    + collisionBox.getMinCorner().getY() + " " + collisionBox.getMinCorner().getZ());
            System.out.println("AABB Max Corner: " + collisionBox.getMaxCorner().getX() + " "
                    + collisionBox.getMaxCorner().getY() + " " + collisionBox.getMaxCorner().getZ());
            System.out.println("Camera Position: " + camera.getPosition().getX() + " "
                    + camera.getPosition().getY() + " " + camera.getPosition().getZ());
            System.out.println(" ");
        }
        */

    }

    /// CALL AFTER PLAYER MOVES SINCE CAMERA NEEDS TO MOVE TOO
    private void cameraUpdatePosition(float newPlayerX, float newPlayerY, float newPlayerZ){
        camera.setPosition(new Vector3f(newPlayerX, newPlayerY + PLAYER_HEIGHT, newPlayerZ));
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
