package game.scene;

import assets.entities.Entity;
import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.map.World;
import game.map.generation.WorldGenerator;
import game.map.persistence.SaveTool;

import java.util.HashMap;

public class GameScene extends Scene {

    World world;

    private int drawDistance = 3;

    public GameScene(){
        initScene();
        setCamera();
        setLighting(1,4,1.3f,1,1,1,0.2f,true);
    }

    @Override
    protected void loadContent() {
        //Load TexturedModels
        HashMap<Block.Type, TexturedModel> loadedModels = new HashMap<>();

        loadedModels.put(Block.Type.GRASS, makeBlockModel("grass"));
        loadedModels.put(Block.Type.CONCRETE, makeBlockModel("acacia"));


        new WorldGenerator(loadedModels).generateNewWorld();
        world = new SaveTool().loadWorldFromSave("myJSON.json", loadedModels);
    }

    @Override
    public void updateScene() {
        camera.update();



        renderWorld();
    }

    private void renderWorld(){
        int currentCamX = ((int) camera.getPosition().getX()) / 16;
        int currentCamY = ((int) camera.getPosition().getZ()) / 16;
        int xLowerBound = Math.max(currentCamX - drawDistance, 0);
        int xUpperBound = Math.min(currentCamX + drawDistance, world.WORLD_X);
        int yLowerBound = Math.max(currentCamY - drawDistance, 0);
        int yUpperBound = Math.min(currentCamY + drawDistance, world.WORLD_Y);
        for(int i =xLowerBound; i <= xUpperBound; i++){
            for(int j=yLowerBound; j<= yUpperBound; j++){
                for(Entity e : world.getChunks()[i][j].getBlocks()){
                    renderer.processEntity(e);
                }
            }
        }
    }


}
