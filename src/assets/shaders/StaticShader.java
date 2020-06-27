package assets.shaders;

import assets.entities.Camera;
import assets.entities.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tools.Maths;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/assets/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/assets/shaders/fragmentShader.txt";

    private int location_transformMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_ambientLightFactor;
    private int location_directionalLight;
    private int location_skyColor;
    private int location_fogDensity;
    private int location_fogGradient;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformMatrix = super.getUniformLocation("transformMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_ambientLightFactor = super.getUniformLocation("ambientLightFactor");
        location_directionalLight = super.getUniformLocation("directionalLight");
        location_skyColor = super.getUniformLocation("skyColor");
        location_fogDensity = super.getUniformLocation("fogDensity");
        location_fogGradient = super.getUniformLocation("fogGradient");
    }

    public void loadFog(float sky_r, float sky_g, float sky_b, float fogDensity, float fogGradient){
        super.loadVector(location_skyColor, new Vector3f(sky_r,sky_g,sky_b));
        super.loadFloat(location_fogDensity, fogDensity);
        super.loadFloat(location_fogGradient, fogGradient);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadTransformMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformMatrix, matrix);
    }

    public Matrix4f loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);

        return viewMatrix;
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadLight(Light light){
        super.loadBoolean(location_directionalLight, light.isDirectional());
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
        super.loadFloat(location_ambientLightFactor, light.getAmbientLight()); //minimum brightness of everything
    }


}
