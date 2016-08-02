package cc.ddhub.cclocker.util;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import cc.ddhub.cclocker.LockerApp;

/**
 * Created by denzelw on 16/7/28.
 */
public class DimenUtil {
    private static DisplayMetrics sMetrics;

    private static void checkMetrics() {
        if (sMetrics == null) {
            sMetrics = LockerApp.getApp().getResources().getDisplayMetrics();
        }
    }

    public static float dp2px(float dp) {
        checkMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, sMetrics);
    }

}
