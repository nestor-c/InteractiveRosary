package earthhero.com.interactiverosary;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nestorcortes on 3/10/18.
 */

public class Counter {
    static private int COUNT;

    public Counter() {
        COUNT=0;
    }
    public Counter(int i) {
        COUNT=i;
    }
    public void dec(){
        if (COUNT > 0) COUNT -=1;
    }
    public void inc(){ COUNT +=1; }
    public int getCount(){
        return COUNT;
    }
    public static String getStringCount(){
        return Integer.toString(COUNT);
    }
    public void reset(){ COUNT=0;};
    public void setCount(int count){
        COUNT = count;
    }

    /*public void checkPosition(int pos){

    }*/
}
