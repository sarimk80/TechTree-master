package mk.techtree.utils.utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mk.techtree.R;


public class Utils {


    /**
     * Any Text Utility start
     */
    private static Map<String, Typeface> typefaceCache = new HashMap<String, Typeface>();

//    public static void setTypeface(AttributeSet attrs, TextView textView) {
//        Context context = textView.getContext();
//
//        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.AnyTextView);
//        String typefaceName = values.getString(R.styleable.AnyTextView_typeface);
//
//        if (typefaceCache.containsKey(typefaceName)) {
//            textView.setTypeface(typefaceCache.get(typefaceName));
//        } else {
//            Typeface typeface;
//            try {
//                typeface = Typeface.createFromAsset(textView.getContext().getAssets(), context.getString(R.string.assets_fonts_folder) + typefaceName);
//            } catch (Exception e) {
//                Log.w(context.getString(R.string.app_name), String.format(context.getString(R.string.typeface_not_found), typefaceName));
//                return;
//            }
//
//            typefaceCache.put(typefaceName, typeface);
//            textView.setTypeface(typeface);
//        }
//
//        values.recycle();
//    }
//
//

    // **** END   **** //


    public static Bitmap loadBitmapFromFile(String mBackgroundFilename) {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inPurgeable = true;
        localOptions.inInputShareable = true;
        return BitmapFactory.decodeFile(mBackgroundFilename, localOptions);
    }


    private static File getCacheDir(Context context, String dirName) {
        return new File(context.getCacheDir(), dirName);
    }

    //http://stackoverflow.com/a/9596132/1121509
    public static Bitmap drawViewToBitmap(View view, int color) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(color);
        view.draw(canvas);
        return returnedBitmap;
    }

    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }
}
