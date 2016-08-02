package cc.ddhub.cclocker;

import android.app.Application;
import android.content.Context;

/**
 * Created by denzelw on 16/7/26.
 */
public class LockerApp extends Application{

    private static LockerApp sApp;

    public static LockerApp getApp() {
        return sApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sApp = this;
    }
}
