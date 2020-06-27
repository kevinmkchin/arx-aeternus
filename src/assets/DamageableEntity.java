package assets;

import assets.entities.Entity;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
import org.lwjgl.util.vector.Vector3f;

public class DamageableEntity extends Entity {

    float Health;

    public DamageableEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, AMGameplayStatics InGameplayStatics) {
        super(model, position, rotX, rotY, rotZ, scale, InGameplayStatics);
        Health = 100.f;
    }

    public DamageableEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float Health, AMGameplayStatics InGameplayStatics) {
        super(model, position, rotX, rotY, rotZ, scale, InGameplayStatics);
        this.Health = Health;
    }

    public void AffectHealth(float Delta)
    {
        Health += Delta;
    }

    public void Update()
    {
        // DEAD
        if(Health <= 0)
        {
            GameplayStatics.getEntitiesToRemove().add(this);
        }
    }

}
