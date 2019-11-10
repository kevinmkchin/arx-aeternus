package game.map;

import assets.entities.blocks.Block;

import java.util.ArrayList;

public class Chunk {

    private int chunkX;
    private int chunkY;

    private ArrayList<Block> blocks = new ArrayList<>();


    public Chunk(int x, int y){
        this.chunkX = x;
        this.chunkY = y;
    }


    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void putBlock(Block block){
        blocks.add(block);
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    public void setChunkY(int chunkY) {
        this.chunkY = chunkY;
    }

}
