package game.map.generation;

import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.map.Chunk;
import game.map.World;

import java.util.HashMap;

public class WorldGenerator {

    HashMap<Block.Type, TexturedModel> loadedModels;

    public WorldGenerator(HashMap<Block.Type, TexturedModel> loadedModels){
        this.loadedModels = loadedModels;
    }

    public World generateNewWorld(){
        World world = new World();

        int[][] levelArray = new DrunkardWalker().DrunkardWalk(32,32,200);

        for(int i = 0; i < world.WORLD_SIZE_X; ++i){
            for(int j = 0; j< world.WORLD_SIZE_Y; ++j){
                Chunk chunk = generateChunk(i, j, levelArray);
                world.getChunks()[i][j] = chunk;
            }
        }

        world.levelData = levelArray;

        return world;
    }

    private Chunk generateChunk(int x, int y, int[][] tiles){
        Chunk chunk = new Chunk(x, y);

        for(int i=0; i < Chunk.CHUNK_SIZE_X; i++){
            for(int j=0; j < Chunk.CHUNK_SIZE_Y; j++){

                int thisOne = tiles[x * Chunk.CHUNK_SIZE_X + i][y * Chunk.CHUNK_SIZE_Y + j];
                if(thisOne == 1)
                {
                    Block concrete = new Block(Block.Type.CONCRETE, loadedModels.get(Block.Type.CONCRETE),
                            (i + x*Chunk.CHUNK_SIZE_X) * World.CUBE_SIZE,
                            4,
                            (j + y*Chunk.CHUNK_SIZE_Y) * World.CUBE_SIZE);
                    chunk.putBlock(concrete);
                    concrete = new Block(Block.Type.CONCRETE, loadedModels.get(Block.Type.CONCRETE),
                            (i + x*Chunk.CHUNK_SIZE_X) * World.CUBE_SIZE,
                            8,
                            (j + y*Chunk.CHUNK_SIZE_Y) * World.CUBE_SIZE);
                    chunk.putBlock(concrete);
                }
                else if(thisOne == 0)
                {
                    Block grass = new Block(Block.Type.GRASS,loadedModels.get(Block.Type.GRASS), (i + x*Chunk.CHUNK_SIZE_X) * 4, 0, (j + y*Chunk.CHUNK_SIZE_Y) * 4);
                    chunk.putBlock(grass);
                    grass = new Block(Block.Type.GRASS,loadedModels.get(Block.Type.GRASS), (i + x*Chunk.CHUNK_SIZE_X) * 4, 12, (j + y*Chunk.CHUNK_SIZE_Y) * 4);
                    chunk.putBlock(grass);
                }

            }
        }

        return chunk;
    }

}
