package game.scene;

import assets.entities.Entity;
import assets.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestScene extends Scene {

    List<Entity> blocks = new ArrayList<>();

    public TestScene(){
        initScene();
        setCamera();
        setLighting(1,2,1.3f, 1,1,1, 0.2f, true);
    }

    @Override
    protected void loadContent() {
        TexturedModel texturedModel = makeBlockModel("acacia");
        TexturedModel grass = makeBlockModel("grass");

        for(int i=0; i<64; i++){
            for(int j=0; j<64; j++) {
                blocks.add(new Entity(texturedModel, new Vector3f(i,0,j),0,0,0,1));
                if(new Random().nextInt(2) == 1) {
                    blocks.add(new Entity(grass, new Vector3f(i, 1, j), 0, 0, 0, 1));
                }
            }
        }
    }

    @Override
    public void updateScene() {
        camera.update();

        for(Entity block : blocks){
            renderer.processEntity(block);
        }

    }

}
