package game.map;

public class World {

    public final int WORLD_X = 64;
    public final int WORLD_Y = 64;

    private Chunk[][] chunks = new Chunk[WORLD_X][WORLD_Y];

    public void putChunk(Chunk chunk){
        chunks[chunk.getChunkX()][chunk.getChunkY()] = chunk;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

}
