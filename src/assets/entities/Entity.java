package assets.entities;

import assets.entities.collisions.AABB;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

    protected AMGameplayStatics GameplayStatics;

    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    private AABB collisionBox = null;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, AMGameplayStatics InGameplayStatics) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        GameplayStatics = InGameplayStatics;
    }

    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
        if(collisionBox != null){
            collisionBox.updateBB(dx,dy,dz);
        }
    }

    public void increaseRotation(float drx, float dry, float drz){
        this.rotX += drx;
        this.rotY += dry;
        this.rotZ += drz;
        ///then collisionBox gets messed up
    }

    /// AABB COLLISIONS
    public void createAABB(float xMax, float xMin, float zMax, float zMin, float yMax, float yMin){
        collisionBox = new AABB(xMax, xMin, zMax, zMin, yMax, yMin);
    }
    public void createAABB(float xMin, float yMin, float zMin, float sideLength){
        collisionBox = new AABB(xMin, yMin, zMin, sideLength);
    }
    public void createAABB(float xCenter, float yBase, float zCenter, float width, float height){
        collisionBox = new AABB(xCenter, yBase, zCenter, width, height);
    }
    public void createBlockAABB(){
        collisionBox = new AABB(position.x, position.y, position.z, 4);
        //requires block side length to be same size
    }
    public AABB getAABB() {
        return collisionBox;
    }
    public void setAABB(AABB boundingBox) {
        this.collisionBox = boundingBox;
    }

    // GETTERS & SETTERS
    public TexturedModel getModel() {
        return model;
    }
    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        collisionBox.updateBB(position.x - this.position.x,
                position.y - this.position.y,
                position.z - this.position.z); //might be broken idk
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }

}
