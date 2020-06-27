package game.map.persistence;

import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
import game.map.World;

import java.util.HashMap;

public class SaveTool {

    private String saveLocation = "res/";

    public World loadWorldFromSave(String saveFileName, HashMap<Block.Type, TexturedModel> loadedModels, AMGameplayStatics gameplayStatics){
        return new JSONReader(loadedModels, gameplayStatics).loadWorld(saveLocation + saveFileName);
    }

    public void writeWorldToSave(String saveFileName, World world){
        JSONWriter.writeWorldToSave(saveLocation + saveFileName, world);
    }


}
