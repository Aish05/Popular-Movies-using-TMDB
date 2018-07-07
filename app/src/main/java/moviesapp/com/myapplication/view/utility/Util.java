package moviesapp.com.myapplication.view.utility;

import android.content.Context;
import android.net.ConnectivityManager;

public class Util {
    public static boolean isOnline(Context mContext)
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static void saveStringDataInPref(Context paramContext, String paramString1, String paramString2)
    {
        paramContext.getSharedPreferences(paramContext.getPackageName(), 0).edit().putString(paramString1, paramString2).apply();
    }


    public static void saveBooleanDataInPref(Context paramContext, String paramString, boolean paramBoolean)
    {
        paramContext.getSharedPreferences(paramContext.getPackageName(), 0).edit().putBoolean(paramString, paramBoolean).apply();
    }

    public static boolean getSavedBooleanDataFromPref(Context paramContext, String paramString)
    {
        return paramContext.getSharedPreferences(paramContext.getPackageName(), 0).getBoolean(paramString, false);
    }

    public static String getSavedStringOnlyDataFromPrefForData(Context paramContext, String paramString)
    {
        return paramContext.getSharedPreferences(paramContext.getPackageName(), 0).getString(paramString, null);
    }


}
