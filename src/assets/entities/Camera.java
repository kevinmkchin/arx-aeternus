package assets.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import tools.Maths;

public class Camera {

    private Vector3f position = new Vector3f(10,10,10);
    private float pitch = 0;
    private float yaw = 180;
    private float sensitivity = 0.1f;
    private boolean freeMode = false;
    private float maxPitch = 89; //in deg

    public Vector3f getCamDirection(){
        //Calculate direction vector from Pitch and Yaw
        float x = (float) (Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw)));
        float y = (float) -Math.sin(Math.toRadians(pitch));
        float z = (float) -(Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)));
        Vector3f camDirectionVector = new Vector3f(x,y,z);
        return camDirectionVector;
    }

    public void update(){
        calculatePitch();
        calculateYaw();
    }

    private void calculatePitch(){
        float pitchChange = Mouse.getDY() * sensitivity;
        pitch -= pitchChange;
        if(!freeMode){ //limit pitch
            if(Math.abs(pitch) >= maxPitch){
                pitch = Maths.getSign(pitch) * maxPitch;
            }
        }
    }
    private void calculateYaw(){
        float yawChange = Mouse.getDX() * sensitivity;
        yaw += yawChange;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }
    public float getYaw() {
        return yaw;
    }

    public void setFreeMode(boolean freeMode){
        this.freeMode = freeMode;
    }

}
