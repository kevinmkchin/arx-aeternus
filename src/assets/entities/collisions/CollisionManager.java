package assets.entities.collisions;

import assets.entities.Entity;
import assets.entities.blocks.Block;
import assets.helpers.Plane;
import game.map.Chunk;
import game.map.World;

import java.util.ArrayList;

public class CollisionManager {

    private static int colCheckDist = 3;

    /*** COLLISION FOR ENTITIES WITH ORIGIN IN THE CENTER OF THEIR AABB ***
     *
     * RETURNS the entity that we will collide with. Null if we will not collide with any.
     *
     * REQUIRES the origin must be in the middle of AABB
     * (e.g. if AABB is x bound from 0 to 1, then xPos must be 0.5)
     * World is the world we are checking collision in.
     * xPos, yPos, zPos are x y z of the entity before movement.
     * Plane is the plane (x or y or z) that we are checking collision in.
     * add is whether we are adding or subtracting the delta.
     * delta is the displacement the entity is trying to move.
     * width, height are the width of the entity AABB and the height of the entity AABB
     * */
    public static Entity checkCollision(World world,
                         float xPos, float yPos, float zPos,
                         Plane plane,
                         boolean add,
                         float delta,
                         float width, float height){

        ArrayList<Block> blocksToCheck = new ArrayList<>();
        int chunkX = (int) xPos / World.WORLD_X;
        int chunkY = (int) zPos / World.WORLD_Y;

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                try {
                    Chunk currentChunk = world.getChunks()[chunkX + i][chunkY + j];
                    for (Block b : currentChunk.getBlocks()) {
                        if (Math.abs(b.getX() - xPos) <= colCheckDist
                                && Math.abs(b.getY() - yPos) <= colCheckDist
                                && Math.abs(b.getZ() - zPos) <= colCheckDist) {
                            blocksToCheck.add(b);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }

        Entity badEntity = null; //entity to return. should not be null if collided.

        switch(plane){
            case X:
                float xToCheck;
                if(add){
                    xToCheck = xPos + delta;
                }else{
                    xToCheck = xPos - delta;
                }
                AABB temp1 = new AABB(xToCheck, yPos + 0.1f, zPos, width, height - 0.2f);
                for(Block b : blocksToCheck){
                    if(b.getAABB().isBoxColliding(temp1)){
                        badEntity = b;
                        break;
                    }
                }

                break;
            case Y:
                float yToCheck;
                if(add){
                    yToCheck = yPos + delta;
                }else{
                    yToCheck = yPos - delta;
                }
                AABB tempY = new AABB(xPos, yToCheck, zPos, width, height);
                for(Block b : blocksToCheck){
                    if(b.getAABB().isBoxColliding(tempY)){
                        badEntity = b;
                        break;
                    }
                }
                break;
            case Z:
                float zToCheck;
                if(add){
                    zToCheck = zPos + delta;
                }else{
                    zToCheck = zPos - delta;
                }
                AABB temp2 = new AABB(xPos, yPos + 0.1f, zToCheck, width, height - 0.2f);
                for(Block b : blocksToCheck){
                    if(b.getAABB().isBoxColliding(temp2)){
                        badEntity = b;
                        break;
                    }
                }
                break;
        }

        return badEntity;
    }

}
