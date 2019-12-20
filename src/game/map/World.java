package game.map;

public class World {

    public static final int WORLD_X = 16;
    public static final int WORLD_Y = 16;

    private Chunk[][] chunks = new Chunk[WORLD_X][WORLD_Y];

    public void putChunk(Chunk chunk){
        chunks[chunk.getChunkX()][chunk.getChunkY()] = chunk;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

}
