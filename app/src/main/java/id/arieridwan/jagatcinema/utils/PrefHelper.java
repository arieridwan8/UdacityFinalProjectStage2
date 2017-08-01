package id.arieridwan.jagatcinema.utils;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

public class PrefHelper {

    public static boolean getStateChecking(Context context, String title){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.IS_FAVOURITE,MODE_PRIVATE);
        boolean isChecked = sharedPreferences.getBoolean(title, false);
        return isChecked;
    }

    public static void setStateChecking(Context context, String title, boolean isChecked){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.IS_FAVOURITE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(title, isChecked);
        editor.commit();
    }

}
