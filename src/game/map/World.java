package game.map;

public class World {

    public static final int WORLD_SIZE_X = 2;
    public static final int WORLD_SIZE_Y = 2;
    //cube size if using cubes
    public static final int CUBE_SIZE = 4;

    public int[][] levelData;

    private Chunk[][] chunks = new Chunk[WORLD_SIZE_X][WORLD_SIZE_Y];

    public void putChunk(Chunk chunk){
        chunks[chunk.getChunkX()][chunk.getChunkY()] = chunk;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

}
