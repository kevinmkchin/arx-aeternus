package game.map;

public class World {

    public static final int WORLD_SIZE_X = 3;//24;
    public static final int WORLD_SIZE_Y = 3;//24;

    private Chunk[][] chunks = new Chunk[WORLD_SIZE_X][WORLD_SIZE_Y];

    public void putChunk(Chunk chunk){
        chunks[chunk.getChunkX()][chunk.getChunkY()] = chunk;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

}
