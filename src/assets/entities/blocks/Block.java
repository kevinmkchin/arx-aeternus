package assets.entities.blocks;

import assets.entities.Entity;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
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
                 float scale, AMGameplayStatics gameplayStatics) {
        super(model, new Vector3f(x,y,z), rotX, rotY, rotZ, scale, gameplayStatics);
        this.type = type;
        createBlockAABB();
    }

    public Block(Type type, TexturedModel model,
                 float x, float y, float z, AMGameplayStatics gameplayStatics){

        super(model, new Vector3f(x,y,z),0,0,0,4, gameplayStatics);
        this.type = type;
        //createBlockAABB();
        createAABB(x + 4, x, z + 4, z,y + 4, y);

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
