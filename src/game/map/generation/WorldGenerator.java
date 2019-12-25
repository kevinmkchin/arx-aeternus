package game.map.generation;

import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.map.Chunk;
import game.map.World;
import game.map.persistence.SaveTool;

import java.util.HashMap;
import java.util.Random;

public class WorldGenerator {

    HashMap<Block.Type, TexturedModel> loadedModels;

    public WorldGenerator(HashMap<Block.Type, TexturedModel> loadedModels){
        this.loadedModels = loadedModels;
    }

    public void generateNewWorld(){
        World world = new World();

        //TODO GET RID OF THIS STUPID ONE LATER
        for(int i = 0; i < world.WORLD_SIZE_X; i++){
            for(int j = 0; j< world.WORLD_SIZE_Y; j++){
                Chunk chunk = generateChunk(i, j);
                world.getChunks()[i][j] = (chunk);
            }
        }

        new SaveTool().writeWorldToSave("myJSON.json", world);
    }

    //TODO JUST A PLACEHOLDER ALGORITHM FOR GENERATING CHUNK
    private Chunk generateChunk(int x, int y){
        Chunk chunk = new Chunk(x, y);

        for(int i=0; i < Chunk.CHUNK_SIZE_X; i++){
            for(int j=0; j < Chunk.CHUNK_SIZE_Y; j++){
                Block concrete = new Block(Block.Type.CONCRETE,loadedModels.get(Block.Type.CONCRETE),i + x*Chunk.CHUNK_SIZE_X,1,j + y*Chunk.CHUNK_SIZE_Y);
                Block c2 = new Block(Block.Type.CONCRETE,loadedModels.get(Block.Type.CONCRETE),i + x*Chunk.CHUNK_SIZE_X,5,j + y*Chunk.CHUNK_SIZE_Y);
                Block grass = new Block(Block.Type.GRASS,loadedModels.get(Block.Type.GRASS), i + x*Chunk.CHUNK_SIZE_X, 2, j + y*Chunk.CHUNK_SIZE_Y);

                chunk.putBlock(concrete);
                //chunk.putBlock(c2);
                if(new Random().nextInt(2) == 1) {
                    chunk.putBlock(grass);
                }
            }
        }

        return chunk;
    }

}
