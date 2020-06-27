package assets.entities;

import assets.DamageableEntity;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
import game.player.Player;
import org.lwjgl.util.vector.Vector3f;

public class Enemy extends DamageableEntity {


    public Enemy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, AMGameplayStatics gameplayStatics) {
        super(model, position, rotX, rotY, rotZ, scale, gameplayStatics);

        createAABB(position.x, position.y, position.z, 0.9f, 1.7f);
    }

    public Enemy(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float Health, AMGameplayStatics gameplayStatics) {
        super(model, position, rotX, rotY, rotZ, scale, Health, gameplayStatics);

        createAABB(position.x, position.y, position.z, 0.6f, 1.7f);
    }

    //called every tick
    @Override
    public void Update()
    {
        super.Update();

        // Rotate enemy to look at player
        Player p = GameplayStatics.getGlobalPlayer();
        Vector3f NeedToLookDirection = new Vector3f(p.getxPos() - getPosition().x, p.getyPos() - getPosition().y, p.getzPos() - getPosition().z);
        double bruh = Math.atan(NeedToLookDirection.x / NeedToLookDirection.z);
        float RotationToLookAtPlayer = (float) (bruh * 180 / Math.PI);
        setRotY(RotationToLookAtPlayer);



        if(NeedToLookDirection.length() < 20.f)
        {
            NeedToLookDirection.normalise(NeedToLookDirection);
            increasePosition(NeedToLookDirection.x*0.02f, 0, NeedToLookDirection.z*0.02f);
        }

    }

}
