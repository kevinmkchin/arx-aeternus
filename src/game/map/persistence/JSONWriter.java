package game.map.persistence;

import assets.entities.blocks.Block;
import game.map.Chunk;
import game.map.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {


    public static void writeWorldToSave(String saveFilePath, World world) {

        JSONObject master = new JSONObject();
        JSONArray chunkArray = new JSONArray();

        for (int i = 0; i<world.WORLD_SIZE_X; i++) {
            for(int j = 0; j<world.WORLD_SIZE_Y; j++) {
                Chunk c = world.getChunks()[i][j];

                JSONObject chunk = new JSONObject();
                chunk.put("chunkX", c.getChunkX());
                chunk.put("chunkY", c.getChunkY());

                JSONArray blockArray = new JSONArray();
                for (Block b : c.getBlocks()) {
                    JSONObject block = new JSONObject();
                    block.put("x", b.getX());
                    block.put("y", b.getY());
                    block.put("z", b.getZ());
                    block.put("type", b.getType());

//                if(b.getType().equals(Block.Type.CHEST)){
//                    //then store more stuff into the json
//                }

                    blockArray.put(block);
                }
                chunk.put("blocks", blockArray);

                chunkArray.put(chunk);
            }
        }

        master.put("chunks", chunkArray);

        try (FileWriter file = new FileWriter(saveFilePath)) {
            file.write(master.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
