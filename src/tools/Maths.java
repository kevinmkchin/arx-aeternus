package tools;

import assets.entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Maths {

    // CREATE TRANSFORM MATRIX
    public static Matrix4f createTransformMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

        return matrix;
    }

    // CREATE VIEW MATRIX
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f oppositeOfCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(oppositeOfCameraPos, viewMatrix, viewMatrix);

        return viewMatrix;
    }

    // MULTIPLY TRANSFORMATION MATRIX WITH POSITION VECTOR
    public static Vector3f applyTransformationMatrix(Vector3f initialPosition, Matrix4f transformMatrix){
        Vector4f position4d = new Vector4f(initialPosition.x, initialPosition.y, initialPosition.z, 1);
        float x = transformMatrix.m00 * position4d.x
                + transformMatrix.m10 * position4d.y
                + transformMatrix.m20 * position4d.z
                + transformMatrix.m30 * position4d.w;
        float y = transformMatrix.m01 * position4d.x
                + transformMatrix.m11 * position4d.y
                + transformMatrix.m21 * position4d.z
                + transformMatrix.m31 * position4d.w;
        float z = transformMatrix.m02 * position4d.x
                + transformMatrix.m12 * position4d.y
                + transformMatrix.m22 * position4d.z
                + transformMatrix.m32 * position4d.w;
        Vector3f newPosition = new Vector3f(x,y,z);

        return newPosition;
    }

    // CHECK IF NUM IS MIN <= NUM <= MAX
    public static boolean isNumBetween(float min, float max, float num){
        return min <= num && num <= max;
    }

    /// returns 1 or -1
    public static int getSign(float n){
        int sign = (int) (n / Math.abs(n));
        if(Math.abs(sign) != 1){
            return 1;
        }else{
            return sign;
        }
    }

}
