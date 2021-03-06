package game.player;

import assets.AMGUI;
import assets.DamageableEntity;
import assets.entities.Entity;
import game.AMGameplayStatics;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class WeaponSystem {



    // I wrote this for absolutely no reason because I can just get the direction that the camera is facing....
//    public void CenterRaycast(Matrix4f InProjectionMatrix, Matrix4f InViewMatrix)
//    {
//        //4d homogeneous clip coordinates
//        Vector4f RayClip = new Vector4f(0, 0, -1, 1); //-1 z for "into the screen"
//
//        //4d eye coordinates
//        Matrix4f InvertedProjectionMatrix = new Matrix4f();
//        Matrix4f.invert(InProjectionMatrix, InvertedProjectionMatrix);
//        Vector4f RayEye = new Vector4f();
//        Matrix4f.transform(InvertedProjectionMatrix, RayClip, RayEye);
//        RayEye.z = -1.f;
//        RayEye.w = 0.f;
//
//        //4d world coordinates
//        Matrix4f InvertedViewMatrix = new Matrix4f();
//        Matrix4f.invert(InViewMatrix, InvertedViewMatrix);
//        Vector4f RayWorld4f = new Vector4f();
//        Matrix4f.transform(InvertedViewMatrix, RayEye, RayWorld4f);
//        Vector3f RayWorld = new Vector3f();
//        RayWorld.setX(RayWorld4f.getX());
//        RayWorld.setY(RayWorld4f.getY());
//        RayWorld.setZ(RayWorld4f.getZ());
//        RayWorld.normalise(RayWorld);
//
//        System.out.println(RayWorld.toString());
//    }

    public boolean bPendingFire;

    private int FireInterval = 20;
    private int FireCooldown = 0;

    AMGameplayStatics GameplayStatics;
    AMGUI pistol;

    public WeaponSystem(AMGameplayStatics GameplayStatics)
    {
        this.GameplayStatics = GameplayStatics;

        //TODO
        pistol = new AMGUI(GameplayStatics.getGlobalModelLoader().loadGUI("auto1"), new Vector2f(0.67f,-0.63f), new Vector2f(0.5f, 0.48f));
        GameplayStatics.addGlobalGui(pistol);
    }

    public void StartUse()
    {
        bPendingFire = true;
    }

    public void StopUse()
    {
        bPendingFire = false;
        FireCooldown = 0;
    }

    public void Update()
    {
        //update pistol ui
        if(FireCooldown >= 18)
        {
            GameplayStatics.getGlobalGuis().remove(pistol);
            pistol = new AMGUI(GameplayStatics.getGlobalModelLoader().loadGUI("auto2"), new Vector2f(0.67f,-0.63f), new Vector2f(0.5f, 0.48f));
            GameplayStatics.addGlobalGui(pistol);
        }
        else if(FireCooldown >= 10)
        {
            GameplayStatics.getGlobalGuis().remove(pistol);
            pistol = new AMGUI(GameplayStatics.getGlobalModelLoader().loadGUI("auto3"), new Vector2f(0.67f,-0.63f), new Vector2f(0.5f, 0.48f));
            GameplayStatics.addGlobalGui(pistol);
        }
        else if(FireCooldown >= 5)
        {
            GameplayStatics.getGlobalGuis().remove(pistol);
            pistol = new AMGUI(GameplayStatics.getGlobalModelLoader().loadGUI("auto4"), new Vector2f(0.67f,-0.63f), new Vector2f(0.5f, 0.48f));
            GameplayStatics.addGlobalGui(pistol);
        }
        else if(FireCooldown <= 1)
        {
            GameplayStatics.getGlobalGuis().remove(pistol);
            pistol = new AMGUI(GameplayStatics.getGlobalModelLoader().loadGUI("auto1"), new Vector2f(0.67f,-0.63f), new Vector2f(0.5f, 0.48f));
            GameplayStatics.addGlobalGui(pistol);
        }

        //update cooldown
        if(bPendingFire && FireCooldown <= 0)
        {
            CenterRaycast(15000);
            FireCooldown = FireInterval;
        }
        else
        {
            --FireCooldown;
        }
    }

    public void CenterRaycast(int RayLengthInCM)
    {
        //CamDirection is length 1 metre
        Vector3f CamPos = GameplayStatics.getGlobalCamera().getPosition();
        Vector3f CamDir = GameplayStatics.getGlobalCamera().getCamDirection();
        ArrayList<Entity> EnemiesToCheck = GameplayStatics.GetEntitiesWithTag(AMGameplayStatics.Tags.ENEMIES);

        int iterations = RayLengthInCM / 10; //this makes it so we check collision in 10cm intervals
        for(int i=0; i < iterations; ++i)
        {
            Vector3f LineEndPoint = new Vector3f();
            Vector3f Direction = new Vector3f();
            //Scale the camera direction
            Direction.x = CamDir.x * i * 0.1f;
            Direction.y = CamDir.y * i * 0.1f;
            Direction.z = CamDir.z * i * 0.1f;
            //Add it to cam pos to get end point of raycast
            Vector3f.add(CamPos, Direction, LineEndPoint);

            for(Entity e : EnemiesToCheck)
            {
                //DO DAMAGE
                if(e.getAABB().isPointColliding(LineEndPoint))
                {
                    DamageableEntity de = (DamageableEntity) e;
                    de.AffectHealth(-30.f);

                    return;
                }
            }

        }
    }


}
