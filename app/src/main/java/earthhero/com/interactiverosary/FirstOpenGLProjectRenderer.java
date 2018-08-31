package earthhero.com.interactiverosary;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.orthoM;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class FirstOpenGLProjectRenderer implements Renderer {
    private final Context context;
    private int program;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private int POSITION_COMPONENT_COUNT = 3;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer positions;
    private static final String U_MATRIX = "u_Matrix";
    private final float[] projectionMatrix = new float[16];
    private int uMatrixLocation;

    FirstOpenGLProjectRenderer(Context context){
        this.context = context;
        ObjLoader objLoader = new ObjLoader(context, "Rosary.obj");
        int numFaces = objLoader.numFaces;
        int mBytesPerFloat = 4;
        // Initialize the buffers.
        positions = ByteBuffer.allocateDirect(objLoader.positions.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        positions.put(objLoader.positions).position(0);
        FloatBuffer normals = ByteBuffer.allocateDirect(objLoader.normals.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        normals.put(objLoader.normals).position(0);
        FloatBuffer textureCoordinates = ByteBuffer.allocateDirect(objLoader.textureCoordinates.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates.put(objLoader.textureCoordinates).position(0);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f,0.0f,0.0f, 1.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader,fragmentShader);

        if (LoggerConfig.ON) { ShaderHelper.validateProgram(program); }

        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        positions.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, positions);
        glEnableVertexAttribArray(aPositionLocation);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        ObjLoader objLoader = new ObjLoader(context, "Rosary.obj");
        int numFaces = objLoader.numFaces;
        glClear(GL_COLOR_BUFFER_BIT);
        glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, numFaces);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        final float aspectRatio = width > height ? (float) width / (float) height : (float) height / (float) width;
        if (width > height) { // Landscape
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f); }
        else {
            // Portrait or square
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }
}
