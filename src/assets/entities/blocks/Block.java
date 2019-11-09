package assets.entities.blocks;

import assets.entities.Entity;
import assets.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Block extends Entity {

    public Block(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

}
