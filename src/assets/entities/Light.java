package assets.entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {

    /**
     * Represents a light source (e.g. sun) or directional lighting (e.g. sun that is really far away)
     * Position vector represents one of:
     * - Light's position in 3D space (if not directional)
     * - The directional towards the light (if directional)
     * Color vector represents the color of the light.
     * */

    private Vector3f position;
    private Vector3f color;
    private float ambientLight;
    private boolean directional;

    public Light(Vector3f position, Vector3f color, float ambientLight, boolean directional){
        this.position = position;
        this.color = color;
        this.ambientLight = ambientLight;
        this.directional = directional;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(float ambientLight) {
        this.ambientLight = ambientLight;
    }

    public boolean isDirectional() {
        return directional;
    }

    public void setDirectional(boolean directional) {
        this.directional = directional;
    }
}
