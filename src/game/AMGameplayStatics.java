package game;

import assets.AMGUI;
import assets.DamageableEntity;
import assets.entities.Camera;
import assets.entities.Entity;
import game.map.World;
import game.player.Player;
import renderEngine.MainRenderer;
import renderEngine.ModelLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class AMGameplayStatics {

    public enum Tags
    {
        ENEMIES,
        PICKUPS,
        COLLIDABLE
    }

    private Player GlobalPlayer;
    private Camera GlobalCamera;
    private World GlobalWorld;
    private HashMap<Tags, ArrayList<Entity>> EntityMap;
    private MainRenderer GlobalMainRenderer;
    private ModelLoader GlobalModelLoader;
    private ArrayList<AMGUI> GlobalGuis;
    private ArrayList<DamageableEntity> EntitiesToRemove;


    public AMGameplayStatics()
    {
        //TODO put more arraylists in this map if you want to use more TAGS
        EntityMap = new HashMap<>();
        EntityMap.put(Tags.ENEMIES, new ArrayList<>());
        EntityMap.put(Tags.PICKUPS, new ArrayList<>());
        GlobalGuis = new ArrayList<>();
        EntitiesToRemove = new ArrayList<>();
    }

    public ArrayList<Entity> GetEntitiesWithTag(Tags Tag)
    {
        return EntityMap.get(Tag);
    }

    public Player getGlobalPlayer() {
        return GlobalPlayer;
    }

    public void setGlobalPlayer(Player globalPlayer) {
        GlobalPlayer = globalPlayer;
    }

    public Camera getGlobalCamera() {
        return GlobalCamera;
    }

    public void setGlobalCamera(Camera globalCamera) {
        GlobalCamera = globalCamera;
    }

    public World getGlobalWorld() {
        return GlobalWorld;
    }

    public void setGlobalWorld(World globalWorld) {
        GlobalWorld = globalWorld;
    }

    public MainRenderer getGlobalMainRenderer() {
        return GlobalMainRenderer;
    }

    public void setGlobalMainRenderer(MainRenderer globalMainRenderer) {
        GlobalMainRenderer = globalMainRenderer;
    }

    public ModelLoader getGlobalModelLoader() {
        return GlobalModelLoader;
    }

    public void setGlobalModelLoader(ModelLoader globalModelLoader) {
        GlobalModelLoader = globalModelLoader;
    }

    public ArrayList<AMGUI> getGlobalGuis() {
        return GlobalGuis;
    }

    public void addGlobalGui(AMGUI newGui)
    {
        GlobalGuis.add(newGui);
    }

    public ArrayList<DamageableEntity> getEntitiesToRemove() {
        return EntitiesToRemove;
    }

}
