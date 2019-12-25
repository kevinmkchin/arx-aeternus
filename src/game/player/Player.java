package game.player;

import assets.entities.Camera;
import assets.entities.Entity;
import assets.entities.collisions.AABB;
import assets.entities.collisions.CollisionManager;
import assets.helpers.Plane;
import game.map.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import tools.Maths;

public class Player {

    private final float PLAYER_WIDTH = 0.6f;
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
    private float camOffsetFromTop = 0.1f;
    private Camera camera;
    private AABB collisionBox;
    private World world;

    private float gravity = 0.02f;
    private float ySpeed = 0f;
    private float maxYSpeed = 0.5f;
    private float jumpSpeed = 0.25f;
    private boolean spaceDown = false;
    private boolean canJump = true;

    private boolean freeMode = false; //TODO set this back to false for normal fps

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

        updateY();

        Vector3f camDir = camera.getCamDirection();
        float xUnit = camDir.getX()
                / (float) Math.sqrt((camDir.getX()*camDir.getX()) + (camDir.getZ()*camDir.getZ()));
        float zUnit = camDir.getZ()
                / (float) Math.sqrt((camDir.getX()*camDir.getX()) + (camDir.getZ()*camDir.getZ()));

        if(!freeMode){
            camera.update();
            Mouse.setGrabbed(true);
        }else{
            doFreeMode();
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

        //DEBUGGING POSITIONS
        if(Keyboard.isKeyDown(Keyboard.KEY_J)){
            System.out.println("Player Position: " + xPos + " " + yPos + " " + zPos);
            System.out.println("AABB Min Corner: " + collisionBox.getMinCorner().getX() + " "
                    + collisionBox.getMinCorner().getY() + " " + collisionBox.getMinCorner().getZ());
            System.out.println("AABB Max Corner: " + collisionBox.getMaxCorner().getX() + " "
                    + collisionBox.getMaxCorner().getY() + " " + collisionBox.getMaxCorner().getZ());
            System.out.println("Camera Position: " + camera.getPosition().getX() + " "
                    + camera.getPosition().getY() + " " + camera.getPosition().getZ());
            System.out.println(" ");
        }

    }

    /// CALL AFTER PLAYER MOVES SINCE CAMERA NEEDS TO MOVE TOO
    private void cameraUpdatePosition(float newPlayerX, float newPlayerY, float newPlayerZ){
        camera.setPosition(new Vector3f(newPlayerX,
                newPlayerY + PLAYER_HEIGHT - camOffsetFromTop,
                newPlayerZ));
    }

    /// Manage Jumping and Gravity
    private void updateY(){

        ySpeed -= gravity;
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !spaceDown){
            spaceDown = true;
            if(canJump) {
                ySpeed = jumpSpeed;
            }
        }

        if(spaceDown && !Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            spaceDown = false;
        }

        if(Math.abs(ySpeed) >= maxYSpeed){
            ySpeed = Maths.getSign(ySpeed) * maxYSpeed;
        }

        Entity badYEntity = CollisionManager.checkCollision(world,
                xPos, yPos, zPos,
                Plane.Y,
                true,
                ySpeed,
                PLAYER_WIDTH, PLAYER_HEIGHT);
        if(badYEntity == null){
            yPos += ySpeed;
            canJump = false;
        }else{
            float deltaY = 0;
            if(ySpeed <= 0) {
                while (!badYEntity.getAABB().isYColliding(yPos + deltaY)) {
                    deltaY -= 0.001f;
                }
                canJump = true;
                yPos += deltaY + 0.001f;
            }else{
                while (!badYEntity.getAABB().isYColliding(yPos + PLAYER_HEIGHT + deltaY)) {
                    deltaY += 0.001f;
                }
                yPos += deltaY - 0.001f;
            }
            ySpeed = 0;
        }
    }

    /// Free Camera Mode
    private void doFreeMode(){
        if(Mouse.isButtonDown(1)) {
            camera.update();

            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                yPos += playerSpeed;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                yPos -= playerSpeed;
            }
        }
    }

    private void moveForward(float xUnit, float zUnit){
        moveWithCollision(true, xUnit * playerSpeed, true, zUnit * playerSpeed);
    }
    private void moveBackward(float xUnit, float zUnit){
        moveWithCollision(false, xUnit * playerSpeed, false, zUnit * playerSpeed);
    }
    private void strafeLeft(float xUnit, float zUnit){
        moveWithCollision(true, zUnit * playerSpeed, false, xUnit * playerSpeed);
    }
    private void strafeRight(float xUnit, float zUnit){
        moveWithCollision(false, zUnit * playerSpeed, true, xUnit * playerSpeed);
    }

    private void moveWithCollision(boolean addX, float deltaX, boolean addZ, float deltaZ){
        Entity badE = CollisionManager.checkCollision(world,
                xPos,yPos,zPos,
                Plane.X,
                addX,
                deltaX,
                PLAYER_WIDTH,PLAYER_HEIGHT);

        if(badE == null){
            if(addX){
                xPos += deltaX;
            }else{
                xPos -= deltaX;
            }
        }else{
            //DO PRECISION COLLISION
            float delta = CollisionManager.getPixelPerfectDelta(badE,
                    xPos,
                    Plane.X,
                    addX,
                    deltaX,
                    PLAYER_WIDTH, PLAYER_HEIGHT);
            if(addX){
                xPos += delta;
            }else{
                xPos -= delta;
            }
        }

        badE = CollisionManager.checkCollision(world,
                xPos,yPos,zPos,
                Plane.Z,
                addZ,
                deltaZ,
                PLAYER_WIDTH,PLAYER_HEIGHT);

        if(badE == null){
            if(addZ){
                zPos += deltaZ;
            }else{
                zPos -= deltaZ;
            }
        }else{
            //DO PRECISION COLLISION
            float delta = CollisionManager.getPixelPerfectDelta(badE,
                    zPos,
                    Plane.Z,
                    addZ,
                    deltaZ,
                    PLAYER_WIDTH, PLAYER_HEIGHT);
            if(addZ){
                zPos += delta;
            }else{
                zPos -= delta;
            }
        }
    }

    public World getWorld() {
        return world;
    }
    public void setWorld(World world) {
        this.world = world;
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
