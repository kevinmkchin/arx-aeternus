package assets.entities;

import assets.DamageableEntity;
import assets.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Enemy extends DamageableEntity {

    public Enemy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);

        createAABB(position.x, position.y, position.z, 0.6f, 1.7f);
    }

    public Enemy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float Health) {
        super(model, position, rotX, rotY, rotZ, scale, Health);

        createAABB(position.x, position.y, position.z, 0.6f, 1.7f);
    }

}
