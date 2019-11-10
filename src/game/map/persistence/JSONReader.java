package game.map.persistence;

import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.map.Chunk;
import game.map.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JSONReader {

    HashMap<Block.Type, TexturedModel> loadedModels;

    public JSONReader(HashMap<Block.Type, TexturedModel> loadedModels){
        this.loadedModels = loadedModels;
    }

    public World loadWorld(String saveFilePath){
        World world = new World();

        String jsonString = readJSONString(saveFilePath);
        JSONObject master = new JSONObject(jsonString);
        JSONArray chunks = master.getJSONArray("chunks");

        for(Object c : chunks){
            JSONObject chunk = (JSONObject) c;
            int cx = (int) chunk.get("chunkX");
            int cy = (int) chunk.get("chunkY");
            Chunk chunkToAdd = new Chunk(cx, cy);
            loadBlocksToChunk(chunkToAdd, chunk.getJSONArray("blocks"));
            world.putChunk(chunkToAdd);
        }

        return world;
    }

    private void loadBlocksToChunk(Chunk chunk, JSONArray blocks){
        for(Object b : blocks){
            JSONObject block = (JSONObject) b;

            Block.Type type = Block.Type.valueOf((String) block.get("type"));
            int x = (int) block.get("x");
            int y = (int) block.get("y");
            int z = (int) block.get("z");

            Block blockToAdd = new Block(type,loadedModels.get(type),x,y,z);

            chunk.putBlock(blockToAdd);
        }
    }

    private String readJSONString(String saveFilePath){

        String finalString = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFilePath));
            String line = reader.readLine();
            while (line != null) {
                finalString += line;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return finalString;
        }

    }

}
