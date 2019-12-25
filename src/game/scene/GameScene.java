package game.scene;

import assets.entities.Entity;
import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.map.World;
import game.map.persistence.SaveTool;
import game.player.Player;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;

public class GameScene extends Scene {

    World world;
    Player player;

    private int drawDistance = 3;

    public GameScene(){
        initScene();
        setCamera();
        setLighting(1,4,1.3f,1,1,1,0.2f,true);

        player = new Player(1.5f,3f,1.5f, camera);
        player.setWorld(world);
    }

    @Override
    protected void loadContent() {
        //Load TexturedModels
        HashMap<Block.Type, TexturedModel> loadedModels = new HashMap<>();

        loadedModels.put(Block.Type.GRASS, makeBlockModel("grass"));
        loadedModels.put(Block.Type.CONCRETE, makeBlockModel("acacia"));


        //new WorldGenerator(loadedModels).generateNewWorld(); //TODO WORLD GEN
        world = new SaveTool().loadWorldFromSave("myJSON.json", loadedModels);
    }

    @Override
    public void updateScene() {
        player.update();

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            System.exit(0);
        }

        processWorld();
    }

    private void processWorld(){
        int currentCamX = ((int) player.getxPos()) / World.WORLD_X;
        int currentCamY = ((int) player.getzPos()) / World.WORLD_Y;
        int xLowerBound = Math.max(currentCamX - drawDistance, 0);
        int xUpperBound = Math.min(currentCamX + drawDistance, World.WORLD_X-1);
        int yLowerBound = Math.max(currentCamY - drawDistance, 0);
        int yUpperBound = Math.min(currentCamY + drawDistance, World.WORLD_Y-1);
        for(int i =xLowerBound; i <= xUpperBound; i++){
            for(int j=yLowerBound; j<= yUpperBound; j++){
                for(Entity e : world.getChunks()[i][j].getBlocks()){
                    renderer.processEntity(e);
                }
            }
        }
    }


}
