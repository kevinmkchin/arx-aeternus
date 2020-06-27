package game.scene;

import assets.AMGUI;
import assets.entities.Entity;
import assets.entities.blocks.Block;
import assets.models.TexturedModel;
import game.AMGameplayStatics;
import game.map.Chunk;
import game.map.World;
import game.map.generation.WorldGenerator;
import game.player.Player;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import tools.AMVector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameScene extends Scene {

    //Super has AMGameplayStatics *** Use this for everything
    //Super has ModelRenderer
    //Super has Light

    private int drawDistance = 2; //in chunks

    public GameScene(){
        initScene();
        setCamera();
        setLighting(1,4,1.3f,1,1,1,0.5f,true);

        SpawnEverything();

    }

    @Override
    protected void loadContent() {
        //Load TexturedModels
        HashMap<Block.Type, TexturedModel> loadedModels = new HashMap<>();

        //Load Textures to Models
        loadedModels.put(Block.Type.GRASS, makeBlockModel("hexfloor"));
        loadedModels.put(Block.Type.CONCRETE, makeBlockModel("darkwall"));
        AMGUI crosshair = new AMGUI(modelLoader.loadGUI("crosshair"), new Vector2f(0,0), new Vector2f(0.018f, 0.032f));
        GameplayStatics.addGlobalGui(crosshair);

        //Generate the world
        GameplayStatics.setGlobalWorld(new WorldGenerator(loadedModels).generateNewWorld());
    }

    private void SpawnEverything()
    {
        //Get floor tiles
        ArrayList<AMVector2> FloorTiles =  new ArrayList<>();
        World CurrWorld = GameplayStatics.getGlobalWorld();
        for(int i = 0; i < CurrWorld.levelData.length; ++i)
        {
            for(int j = 0; j < CurrWorld.levelData[0].length; ++j)
            {
                if(CurrWorld.levelData[i][j] == 0)
                {
                    FloorTiles.add(new AMVector2((float) i, (float) j));
                }
            }
        }

        //Spawn the player
        AMVector2 Spawn = FloorTiles.get(new Random().nextInt(FloorTiles.size()));
        //GameplayStatics.setGlobalPlayer(new Player(Spawn.x * World.CUBE_SIZE + 0.5f,6.f,Spawn.y * World.CUBE_SIZE + 0.5f, GameplayStatics));
        GameplayStatics.setGlobalPlayer(new Player(2,2,2, GameplayStatics));

        GameplayStatics.GetEntitiesWithTag(AMGameplayStatics.Tags.ENEMIES).add(new Block(Block.Type.LEAVES, makeBlockModel("leaves"),
                0, 0, 0));
    }

    @Override
    public void updateScene() {
        //UPDATE/TICK THE PLAYER
        GameplayStatics.getGlobalPlayer().update();

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            System.exit(0);
        }

        //Process and Render the WORLD
        processWorld();

        //Process enemies
        for(Entity e : GameplayStatics.GetEntitiesWithTag(AMGameplayStatics.Tags.ENEMIES))
        {
            GameplayStatics.getGlobalMainRenderer().processEntity(e);
        }
    }

    private void processWorld(){
        int currentCamX = ((int) GameplayStatics.getGlobalPlayer().getxPos()) / (Chunk.CHUNK_SIZE_X * World.CUBE_SIZE);
        int currentCamY = ((int) GameplayStatics.getGlobalPlayer().getzPos()) / (Chunk.CHUNK_SIZE_Y * World.CUBE_SIZE);
        int xLowerBound = Math.max(currentCamX - drawDistance, 0);
        int xUpperBound = Math.min(currentCamX + drawDistance, World.WORLD_SIZE_X -1);
        int yLowerBound = Math.max(currentCamY - drawDistance, 0);
        int yUpperBound = Math.min(currentCamY + drawDistance, World.WORLD_SIZE_Y -1);
        for(int i =xLowerBound; i <= xUpperBound; ++i){
            for(int j=yLowerBound; j<= yUpperBound; ++j){
                for(Entity e : GameplayStatics.getGlobalWorld().getChunks()[i][j].getBlocks()){
                    GameplayStatics.getGlobalMainRenderer().processEntity(e);
                }
            }
        }
    }


}
