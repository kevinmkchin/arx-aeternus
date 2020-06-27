package assets;

import assets.entities.Entity;
import assets.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class DamageableEntity extends Entity {

    float Health;

    public DamageableEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        Health = 100.f;
    }

    public DamageableEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float Health) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.Health = Health;
    }

    public void AffectHealth(float Delta)
    {
        Health += Delta;
    }

}
