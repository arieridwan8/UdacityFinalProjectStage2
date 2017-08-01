package id.arieridwan.jagatcinema.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by arieridwan on 01/08/2017.
 */

public class ViewHelper {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
}
