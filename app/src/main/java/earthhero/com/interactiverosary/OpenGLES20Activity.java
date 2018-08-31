package earthhero.com.interactiverosary;

import android.app.Activity;
import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLES20Activity extends Activity {
    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    class MyGLSurfaceView extends GLSurfaceView{
        private final FirstOpenGLProjectRenderer mRenderer;
        public MyGLSurfaceView(Context context){
            super(context);
            setEGLContextClientVersion(2);
            mRenderer = new FirstOpenGLProjectRenderer(context);
            setRenderer(mRenderer);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }


}