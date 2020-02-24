package game.map.generation;

import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.map.Chunk;

import java.util.HashMap;
import java.util.Random;

public class ChunkGeneration {

    //designs the 16x16 chunk of blocks


    public void growTrees(Chunk chunk, HashMap<Block.Type, TexturedModel> models){

        for(int i = 0; i < Chunk.CHUNK_SIZE_X; i++){
            for(int j = 0; j < Chunk.CHUNK_SIZE_Y; j++){
                if(new Random().nextInt(24) == 1){
                    for(int k=0; k<9; k++){
                        Block trunk = new Block(Block.Type.CONCRETE, models.get(Block.Type.CONCRETE),
                                i + chunk.getChunkX()*Chunk.CHUNK_SIZE_X,
                                k + 1,
                                j + chunk.getChunkY()*Chunk.CHUNK_SIZE_Y,
                                0,0,0,1);
                        chunk.putBlock(trunk);
                    }
                    for(int l=0; l<6; l++){
                        Block leaf = new Block(Block.Type.LEAVES, models.get(Block.Type.LEAVES),
                                i + chunk.getChunkX()*Chunk.CHUNK_SIZE_X + 1,
                                l + 5,
                                j + chunk.getChunkY()*Chunk.CHUNK_SIZE_Y,
                                0,0,0,1);
                        chunk.putBlock(leaf);
                        leaf = new Block(Block.Type.LEAVES, models.get(Block.Type.LEAVES),
                                i + chunk.getChunkX()*Chunk.CHUNK_SIZE_X - 1,
                                l + 5,
                                j + chunk.getChunkY()*Chunk.CHUNK_SIZE_Y,
                                0,0,0,1);
                        chunk.putBlock(leaf);
                        leaf = new Block(Block.Type.LEAVES, models.get(Block.Type.LEAVES),
                                i + chunk.getChunkX()*Chunk.CHUNK_SIZE_X,
                                l + 5,
                                j + chunk.getChunkY()*Chunk.CHUNK_SIZE_Y + 1,
                                0,0,0,1);
                        chunk.putBlock(leaf);
                        leaf = new Block(Block.Type.LEAVES, models.get(Block.Type.LEAVES),
                                i + chunk.getChunkX()*Chunk.CHUNK_SIZE_X,
                                l + 5,
                                j + chunk.getChunkY()*Chunk.CHUNK_SIZE_Y - 1,
                                0,0,0,1);
                        chunk.putBlock(leaf);
                    }
                    Block leaf = new Block(Block.Type.LEAVES, models.get(Block.Type.LEAVES),
                            i + chunk.getChunkX()*Chunk.CHUNK_SIZE_X,
                            11,
                            j + chunk.getChunkY()*Chunk.CHUNK_SIZE_Y,
                            0,0,0,1);
                    chunk.putBlock(leaf);
                }
            }
        }
    }

}
