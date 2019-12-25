package assets.entities.collisions;

import org.lwjgl.util.vector.Vector3f;
import tools.Maths;

public class AABB {

    private float xMax;
    private float xMin;
    private float zMax;
    private float zMin;
    private float yMax;
    private float yMin;
    private Vector3f maxCorner;
    private Vector3f minCorner;

    ///for when we have all 6 values
    public AABB(float xMax, float xMin, float zMax, float zMin, float yMax, float yMin) {
        this.xMax = xMax;
        this.xMin = xMin;
        this.zMax = zMax;
        this.zMin = zMin;
        this.yMax = yMax;
        this.yMin = yMin;
        maxCorner = new Vector3f(xMax, yMax, zMax);
        minCorner = new Vector3f(xMin, yMin, zMin);
    }

    ///for cubes
    public AABB(float xMin, float yMin, float zMin, float sideLength){
        this.xMin = xMin;
        this.yMin = yMin;
        this.zMin = zMin;
        this.xMax = xMin + sideLength;
        this.zMax = zMin + sideLength;
        this.yMax = yMin + sideLength;
        maxCorner = new Vector3f(xMax, yMax, zMax);
        minCorner = new Vector3f(xMin, yMin, zMin);
    }

    ///for when we have centre origin at the base of the entity (e.g. player)
    public AABB(float xCenter, float yBase, float zCenter, float width, float height){
        this.xMin = xCenter - (width/2);
        this.xMax = xMin + width;
        this.zMin = zCenter - (width/2);
        this.zMax = zMin + width;
        this.yMin = yBase;
        this.yMax = yBase + height;
        maxCorner = new Vector3f(xMax, yMax, zMax);
        minCorner = new Vector3f(xMin, yMin, zMin);
    }

    public boolean isBoxColliding(AABB otherBox){
        float oxMax = otherBox.getMaxCorner().x;
        float oxMin = otherBox.getMinCorner().x;
        float oyMax = otherBox.getMaxCorner().y;
        float oyMin = otherBox.getMinCorner().y;
        float ozMax = otherBox.getMaxCorner().z;
        float ozMin = otherBox.getMinCorner().z;
        boolean xBound = Maths.isNumBetween(xMin, xMax, oxMax) || Maths.isNumBetween(xMin, xMax, oxMin);
        if(!xBound){return false;}
        boolean yBound = Maths.isNumBetween(yMin, yMax, oyMax) || Maths.isNumBetween(yMin, yMax, oyMin);
        if(!yBound){return false;}
        boolean zBound = Maths.isNumBetween(zMin, zMax, ozMax) || Maths.isNumBetween(zMin, zMax, ozMin);

        return xBound && yBound && zBound;
    }

    public boolean isPointColliding(Vector3f point){
        boolean xBound = Maths.isNumBetween(xMin, xMax, point.x);
        if(!xBound){return false;}
        boolean yBound = Maths.isNumBetween(yMin, yMax, point.y);
        if(!yBound){return false;}
        boolean zBound = Maths.isNumBetween(zMin, zMax, point.z);

        return xBound && yBound && zBound;
    }

    public boolean isXColliding(float xToCheck){
        return xMin <= xToCheck && xMax >= xToCheck;
    }

    public boolean isYColliding(float yToCheck){
        return yMin <= yToCheck && yMax >= yToCheck;
    }

    public boolean isZColliding(float zToCheck){
        return zMin <= zToCheck && zMax >= zToCheck;
    }

    ///UPDATE BOUNDING BOX WHENEVER THE COLLISION BOX NEEDS TO MOVE (e.g. entity moved)
    public void updateBB(float dx, float dy, float dz){
        xMax += dx;
        xMin += dx;
        yMax += dy;
        yMin += dy;
        zMax += dz;
        zMin += dz;
        maxCorner = new Vector3f(xMax, yMax, zMax);
        minCorner = new Vector3f(xMin, yMin, zMin);
    }

    public Vector3f getMaxCorner() {
        return maxCorner;
    }
    public Vector3f getMinCorner() {
        return minCorner;
    }

}
