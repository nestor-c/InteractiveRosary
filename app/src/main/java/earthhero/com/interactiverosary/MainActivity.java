package earthhero.com.interactiverosary;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));

        if(supportsEs2){
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new FirstOpenGLProjectRenderer(this));
            rendererSet = true;
        }else{
            Toast.makeText(this, "This device does not support OpenGL ES 2.0", Toast.LENGTH_LONG).show();
            return;
        }





        setContentView(R.layout.activity_main);
        final Button btn_Start = (Button) findViewById(R.id.BTN_Start);
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPrayerFragment();
                btn_Start.setVisibility(View.GONE);
            }
        });
    }
    public void startPrayerFragment() {
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        final Fragment vpf = new ViewPagerFragment();
        String TAG = "VPF";
        ft.add(R.id.mainLayout, vpf, TAG);
        ft.addToBackStack(TAG);
        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onResume(){
        super.onResume();
        invalidateOptionsMenu();
    }
}








