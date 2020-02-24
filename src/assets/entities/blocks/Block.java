package assets.entities.blocks;

import assets.entities.Entity;
import assets.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Block extends Entity {

    public enum Type {
        GRASS,
        CONCRETE,
        DIRT,
        LEAVES
    }

    private Type type;


    public Block(Type type, TexturedModel model,
                 float x, float y, float z,
                 float rotX, float rotY, float rotZ,
                 float scale) {
        super(model, new Vector3f(x,y,z), rotX, rotY, rotZ, scale);
        this.type = type;
        createBlockAABB();
    }

    public Block(Type type, TexturedModel model,
                 float x, float y, float z){
        super(model, new Vector3f(x,y,z),0,0,0,1);
        this.type = type;
        createBlockAABB();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getX(){
        return (int) getPosition().getX();
    }

    public int getY(){
        return (int) getPosition().getY();
    }

    public int getZ(){
        return (int) getPosition().getZ();
    }

}
