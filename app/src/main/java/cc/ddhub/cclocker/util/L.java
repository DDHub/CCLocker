package cc.ddhub.cclocker.util;

import android.util.Log;

import cc.ddhub.cclocker.BuildConfig;

/**
 * Created by denzelw on 16/7/26.
 */
public class L {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String tag, String log) {
        if (DEBUG) {
            Log.d(tag, log);
        }
    }

}
