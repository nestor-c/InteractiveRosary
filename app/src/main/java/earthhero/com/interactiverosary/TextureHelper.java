package earthhero.com.interactiverosary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES20.glTexParameteri;


public class TextureHelper {
        private static final String TAG = "TextureHelper";

        //Method for loading texture
        public static int loadTexture(Context context, int resourceID){
            //create a single item array for storing the textureID or lackthereof represented by a 0
            final int textureObjectIds[] = new int[1];
            //Generate a texture object
            glGenTextures(1, textureObjectIds, 0);
            //catch if there is an error creating the texture object
            if(textureObjectIds[0] == 0) {
                if (LoggerConfig.ON){
                    Log.w(TAG,"Could not generate a new OpenGL texture object.");
                }
                return 0;
            }
            //Set Bitmap option "inscaled" to false to make use of original image and not a scaled version of it
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            //Decode the compressed image using the options we set.
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);
            //Catch if the image couldn't be decoded
            if (bitmap == null) {
                if (LoggerConfig.ON){
                    Log.w(TAG, "Resource ID " + resourceID + " could not be decoded");
                }
                glDeleteTextures(1, textureObjectIds, 0);
                return 0;
            }
            //Bind newly generated texture to OpenGL.
            glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);
            //Sets the texture parameters. First instance is for minification while the second
            //instance is for magnification. For minification we use bilinear filtering with mipmapping.
            //Magnification only uses bilinear filtering.
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            //Load the actual bitmap into OpenGL using the texture currently bound.
            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
            //Bitmap isn't needed after being copied to bound texture so delete right away.
            bitmap.recycle();
            //Generates the mipmap for minification.
            glGenerateMipmap(GL_TEXTURE_2D);
            //Unbind from texture to prevent further changes.
            glBindTexture(GL_TEXTURE_2D,0);
            return textureObjectIds[0];
        }
}
